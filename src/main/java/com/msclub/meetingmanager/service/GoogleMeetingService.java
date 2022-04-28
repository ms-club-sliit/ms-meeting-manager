package com.msclub.meetingmanager.service;

import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.CalendarScopes;
import com.google.api.services.calendar.model.*;
import com.msclub.meetingmanager.model.google.AuthorizationFlow;
import com.msclub.meetingmanager.model.google.AuthorizationToken;
import com.msclub.meetingmanager.model.google.GoogleMeeting;
import com.msclub.meetingmanager.model.google.MeetingType;

import java.io.*;
import java.util.*;

/*
* This is the Google Meetings Service.
* Note -
*   This requires the credentials.json file as a base64 encoded string.
*   Set it to "GoogleCredentialsJSON" Environment variable.
*
*   (This step is not needed if "GoogleRefreshToken" is set, But, if not set, /requestAuthorization won't work).
*   And to authorize an account, go to the "/requestAuthorization" endpoint in a browser
*   For this to work, set "GoogleRedirectURIHost" so google knows which url to redirect when authorization.
*   For example - GoogleRedirectURIHost=http://localhost:8080
*
* */
public class GoogleMeetingService {
    private static final String APPLICATION_NAME = "Google Calender API";
    private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();
    private static final List<String> SCOPES = Arrays.asList(CalendarScopes.CALENDAR);
    private static NetHttpTransport HTTP_TRANSPORT = null;
    private static GoogleClientSecrets CLIENT_SECRETS = null;
    private static HashMap<String,AuthorizationFlow> flowMap = new HashMap<>();
    private static Timer timer = new Timer("Timer");
    private static AuthorizationToken token = null;

    public static void init() throws Exception {
        HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();

        // Load base64 encoded client secrets.
        String credentialsJSON = System.getenv("GoogleCredentialsJSON");

        if(credentialsJSON.trim().length() == 0){
            throw new Exception("GoogleCredentialsJSON is not set");
        }

        byte[] credentailsJSON = Base64.getDecoder().decode(credentialsJSON);

        InputStream in = new ByteArrayInputStream(credentailsJSON);

        if (in == null) {
            throw new Exception("JSON decoding error");
        }

        CLIENT_SECRETS = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));

        // Try to get existing refresh token from environment
        String refreshToken = System.getenv("GoogleRefreshToken");

        if(refreshToken.trim().length() > 0){
            token = new AuthorizationToken(refreshToken, HTTP_TRANSPORT,CLIENT_SECRETS);
        }else{
            throw new Exception("GoogleRefreshToken is not set");
        }
    }

    public static AuthorizationFlow startAuthorization(String redirectURI, String uuid) throws IOException {
        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                HTTP_TRANSPORT, JSON_FACTORY, CLIENT_SECRETS, SCOPES)
                .setAccessType("offline")
                .build();

        AuthorizationFlow authFlow = new AuthorizationFlow(flow, redirectURI, uuid);

        flowMap.put(uuid, authFlow);

        // Schedule a timer so, it will time out and delete the flow
        TimerTask task = new TimerTask() {
            public void run() {
                flowMap.remove(uuid);
            }
        };

        timer.schedule(task, 300000);
        return authFlow;
    }

    public static String authorize(String uuid, String code) throws Exception {
        AuthorizationFlow auth = flowMap.get(uuid);

        if(auth == null){
            throw new Exception("Authorization Failed");
        }

        GoogleTokenResponse response = (GoogleTokenResponse) auth.getFlow().newTokenRequest(code).setRedirectUri(auth.getRedirectURI()).execute();
        flowMap.remove(uuid);

        token = new AuthorizationToken(response.getAccessToken(), response.getRefreshToken(), HTTP_TRANSPORT, CLIENT_SECRETS);

        return response.getRefreshToken();
    }

    public static Calendar getCalenderService() throws IOException {
        return new Calendar.Builder(HTTP_TRANSPORT, JSON_FACTORY, token.getCredential())
                .setApplicationName(APPLICATION_NAME).build();
    }

    public static GoogleMeeting schedule(GoogleMeeting meetingDetails, MeetingType type) throws IOException {
        Calendar service = GoogleMeetingService.getCalenderService();

        switch (type){
            case INTERVIEW:
                meetingDetails.setMeetingName("[MS Club of SLIIT - Interview] " + meetingDetails.getMeetingName());
                break;
        }

        Event event = meetingDetails.toEvent();

        ConferenceSolutionKey conferenceSKey = new ConferenceSolutionKey();
        conferenceSKey.setType("hangoutsMeet");

        CreateConferenceRequest createConferenceReq = new CreateConferenceRequest();
        createConferenceReq.setConferenceSolutionKey(conferenceSKey);
        createConferenceReq.setRequestId("dsfasfs");

        EntryPoint entryPoint = new EntryPoint();
        entryPoint.setEntryPointType("video");

        List<EntryPoint> entryPoints = new ArrayList<>();
        entryPoints.add(entryPoint);

        ConferenceData conferenceData = new ConferenceData();
        conferenceData.setCreateRequest(createConferenceReq);
        conferenceData.setEntryPoints(entryPoints);

        event.setConferenceData(conferenceData);

        Event createdEvent = service.events()
                .insert("primary", event)
                .setConferenceDataVersion(1)
                .execute();
        return GoogleMeeting.FromEvent(createdEvent);
    }
}
