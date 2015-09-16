package L2_tasks;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Task5 {
    public static void main(String[] args) {
        String test = "This task starts at the beginning of the day";
        Pattern pattern = Pattern.compile("^[t][a-zA-z]+|[ ][t][a-zA-z]+", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(test);

        while (matcher.find()) {
            System.out.print(test.substring(matcher.start(), matcher.end()) + ", ");
        }
    }
}
