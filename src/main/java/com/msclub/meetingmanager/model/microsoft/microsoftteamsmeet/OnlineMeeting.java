package com.msclub.meetingmanager.model.microsoft.microsoftteamsmeet;

public class OnlineMeeting {
    public String joinUrl;

    public OnlineMeeting() {
    }

    public OnlineMeeting(String joinUrl) {
        this.joinUrl = joinUrl;
    }

    public String getJoinUrl() {
        return joinUrl;
    }

    public void setJoinUrl(String joinUrl) {
        this.joinUrl = joinUrl;
    }
}
