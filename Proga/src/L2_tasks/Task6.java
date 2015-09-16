package L2_tasks;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Task6 {
    public static void main(String[] args) {
        Pattern pattern = Pattern.compile("(.)*(\\w)\\2(.)*");
        Matcher matcher = pattern.matcher("jsdfhs skfjskl sd ff");
        System.out.println(matcher.matches());
    }
}
