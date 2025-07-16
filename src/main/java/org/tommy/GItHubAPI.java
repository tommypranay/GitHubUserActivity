package org.tommy;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

/**
 * Utility class to interact with GitHub's public REST API.
 * <p>
 * Specifically, it fetches public events for a given GitHub username using the endpoint:
 * https://api.github.com/users/{username}/events
 */

public class GItHubAPI {

    // GitHub API endpoint template for fetching a user's public activity events
    public static final String GITHUB_EVENT_API = "https://api.github.com/users/%s/events";

    /**
     * Fetches recent public events for the specified GitHub username.
     *
     * @param userName GitHub username for which to retrieve event activity
     * @return JSON string representing the list of events
     * @throws RuntimeException if the user is not found, response is invalid, or network fails
     */
    public String getGitHubEvents(String userName){
        HttpClient httpClient = HttpClient.newHttpClient();

        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(URI.create(String.format(GITHUB_EVENT_API,userName)))
                .GET()
                .build();
        try {
            HttpResponse<String> response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
            int statusCode = response.statusCode();
            String responseBody = response.body();

            if (statusCode == 404 && responseBody.contains("\"message\":\"Not Found\"")) {
                throw new RuntimeException("User '" + userName + "' does not exist on GitHub.");
            } else if (statusCode != 200) {
                throw new RuntimeException("Failed with status " + statusCode + ": " + responseBody);
            }
            return responseBody;
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }

    }
}
