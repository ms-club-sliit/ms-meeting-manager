package com.msclub.meetingmanager.controller;

import com.msclub.meetingmanager.model.microsoft.MeetingDetails;
import com.msclub.meetingmanager.model.microsoft.microsoftteamsmeet.MicrosoftTeamsMeetingType;
import com.msclub.meetingmanager.service.MicrosoftTeamsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.CompletableFuture;

@CrossOrigin("*")
@RequestMapping(value = "/api/msteams")
@RestController
public class MicrosoftTeamsController {

    @Autowired
    private MicrosoftTeamsService microsoftTeamsService;

    @PostMapping("/schedule")
    public ResponseEntity<?> scheduleMeeting(@RequestBody MeetingDetails meetingDetails) {
        return microsoftTeamsService.scheduleMicrosoftMeeting(meetingDetails, MicrosoftTeamsMeetingType.INTERVIEW);
    }

    @PostMapping("/internalmeeting/schedule")
    public ResponseEntity<?> scheduleInternalMeeting(@RequestBody MeetingDetails meetingDetails) {
        return microsoftTeamsService.scheduleMicrosoftMeeting(meetingDetails, MicrosoftTeamsMeetingType.INTERNAL_MEETING);
    }

    @DeleteMapping("/internalmeeting/{meetingId}")
    public ResponseEntity<?> deleteMeeting(@PathVariable String meetingId) {
        return microsoftTeamsService.deleteScheduleMeeting(meetingId);
    }

    @PatchMapping("/meeting/{meetingId}")
    public CompletableFuture<Void> updateMeeting(@PathVariable String meetingId, @RequestBody MeetingDetails meetingDetails) {
        return microsoftTeamsService.updateScheduleMeeting(meetingId, meetingDetails);
    }
}
