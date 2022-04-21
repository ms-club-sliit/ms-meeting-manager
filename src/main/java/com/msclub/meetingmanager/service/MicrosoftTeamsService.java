package com.msclub.meetingmanager.service;

import com.google.gson.Gson;
import com.msclub.meetingmanager.model.microsoft.*;
import com.msclub.meetingmanager.model.microsoft.microsoftteamsmeet.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;

@Service
public class MicrosoftTeamsService {

    @Autowired
    private MicrosoftCredentials microsoftCredentials;

    static RestTemplate restTemplate = new RestTemplate();

    public ResponseEntity<?> scheduleMicrosoftMeeting(MeetingDetails meetingDetails, MicrosoftTeamsMeetingType type) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("client_id", microsoftCredentials.getClientId());
        map.add("client_secret", microsoftCredentials.getClientSecret());
        map.add("grant_type", microsoftCredentials.getGrantType());
        map.add("scope", microsoftCredentials.getScope());
        map.add("refresh_token", microsoftCredentials.getRefreshToken());

        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(map, headers);

        ResponseEntity<MicrosoftTokenApiResponse> response =
                restTemplate.exchange(microsoftCredentials.getAuthTokenUri(),
                        HttpMethod.POST,
                        entity,
                        MicrosoftTokenApiResponse.class);
        if (response.getStatusCode() == HttpStatus.OK) {
            return new ResponseEntity<>(createMicrosoftMeeting(response.getBody().getAccess_token(), meetingDetails, type), HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>("Something went wrong", HttpStatus.BAD_REQUEST);
        }

    }

    public ResponseEntity<?> createMicrosoftMeeting(String accessToken, MeetingDetails meetingDetails, MicrosoftTeamsMeetingType type) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + accessToken);

        Body body = new Body("HTML", "Thank you for applying with MS Club SLIIT. We would like to invite you for the next step of your application. Does this time work for you?");
        Start start = new Start(meetingDetails.getStartDateTime(), "India Standard Time");
        End end = new End(meetingDetails.getEndDateTime(), "India Standard Time");
        Location location = new Location("MS Club Conference Room");

        String[] emailList = meetingDetails.getEmailList();
        EmailAddress emailAddress;
        Attendee attendee;

        ArrayList<Attendee> attendees = new ArrayList<>();

        for (String email : emailList) {
            emailAddress = new EmailAddress(email);
            attendee = new Attendee(emailAddress, "required");
            attendees.add(attendee);
        }

        // create request body
        MicrosoftTeamsMeetingDetails microsoftTeamsMeetingDetails = new MicrosoftTeamsMeetingDetails();

        switch (type) {
            case INTERVIEW:

                microsoftTeamsMeetingDetails.setSubject("MS Club of SLIIT - Interview " + meetingDetails.getMeetingName());
                break;
            case INTERNAL_MEETING:

                microsoftTeamsMeetingDetails.setSubject(meetingDetails.getMeetingName());
                break;
        }

        microsoftTeamsMeetingDetails.setBody(body);
        microsoftTeamsMeetingDetails.setStart(start);
        microsoftTeamsMeetingDetails.setEnd(end);
        microsoftTeamsMeetingDetails.setLocation(location);
        microsoftTeamsMeetingDetails.setAttendees(attendees);
        microsoftTeamsMeetingDetails.setAllowNewTimeProposals(true);
        microsoftTeamsMeetingDetails.setOnlineMeeting(true);
        microsoftTeamsMeetingDetails.setOnlineMeetingProvider("teamsForBusiness");

        HttpEntity<String> entity =
                new HttpEntity<String>(new Gson().toJson(microsoftTeamsMeetingDetails), headers);

        ResponseEntity<MSMeetingResponse> response =
                restTemplate.exchange(microsoftCredentials.getTeamsMeetingCreateUrl(),
                        HttpMethod.POST,
                        entity,
                        MSMeetingResponse.class);

        if (response.getStatusCode() == HttpStatus.CREATED) {
            return new ResponseEntity<>(response.getBody(), HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>("Something went wrong", HttpStatus.BAD_REQUEST);
        }
    }

    public String getAccessToken() {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("client_id", microsoftCredentials.getClientId());
        map.add("client_secret", microsoftCredentials.getClientSecret());
        map.add("grant_type", microsoftCredentials.getGrantType());
        map.add("scope", microsoftCredentials.getScope());
        map.add("refresh_token", microsoftCredentials.getRefreshToken());

        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(map, headers);

        ResponseEntity<MicrosoftTokenApiResponse> response =
                restTemplate.exchange(microsoftCredentials.getAuthTokenUri(),
                        HttpMethod.POST,
                        entity,
                        MicrosoftTokenApiResponse.class);

        if (response.getStatusCode() == HttpStatus.OK) {
            return response.getBody().getAccess_token();
        } else {
            return "Something went wrong";
        }

    }

    public ResponseEntity<String> deleteScheduleMeeting(String meetingId) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + getAccessToken());

        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<String> response =
                restTemplate.exchange("https://graph.microsoft.com/v1.0/me/events/"+meetingId,
                        HttpMethod.DELETE,
                        entity,
                        String.class);

        if (response.getStatusCode() == HttpStatus.NO_CONTENT) {
            return new ResponseEntity<String>("Scheduled Meeting Deleted", HttpStatus.OK);
        } else {
            return new ResponseEntity<String>("Something went wrong", HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<String> updateScheduleMeeting(String meetingId, MeetingDetails meetingDetails) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + getAccessToken());

        HttpEntity<String> entity = new HttpEntity<>(new Gson().toJson(meetingDetails), headers);

        ResponseEntity<String> response =
                restTemplate.exchange("https://graph.microsoft.com/v1.0/me/events/"+meetingId,
                        HttpMethod.PATCH,
                        entity,
                        String.class);

        if (response.getStatusCode() == HttpStatus.NO_CONTENT) {
            return new ResponseEntity<String>("Scheduled Meeting Updated", HttpStatus.OK);
        } else {
            return new ResponseEntity<String>("Something went wrong", HttpStatus.BAD_REQUEST);
        }
    }
}
