package org.tommy;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GitHubUserNameValidator {

    private static Pattern validPattern = Pattern.compile("^[a-zA-Z0-9-]+$");

    private static Pattern invalidPattern = Pattern.compile("(--)");

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
