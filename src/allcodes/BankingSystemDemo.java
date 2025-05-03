package allcodes;

import java.util.*;


class Customer {
    private String customerId;
    private String name;

    public Customer(String customerId, String name) {
        this.customerId = customerId;
        this.name = name;
    }

    public String getCustomerId() {
        return customerId;
    }

    public String getName() {
        return name;
    }
}


class Account {
    private String accountNumber;
    private double balance;
    private String customerId;

    public Account(String accountNumber, String customerId) {
        this.accountNumber = accountNumber;
        this.customerId = customerId;
        this.balance = 0.0;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public String getCustomerId() {
        return customerId;
    }

    public double getBalance() {
        return balance;
    }

    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
            System.out.println("Deposited $" + amount + " to account " + accountNumber);
        } else {
            System.out.println("Invalid deposit amount.");
        }
    }

    public void withdraw(double amount) {
        if (amount > 0 && balance >= amount) {
            balance -= amount;
            System.out.println("Withdrew $" + amount + " from account " + accountNumber);
        } else {
            System.out.println("Invalid withdrawal amount or insufficient balance.");
        }
    }
}


class Bank {
    private HashMap<String, Customer> customers = new HashMap<>();
    private HashMap<String, Account> accounts = new HashMap<>();
    private HashMap<String, ArrayList<String>> customerAccounts = new HashMap<>();

    public void addCustomer(String customerId, String name) {
        if (!customers.containsKey(customerId)) {
            Customer customer = new Customer(customerId, name);
            customers.put(customerId, customer);
            customerAccounts.put(customerId, new ArrayList<>());
            System.out.println("Customer " + name + " added.");
        } else {
            System.out.println("Customer ID already exists.");
        }
    }

    public void createAccount(String customerId, String accountNumber) {
        if (customers.containsKey(customerId)) {
            if (!accounts.containsKey(accountNumber)) {
                Account account = new Account(accountNumber, customerId);
                accounts.put(accountNumber, account);
                customerAccounts.get(customerId).add(accountNumber);
                System.out.println("Account " + accountNumber + " created for customer " + customerId);
            } else {
                System.out.println("Account number already exists.");
            }
        } else {
            System.out.println("Customer ID not found.");
        }
    }

    public void deposit(String accountNumber, double amount) {
        Account account = accounts.get(accountNumber);
        if (account != null) {
            account.deposit(amount);
        } else {
            System.out.println("Account not found.");
        }
    }

    public void withdraw(String accountNumber, double amount) {
        Account account = accounts.get(accountNumber);
        if (account != null) {
            account.withdraw(amount);
        } else {
            System.out.println("Account not found.");
        }
    }

    public void displayCustomerAccounts(String customerId) {
        if (customerAccounts.containsKey(customerId)) {
            System.out.println("Accounts for customer " + customerId + ":");
            for (String accNum : customerAccounts.get(customerId)) {
                Account acc = accounts.get(accNum);
                System.out.println("Account Number: " + acc.getAccountNumber() + ", Balance: $" + acc.getBalance());
            }
        } else {
            System.out.println("Customer ID not found.");
        }
    }
}


public class BankingSystemDemo {
    public static void main(String[] args) {
        Bank bank = new Bank();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n--- Banking System Menu ---");
            System.out.println("1. Add Customer");
            System.out.println("2. Create Account");
            System.out.println("3. Deposit Money");
            System.out.println("4. Withdraw Money");
            System.out.println("5. Display Customer Accounts");
            System.out.println("6. Exit");
            System.out.print("Enter your choice (1-6): ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume the leftover newline character

            switch (choice) {
                case 1:
                    System.out.print("Enter Customer ID: ");
                    String customerId = scanner.nextLine();
                    System.out.print("Enter Customer Name: ");
                    String name = scanner.nextLine();
                    bank.addCustomer(customerId, name);
                    break;

                case 2:
                    System.out.print("Enter Customer ID for new Account: ");
                    String custId = scanner.nextLine();
                    System.out.print("Enter new Account Number: ");
                    String accNumber = scanner.nextLine();
                    bank.createAccount(custId, accNumber);
                    break;

                case 3:
                    System.out.print("Enter Account Number to Deposit: ");
                    String depositAcc = scanner.nextLine();
                    System.out.print("Enter Amount to Deposit: ");
                    double depositAmount = scanner.nextDouble();
                    bank.deposit(depositAcc, depositAmount);
                    break;

                case 4:
                    System.out.print("Enter Account Number to Withdraw: ");
                    String withdrawAcc = scanner.nextLine();
                    System.out.print("Enter Amount to Withdraw: ");
                    double withdrawAmount = scanner.nextDouble();
                    bank.withdraw(withdrawAcc, withdrawAmount);
                    break;

                case 5:
                    System.out.print("Enter Customer ID to Display Accounts: ");
                    String displayId = scanner.nextLine();
                    bank.displayCustomerAccounts(displayId);
                    break;

                case 6:
                    System.out.println("Exiting... Thank you!");
                    scanner.close();
                    return;

                default:
                    System.out.println("Invalid choice. Please select between 1 and 6.");
            }
        }
    }
}
