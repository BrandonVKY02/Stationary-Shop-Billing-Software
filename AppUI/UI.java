package AppUI;
import java.util.*;
import AppBilling.Billing;
import AppOrder.Item;
import AppOrder.Order;
import AppUser.Administrator;
import AppUser.Customer;
import AppUser.User;

public class UI {
	static ArrayList<User> userList = new ArrayList<User>();// user in system
    static HashMap<String, Item> itemMap = new LinkedHashMap<>();// item show for user
    static Customer currentCustomer = null;
    static Administrator currentAdministrator = null;
    static boolean login = false;
    static int count = 0;// update for the ordernumber when new order is created

    public static void main(String[] args) {
        // initiate data
        Scanner scanner = new Scanner(System.in);
        userList.add(new Administrator("ADMIN", "ADMIN"));
        userList.add(new Customer("aaron", "aaron@gmail.com", 21, 123, "msia", "AARON", "111"));
        itemMap.put("PENCIL", new Item("PENCIL", 1.5));
        itemMap.put("ERASER", new Item("ERASER", 0.7));
        itemMap.put("5 PENCIL BUNDLE", new Item("5 PENCIL BUNDLE", 5));
        itemMap.put("RULER", new Item("RULER", 1.8));
        itemMap.put("PEN", new Item("PEN", 3));
        itemMap.put("CORRECTION TAPE", new Item("CORRECTION TAPE", 6.9));
        itemMap.put("500 SHEET FOOLSCAPE", new Item("500 SHEET FOOLSCAPE", 20));
        itemMap.put("CASIO CALCULATOR", new Item("CASIO CALCULATOR", 49.9));
        boolean start = true;

        // app start
        while (start) {
            int choice = 0;
            while (choice != 1) {
                mainMenu();
                choice = getNumericInput(scanner);
                switch (choice) {
                    case 1:
                        login(scanner);
                        break;
                    case 2:
                        createUser(scanner);
                        break;
                    case 3:
                        System.out.println("THANK YOU FOR USING THIS SYSTEM !!!");
                        System.out.println("\t\t HAVE A NICE DAY!!");
                        return;

                    default:
                        System.out.println("THE OPTION THAT YOU KEY IN IS NOT AT THE MENU, PLEASE CHOOSE AGAIN\n");
                        break;
                }
            }

            while (login) {
                if (currentCustomer != null) {

                    printMenuForCus();
                    choice = getNumericInput(scanner);
                    System.out.println();
                    switch (choice) {
                        case 1:
                            createOrder(scanner);
                            break;
                        case 2:
                            updateOrder(scanner);
                            break;
                        case 3:
                            deleteOrder(scanner);
                            break;
                        case 4:
                            searchItem(scanner);
                            break;
                        case 5:
                            viewCurrentOrder();
                            break;
                        case 6:
                            checkoutBill(scanner);
                            break;
                        case 7:
                            viewBill();
                            break;
                        case 8:
                            showItem();
                            break;
                        case 9:
                            login = false;
                            currentCustomer = null;
                            break;
                        default:
                            System.out.println("THE OPTION THAT YOU KEY IN IS NOT AT THE MENU, PLEASE CHOOSE AGAIN\n");
                            break;
                    }
                } else if (currentAdministrator != null) {
                    printMenuForAd();
                    choice = getNumericInput(scanner);
                    switch (choice) {
                        case 1:
                            currentAdministrator.getAllOrder(userList);
                            System.out.println();
                            break;
                        case 2:
                            login = false;
                            currentAdministrator = null;
                            System.out.println();
                            break;
                        default:
                            System.out.println("THE OPTION THAT YOU KEY IN IS NOT AT THE MENU, PLEASE CHOOSE AGAIN\n");
                            break;
                    }

                }
            }
        }

    }

    // login method and if successful will become current user
    public static void login(Scanner scanner) {
        if (userList != null && !userList.isEmpty()) {
            System.out.print("ENTER USERNAME: ");
            String uid = getStringInput(scanner);
            System.out.print("ENTER PASSWORD: ");
            String password = getStringInput(scanner);
            for (User user : userList) {
                if (user != null && user.getUserID().equals(uid) && user.getUserPassword().equals(password)) {
                    System.out.println("\n-----------------------------------------------");
                    System.out.println("		WELCOME, " + user.getUserID().toUpperCase() + "!");
                    System.out.println("------------------------------------------------\n");
                    System.out.println();
                    login = true;
                    if (user instanceof Customer) {
                        currentCustomer = (Customer) user;
                    } else if (user instanceof Administrator) {
                        currentAdministrator = (Administrator) user;
                    }
                    return;
                }

            }
            System.out.println("Invalid username or password.");
            System.out.println();
        } else {
            System.out.println("No account in Database");
            System.out.println();
        }

    }

