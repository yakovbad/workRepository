package ClassWork;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class CreateTable {
    public static void main(String[] args) {
        try {
            Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        String url = "jdbc:derby:db;create=true;";
        try {
            Connection con = DriverManager.getConnection(url);
            Statement stmt = con.createStatement();
            String sql = "CREATE TABLE USERS" +
                    "(id INTEGER not NULL GENERATED ALWAYS AS IDENTITY (START WITH  1, INCREMENT BY 1)," +
                    "lastname VARCHAR(255), " +
                    "firstname VARCHAR(255), " +
                    "middlename VARCHAR(255), " +
                    "age INTEGER, " +
                    " PRIMARY KEY ( id ))";
            stmt.execute(sql);

            sql = "CREATE TABLE Orders " +
                    "(id INTEGER not NULL GENERATED ALWAYS AS IDENTITY (START WITH  1, INCREMENT BY 1)," +
                    "customerId INTEGER , " +
                    "salesPersonId INTEGER, " +
                    " PRIMARY KEY ( id ))";
            stmt.execute(sql);
        } catch (Exception e) {}
    }
}
