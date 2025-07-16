package org.tommy.test;

import org.tommy.GitHubUserActivity;

public class GitHubUserActivityTest {

    void testGitHubUserActivity(){
        GitHubUserActivity gitHubUserActivity = new GitHubUserActivity();
        gitHubUserActivity.getGitHubUserActivity(new String[]{"tommypranay"});
    }

    public static void main(String[] args) {
        GitHubUserActivityTest test = new GitHubUserActivityTest();
        test.testGitHubUserActivity();
    }

}