    // create user profile
    public static void createUser(Scanner scanner) {

        System.out.print("ENTER NAME: ");
        String name = getStringInput(scanner);
        
        System.out.print("ENTER EMAIL: ");
        String email = getStringInput(scanner);
        
        System.out.print("ENTER AGE: ");
        int age = getNumericInput(scanner);

        System.out.print("ENTER ADDRESS: ");
        String address = getStringInput(scanner);

        System.out.print("ENTER PHONE NUMBER: ");
        int phone = getNumericInput(scanner);

        String userID = null;
        boolean isUnique = false;

        while (!isUnique) {
            boolean usernameExists = false;
            System.out.print("ENTER USERNAME: ");
            userID = getStringInput(scanner);

            // Check if username already exists in the list
            for (User user : userList) {
                if (user.getUserID().equals(userID)) {
                    System.out.println("\n-----------------------------------------------");
                    System.out.println("  THIS USERNAME ALREADY EXIST!");
                    System.out.println("\t\t PLEASE CHOOSE ANOTHER ONE!!");
                    System.out.println("------------------------------------------------\n");
                    usernameExists = true;
                    break;
                }
            }

            if (!usernameExists) {
                isUnique = true;
            }
        }
        if (isUnique) {
            System.out.print("ENTER PASSWORD: ");
            String userPassword = getStringInput(scanner);
            userList.add(new Customer(name, email, age, phone, address, userID, userPassword));
            System.out.println("\n-----------------------------------------------");
            System.out.println("            ACCOUNT CREATED SUCCESSFULLY!          ");
            System.out.println("------------------------------------------------\n");
        }

    }

    // menu that show item in app
    public static void showItem() {
        System.out.println("***********AVAILABLE ITEMS*********");
        for (String key : itemMap.keySet()) {
            Item item = itemMap.get(key);
            System.out.println(item.getSTname() + " - RM " + item.getSTprice());
        }
        System.out.println("***********************************");
        System.out.println();
    }

    // create order and add into current user order array
    public static void createOrder(Scanner scanner) {

        Order order = new Order(count);
        count++;
        for (Item item : itemMap.values()) {
            System.out.print("Enter the quantity of \"" + item.getSTname() + "\": ");
            int quantity = getNumericInput(scanner);

            if (quantity > 0) {
                order.addItem(item, quantity);
            }
        }
        currentCustomer.addOrder(order);
        System.out.println("\n-----------------------------------------------");
        System.out.println("	ORDER CREATED SUCCESSFULLY!");
        System.out.println("        ORDER NUMBER: " + order.getOrderNum());
        System.out.println("------------------------------------------------\n");
    }

    // update the quantity of the order by input item name(capital letter also can)
    // find order through ordernumber first from current user's orderarray
    // and call the handleOrderUpdate for output menu
    // the handleOrderUpdate will call another 3 method for each functionality
    public static void updateOrder(Scanner scanner) {
        System.out.println("ENTER ORDER NUMBER TO UPDATE: ");
        int orderNum = getNumericInput(scanner);
        scanner.nextLine();
        Order order = currentCustomer.getOrder(orderNum);
        if (order != null) {
            handleOrderUpdate(scanner, order);
        } else {
            System.out.println("ORDER NOT FOUND!");
            System.out.println("PLEASE INPUT THE CORRECT ORDER NUMBER!!!");
            System.out.println();
        }

    }

    public static void handleOrderUpdate(Scanner scanner, Order order) {
        System.out.println("--------- MENU ---------");
        System.out.println("1. ADD NEW ITEM");
        System.out.println("2. REMOVE ITEM");
        System.out.println("3. MODIFY ITEM QUANTITY");
        System.out.println("------------------------");
        System.out.println("PLEASE CHOOSE OPTION 1-3 : ");
        int updateChoice = getNumericInput(scanner);
        System.out.println();

        switch (updateChoice) {
            case 1:
                // this method is declare in static
                // cause need to interact with the sample item(array list of item on top)
                addItemToOrder(scanner, order);
                break;
            case 2:
                // call from order (because it manipulate data in the order object)
                System.out.println("Enter Item Name");
                String itemname = getStringInput(scanner);
                order.deleteItem(itemname);
                break;
            case 3:
                // call from order (because it manipulate data in the order object)
                order.modifyQuantity(() -> getStringInput(scanner), () -> getNumericInput(scanner));
                break;
        }
    }

