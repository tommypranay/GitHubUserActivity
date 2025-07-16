package org.tommy;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class GItHubAPI {

    public static final String GITHUB_EVENT_API = "https://api.github.com/users/%s/events";

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
