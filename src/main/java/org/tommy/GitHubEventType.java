package org.tommy;

import java.util.HashMap;
import java.util.Map;


// NOTE: Currently unused. This enum can be utilized in the future if we implement an Event class
// to represent GitHub activities in a structured form.


/**
 * Enum representing supported GitHub event types, each associated with:
 * - Its actual GitHub event type string (as received from the GitHub API)
 * - A human-readable description for display purposes
 * - A classification (`EventRefType`) for how to interpret the ref and action in the event
 *
 * This structure simplifies parsing and displaying GitHub user activity meaningfully.
 */
public enum GitHubEventType {
    /***
     * Events with definite action on a definite ref
     * */
    COMMIT_COMMENT_EVENT("CommitCommentEvent", "Commented on a commit",EventRefType.DEFINITE_ACTION_DEFINITE_REF),
    FORK_EVENT("ForkEvent", "Forked a repository",EventRefType.DEFINITE_ACTION_DEFINITE_REF),
    MEMBER_EVENT("MemberEvent", "Added a collaborator to a repository",EventRefType.DEFINITE_ACTION_DEFINITE_REF),
    PUBLIC_EVENT("PublicEvent", "Made a repository public",EventRefType.DEFINITE_ACTION_DEFINITE_REF),
    PULL_REQUEST_REVIEW_EVENT("PullRequestReviewEvent",
            "Reviewed a pull request",EventRefType.DEFINITE_ACTION_DEFINITE_REF),
    PULL_REQUEST_REVIEW_COMMENT_EVENT("PullRequestReviewCommentEvent",
            "Commented on a pull request review",EventRefType.DEFINITE_ACTION_DEFINITE_REF),
    PUSH_EVENT("PushEvent", "Pushed to a repository",EventRefType.DEFINITE_ACTION_DEFINITE_REF),
    RELEASE_EVENT("ReleaseEvent", "Published a release",EventRefType.DEFINITE_ACTION_DEFINITE_REF),
    SPONSOR_EVENT("SponsorshipEvent", "Sponsored a developer",EventRefType.DEFINITE_ACTION_DEFINITE_REF),
    WATCH_EVENT("WatchEvent", "Starred a repository",EventRefType.DEFINITE_ACTION_DEFINITE_REF),

    /***
    * Events with definite action on an un-definite ref
    * */
        ISSUE_COMMENT_EVENT("IssueCommentEvent",
            "Commented on an issue or pull request",EventRefType.DEFINITE_ACTION_UNDEFINITE_REF),
    DELETE_EVENT("DeleteEvent", "Deleted a branch or tag",EventRefType.DEFINITE_ACTION_UNDEFINITE_REF),
    CREATE_EVENT("CreateEvent", "Created a repository, branch, or tag",EventRefType.DEFINITE_ACTION_UNDEFINITE_REF),

    /***
     * Events with un-definite action on a definite ref
     * */
    ISSUES_EVENT("IssuesEvent", "Opened or closed an issue",EventRefType.UNDEFINITE_ACTION_DEFINITE_REF),
    GOLLUM_EVENT("GollumEvent", "Created or updated a wiki page",EventRefType.UNDEFINITE_ACTION_DEFINITE_REF),
    PULL_REQUEST_EVENT("PullRequestEvent", "Opened, closed, or reopened a pull request",EventRefType.UNDEFINITE_ACTION_DEFINITE_REF);

    private final String eventType;
    private final String description;
    private final EventRefType eventRefType;


    GitHubEventType(String eventType, String description, EventRefType eventRefType) {
        this.eventType = eventType;
        this.description = description;
        this.eventRefType = eventRefType;
    }

    public String getEventType() {
        return eventType;
    }

    public String getDescription() {
        return description;
    }

    public EventRefType getEventRefType() {
        return eventRefType;
    }

    // Static map for reverse lookup
    private static final Map<String, GitHubEventType> EVENT_TYPE_MAP = new HashMap<>();

    static {
        for (GitHubEventType type : GitHubEventType.values()) {
            EVENT_TYPE_MAP.put(type.eventType, type);
        }
    }

    public static GitHubEventType fromEventType(String eventType) {
        return EVENT_TYPE_MAP.get(eventType);
    }
}

