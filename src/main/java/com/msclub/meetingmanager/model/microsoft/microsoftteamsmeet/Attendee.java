package com.msclub.meetingmanager.model.microsoft.microsoftteamsmeet;

public class Attendee {

    public EmailAddress emailAddress;
    public String type;

    public Attendee(EmailAddress emailAddress, String type) {
        this.emailAddress = emailAddress;
        this.type = type;
    }
}
