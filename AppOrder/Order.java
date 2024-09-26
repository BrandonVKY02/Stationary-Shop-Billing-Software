package AppOrder;
import java.util.*;
import java.util.function.Supplier;


public class Order {
	private int orderNum;// this is same with billingnumber
    private HashMap<Item, Integer> orders;// key is item,and integer is quantity of the key(item)

    public Order(int count) {
        this.orders = new HashMap<Item, Integer>();
        this.orderNum = 100 + count;

    }

    // add item into the hashmap
    public void addItem(Item item, int quantity) {
        orders.put(item, quantity);
    }

    // remove item from hashmap
    public void deleteItem(String itemName) {
        for (Item item : orders.keySet()) {
            if (item.getSTname().equalsIgnoreCase(itemName)) {
                orders.remove(item);
                System.out.println("\n-----------------------------------------------");
                System.out.println("            ITEM REMOVED SUCCESSFULLY!          ");
                System.out.println("------------------------------------------------");
                System.out.println();
                return;
            }
        }

        System.out.println("No such item found in your order.");
        System.out.println("\t\tPlease check your order and try again.");
        System.out.println();
    }

    // modify the key and value in linked hashmap
    public void modifyQuantity(Supplier<String> stringSupplier, Supplier<Integer> intSupplier) {
        System.out.println("Please enter Item name");
        String itemName = stringSupplier.get();

        // Check if the item exists in the order
        Item item = null;
        for (Item i : orders.keySet()) {
            if (i.getSTname().equals(itemName)) {
                item = i;
                break;
            }
        }
        if (item != null) {
            System.out.println("Please enter new quantity");
            int quantity = intSupplier.get();
            Integer oldQuantity = orders.get(item);
            if (oldQuantity != null) {
                System.out.printf("Old quantity of %s was %d. ", item.getSTname(), oldQuantity);
            }
            orders.put(item, quantity);
            System.out.printf("New quantity of %s is %d.\n", item.getSTname(), quantity);

            System.out.println("\n-----------------------------------------------");
            System.out.println("            ORDER MODIFIED SUCCESSFULLY!          ");
            System.out.println("------------------------------------------------");
            System.out.println();

        } else {
            System.out.println("No such item found in your order.");
            System.out.println("\t\tPlease check your order and try again.");
            System.out.println();
        }
    }

    // get current order object
    public HashMap<Item, Integer> getOrder() {
        return this.orders;
    }

    // get current object order number
    public int getOrderNum() {
        return this.orderNum;
    }

    // display the item in current object hashmap item and quantity
    public void displayOrder() {

        System.out.println("Order Number : " + getOrderNum() + "\nItems: ");
        for (HashMap.Entry<Item, Integer> order : orders.entrySet()) {
            Item item = order.getKey();
            int quantity = order.getValue();
            System.out.println(item.getSTname() + " - $" + item.getSTprice() + " x " + quantity);
        }
        System.out.println();
    }
}
