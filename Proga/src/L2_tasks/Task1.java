package L2_tasks;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Task1 {
    public static void main(String[] args) {
        Pattern pattern = Pattern.compile("test");
        Matcher matcher = pattern.matcher("Hello Test is test");
        while (matcher.find()) {
            System.out.println(matcher.start() + " " + matcher.end());
        }
    }
}
