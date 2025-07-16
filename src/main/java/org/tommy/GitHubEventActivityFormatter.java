package org.tommy;

import org.tommy.model.JsonReader;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Formats GitHub event data into human-readable strings.
 * <p>
 * This class maps different event types from the GitHub API
 * into string representations suitable for display.
 * Each supported event type has a corresponding formatter method.
 */

public class GitHubEventActivityFormatter {

    /**
     * Accepts a list of {@link JsonReader} representing GitHub event data and formats each
     * event into a user-friendly string based on the event type.
     *
     * @param jsonDataList List of parsed GitHub event data
     * @return A list of formatted event descriptions
     * @throws IllegalArgumentException if an unsupported event type is encountered
     */

    public List<String> formatEvents(List<JsonReader> jsonDataList){
        List<String> formattedEvents = new ArrayList<>();
        for(JsonReader jsonData: jsonDataList){
            String type = jsonData.getValueForKey("type");
            switch (type){
                case "IssueCommentEvent" ->
                    formattedEvents.add(issueCommentEventFormatter(jsonData));
                case "CommitCommentEvent" ->
                    formattedEvents.add(commitCommentEventFormatter(jsonData));
                case "ForkEvent" ->
                    formattedEvents.add(forkEventFormatter(jsonData));
                case "MemberEvent" ->
                    formattedEvents.add(memberEventFormatter(jsonData));
                case "PublicEvent" ->
                    formattedEvents.add(publicEventFormatter(jsonData));
                case "PullRequestReviewEvent" ->
                    formattedEvents.add(pullRequestReviewEventFormatter(jsonData));
                case "PullRequestReviewCommentEvent" ->
                    formattedEvents.add(pullRequestReviewCommentEventFormatter(jsonData));
                case "PushEvent" ->
                    formattedEvents.add(pushEventFormatter(jsonData));
                case "ReleaseEvent" ->
                    formattedEvents.add(releaseEventFormatter(jsonData));
                case "SponsorshipEvent" ->
                    formattedEvents.add(sponsorshipEventFormatter(jsonData));
                case "WatchEvent" ->
                    formattedEvents.add(watchEventFormatter(jsonData));
                case "DeleteEvent" ->
                    formattedEvents.add(deleteEventFormatted(jsonData));
                case "CreateEvent" ->
                    formattedEvents.add(createEventFormatter(jsonData));
                case "IssuesEvent" ->
                    formattedEvents.add(issuesEventFormatter(jsonData));
                case "GollumEvent" ->
                    formattedEvents.add(gollumEventFormatter(jsonData));
                case "PullRequestEvent" ->
                    formattedEvents.add(pullRequestEventFormatter(jsonData));
                default ->
                    throw new IllegalArgumentException("Unsupported GitHub event type: " + type);


            }
        }
        return formattedEvents;
    }

    // Individual formatter methods for supported event types.
    // Each method below extracts relevant fields and returns a descriptive string.

    private String memberEventFormatter(JsonReader readerData){
        String member = readerData.getValueForKey("payload.member.login");
        String repo = readerData.getValueForKey("repo.name");
        return String.format("Added %s as a collaborator to %s", member, repo);
    }

    private String forkEventFormatter(JsonReader readerData) {
        String source = readerData.getValueForKey("payload.forkee.full_name");
        String destination = readerData.getValueForKey("repo.name");
        return String.format("Forked %s -> %s",source,destination);
    }

    private String commitCommentEventFormatter(JsonReader readerData) {
        String commitId =  readerData.getValueForKey("payload.comment.commitId");
        commitId = commitId.substring(0,6);
        String repo = readerData.getValueForKey("repo.name");
        return String.format("Commented on commit %s in %s", commitId, repo);
    }

    private String issueCommentEventFormatter(JsonReader readerData){
        long issueNumber = readerData.getValueForKey("payload.issue.number");
        String repo = readerData.getValueForKey("repo.name");
        Map<String, Object> issueData= readerData.getValueForKey("payload.issue");
        String ref = "issue";
        String action = readerData.getValueForKey("payload.action");
        if(issueData.containsKey("pull_request")){
            ref = "pull request";
        }
        return String.format("%s a comment on %s #%d in %s", action,ref, issueNumber, repo);
    }

