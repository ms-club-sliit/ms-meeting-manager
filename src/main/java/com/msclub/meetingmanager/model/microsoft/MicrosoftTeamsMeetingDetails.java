package com.msclub.meetingmanager.model.microsoft;
import com.msclub.meetingmanager.model.microsoft.microsoftteamsmeet.Attendee;
import com.msclub.meetingmanager.model.microsoft.microsoftteamsmeet.End;
import com.msclub.meetingmanager.model.microsoft.microsoftteamsmeet.Location;
import com.msclub.meetingmanager.model.microsoft.microsoftteamsmeet.Start;
import com.msclub.meetingmanager.model.microsoft.microsoftteamsmeet.Body;
import java.util.ArrayList;

public class MicrosoftTeamsMeetingDetails{
    public String subject;
    public Body body;
    public Start start;
    public End end;
    public Location location;
    public ArrayList<Attendee> attendees;
    public boolean allowNewTimeProposals;
    public boolean isOnlineMeeting;
    public String onlineMeetingProvider;




    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public Body getBody() {
        return body;
    }

    public void setBody(Body body) {
        this.body = body;
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

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public ArrayList<Attendee> getAttendees() {
        return attendees;
    }

    public void setAttendees(ArrayList<Attendee> attendees) {
        this.attendees = attendees;
    }

    public boolean isAllowNewTimeProposals() {
        return allowNewTimeProposals;
    }

    public void setAllowNewTimeProposals(boolean allowNewTimeProposals) {
        this.allowNewTimeProposals = allowNewTimeProposals;
    }

    public boolean isOnlineMeeting() {
        return isOnlineMeeting;
    }

    public void setOnlineMeeting(boolean onlineMeeting) {
        isOnlineMeeting = onlineMeeting;
    }

    public String getOnlineMeetingProvider() {
        return onlineMeetingProvider;
    }

    public void setOnlineMeetingProvider(String onlineMeetingProvider) {
        this.onlineMeetingProvider = onlineMeetingProvider;
    }
}
