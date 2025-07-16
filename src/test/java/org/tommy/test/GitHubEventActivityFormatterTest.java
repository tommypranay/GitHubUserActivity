package org.tommy.test;

import org.tommy.GitHubEventActivityFormatter;
import org.tommy.model.JsonDataHandler;
import org.tommy.model.JsonReader;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class GitHubEventActivityFormatterTest {

    private final JsonDataHandler dataHandler = new JsonDataHandler();
    private final GitHubEventActivityFormatter formatter = new GitHubEventActivityFormatter();

    @SuppressWarnings("unchecked")
    void testFromFile() throws IOException {
        String content = readFileFromResources("sample_github_events.json"); // Adjust path if needed
        List<JsonReader> jsonReaderList = (List<JsonReader>) dataHandler.extractJsonData(content);

        List<String> outputs = formatter.formatEvents(jsonReaderList);
        for (String output : outputs) {
            System.out.println(output);
        }
    }
    private String readFileFromResources(String fileName) throws IOException {
        try (InputStream is = getClass().getClassLoader().getResourceAsStream(fileName)) {
            if (is == null) {
                throw new IOException("File not found: " + fileName);
            }
            return new String(is.readAllBytes(), StandardCharsets.UTF_8);
        }
    }


    public static void main(String[] args) throws IOException{
        GitHubEventActivityFormatterTest test = new GitHubEventActivityFormatterTest();
        test.testFromFile();
    }
}
