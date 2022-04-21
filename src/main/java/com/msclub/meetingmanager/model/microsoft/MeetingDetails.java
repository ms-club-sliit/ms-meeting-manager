package com.msclub.meetingmanager.model.microsoft;

public class MeetingDetails {


    private String meetingName;

    private String startDateTime;

    private String endDateTime;

    private String[] emailList;





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

    public String[] getEmailList() {
        return emailList;
    }

    public void setEmailList(String[] emailList) {
        this.emailList = emailList;
    }
}
