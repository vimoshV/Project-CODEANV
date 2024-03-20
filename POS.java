import java.util.ArrayList;
import java.util.List;
import java.time.LocalDateTime;
import java.io.*;

// class to represent Cashier
class Cashier implements Serializable {
    private String name;
    private String id;
    private String branch;
    private String password;

    public Cashier(String name, String id, String branch, String password) {
        this.name = name;
        this.id = id;
        this.branch = branch;
        this.password = password;
    }

    public String getName() {
        return this.name;
    }

    public String getBranch() {
        return this.branch;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean checkPassword(String password) {
        return this.password.equals(password);
    }

    public Object getCashierId() {
        return this.id;
    }

}

// class to represent a registered customers
class RegisteredCustomer implements Serializable {
    private String name;
    private String id;
    private String phone;

    public RegisteredCustomer(String name, String id, String phone) {
        this.name = name;
        this.id = id;
        this.phone = phone;
    }

    public String getName() {
        return this.name;
    }

    public String getPhone() {
        return this.phone;
    }

    public Object getCustomerId() {
        return this.id;
    }
}
// Class to represent a bill item
class ProductItem implements Serializable {
    private String itemCode;
    private String name;
    private double price;
    private double size;
    private LocalDateTime manufactureDate;
    private LocalDateTime expiryDate;
    private String manufacturerName;

    public ProductItem(String itemCode, String name, double price, double size, LocalDateTime manufactureDate,
            LocalDateTime expiryDate, String manufacturerName) {
        this.itemCode = itemCode;
        this.name = name;
        this.price = price;
        this.size = size;
        this.manufactureDate = manufactureDate;
        this.expiryDate = expiryDate;
        this.manufacturerName = manufacturerName;
    }
    // Getters for bill item details
    public String getItemCode() {
        return this.itemCode;
    }

    public String getName() {
        return this.name;
    }

    public double getPrice() {
        return this.price;
    }

    public double getSize() {
        return this.size;
    }

    public LocalDateTime getManufactureDate() {
        return this.manufactureDate;
    }

    public LocalDateTime getExpiryDate() {
        return this.expiryDate;
    }

    public String getManufacturerName() {
        return this.manufacturerName;
    }
}
// Class to represent a bill
class BillItem implements Serializable {
    private ProductItem item;
    private int quantity;
    private double discount;
    private double amount;

    public BillItem(ProductItem item, int quantity, double discount) {
        this.item = item;
        this.quantity = quantity;
        this.discount = discount;
        this.amount = item.getPrice() * quantity;
    }

    public ProductItem getItem() {
        return this.item;
    }

    public int getQuantity() {
        return this.quantity;
    }

    public double getDiscount() {
        return this.discount;
    }

    public double getAmount() {
        return this.amount;
    }
}

class Bill implements Serializable {
    private String billId;
    private LocalDateTime billDate;
    private String cashierId;
    private String cashierName;
    private String customerId;
    private String customerName;
    private List<BillItem> items;
    private double totalAmount;
    private double discount;
    private double netAmount;

    public Bill(String billId, LocalDateTime billDate, String cashierId, String customerId, List<BillItem> items,
            double totalAmount, double discount, double netAmount) {
        this.billId = billId;
        this.billDate = billDate;
        this.cashierId = cashierId;
        this.customerId = customerId;
        this.items = items;
        this.totalAmount = totalAmount;
        this.discount = discount;
        this.netAmount = netAmount;
    }

    public String getBillId() {
        return this.billId;
    }

    public LocalDateTime getBillDate() {
        return this.billDate;
    }

    public String getCashierId() {
        return this.cashierId;
    }

    public String getCustomerId() {
        return this.customerId;
    }

    public List<BillItem> getItems() {
        return this.items;
    }

    public double getTotalAmount() {
        return this.totalAmount;
    }

    public double getDiscount() {
        return this.discount;
    }

    public double getNetAmount() {
        return this.netAmount;
    }
}

// Class to represent a collection of pending bills
class PendingBill implements Serializable {
    // defining variables
    private String billId;
    private LocalDateTime billDate;
    private String cashierId;
    private String customerId;
    private List<BillItem> items;

    public PendingBill(String billId, LocalDateTime billDate, String cashierId, String customerId,List<BillItem> items) 
    {
        this.billId = billId;
        this.billDate = billDate;
        this.cashierId = cashierId;
        this.customerId = customerId;
        this.items = items;
    }

    public String getBillId() {
        return this.billId;
    }

    public LocalDateTime getBillDate() {
        return this.billDate;
    }

