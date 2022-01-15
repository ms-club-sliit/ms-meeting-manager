package com.msclub.meetingmanager.controller;

import com.msclub.meetingmanager.model.microsoft.MSTeamsInterviewDetails;
import com.msclub.meetingmanager.service.MicrosoftTeamsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@RequestMapping(value = "/api/msteams")
@RestController
public class MicrosoftTeamsController {

    @Autowired
    private MicrosoftTeamsService microsoftTeamsService;

    @PostMapping("/schedule")
    public String scheduleMeeting(@RequestBody  MSTeamsInterviewDetails msTeamsInterviewDetails) {
        return microsoftTeamsService.scheduleMicrosoftMeeting(msTeamsInterviewDetails);
    }

}
