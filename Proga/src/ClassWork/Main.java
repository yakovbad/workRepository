package ClassWork;


import ClassWork.Entities.Order;
import ClassWork.Entities.User;
import ClassWork.repositories.OrdersRepositoryImpl;
import ClassWork.repositories.UserRepositoryImpl;



import java.util.List;

public class Main {
    private static void show(List arrayList){
        System.out.println("Последние 10 пользователей:");
        int i = 0;
        if (arrayList.size() > 10)
            i = arrayList.size() - 11;
        for (int j  = i; j < arrayList.size(); j++) {
            System.out.println(arrayList.get(j));
        }
    }

    public static void main(String[] args) {
        UserRepositoryImpl userRepository = new UserRepositoryImpl("jdbc:derby:db;create=true;");

        User user = new User("Сидоров", "Андрей", "Игнатьевич", 18);
        userRepository.addUser(user);

        user = new User("Червяков", "Артем", "Сергеевич", 19);
        userRepository.addUser(user);

        user = new User("Гарифьянов", "Рустем", "Ильдарович", 19);
        userRepository.addUser(user);

        user = new User("Козлов", "Петр", "Иванович", 19);
        userRepository.addUser(user);

        List<User> arrayList = userRepository.getAllUsers();
        System.out.println("В таблице " + arrayList.size() + " пользователей");

        show(arrayList);

        user = new User("Грачев", "Акаций", "Ильдарович", 25);
        userRepository.addUser(user);

        user = new User("Гарифьянов", "Петр", "Иванович", 18);
        userRepository.addUser(user);

        arrayList = userRepository.getAllUsers();
        System.out.println("В таблице " + arrayList.size() + " пользователей");

        String firstName = "Петр";
        arrayList = userRepository.findUserByFirstName(firstName);
        if (arrayList.size() > 0)
            System.out.println(arrayList.get(0));
        else
            System.out.println("Нет пользователя с именем " + firstName);

        userRepository.deleteUser(arrayList.get(0).getId());

        arrayList = userRepository.getAllUsers();
        arrayList.get(0).setFirstName("Yakov");
        arrayList.get(0).setAge(20);
        userRepository.updateUser(arrayList.get(0));

        arrayList = userRepository.getAllUsers();
        show(arrayList);

        System.out.println("В таблице " + arrayList.size() + " пользователей");

        Order order = new Order(2, 3);
        Order order1 = new Order(1, 5);
        Order order2 = new Order(10, 1);

        OrdersRepositoryImpl ordersRepository = new OrdersRepositoryImpl("jdbc:derby:db;create=true;");

        ordersRepository.addOrder(order);
        ordersRepository.addOrder(order1);
        ordersRepository.addOrder(order2);
        System.out.println("Продажи");
        List<Order> orders = ordersRepository.getAllOrders();
        for (int i = 0; i < orders.size(); i++) {
            System.out.println(userRepository.findUserById(orders.get(i).getCustomerId())+
                    " -> "+ userRepository.findUserById(orders.get(i).getSalesPersonId()));
        }
    }
}