    public String getCashierId() {
        return this.cashierId;
    }

    public String getCustomerId() {
        return this.customerId;
    }

    public List<BillItem> getItems() {
        return this.items;
    }
}

// class to serialize pendingbills
class PendingBills implements Serializable {
    private List<PendingBill> pendingBills;

    public PendingBills() {
        this.pendingBills = new ArrayList<>();
    }

    public void addPendingBill(PendingBill bill) {
        pendingBills.add(bill);
    }

    public void removePendingBill(PendingBill bill) {
        pendingBills.remove(bill);
    }

    public List<PendingBill> getPendingBills() {
        return pendingBills;
    }

    public void serialize(String fileName) {
        // exception methods
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileName))) {
            oos.writeObject(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static PendingBills deserialize(String fileName) {
        // exception method
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileName))) {
            return (PendingBills) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            return null;
        }
    }
}
// Custom exception for item code not found
class ItemCodeNotFound extends Exception {
    public ItemCodeNotFound(String message) {
        super(message);
    }
}
// Point of Sale (POS) class
class POS {

    public ProductItem GetItemDetails(String itemCode, List<ProductItem> items) throws ItemCodeNotFound {
        for (ProductItem item : items) {
            if (item.getItemCode().equals(itemCode)) {
                return item;
            }
        }
        throw new ItemCodeNotFound("Item code not found");
    }

    public void AddItemToBill(ProductItem item, int quantity, double discount, List<BillItem> billItems) {
        billItems.add(new BillItem(item, quantity, discount));
    }

