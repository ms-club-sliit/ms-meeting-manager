package com.msclub.meetingmanager.model.microsoft.microsoftteamsmeet;

public class MSMeetingResponse {

    public String id;

    public OnlineMeeting onlineMeeting;

    public Start start;

    public End end;

    public String onlineMeetingProvider;

    public MSMeetingResponse() {
    }

    public MSMeetingResponse(String id, OnlineMeeting onlineMeeting, Start start, End end, String onlineMeetingProvider) {
        this.id = id;
        this.onlineMeeting = onlineMeeting;
        this.start = start;
        this.end = end;
        this.onlineMeetingProvider = onlineMeetingProvider;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public OnlineMeeting getOnlineMeeting() {
        return onlineMeeting;
    }

    public void setOnlineMeeting(OnlineMeeting onlineMeeting) {
        this.onlineMeeting = onlineMeeting;
    }

    public Start getStart() {
        return start;
    }

    public void setStart(Start start) {
        this.start = start;
    }

    public End getEnd() {
        return end;
    }

    public void setEnd(End end) {
        this.end = end;
    }

    public String getOnlineMeetingProvider() {
        return onlineMeetingProvider;
    }

    public void setOnlineMeetingProvider(String onlineMeetingProvider) {
        this.onlineMeetingProvider = onlineMeetingProvider;
    }
}