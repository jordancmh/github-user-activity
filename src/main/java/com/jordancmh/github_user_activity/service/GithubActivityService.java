package com.jordancmh.github_user_activity.service;

import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Service
public class GithubActivityService {

    public String fetchGithubActivity(String username) throws IOException, InterruptedException {
        String GITHUB_ACTIVITY_URL = "https://api.github.com/users/" + username + "/events";

        try (HttpClient client = HttpClient.newHttpClient()) {
            HttpRequest request = HttpRequest.newBuilder(
                            URI.create(GITHUB_ACTIVITY_URL))
                    .header("accept", "application/json")
                    .build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() != 200) {
                System.out.println("Failed to fetch activity. Status code: " + response.statusCode());
            }

            return response.body();
        }
    }

}