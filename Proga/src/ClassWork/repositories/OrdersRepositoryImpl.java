package ClassWork.repositories;

import ClassWork.Entities.Order;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrdersRepositoryImpl implements OrdersRepository {
    private String url;

    public OrdersRepositoryImpl(String url) {
        this.url = url;
    }

    @Override
    public List<Order> getAllOrders() {
        List<Order> res = new ArrayList<Order>();
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

        String sql = "SELECT * FROM Orders";

        try {
            if (stmt != null) {
                ResultSet resultSet = stmt.executeQuery(sql);
                while (resultSet.next()) {
                    res.add(new Order(Integer.valueOf(resultSet.getString(1)),
                            Integer.valueOf(resultSet.getString(2)),
                            Integer.valueOf(resultSet.getString(3))));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }

    @Override
    public void addOrder(Order order) {
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

        String sql = "INSERT INTO Orders(customerId, salesPersonId)" +
                "VALUES ("+order.getCustomerId()+", "+order.getSalesPersonId()+")";

        try {
            if (stmt != null) {
                stmt.execute(sql);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateOrder(Order order) {
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

        String sql = "UPDATE Orders set customerId ="+ order.getCustomerId()+
                ", salesPersonId ="+order.getSalesPersonId()
                +"where id = " + order.getId() + "";

        try {
            if (stmt != null) {
                stmt.execute(sql);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteOrder(int id) {
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

        String sql = "DELETE FROM Orders WHERE id = " + id;

        try {
            if (stmt != null) {
                stmt.execute(sql);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Order> findOrdersByCustomerId(int customerId) {
        List<Order> res = new ArrayList<Order>();
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

        String sql = "SELECT * FROM Orders WHERE customerId = " + customerId;

        try {
            if (stmt != null) {
                ResultSet resultSet = stmt.executeQuery(sql);
                while (resultSet.next()) {
                    res.add(new Order(Integer.valueOf(resultSet.getString(1)),
                            Integer.valueOf(resultSet.getString(2)),
                            Integer.valueOf(resultSet.getString(3))));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }
}
