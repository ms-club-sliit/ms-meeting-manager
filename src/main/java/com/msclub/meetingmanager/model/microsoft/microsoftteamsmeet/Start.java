package com.msclub.meetingmanager.model.microsoft.microsoftteamsmeet;

public class Start {
    public String dateTime;
    public String timeZone;

    public Start() {
    }

    public Start(String dateTime, String timeZone) {
        this.dateTime = dateTime;
        this.timeZone = timeZone;
    }
}
