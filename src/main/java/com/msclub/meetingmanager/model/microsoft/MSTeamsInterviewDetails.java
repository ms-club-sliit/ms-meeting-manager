package com.msclub.meetingmanager.model.microsoft;

public class MSTeamsInterviewDetails {

    private String studentName;

    private String startDateTime;

    private String endDateTime;

    private String[] emailList;

    public MSTeamsInterviewDetails() {
    }

    public MSTeamsInterviewDetails(String studentName, String startDateTime, String endDateTime, String[] emailList) {
        this.studentName = studentName;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.emailList = emailList;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
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
