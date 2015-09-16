package L2_tasks;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Task4 {
    public static void main(String[] args) {
        String test = "This \"huge test\" is pointless";


        Pattern pattern = Pattern.compile("(\"[^\"]*\"|[^\" ]+)");
        Matcher matcher = pattern.matcher(test);
        while (matcher.find()) {
            System.out.print(test.substring(matcher.start(), matcher.end()).replace("\"","") + ", ");
        }
        System.out.println();

        test = "Suzie Smith-Hopper test-hyphens. I can't do it. Too long; didn't read.";
        Pattern pattern1 = Pattern.compile("([^- ]+(-?'?[^- \\p{Punct}]+)+|[^- \\p{Punct}]+)");
        Matcher matcher1 = pattern1.matcher(test);
        while (matcher1.find()) {
            System.out.print(test.substring(matcher1.start(), matcher1.end()) + ", ");
        }
        System.out.println();
    }
}
