package org.tommy;

import org.tommy.model.JsonReader;
import org.tommy.model.JsonDataHandler;

import java.util.List;

/**
 * The GitHubUserActivity class is the entry point for retrieving and displaying
 * recent GitHub public activity for a given user.
 *
 * It validates the username, makes an API call to fetch public events,
 * parses the returned JSON, and formats the activity output.
 */

public class GitHubUserActivity {

    /**
     * Retrieves GitHub activity for the given username, validates it, fetches events from the GitHub API,
     * parses the events into structured JSON objects, formats them, and prints them to the console.
     *
     * @param args Command-line arguments; expects the first argument to be a valid GitHub username.
     */
    @SuppressWarnings("unchecked")
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
        String userName = args[0];
        GItHubAPI gItHubAPI = new GItHubAPI();
        try{
            String json = gItHubAPI.getGitHubEvents(userName);
            JsonDataHandler jsonDataHandler = new JsonDataHandler();
            List<JsonReader> jsonReaderList =  (List<JsonReader>)jsonDataHandler.extractJsonData(json);
            GitHubEventActivityFormatter formatter = new GitHubEventActivityFormatter();
            List<String> list = formatter.formatEvents(jsonReaderList);
            list.forEach(System.out::println);

        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
            System.exit(1);
        }


    }

    public static void main(String[] args) {
        GitHubUserActivity userActivity = new GitHubUserActivity();
        userActivity.getGitHubUserActivity(args);

    }
}
