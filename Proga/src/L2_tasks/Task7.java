package L2_tasks;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Task7 {
    public static void main(String[] args) {
        String password = "test";
        String password1 = "Test123";
        String password2 = "Testtt1";

        Pattern p1 = Pattern.compile("^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[_])*.*$");
        Matcher m1 = p1.matcher(password);

        Pattern p2 = Pattern.compile("(.)*(\\w)\\2\\2(.)*");
        Matcher m2 = p2.matcher(password);
        System.out.print(password + " -> ");
        System.out.println((m1.matches() && !m2.matches()) ? "Yes" : "NO");

        m1 = p1.matcher(password1);
        m2 = p2.matcher(password1);

        System.out.print(password1 + " -> ");
        System.out.println((m1.matches() && !m2.matches())?"Yes" : "NO");


        m1 = p1.matcher(password2);
        m2 = p2.matcher(password2);

        System.out.print(password2 + " -> ");
        System.out.println((m1.matches() && !m2.matches())?"Yes" : "NO");
    }
}
