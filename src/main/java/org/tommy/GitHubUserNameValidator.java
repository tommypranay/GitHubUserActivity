package org.tommy;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Utility class for validating GitHub usernames.

 * GitHub usernames must satisfy the following conditions:
 * - Only contain alphanumeric characters or hyphens
 * - Cannot have multiple consecutive hyphens
 * - Cannot begin or end with a hyphen
 * - Must be between 1 and 39 characters long
 *
 */
public class GitHubUserNameValidator {

    // Pattern allowing only alphanumeric characters and hyphen
    private static final Pattern validPattern = Pattern.compile("^[a-zA-Z0-9-]+$");

    // Pattern that detects two or more consecutive hyphens
    private static final Pattern invalidPattern = Pattern.compile("(--)");

    /**
     * Validates a GitHub username based on GitHub's public username rules.
     *
     * @param userName The username to validate.
     * @return true if the username is valid according to GitHub rules; false otherwise.
     */
    public static boolean isValidUserName(String userName){
        int userNameLength = userName.length();
        if(userNameLength>39 || userNameLength<1) return false;
        if(userName.startsWith("-") || userName.endsWith("-")) return false;
        Matcher matcher = validPattern.matcher(userName);
        if(!matcher.matches()) return false;
        matcher = invalidPattern.matcher(userName);
        if(matcher.matches()) return false;
        return true;
    }


}
