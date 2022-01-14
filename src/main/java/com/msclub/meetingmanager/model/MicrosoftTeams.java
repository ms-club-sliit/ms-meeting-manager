package com.msclub.meetingmanager.model;

import java.util.Date;

public class MicrosoftTeams {

    private String studentName;

    private Date startDateTime;

    private Date endDateTime;

    private String[] emailList;

    public MicrosoftTeams() {
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public Date getStartDateTime() {
        return startDateTime;
    }

    public void setStartDateTime(Date startDateTime) {
        this.startDateTime = startDateTime;
    }

    public Date getEndDateTime() {
        return endDateTime;
    }

    public void setEndDateTime(Date endDateTime) {
        this.endDateTime = endDateTime;
    }

    public String[] getEmailList() {
        return emailList;
    }

    public void setEmailList(String[] emailList) {
        this.emailList = emailList;
    }
}
