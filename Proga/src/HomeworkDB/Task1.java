package HomeworkDB;

import java.sql.*;

public class Task1 {
    public static void main(String[] args) {
        try {
            Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        String url = "jdbc:derby:memory:db;create=true";
        try {
            Connection con = DriverManager.getConnection(url);
            //Создание таблицы
            createTable(con);
            //Добавление 10 студентов
            insertStudent(con);
            // выборка студентов, id которых нечетен, сортировка по фамилии по убыванию, а по имени по возрастанию
            System.out.println("выборка студентов, id которых нечетен, сортировка по фамилии по убыванию, а по имени по возрастанию");
            selectOne(con);
            System.out.println("Вторая выборка");
            selectTwo(con);
            createTableOrder(con);
            insertOrder(con);
            System.out.println("Вывод продаж в заданном формате");
            selectOrder(con);
            System.out.println();
            System.out.println("Join Left");
            selectLeftJoin(con);
            System.out.println("Join Right");
            selectRightJoin(con);
            System.out.println("Join");
            selectJoin(con);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void createTable(Connection con) throws SQLException {
        Statement stmt = con.createStatement();
        String sql = "CREATE TABLE STUDENTS " +
                "(id INTEGER not NULL GENERATED ALWAYS AS IDENTITY (START WITH  1, INCREMENT BY 1)," +
                "lastname VARCHAR(255), " +
                "firstname VARCHAR(255), " +
                "middlename VARCHAR(255), " +
                "age INTEGER, " +
                " PRIMARY KEY ( id ))";
        stmt.execute(sql);
    }

    private static void insertStudent(Connection con) {
        String sql = "INSERT INTO Students(lastname, firstname, middlename, age) " +
                "VALUES ('Петров', 'Андрей', 'Витальевич', 18)," +
                "('Бадыгин', 'Яков', 'Васильевич', 19)," +
                "('Сидоров', 'Андрей', 'Игнатьевич', 18)," +
                "('Червяков', 'Артем', 'Сергеевич', 18)," +
                "('Гарифьянов', 'Рустем', 'Ильдарович', 18)," +
                "('Козлов', 'Петр', 'Иванович', 19)," +
                "('Сидоров', 'Вася', 'Игнатьевич', 18)," +
                "('Воробьев', 'Моисей', 'Петрович', 20)," +
                "('Грачев', 'Акакий', 'Сергеевич', 18)," +
                "('Андреев', 'Владислав', 'Динарович', 19)";
        try {
            Statement stmt = con.createStatement();
            stmt.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void selectOne(Connection con) {
        String sqlSelect = "Select id, lastname, firstname, age from STUDENTS WHERE (mod(id, 2) = 1) " +
                "ORDER BY lastname DESC, firstname";
        try {
            Statement stmt = con.createStatement();
            ResultSet results = stmt.executeQuery(sqlSelect);
            while (results.next()) {
                String id = results.getString(1);
                String lastName = results.getString(2);
                String name = results.getString(3);
                Integer age = results.getInt(4);
                System.out.println(id + " " + lastName + " " + name + " " + age);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void selectTwo(Connection con) {
        String sqlSelect = "Select id, lastname, firstname, age from STUDENTS WHERE lastname LIKE '%д%'";
        try {
            Statement stmt = con.createStatement();
            ResultSet results = stmt.executeQuery(sqlSelect);
            while (results.next()) {
                String id = results.getString(1);
                String lastName = results.getString(2);
                String name = results.getString(3);
                Integer age = results.getInt(4);
                System.out.println(id + " " + lastName + " " + name + " " + age);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void createTableOrder(Connection con) throws SQLException {
        Statement stmt = con.createStatement();
        String sql = "CREATE TABLE Orders " +
                "(id INTEGER not NULL GENERATED ALWAYS AS IDENTITY (START WITH  1, INCREMENT BY 1)," +
                "customerId INTEGER , " +
                "salesPersonId INTEGER, " +
                " PRIMARY KEY ( id ))";
        stmt.execute(sql);
    }

    private static void insertOrder(Connection con) throws SQLException{
        Statement stmt = con.createStatement();
        String sql = "INSERT INTO Orders (customerId, salesPersonId)" +
                "VALUES (1, 2)," +
                "(4,5)," +
                "(5,1)";
        stmt.execute(sql);
    }

    private static void selectOrder(Connection con) throws SQLException {
        Statement stmt = con.createStatement();
        String sql = "SELECT * FROM Orders";

        ResultSet resultSet = stmt.executeQuery(sql);

        while (resultSet.next()) {
            String id = resultSet.getString("id");
            String customerId = resultSet.getString("customerId");
            String salesPersonId = resultSet.getString("salesPersonId");
            System.out.println(id + ' ' + selectStudent(con, customerId) + " -> " + selectStudent(con, salesPersonId));
        }
    }



    private static String selectStudent(Connection con, String id) throws SQLException {
        Statement stmt = con.createStatement();
        String sql1 = "SELECT lastname, firstname FROM Students WHERE id =" + id;
        ResultSet res = stmt.executeQuery(sql1);
        boolean a = res.next();
        if (a)
            return res.getString(1) + ' ' + res.getString(2);
        else
            return "";
    }

    private static void selectLeftJoin(Connection con) throws SQLException {
        Statement stmt = con.createStatement();
        String sql = "SELECT * FROM STUDENTS LEFT JOIN Orders ON STUDENTS.id = Orders.customerId";

        ResultSet resultSet = stmt.executeQuery(sql);

        while (resultSet.next()) {
            String id = resultSet.getString("id");
            String lastname = resultSet.getString("lastname");
            String firstname = resultSet.getString("firstname");
            System.out.println(id + '|' + lastname + '|' + firstname);
        }
    }

    private static void selectRightJoin(Connection con) throws SQLException {
        Statement stmt = con.createStatement();
        String sql = "SELECT * FROM STUDENTS RIGHT JOIN Orders ON STUDENTS.id = Orders.customerId";

        ResultSet resultSet = stmt.executeQuery(sql);

        while (resultSet.next()) {
            String id = resultSet.getString("id");
            String lastname = resultSet.getString("lastname");
            String firstname = resultSet.getString("firstname");
            System.out.println(id + '|' + lastname + '|' + firstname);
        }
    }

    private static void selectJoin(Connection con) throws SQLException {
        Statement stmt = con.createStatement();
        String sql = "SELECT * FROM STUDENTS JOIN Orders ON STUDENTS.id = Orders.customerId";

        ResultSet resultSet = stmt.executeQuery(sql);

        while (resultSet.next()) {
            String id = resultSet.getString("id");
            String lastname = resultSet.getString("lastname");
            String firstname = resultSet.getString("firstname");
            System.out.println(id + '|' + lastname + '|' + firstname);
        }
    }

}
