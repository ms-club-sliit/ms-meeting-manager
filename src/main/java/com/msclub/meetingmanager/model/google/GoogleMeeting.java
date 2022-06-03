package com.msclub.meetingmanager.model.google;

import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventAttendee;
import com.google.api.services.calendar.model.EventDateTime;

import java.util.ArrayList;
import java.util.List;

public class GoogleMeeting {
    private String meetingId;
    private String meetingName;
    private String startDateTime;
    private String endDateTime;
    private List<String> emailList = new ArrayList<>();
    private String scheduledLink;

    public String getMeetingId() {
        return meetingId;
    }

    public void setMeetingId(String meetingId) {
        this.meetingId = meetingId;
    }
    public String getScheduledLink() {
        return scheduledLink;
    }

    public void setScheduledLink(String scheduledLink) {
        this.scheduledLink = scheduledLink;
    }

    public String getMeetingName() {
        return meetingName;
    }

    public void setMeetingName(String meetingName) {
        this.meetingName = meetingName;
    }

    public String getStartDateTime() {
        return startDateTime;
    }

    public void setStartDateTime(String startDateTime) {
        this.startDateTime = startDateTime;
    }

    public String getEndDateTime() {
        return endDateTime;
    }

    public void setEndDateTime(String endDateTime) {
        this.endDateTime = endDateTime;
    }

    public List<String> getEmailList() {
        return emailList;
    }

    public void setEmailList(List<String> emailList) {
        this.emailList = emailList;
    }

    public Event toEvent() {
        List<EventAttendee> attendees = new ArrayList<>();

        for(String email : emailList){
            attendees.add(new EventAttendee().setEmail(email));
        }

        return new Event()
                .setStart(new EventDateTime().setDateTime(new DateTime(startDateTime)))
                .setEnd(new EventDateTime().setDateTime(new DateTime(endDateTime)))
                .setSummary(meetingName)
                .setAttendees(attendees);

    }

    public static GoogleMeeting FromEvent(Event event){
        GoogleMeeting meetingDetails = new GoogleMeeting();

        meetingDetails.setMeetingId(event.getId());
        meetingDetails.setStartDateTime(event.getStart().getDateTime().toStringRfc3339());
        meetingDetails.setEndDateTime(event.getEnd().getDateTime().toStringRfc3339());

        List<String> emailAddresses = meetingDetails.getEmailList();

        for(EventAttendee attendee: event.getAttendees()){
            emailAddresses.add(attendee.getEmail());
        }

        meetingDetails.setEmailList(emailAddresses);
        meetingDetails.setMeetingName(event.getSummary());

        String link = event.getConferenceData().getEntryPoints().get(0).getUri();
        meetingDetails.setScheduledLink(link);

        return meetingDetails;
    }
}