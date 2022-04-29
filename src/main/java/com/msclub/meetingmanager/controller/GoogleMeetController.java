package com.msclub.meetingmanager.controller;

import com.google.api.services.calendar.Calendar;
import com.msclub.meetingmanager.model.google.AuthorizationFlow;
import com.msclub.meetingmanager.model.google.GoogleMeeting;
import com.msclub.meetingmanager.model.google.MeetingType;
import com.msclub.meetingmanager.service.GoogleMeetingService;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

@CrossOrigin("*")
@RequestMapping(value = "/api/googlemeet")
@RestController
public class GoogleMeetController {
    @GetMapping("/requestAuthorization")
    public void requestAuth(HttpServletResponse httpServletResponse) {
        try{
            String uuid = UUID.randomUUID().toString();
            AuthorizationFlow auth = GoogleMeetingService.startAuthorization(System.getenv("GoogleRedirectURIHost") + "/api/googlemeet/authorizeGoogle?authId=" + uuid, uuid);
            httpServletResponse.sendRedirect(auth.getRedirectURL().toString());

        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @GetMapping("/authorizeGoogle")
    @ResponseBody
    public String auth(@RequestParam(required = true) String code, @RequestParam(required = true) String authId){
        try{
            return GoogleMeetingService.authorize(authId, code);
        }catch (Exception e){
            return e.getMessage();
        }
    }

    @PostMapping("/schedule")
    public GoogleMeeting schedule(@RequestBody GoogleMeeting meetingDetails) throws IOException {
        return GoogleMeetingService.schedule(meetingDetails, MeetingType.INTERVIEW);
    }

    @PostMapping("/internalmeeting/schedule")
    public GoogleMeeting internalMeetSchedule(@RequestBody GoogleMeeting meetingDetails) throws IOException {
        return GoogleMeetingService.schedule(meetingDetails, MeetingType.INTERNAL_MEETING);
    }

    @DeleteMapping("/meeting/{meetingId}")
    public void deleteMeeting(@PathVariable String meetingId) throws IOException {
        Calendar service = GoogleMeetingService.getCalenderService();
        service.events().delete("primary", meetingId).execute();
    }

    @PatchMapping("/meeting/{meetingId}")
    public void updateMeeting(@PathVariable String meetingId, @RequestBody GoogleMeeting meetingDetails) throws IOException {
        Calendar service = GoogleMeetingService.getCalenderService();
        service.events().update("primary", meetingId, meetingDetails.toEvent()).execute();
    }

}
