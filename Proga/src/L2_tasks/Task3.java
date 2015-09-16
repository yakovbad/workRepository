package L2_tasks;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Task3 {
    public static void main(String[] args) {
        Pattern pattern1 = Pattern.compile("^(((https?)\\:\\/\\/)(www\\.)?(((([a-z0-9-]))+)\\.){1,2}([a-z]+)(\\/?([a-z0-9-/])*))");
        Matcher url = pattern1.matcher("http://a.google.com/dsf/sdfds/sdfhjsd");

        System.out.println(url.matches());
    }
}
