package com.msclub.meetingmanager.model.microsoft.microsoftteamsmeet;

public class Body {
    public String contentType;
    public String content;

    public Body() {
    }

    public Body(String contentType, String content) {
        this.contentType = contentType;
        this.content = content;
    }
}
