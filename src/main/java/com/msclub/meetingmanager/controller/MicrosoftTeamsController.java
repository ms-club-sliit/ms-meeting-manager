package com.msclub.meetingmanager.controller;

import com.msclub.meetingmanager.model.MicrosoftTeams;
import com.msclub.meetingmanager.service.MicrosoftTeamsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@RequestMapping(value = "/api")
@RestController
public class MicrosoftTeamsController {

    @Autowired
    private MicrosoftTeamsService microsoftTeamsService;

    @PostMapping
    public String scheduleMeeting(@RequestBody MicrosoftTeams microsoftTeams) {
        return microsoftTeamsService.scheduleMeeting(microsoftTeams);
    }

    @GetMapping("/test")
    public String scheduleMeetingTest() {
        return microsoftTeamsService.scheduleMeetingTest();
    }


}
