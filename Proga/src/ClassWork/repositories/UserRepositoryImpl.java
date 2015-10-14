package ClassWork.repositories;


import ClassWork.Entities.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserRepositoryImpl implements UserRepository {
    private String url;
    public UserRepositoryImpl(String url) {
        this.url = url;
    }

    @Override
    public List<User> getAllUsers() {
        List<User> res = new ArrayList<User>();
        Statement stmt = null;
        try {
            Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            Connection con = DriverManager.getConnection(url);
            stmt = con.createStatement();
        } catch (Exception e) {
            e.printStackTrace();
        }

        String sql = "SELECT * FROM USERS";

        try {
            if (stmt != null) {
                ResultSet resultSet = stmt.executeQuery(sql);
                while (resultSet.next()) {
                    res.add(new User(Integer.valueOf(resultSet.getString(1)),
                            resultSet.getString(2),
                            resultSet.getString(3),
                            resultSet.getString(4),
                            Integer.valueOf(resultSet.getString(5))));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }

    @Override
    public void addUser(User user) {
        Statement stmt = null;
        try {
            Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            Connection con = DriverManager.getConnection(url);
            stmt = con.createStatement();
        } catch (Exception e) {
            e.printStackTrace();
        }

        String sql = "INSERT INTO USERS(lastname, firstname, middlename, age)" +
                "VALUES ('" + user.getLastName() + "','" + user.getFirstName() + "','" + user.getMiddleName() + "'," + user.getAge() + ")";

        try {
            if (stmt != null) {
                stmt.execute(sql);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateUser(User user) {
        Statement stmt = null;
        try {
            Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            Connection con = DriverManager.getConnection(url);
            stmt = con.createStatement();
        } catch (Exception e) {
            e.printStackTrace();
        }

        String sql = "UPDATE USERS set lastname ='" + user.getLastName() +
                "', firstname ='" + user.getFirstName() +
                "', middlename ='" + user.getMiddleName() +
                "', age = "+user.getAge()+"where id = " + user.getId() + "";

        try {
            if (stmt != null) {
                stmt.execute(sql);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteUser(int id) {
        Statement stmt = null;
        try {
            Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            Connection con = DriverManager.getConnection(url);
            stmt = con.createStatement();
        } catch (Exception e) {
            e.printStackTrace();
        }

        String sql = "DELETE FROM USERS WHERE id = " + id;

        try {
            if (stmt != null) {
                stmt.execute(sql);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<User> findUserByFirstName(String firstName) {
        List<User> res = new ArrayList<User>();
        Statement stmt = null;
        try {
            Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            Connection con = DriverManager.getConnection(url);
            stmt = con.createStatement();
        } catch (Exception e) {
            e.printStackTrace();
        }
        String sql = "SELECT * FROM USERS WHERE firstname = '" + firstName + "'";

        try {
            if (stmt != null) {
                ResultSet resultSet = stmt.executeQuery(sql);
                while (resultSet.next()) {
                    res.add(new User(Integer.valueOf(resultSet.getString(1)),
                            resultSet.getString(2),
                            resultSet.getString(3),
                            resultSet.getString(4),
                            Integer.valueOf(resultSet.getString(5))));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }

    @Override
    public User findUserById(int id) {
        Statement stmt = null;
        try {
            Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            Connection con = DriverManager.getConnection(url);
            stmt = con.createStatement();
        } catch (Exception e) {
            e.printStackTrace();
        }
        String sql = "SELECT * FROM USERS WHERE id =" + id;

        try {
            if (stmt != null) {
                ResultSet resultSet = stmt.executeQuery(sql);
                while (resultSet.next()) {
                    User user = new User(Integer.valueOf(resultSet.getString(1)),
                            resultSet.getString(2),
                            resultSet.getString(3),
                            resultSet.getString(4),
                            Integer.valueOf(resultSet.getString(5)));
                    return user;
                }

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
