package com.msclub.meetingmanager.service;

import com.google.gson.Gson;
import com.msclub.meetingmanager.model.microsoft.MSTeamsInterviewDetails;
import com.msclub.meetingmanager.model.microsoft.MicrosoftCredentials;
import com.msclub.meetingmanager.model.microsoft.MicrosoftTokenApiResponse;
import com.msclub.meetingmanager.model.microsoft.MicrosoftTeamsMeetingDetails;
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

    public String scheduleMicrosoftMeeting(MSTeamsInterviewDetails msTeamsInterviewDetails) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("client_id",microsoftCredentials.getClientId());
        map.add("client_secret",microsoftCredentials.getClientSecret());
        map.add("grant_type",microsoftCredentials.getGrantType());
        map.add("scope",microsoftCredentials.getScope());
        map.add("refresh_token",microsoftCredentials.getRefreshToken());

        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(map, headers);

        ResponseEntity<MicrosoftTokenApiResponse> response =
                restTemplate.exchange(microsoftCredentials.getAuthTokenUri(),
                        HttpMethod.POST,
                        entity,
                        MicrosoftTokenApiResponse.class);
        if (response.getStatusCode() == HttpStatus.OK) {
            return createMicrosoftMeeting(response.getBody().getAccess_token(),msTeamsInterviewDetails);
        } else {
            return null;
        }

    }

    public String createMicrosoftMeeting(String accessToken, MSTeamsInterviewDetails msTeamsInterviewDetails) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + accessToken);

        Body body = new Body("HTML","Thank you for applying with MS Club SLIIT. We would like to invite you for the next step of your application. Does this time work for you?");
        Start start = new Start(msTeamsInterviewDetails.getStartDateTime(),"India Standard Time");
        End end = new End(msTeamsInterviewDetails.getEndDateTime(),"India Standard Time");
        Location location = new Location("MS Club Conference Room");

        String[] emailList = msTeamsInterviewDetails.getEmailList();
        EmailAddress emailAddress;
        Attendee attendee;

        ArrayList<Attendee> attendees = new ArrayList<>();

        for (String email:emailList) {
            emailAddress = new EmailAddress(email);
            attendee = new Attendee(emailAddress,"required");
            attendees.add(attendee);
        }

        // create request body
        MicrosoftTeamsMeetingDetails microsoftTeamsMeetingDetails = new MicrosoftTeamsMeetingDetails();
        microsoftTeamsMeetingDetails.setSubject("MS Club of SLIIT - Interview " + msTeamsInterviewDetails.getStudentName());
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
            return response.getBody().getOnlineMeeting().getJoinUrl();
        } else {
            return "Error occurred in Meeting Scheduling";
        }
    }

}
