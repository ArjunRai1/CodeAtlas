package com.codeatlas.auth.service;

import com.codeatlas.auth.dto.GithubTokenResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.Map;
import java.util.UUID;

@Service
public class GithubOAuthService {

    @Value("${github.oauth.client-id}")
    private String clientId;

    @Value("${github.oauth.redirect-uri}")
    private String redirectUri;

    @Value("${github.oauth.client-secret}")
    private String clientSecret;

    public String getAuthorizationUrl(HttpSession session) {
        String state = UUID.randomUUID().toString();
        session.setAttribute("github_oauth_state", state);
        return "https://github.com/login/oauth/authorize"
                + "?client_id=" + clientId
                + "&redirect_uri=" + redirectUri
                + "&state=" + state;
    }

    public String exchangeCode(String code) {

        RestClient restClient = RestClient.create();

        GithubTokenResponse response = restClient.post()
                .uri("https://github.com/login/oauth/access_token")
                .header("Accept", "application/json")
                .body(Map.of(
                        "client_id", clientId,
                        "client_secret", clientSecret,
                        "code", code,
                        "redirect_uri", redirectUri
                ))
                .retrieve()
                .body(GithubTokenResponse.class);

        if (response == null || response.accessToken() == null) {
            throw new IllegalStateException("Failed to obtain GitHub access token");
        }
        return response.accessToken();
    }
}