    private String publicEventFormatter(JsonReader readerData){
        String repo = readerData.getValueForKey("repo.name");
        return String.format("Made %S public",repo);
    }

    private String pullRequestReviewEventFormatter(JsonReader readerData){
        String repo = readerData.getValueForKey("repo.name");
        String action = readerData.getValueForKey("payload.review.state");
        long prId = readerData.getValueForKey("payload.pull_request.number");
        return String.format("%s on PR %d in %s",action, prId, repo);
    }

    private String pullRequestReviewCommentEventFormatter(JsonReader readerData){
        String repo = readerData.getValueForKey("repo.name");
        Map<String, Object> payload = readerData.getValueForKey("payload");
        String action = "Added";
        long prId = readerData.getValueForKey("payload.pull_request.number");
        if(payload.containsKey("changes")) action = "Edited";
        return String.format("%s on PR %d in %s",action, prId, repo);
    }

    private String pushEventFormatter(JsonReader readerData){
        String repo = readerData.getValueForKey("repo.name");
        long commits = readerData.getValueForKey("payload.distinct_size");
        return String.format("Pushed %d commits to %s", commits, repo);
    }

    private String releaseEventFormatter(JsonReader readerData){
        String repo = readerData.getValueForKey("repo.name");
        String action = readerData.getValueForKey("payload.action");
        String release = readerData.getValueForKey("payload.release.name");
        return String.format("%s a release %s in %s", action, release, repo);
    }

    private String sponsorshipEventFormatter(JsonReader readerData){
        String action = readerData.getValueForKey("payload.action");
        String sponsorable = readerData.getValueForKey("payload.sponsorable.login");
        if(action.equals("pending_tier_change")){
            return String.format("requested a tier change for sponsorship to sponsorable %s", sponsorable);
        }
        return String.format("%s a sponsorship for %s", action, sponsorable);
    }

    private String watchEventFormatter(JsonReader readerData){
        String repo = readerData.getValueForKey("repo.name");
        return String.format("starred %s", repo);
    }

    private String deleteEventFormatted(JsonReader readerData){
        String ref_type = readerData.getValueForKey("payload.ref_type");
        String ref = readerData.getValueForKey("payload.ref");
        String repo = readerData.getValueForKey("repo.name");
        return String.format("Deleted %s %s from %s", ref_type, ref, repo);
    }

    private String createEventFormatter(JsonReader readerData){
        String repo = readerData.getValueForKey("repo.name");
        String ref = readerData.getValueForKey("payload.ref");
        String ref_type = readerData.getValueForKey("payload.ref_type");
        if(ref == null)
            return String.format("Created %s %s", ref_type, repo);
        return String.format("Created %s %S in %s", ref_type, ref, repo);
    }

    private String issuesEventFormatter(JsonReader readerData){
        String repo = readerData.getValueForKey("repo.name");
        String action = readerData.getValueForKey("payload.action");
        long issueNumber = readerData.getValueForKey("payload.issue.number");
        String issue = readerData.getValueForKey("payload.issue.title");
        return String.format("%s issue #%d '%s' in %s", action, issueNumber, issue, repo);
    }

    private String gollumEventFormatter(JsonReader readerData) {
        String repo = readerData.getValueForKey("repo.name");
        List<Map<String, Object>> pages = readerData.getValueForKey("payload.pages");
        List<String> pageEvents = new ArrayList<>();

        for (Map<String, Object> page : pages) {
            String action = (String) page.get("action");
            String pageName = (String) page.get("page_name");
            pageEvents.add(String.format("%s wiki page \"%s\" in %s", action, pageName, repo));
        }

        return String.join("\n", pageEvents);
    }

    private String pullRequestEventFormatter(JsonReader readerData) {
        String repo = readerData.getValueForKey("repo.name");
        String action = readerData.getValueForKey("payload.action");
        long prNumber = readerData.getValueForKey("payload.pull_request.number");
        String title = readerData.getValueForKey("payload.pull_request.title");
        return String.format("%s pull request #%d '%s' in %s", action, prNumber, title, repo);
    }
}
