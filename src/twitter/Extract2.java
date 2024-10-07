package twitter;
import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
public class Extract {
   public static Timespan getTimespan(List<Tweet> tweets) {
       if (tweets.isEmpty()) {
           return new Timespan(Instant.now(), Instant.now()); // Handling empty case
       }
      
       Instant start = tweets.stream()
           .map(Tweet::getTimestamp)
           .min(Instant::compareTo)
           .orElse(Instant.now());
      
       Instant end = tweets.stream()
           .map(Tweet::getTimestamp)
           .max(Instant::compareTo)
           .orElse(Instant.now());
       return new Timespan(start, end);
   }
   public static Set<String> getMentionedUsers(List<Tweet> tweets) {
       return tweets.stream()
           .flatMap(tweet -> Arrays.stream(tweet.getText().split(" ")))
           .filter(word -> word.startsWith("@"))
           .map(word -> word.substring(1).toLowerCase())
           .collect(Collectors.toSet());
   }
}
