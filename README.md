# GitHub User Activity Viewer
> 🧩 **Built as a practice project from [roadmap.sh](https://roadmap.sh)**  
> This project is implemented as part of the [roadmap.sh GitHub User Activity Viewer project](https://roadmap.sh/projects/github-user-activity), intended to help developers practice real-world Java development and API integration skills.

A Java-based command-line tool that fetches and formats recent GitHub user activity using the public GitHub Events API.

## Features

- Takes a GitHub username as input
- Validates the input using GitHub username rules
- Fetches the user's public events via GitHub REST API
- Parses raw JSON response without any external library
- Displays user activity in a clean, human-readable format
- Graceful error handling for invalid usernames, API issues, or malformed data

## Supported Event Types

- `IssueCommentEvent`
- `CommitCommentEvent`
- `ForkEvent`
- `MemberEvent`
- `PublicEvent`
- `PullRequestReviewEvent`
- `PullRequestReviewCommentEvent`
- `PushEvent`
- `ReleaseEvent`
- `SponsorshipEvent`
- `WatchEvent`
- `DeleteEvent`
- `CreateEvent`
- `IssuesEvent`
- `GollumEvent`
- `PullRequestEvent`

## Getting Started

### Prerequisites

- Java 17+ (recommended Java 21+)
- Internet connection

### Project structure
```
GitHubUserActivity/
├── pom.xml                    (if you're using Maven)
├── README.md                  (the readme file you requested)
│
└── src/
│    └── main/
│        └── java/
│            └── org/
│                └── tommy/
│                    ├── GitHubUserActivity.java
│                    ├── GItHubAPI.java
│                    ├── GitHubUserNameValidator.java
│                    ├── GitHubEventActivityFormatter.java
│                    ├── model/│
│                    │   ├── JsonReader.java
│                    │   └── JsonDataHandler.java
│                    └── parser/
│                        └── JsonParser.java
└── test/
    └── java/
        └── org/
            └── tommy/
                └── test/
                    └── GitHubEventActivityFormatterTest.java



```


### Running the Application

```bash
java org.tommy.GitHubUserActivity <github-username>
```

## Notes
- All JSON parsing is done manually—no external libraries like Jackson or Gson are used.

- The code supports nested key access via dot notation (e.g., payload.issue.number).

## Future Improvements
- Add an Event class hierarchy using the enums GitHubEventType and GitHubEventField.

- Paginate events for users with large activity histories.

- Option to filter or sort events by type or time.

