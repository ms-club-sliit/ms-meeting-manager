package com.msclub.meetingmanager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MsclubmeetingmanagerApplication {

    public static void main(String[] args) {
        try{
            GoogleMeetingService.init();
        }catch (Exception e){
            e.printStackTrace();
        }
        SpringApplication.run(MsclubmeetingmanagerApplication.class, args);
    }

}
