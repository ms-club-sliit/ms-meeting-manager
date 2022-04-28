package com.msclub.meetingmanager.model.google;

import com.google.api.client.auth.oauth2.AuthorizationCodeRequestUrl;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;

public class AuthorizationFlow {
    public GoogleAuthorizationCodeFlow flow = null;
    private String uuid = "";
    private String redirectURI = "";
    public AuthorizationFlow(GoogleAuthorizationCodeFlow flow, String redirectURI, String uuid){
        this.flow = flow;
        this.redirectURI = redirectURI;
        this.uuid = uuid;
    }

    public GoogleAuthorizationCodeFlow getFlow() {
        return flow;
    }

    public AuthorizationCodeRequestUrl getRedirectURL() {
        return flow
                .newAuthorizationUrl()
                .setRedirectUri(this.redirectURI);
    }
    public String getRedirectURI() {
        return redirectURI;
    }
}
