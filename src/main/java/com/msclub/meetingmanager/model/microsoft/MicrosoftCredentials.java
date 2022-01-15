package com.msclub.meetingmanager.model.microsoft;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@ConfigurationProperties(prefix="spring.microsoftcredentials")
@Configuration
public class MicrosoftCredentials {

    private String authTokenUri;
    private String clientId;
    private String clientSecret;
    private String grantType;
    private String scope;
    private String refreshToken;
    private String teamsMeetingCreateUrl;

    public String getAuthTokenUri() {
        return authTokenUri;
    }

    public void setAuthTokenUri(String authTokenUri) {
        this.authTokenUri = authTokenUri;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }

    public String getGrantType() {
        return grantType;
    }

    public void setGrantType(String grantType) {
        this.grantType = grantType;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public String getTeamsMeetingCreateUrl() {
        return teamsMeetingCreateUrl;
    }

    public void setTeamsMeetingCreateUrl(String teamsMeetingCreateUrl) {
        this.teamsMeetingCreateUrl = teamsMeetingCreateUrl;
    }
}
