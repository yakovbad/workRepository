package ClassWork.Entities;

public class Order {
    private int id;
    private int customerId;
    private int salesPersonId;

    public Order(int id, int customerId, int salesPersonId) {
        this.id = id;
        this.customerId = customerId;
        this.salesPersonId = salesPersonId;
    }

    public Order(int customerId, int salesPersonId) {
        this.customerId = customerId;
        this.salesPersonId = salesPersonId;
    }

    public int getId() {
        return id;
    }

    public int getCustomerId() {
        return customerId;
    }

    public int getSalesPersonId() {
        return salesPersonId;
    }
}
