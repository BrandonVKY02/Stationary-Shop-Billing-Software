package AppUser;

import java.util.*;
import AppBilling.Billing;

public class Administrator extends User {
    public Administrator(String aID, String aPassword) {
        super(aID, aPassword);
    }

    public void getAllOrder(ArrayList<User> userList) {
        boolean ordersExist = false; // initialize boolean variable to check if any orders exist

        for (User user : userList) {
            if (!(user instanceof Customer)) {
                continue; // skip this user if it's not a Customer
            }
            Customer customer = (Customer) user;
            ArrayList<Billing> billingList = customer.getBillingList();
            int num = 1;
            for (Billing bill : billingList) {
                System.out.println(
                        num + ". Customer: " + user.getUserID() + "\t");
                bill.getOrder().displayOrder();
                num++;
                System.out.printf("Total: $%.2f\n", bill.getTotal() + "\t");
                
                ordersExist = true; // set boolean variable to true if an order exists

            }
        }

        if (!ordersExist) { // check if no orders exist
            System.out.println("No orders have been made by any customers");
        }
    }
}