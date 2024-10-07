/* Copyright (c) 2007-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package twitter;

import java.time.Instant;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

public class Extract {

    // Regex pattern for a valid Twitter username (only letters, digits, and underscores)
    private static final Pattern USERNAME_PATTERN = Pattern.compile("^[a-zA-Z0-9_]+$");

    public static Timespan getTimespan(List<Tweet> tweets) {
        if (tweets.isEmpty()) {
            return new Timespan(Instant.now(), Instant.now()); // Handling empty case
        }

        Instant start = Instant.MAX; // Initialize to max value
        Instant end = Instant.MIN;   // Initialize to min value

        for (Tweet tweet : tweets) {
            Instant timestamp = tweet.getTimestamp();
            if (timestamp.isBefore(start)) {
                start = timestamp;
            }
            if (timestamp.isAfter(end)) {
                end = timestamp;
            }
        }

        return new Timespan(start, end);
    }

    public static Set<String> getMentionedUsers(List<Tweet> tweets) {
        Set<String> mentionedUsers = new HashSet<>();

        for (Tweet tweet : tweets) {
            String text = tweet.getText();
            String[] words = text.split(" ");
            for (String word : words) {
                if (word.startsWith("@") && word.length() > 1 &&
                    USERNAME_PATTERN.matcher(word.substring(1)).matches()) {
                    
                    String username = word.substring(1).toLowerCase(); // Remove '@' and convert to lowercase
                    mentionedUsers.add(username);
                }
            }
        }

        return mentionedUsers;
    }
}
