package ClassWork.repositories;

import ClassWork.Entities.Order;

import java.util.List;

public interface OrdersRepository {
    public List<Order> getAllOrders();

    public void addOrder(Order order);

    public void updateOrder(Order order);

    public void deleteOrder(int id);

    public List<Order> findOrdersByCustomerId(int CustomerId);
}
