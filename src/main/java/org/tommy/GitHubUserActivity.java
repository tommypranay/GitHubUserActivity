package org.tommy;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class GitHubUserActivity {

    public void getGitHubUserActivity(String[] args){
        if(args.length==0 || !GitHubUserNameValidator.isValidUserName(args[0])){
            String message = """
                    Please provide a valid username
                    Github username may only contain alphanumeric characters or hyphens.
                    Github username cannot have multiple consecutive hyphens.
                    Github username cannot begin or end with a hyphen.
                    """;
            System.out.println(message);
            System.exit(1);
        }
    }

    public static void main(String[] args) {
        GitHubUserActivity userActivity = new GitHubUserActivity();
        userActivity.getGitHubUserActivity(args);

//        HttpClient client = HttpClient.newHttpClient();
//
//        HttpRequest request = HttpRequest.newBuilder()
//                .uri(URI.create("https://api.github.com/users/kexinhuang12345/events"))
//                .GET()
//                .build();
//
//        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
//
//        System.out.println(response.statusCode());
//        System.out.println(response.body().charAt(2));

    }
}
