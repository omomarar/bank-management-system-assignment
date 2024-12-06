/******************************************************************************

                            Online Java Compiler.
                Code, Compile, Run and Debug java program online.
Write your code in this editor and press "Run" button to execute it.

*******************************************************************************/
 import java.util.*;
import java.io.*;

// Interface for reportable accounts
interface Reportable {
    String generateSummary();
}

// Base Class
class BankAccount implements Reportable {
    private String accountNumber;
    private String accountHolderName;
    private double balance;
    private List<String> transactions;

    // Constructor
    public BankAccount(String accountNumber, String accountHolderName, double initialBalance) {
        this.accountNumber = accountNumber;
        this.accountHolderName = accountHolderName;
        this.balance = initialBalance;
        this.transactions = new ArrayList<>();
        recordTransaction("Account created with balance: " + initialBalance);
    }

    // Getter and Setter methods
    public String getAccountNumber() {
        return accountNumber;
    }

    public String getAccountHolderName() {
        return accountHolderName;
    }

    public double getBalance() {
        return balance;
    }

    public void deposit(double amount) {
        if (amount <= 0) {
            System.out.println("Invalid deposit amount!");
            return;
        }
        balance += amount;
        recordTransaction("Deposited: " + amount);
    }

    public void withdraw(double amount) throws InsufficientFundsException {
        if (amount > balance) {
            throw new InsufficientFundsException("Insufficient funds for withdrawal!");
        }
        balance -= amount;
        recordTransaction("Withdrew: " + amount);
    }

    public void displayAccountDetails() {
        System.out.println("Account Number: " + accountNumber);
        System.out.println("Account Holder: " + accountHolderName);
        System.out.println("Balance: " + balance);
    }

    public void displayTransactionHistory() {
        System.out.println("Transaction History:");
        for (String transaction : transactions) {
            System.out.println(transaction);
        }
    }

    private void recordTransaction(String transaction) {
        transactions.add(transaction);
    }

    @Override
    public String generateSummary() {
        return "Account Summary:\nAccount Number: " + accountNumber + 
               "\nHolder: " + accountHolderName + "\nBalance: " + balance;
    }
}

// Derived Class
class SavingsAccount extends BankAccount {
    private double minimumBalance;
    private double annualInterestRate;

    public SavingsAccount(String accountNumber, String accountHolderName, double initialBalance, double minimumBalance, double annualInterestRate) {
        super(accountNumber, accountHolderName, initialBalance);
        this.minimumBalance = minimumBalance;
        this.annualInterestRate = annualInterestRate;
    }

    @Override
    public void withdraw(double amount) throws InsufficientFundsException {
        if (getBalance() - amount < minimumBalance) {
            throw new InsufficientFundsException("Withdrawal would breach minimum balance!");
        }
        super.withdraw(amount);
    }

    public void calculateInterest() {
        double interest = getBalance() * annualInterestRate / 100;
        deposit(interest);
        System.out.println("Interest of " + interest + " added to account.");
    }
}

// Custom Exceptions
class InsufficientFundsException extends Exception {
    public InsufficientFundsException(String message) {
        super(message);
    }
}

// Main Class
public class BankManagementSystem {
    private static Scanner scanner = new Scanner(System.in);
    private static Map<String, BankAccount> accounts = new HashMap<>();

    public static void main(String[] args) {
        boolean exit = false;

        while (!exit) {
            System.out.println("\nBank Management System");
            System.out.println("1. Create Account");
            System.out.println("2. Deposit");
            System.out.println("3. Withdraw");
            System.out.println("4. Check Balance");
            System.out.println("5. View Transactions");
            System.out.println("6. Exit");

            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    createAccount();
                    break;
                case 2:
                    deposit();
                    break;
                case 3:
                    withdraw();
                    break;
                case 4:
                    checkBalance();
                    break;
                case 5:
                    viewTransactions();
                    break;
                case 6:
                    exit = true;
                    break;
                default:
                    System.out.println("Invalid choice! Try again.");
            }
        }
    }

    private static void createAccount() {
        // Implementation for creating an account
          System.out.print("Enter Account Number: ");
    String accountNumber = scanner.next();

    System.out.print("Enter Account Holder Name: ");
    scanner.nextLine(); // Consume newline
    String accountHolderName = scanner.nextLine();

    System.out.print("Enter Initial Balance: ");
    double initialBalance = scanner.nextDouble();

    System.out.print("Choose Account Type (1 for Savings, 2 for Regular): ");
    int type = scanner.nextInt();

    if (type == 1) {
        System.out.print("Enter Minimum Balance: ");
        double minimumBalance = scanner.nextDouble();

        System.out.print("Enter Annual Interest Rate: ");
        double annualInterestRate = scanner.nextDouble();

        SavingsAccount savingsAccount = new SavingsAccount(accountNumber, accountHolderName, initialBalance, minimumBalance, annualInterestRate);
        accounts.put(accountNumber, savingsAccount);
        System.out.println("Savings Account created successfully.");
    } else {
        BankAccount regularAccount = new BankAccount(accountNumber, accountHolderName, initialBalance);
        accounts.put(accountNumber, regularAccount);
        System.out.println("Regular Account created successfully.");
    }
    
    }

    private static void deposit() {
        // Implementation for deposit
         System.out.print("Enter Account Number: ");
    String accountNumber = scanner.next();

    BankAccount account = accounts.get(accountNumber);
    if (account == null) {
        System.out.println("Account not found!");
        return;
    }

    System.out.print("Enter Deposit Amount: ");
    double amount = scanner.nextDouble();
    account.deposit(amount);
    System.out.println("Deposit successful.");
     
    }

    private static void withdraw() {
        // Implementation for withdrawal
        
    System.out.print("Enter Account Number: ");
    String accountNumber = scanner.next();

    BankAccount account = accounts.get(accountNumber);
    if (account == null) {
        System.out.println("Account not found!");
        return;
    }

    System.out.print("Enter Withdrawal Amount: ");
    double amount = scanner.nextDouble();

    try {
        account.withdraw(amount);
        System.out.println("Withdrawal successful.");
    } catch (InsufficientFundsException e) {
        System.out.println("Error: " + e.getMessage());
    }
    }

    private static void checkBalance() {
        // Implementation for checking balance
        System.out.print("Enter Account Number: ");
    String accountNumber = scanner.next();

    BankAccount account = accounts.get(accountNumber);
    if (account == null) {
        System.out.println("Account not found!");
        return;
    }

    System.out.println("Current Balance: " + account.getBalance());
    }

    private static void viewTransactions() {
        // Implementation for viewing transactions
        System.out.print("Enter Account Number: ");
    String accountNumber = scanner.next();

    BankAccount account = accounts.get(accountNumber);
    if (account == null) {
        System.out.println("Account not found!");
        return;
    }

    account.displayTransactionHistory();
    }
}
