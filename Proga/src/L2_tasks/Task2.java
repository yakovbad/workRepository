package L2_tasks;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Task2 {
    public static void main(String[] args) {
        Pattern pattern = Pattern.compile("^((1\\d{3})|(200[0-9])|(201[0-2]))\\/(0[1-9]|1[0-2])\\/([0-2]\\d|3[0-1])");
        Matcher m = pattern.matcher("2012/03/18");
        Matcher n = pattern.matcher("1523/03/18");
        Matcher e = pattern.matcher("1000/01/18");
        Matcher q = pattern.matcher("2013/01/18");

        System.out.println(m.matches());
        System.out.println(n.matches());
        System.out.println(e.matches());
        System.out.println(q.matches());
    }
}
