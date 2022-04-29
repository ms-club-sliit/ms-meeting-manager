package com.msclub.meetingmanager.model.google;

import com.google.api.client.auth.oauth2.BearerToken;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.auth.oauth2.GoogleRefreshTokenRequest;
import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class AuthorizationToken {
    private String accessToken;
    private String refreshToken;
    private boolean expired = false;
    private Timer timer = new Timer("Timer");
    private NetHttpTransport HTTP_TRANSPORT = null;
    private GoogleClientSecrets CLIENT_SECRETS = null;
    private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();
    public AuthorizationToken(String accessToken, String refreshToken, NetHttpTransport transport, GoogleClientSecrets secrets){
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.CLIENT_SECRETS = secrets;
        this.HTTP_TRANSPORT = transport;

        AuthorizationToken that = this;

        // Schedule a task to expire this token in 45 minutes
        TimerTask task = new TimerTask() {
            public void run() {
                synchronized (that) {
                    that.expired = true;
                }
            }
        };

        timer.schedule(task, 2700000);
    }

    public AuthorizationToken(String refreshToken, NetHttpTransport transport, GoogleClientSecrets secrets) throws IOException {
        this.refreshToken = refreshToken;
        this.CLIENT_SECRETS = secrets;
        this.HTTP_TRANSPORT = transport;

        // Refresh the token immediately cuz access token is not provided
        refresh();
    }
    public synchronized void refresh() throws IOException {
        GoogleTokenResponse res = new GoogleRefreshTokenRequest(HTTP_TRANSPORT, JSON_FACTORY, this.refreshToken, CLIENT_SECRETS.getDetails().getClientId(), CLIENT_SECRETS.getDetails().getClientSecret()).execute();
        this.accessToken = res.getAccessToken();

        this.expired = false;

        AuthorizationToken that = this;

        // Schedule a task to expire this token in 45 minutes
        TimerTask task = new TimerTask() {
            public void run() {
                synchronized (that) {
                    that.expired = true;
                }
            }
        };

        timer.schedule(task, 2700000);
    }

    public String getAccessToken() throws IOException {
        if(this.expired){
            refresh();
        }
        return accessToken;
    }

    public Credential getCredential() throws IOException {
        if(this.expired){
            refresh();
        }

        Credential credential =  new Credential.Builder(BearerToken.authorizationHeaderAccessMethod())
                .setTransport(HTTP_TRANSPORT)
                .setJsonFactory(JSON_FACTORY)
                .build();

        credential.setAccessToken(this.accessToken);

        return  credential;
    }
    public String getRefreshToken() {
        return refreshToken;
    }
}
