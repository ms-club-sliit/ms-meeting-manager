package com.msclub.meetingmanager.service;

import com.msclub.meetingmanager.model.MicrosoftTeams;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

@Service
public class MicrosoftTeamsService {

    static RestTemplate restTemplate = new RestTemplate();

    public String scheduleMeeting(MicrosoftTeams microsoftTeams) {

        String url = "https://jsonplaceholder.typicode.com/posts/{id}";

        // create headers
        HttpHeaders headers = new HttpHeaders();

        // set `accept` header
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        // set custom header
        headers.set("x-request-source", "desktop");

        // build the request
        HttpEntity request = new HttpEntity(headers);

        // use `exchange` method for HTTP call
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, request, String.class, 1);
        if (response.getStatusCode() == HttpStatus.OK) {
            return response.getBody();
        } else {
            return null;
        }
    }

    public String scheduleMeetingTest() {

        String url = "https://jsonplaceholder.typicode.com/posts";

        // create headers
        HttpHeaders headers = new HttpHeaders();

        // set `accept` header
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        // set custom header
        headers.set("x-request-source", "desktop");

        // build the request
        HttpEntity request = new HttpEntity(headers);

        // use `exchange` method for HTTP call
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, request, String.class, 1);
        if (response.getStatusCode() == HttpStatus.OK) {
            return response.getBody();
        } else {
            return null;
        }
    }

}