    // and new item into Order by input ordernumber
    public static void addItemToOrder(Scanner scanner, Order order) {
        System.out.println("Enter Item Name");
        String itemToAdd = getStringInput(scanner);
        Item find = findItemByName(itemToAdd);
        if (find != null) {
            System.out.println("ENTER QUANTITY: ");
            int quantity = getNumericInput(scanner);
            order.addItem(find, quantity);
            System.out.println("\n-----------------------------------------------");
            System.out.println("            ITEM ADDED TO ORDER SUCCESSFULLY!          ");
            System.out.println("------------------------------------------------\n");
        }
    }

    // find the item by searching item name(menu)
    // checked
    public static Item findItemByName(String name) {
        Item findItem = null;
        for (HashMap.Entry<String, Item> entry : itemMap.entrySet()) {
            if (entry.getKey().equals(name)) {
                findItem = entry.getValue();
                System.out.println(entry.getValue().getSTname() + " : RM " + entry.getValue().getSTprice());
                System.out.println();
                break;
            }

        }
        if (findItem == null) {
            System.out.println("\n-----------------------------------------------");
            System.out.println("            ITEM NOT FOUND!          ");
            System.out.println("------------------------------------------------\n");
        }
        return findItem;
    }

    public static void printMenuForCus() {
        System.out.println("WELCOME TO THE STATIONARY SHOP APP!!");
        System.out.println("--------- MENU ---------\n"
                + "1. CREATE ORDER\n"
                + "2. UPDATE ORDER\n"
                + "3. CANCLE ORDER\n"
                + "4. SEARCH FOR ITEMS\n"
                + "5. VIEW CURRENT ORDER\n"
                + "6. CHECKOUT FOR BILLING\n"
                + "7. VIEW BILLING\n"
                + "8. SHOW AVAILABLE ITEMS\n"
                + "9. LOG OUT\n"
                + "------------------------");

    }

    public static void mainMenu() {
        System.out.println("--------- LOGIN PAGE ---------\n"
                + "1. LOGIN\n"
                + "2. CREATE ACCOUNT\n"
                + "3. EXIT\n"
                + "------------------------------");
        System.out.println("PLEASE CHOOSE OPTION 1-3 : ");
    }

    public static void printMenuForAd() {
        System.out.println("--------- MENU ---------\n"
                + "1. VIEW ALL ORDER\n"
                + "2. LOG OUT\n"
                + "------------------------");
        System.out.println("PLEASE CHOOSE OPTION 1-2 : ");
    }

    // checked
    public static void searchItem(Scanner scanner) {
        System.out.println("Enter Item name");
        String name = getStringInput(scanner);
        findItemByName(name);
    }

    public static void deleteOrder(Scanner scanner) {
        System.out.println("Enter Your Order Number");
        int orderNum = getNumericInput(scanner);
        currentCustomer.deleteOrder(orderNum);
    }

    public static void viewCurrentOrder() {
        ArrayList<Order> orderList = currentCustomer.getOrderList();
        if (orderList.isEmpty()) {
            System.out.println("\n-----------------------------------------------");
            System.out.println("            NO CURRENT ORDERS FOUND!          ");
            System.out.println("------------------------------------------------");
        } else {
            for (Order order : orderList) {
                order.displayOrder();
            }
        }
    }

    public static void checkoutBill(Scanner scanner) {
        System.out.println("Enter Your Order Number");
        int orderNum = getNumericInput(scanner);
        Order checkoutO = currentCustomer.getOrder(orderNum);
        currentCustomer.addBilling(checkoutO);
        currentCustomer.deleteOrder(orderNum);

    }

    public static void viewBill() {
        ArrayList<Billing> billingList = currentCustomer.getBillingList();
        if (billingList.isEmpty()) {
            System.out.println("\n-----------------------------------------------");
            System.out.println("            NO BILLING RECORDS FOUND!          ");
            System.out.println("------------------------------------------------");
        } else {
            for (Billing bill : billingList) {
                bill.printReceipt();
            }
        }
    }

    ////////////////////////////////////////////////
    public static String getStringInput(Scanner scanner) {
        scanner.nextLine();
        String input = scanner.next().trim();
        return input.toUpperCase();
    }

    public static int getNumericInput(Scanner scanner) {
        int num = 0;
        boolean validInput = false;

        while (!validInput) {
            try {
                num = scanner.nextInt();
                validInput = true;
            } catch (InputMismatchException e) {
                System.out.println("Invalid input! Please enter a valid integer.");
                scanner.next();
            }
        }
        return num;
    }
}
