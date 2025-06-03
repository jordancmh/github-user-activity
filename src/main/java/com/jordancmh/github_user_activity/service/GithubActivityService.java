package com.jordancmh.github_user_activity.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Optional;
import java.util.logging.Logger;

import static org.springframework.util.StringUtils.capitalize;

@Service
public class GithubActivityService {

    public String fetchActivity(String username) {
        String GITHUB_ACTIVITY_URL = "https://api.github.com/users/" + username + "/events";

        try (HttpClient client = HttpClient.newHttpClient()) {
            HttpRequest request = HttpRequest.newBuilder(
                            URI.create(GITHUB_ACTIVITY_URL))
                    .header("accept", "application/json")
                    .build();
            HttpResponse<String> response = null;
            try {
                response = client.send(request, HttpResponse.BodyHandlers.ofString());
                if (response.statusCode() != 200) {
                    return "Failed to fetch activity. Status code: " + response.statusCode();
                }
            } catch (IOException | InterruptedException e) {
                return "Error fetching activity: " + e.getMessage();
            }
            return response.body();
        }
    }

    public JsonNode parseActivities(String responseString) {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode events = mapper.createObjectNode();
        try {
            events = mapper.readTree(responseString);
        } catch (JsonProcessingException e) {
            System.out.println("Error parsing: " + e.getMessage());
        }
        return events;
    }

    public void displayActivities(JsonNode root) {
        if (root.isEmpty() || !root.isArray()) {
            System.out.println("No recent activity found.");
            return;
        }

        for (JsonNode events : root) {
            String type = events.get("type").asText();
            String repoName = events.get("repo").get("name").asText();
            JsonNode payload = events.get("payload");
            switch (type) {
                case "PushEvent":
                    System.out.println("Pushed to " + repoName);
                    break;
                case "CreateEvent":
                    System.out.println("Created " + repoName);
                    break;
                case "PullRequestEvent":
                    String action = String.valueOf(Optional.ofNullable(payload.get("action").asText()));
                    System.out.println(capitalize(action) + " pull request ");
                    break;
                case "IssuesEvent":
                    System.out.println("Pull request review in " + repoName);
                    break;
                case "DeleteEvent":
                    String refType = String.valueOf(Optional.ofNullable(payload.get("ref_type").asText()));
                    System.out.println("Deleted a " + refType + " in " + repoName);
                    break;
                default:
                    System.out.println("Performed " + type + " on " + repoName);
                    break;
            }
        }
    }

    public void returnRecentGithubActivity(String[] username) {
        String jsonResponse = fetchActivity(username[0]);
        JsonNode parsedResponse = parseActivities(jsonResponse);
        displayActivities(parsedResponse);
    }
}