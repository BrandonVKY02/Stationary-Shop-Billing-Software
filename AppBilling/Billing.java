package AppBilling;
import java.util.*;
import AppOrder.Order;
import AppOrder.Item;

public class Billing {
    private int billingNum;// this is same with ordernumber
    private Order order;
    private double total;

    public Billing(Order order) {
        this.order = order;
        this.total = 0;
        this.billingNum = order.getOrderNum();
    }

    // retrieve data from order and print
    public void printReceipt() {
        double orderTotal = calculatePrice();
        System.out.println("Order Receipt:");
        System.out.println("-----------------------------");
        System.out.printf("Billing Number: %d\n", this.billingNum);
        for (HashMap.Entry<Item, Integer> order : order.getOrder().entrySet()) {
            Item item = order.getKey();
            int quantity = order.getValue();
            double itemTotal = item.getSTprice() * quantity;
            System.out.printf("%s x %d - $%.2f\n", item.getSTname(), quantity, itemTotal);
        }
        System.out.println("-----------------------------");
        System.out.printf("Total: $%.2f\n", orderTotal);
        System.out.println();
    }

    // retrieve data from order and calculate
    public double calculatePrice() {
        double orderTotal = 0;
        for (HashMap.Entry<Item, Integer> order : order.getOrder().entrySet()) {
            Item item = order.getKey();
            int quantity = order.getValue();
            double itemTotal = item.getSTprice() * quantity;
            orderTotal += itemTotal;
        }
        this.total = orderTotal;
        return orderTotal;
    }

    public double getTotal() {
        return this.total;
    }

    public int getBillNum() {
        return this.billingNum;
    }

    public Order getOrder() {
        return this.order;
    }
}