package com.msclub.meetingmanager.model.microsoft.microsoftteamsmeet;

public class MSMeetingResponse {
    public OnlineMeeting onlineMeeting;

    public String onlineMeetingProvider;

    public MSMeetingResponse() {
    }

    public MSMeetingResponse(OnlineMeeting onlineMeeting, String onlineMeetingProvider) {
        this.onlineMeeting = onlineMeeting;
        this.onlineMeetingProvider = onlineMeetingProvider;
    }

    public OnlineMeeting getOnlineMeeting() {
        return onlineMeeting;
    }

    public void setOnlineMeeting(OnlineMeeting onlineMeeting) {
        this.onlineMeeting = onlineMeeting;
    }

    public String getOnlineMeetingProvider() {
        return onlineMeetingProvider;
    }

    public void setOnlineMeetingProvider(String onlineMeetingProvider) {
        this.onlineMeetingProvider = onlineMeetingProvider;
    }
}