    public static void main(String[] args) {
        // creating demo data
        List<Cashier> cashiers = new ArrayList<>();
        cashiers.add(new Cashier("Vimosh", "001", "Colombo", "1234"));
        cashiers.add(new Cashier("Aadhi", "002", "Batticaloa", "1111"));

        List<RegisteredCustomer> registeredCustomers = new ArrayList<>();
        registeredCustomers.add(new RegisteredCustomer("Nived", "001", "1111"));
        registeredCustomers.add(new RegisteredCustomer("Abishan", "002", "1112"));

        // including demo datas
        List<ProductItem> items = new ArrayList<>();
        items.add(new ProductItem("001", "Milk", 100.00, 1.0, LocalDateTime.of(2021, 2, 1, 0, 0),LocalDateTime.of(2021, 1, 31, 0, 0), "Nestle"));
        items.add(new ProductItem("002", "Bread", 50.00, 0.5, LocalDateTime.of(2021, 1, 1, 0, 0),LocalDateTime.of(2021, 1, 31, 0, 0), "Nixon Bakery"));
        items.add(new ProductItem("003", "Butter", 200.00, 0.5, LocalDateTime.of(2021, 1, 1, 0, 0),LocalDateTime.of(2021, 1, 31, 0, 0), "Anchor"));
        items.add(new ProductItem("004", "Sugar", 300.00, 1, LocalDateTime.of(2021, 1, 1, 0, 0),LocalDateTime.of(2021, 1, 31, 0, 0), "Karumbu"));

        List<Bill> bills = new ArrayList<>();

        InputStreamReader r = new InputStreamReader(System.in);
        BufferedReader br = new BufferedReader(r);

        while (true) {
            // bill number
            String billId = "B" + String.format("%03d", bills.size() + 1);
            LocalDateTime billDate = LocalDateTime.now();
            String cashierId = "";
            String cashierName = "";
            String customerId = "";
            String customerName = "";

            List<BillItem> billItems = new ArrayList<>();
            try {
                // showing options
                System.out.println("\nOptions:");
                System.out.println("1. Create new bill");
                System.out.println("2. Continue pending bill");
                System.out.print("Enter option: ");
                int option = Integer.parseInt(br.readLine());
                System.out.println("-------------------------------------------");
                if (option == 1) {
                    System.out.print("\nEnter cashier id: ");
                    cashierId = br.readLine();
                    // looping
                    for (Cashier cashier : cashiers) {
                        if (cashier.getCashierId().equals(cashierId)) {
                            cashierName = cashier.getName();
                        }
                    }
                    try {
                        System.out.print("Enter customer Phone Number: ");
                        customerId = br.readLine();
                        System.out.println("-------------------------------------------");
                    } 
                    // catching exceptions
                    catch (IOException e) {
                        e.printStackTrace();
                    }

                } else if (option == 2) {
                    PendingBills pendingBills = PendingBills.deserialize("pending_bills.ser");
                    if (pendingBills != null && !pendingBills.getPendingBills().isEmpty()) {
                        PendingBill lastPendingBill = pendingBills.getPendingBills().get(pendingBills.getPendingBills().size() - 1);
                        cashierId = lastPendingBill.getCashierId();
                        customerId = lastPendingBill.getCustomerId();
                        billItems = lastPendingBill.getItems();
                        pendingBills.removePendingBill(lastPendingBill);
                    } else {
                        System.out.println("No pending bills found.");
                        continue;
                    }
                } else {
                    System.out.println("Invalid option. Please enter 1 or 2.");
                    continue;
                }
            } catch (IOException | NumberFormatException e) {
                e.printStackTrace();
                continue;
            }

            for (RegisteredCustomer customer : registeredCustomers) {
                if (customer.getCustomerId().equals(customerId)) {
                    customerName = customer.getName();
                }
            }
            while (true) {
                // pending or billing
                System.out.println("\nType 'done' to finish the bill or 'pending' to continue the bill later.");
                System.out.print("\nEnter item code : ");
                String itemCode = "";
                try {
                    itemCode = br.readLine();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                
                // adding options to done
                if (itemCode.equals("done")) {
                    double totalAmount = 0.0;
                    for (BillItem billItem : billItems) {
                        totalAmount += billItem.getAmount();
                    }

                    double totalDiscount = 0.0;
                    for (BillItem billItem : billItems) {
                        totalDiscount += billItem.getAmount() * billItem.getDiscount() / 100;
                    }

                    double netAmount = totalAmount - totalDiscount;

                    Bill bill = new Bill(billId, billDate, cashierId, customerId, billItems, totalAmount, totalDiscount,
                            netAmount);

                    bills.add(bill);
                    // method to print bill
                    System.out.println("\n-------------- Bill ID: " + billId+" --------------");
                    System.out.println("Bill Date: " + billDate);
                    System.out.println();
                    System.out.println("Cashier ID: " + cashierId);
                    System.out.println("Cashier Name: " + cashierName);
                    System.out.println();
                    System.out.println("Customer ID: " + customerId);
                    System.out.println("Customer Name: " + customerName);
                    System.out.println();
                    System.out.println("----------------- Items -------------------\n");
                    for (BillItem billItem : billItems) {
                        System.out.println("Item Code: " + billItem.getItem().getItemCode());
                        System.out.println("Item Name: " + billItem.getItem().getName());
                        System.out.println("Item Price: " + billItem.getItem().getPrice());
                        System.out.println("Item Discount: " + billItem.getDiscount());
                        System.out.println("Quantity: " + billItem.getQuantity());
                        System.out.println("Amount: " + billItem.getAmount());
                        System.out.println("-------------------------------------------");
                    }
                    System.out.println("Total Amount: " + totalAmount);
                    System.out.println("Total Discount: " + totalDiscount);
                    System.out.println();
                    System.out.println("Net Amount: " + netAmount);
                    System.out.println("-------------------------------------------");
                    break;
                } else if (itemCode.equals("pending")) {
                    PendingBills pendingBills = PendingBills.deserialize("pending_bills.ser");
                    if (pendingBills == null) {
                        pendingBills = new PendingBills();
                    }
                    PendingBill pendingBill = new PendingBill(billId, billDate, cashierId, customerId, billItems);
                    pendingBills.addPendingBill(pendingBill);
                    pendingBills.serialize("pending_bills.ser");

                    System.out.println("\nBill is pending.");
                    break;
                }
                // asking for bill
                System.out.print("Enter quantity: ");
                int quantity = 0;
                try {
                    quantity = Integer.parseInt(br.readLine());
                } catch (IOException | NumberFormatException e) {
                    e.printStackTrace();
                }

                // asking for discount
                System.out.print("Enter discount (%): ");
                double discount = 0.0;
                try {
                    discount = Double.parseDouble(br.readLine());
                } catch (IOException | NumberFormatException e) {
                    e.printStackTrace();
                }
                // product item as null
                ProductItem item = null;
                for (ProductItem pi : items) {
                    if (pi.getItemCode().equals(itemCode)) {
                        item = pi;
                        break;
                    }
                }

                if (item != null) {
                    BillItem billItem = new BillItem(item, quantity, discount);
                    billItems.add(billItem);
                } else {
                    System.out.println("Item not found. Please enter a valid item code.");
                }
            }
            // asking for yes or no
            System.out.println("\nDo you want to process another bill? (yes/no)");
            String continueChoice = "";
            try {
                continueChoice = br.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }

            if (!continueChoice.equalsIgnoreCase("yes")) {
                break;
            }
        }
    }

}
