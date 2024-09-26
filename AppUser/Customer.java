package AppUser;

import java.util.*;
import AppOrder.Order;
import AppBilling.Billing;

public class Customer extends User {
    private String userName;
    private String userEmail;
    private int userAge;
    private int userPhoneNumber;
    private String userAddress;
    private ArrayList<Order> orderList;
    private ArrayList<Billing> billingList;

    public Customer(String userName, String userEmail, int userAge, int userPhoneNumber, String userAddress, String userID, String userPassword) {
        super(userID, userPassword);
        this.userName = userName;
        this.userEmail = userEmail;
        this.userAge = userAge;
        this.userPhoneNumber = userPhoneNumber;
        this.userAddress = userAddress;
        this.orderList = new ArrayList<Order>();
        this.billingList = new ArrayList<Billing>();
    }
    
    public String setUserName() {
    	return userName;
    }
    
    public String setUserEmail() {
    	return userEmail;
    }
    
    public int setUserAge() {
    	return userAge;
    }
    
    public int setUserPhoneNumber() {
    	return userPhoneNumber;
    }
    
    public String setUserAddress() {
    	return userAddress;
    }

    public void addOrder(Order order) {
        this.orderList.add(order);
    }

    public void deleteOrder(int orderNum) {
        for (Order order : orderList) {
            if (order.getOrderNum() == orderNum) {
                orderList.remove(order);

                System.out.println("\n-----------------------------------------------");
                System.out.println(" Order " + orderNum + " has been deleted          ");
                System.out.println("------------------------------------------------");
                System.out.println();
                return;
            }
        }
        System.out.println("Order " + orderNum + " not found.");
    }

    public void addBilling(Order order) {
        Billing billing = new Billing(order);
        billingList.add(billing);
        System.out.println();
        billing.printReceipt();
    }

    // find and return single order object from array by using order number
    public Order getOrder(int orderNum) {
        for (Order order : orderList) {
            if (order.getOrderNum() == orderNum) {
                return order;
            }
        }
        System.out.println("\n-----------------------------------------------");
        System.out.println(" Order " + orderNum + " not found      ");
        System.out.println("------------------------------------------------");
        System.out.println();
        return null;
    }

    public ArrayList<Billing> getBillingList() {
        return this.billingList;
    }

    public ArrayList<Order> getOrderList() {
        return this.orderList;
    }

}

