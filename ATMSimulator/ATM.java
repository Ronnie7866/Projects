package ATMSimulator;

import java.util.*;

public class ATM {
    private static final BankCustomer customer = new Customer("1998");
    private static final Scanner sc = new Scanner(System.in);
    private static final int PIN_LENGTH = 4;
    private static final int MAX_ATTEMPT = 3;
    private static int attempts = 0;
    private static final int MAX_MINI_STATEMENT_ENTRIES = 5;
    private static List<String> transactionHistory = new ArrayList<>();

    public static void main(String[] args) {
        System.out.println(customer.getPIN());
        System.out.println("\t\t\tBank of India");
        menu();
    }

    public static boolean verifyPIN() {
        System.out.print("Enter your PIN : ");
        String enteredPIN = sc.next();
        if (enteredPIN.length() != PIN_LENGTH || !enteredPIN.matches("\\d+")) {
            System.out.println("Invalid PIN format. Please enter 4-digit PIN. ");
            return false; // Return false if PIN format is invalid
        }

        // for stored PIN

        String storedPIN = customer.getPIN();
        if (enteredPIN.equals(storedPIN)) {
            menu();
            return true; // Return true if PIN matches
        } else {
            attempts++;
            if (attempts < MAX_ATTEMPT) {
                System.out.println("Invalid PIN. Please try again.");
                return verifyPIN(); // Recursively call verifyPIN() if attempts are less than MAX_ATTEMPT
            } else {
                System.out.println("Maximum attempts reached. Your card has been blocked.");
                System.exit(0);
                return false; // Return false if maximum attempts reached
            }
        }
    }

    public static void menu() {
        System.out.println("1 : DEPOSIT\t\t\t\t\t 2 : FAST CASH\n3 : TRANSFER\t\t\t\t 4 : CASH WITHDRAWAL\n5 : PIN CHANGE\t\t\t\t 6 : BALANCE INQUIRY\n7 : GENERATE PIN\t\t\t 8 : MINI STATEMENT");
        try {
            int input = sc.nextInt();
            if (input == 1) deposit();
            else if (input == 2) fastCash();
            else if (input == 3) transfer();
            else if (input == 4) cashWithdrawal();
            else if (input == 5) PIN_CHANGE();
            else if (input == 6) balanceInquiry();
            else if (input == 7) generatePIN();
            else if (input == 8) miniStatement();
            else {
                System.out.println("Invalid option. Please choose a valid option.");
                menu();
            }
        } catch (InputMismatchException e) {
            System.out.println("Invalid input. Please enter a valid option.");
            sc.nextLine(); // Consume the invalid input
            menu();
        }
    }

    public static void generatePIN() {
        if (customer.getPIN() == null) {
            System.out.println("ENTER 4 - DIGIT PIN");
            String input = sc.next();
            System.out.println("RE-ENTER FOUR DIGIT PIN");
            String reinput = sc.next();
            if (input.equals(reinput)) {
                System.out.println("PIN GENERATED SUCCESSFULLY");
                option();
            }
        }
        else {
            PIN_CHANGE();
        }
    }

    private static void balanceInquiry() {
        try {
            Double accountBalance = customer.getAccountBalance();
            System.out.println(accountBalance);
            option();
        } catch (Exception e) {
            System.out.println("An error occurred while fetching the account balance.");
            e.printStackTrace();
        }
    }

    public static void deposit() {
        System.out.println("Enter your PIN.");
        String input = sc.next();

        if (customer.getPIN().equals(input)) {
            try {
                System.out.println("Enter the amount of Deposit");
                double amount = sc.nextDouble();
                if (customer.getAccountBalance() != null) {
                    Double accountBalance = customer.getAccountBalance();
                    customer.setAccountBalance(accountBalance + amount);
                } else {
                    customer.setAccountBalance(amount);
                }
                System.out.println("Your account has been deposited");
                recordTransaction("Deposit: + " + amount);
                seeClearbalance();
                option();
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid amount.");
                sc.nextLine(); // Consume the invalid input
                deposit();
            }
        } else {
            attempts++;
            if (attempts > 3) {
                System.out.println("\"Maximum attempts reached. Your card has been blocked.\"");
                System.exit(0);
            } else {
                System.out.println("Invalid PIN. Please try again.");
                verifyPIN();
            }
        }
    }

    public static void fastCash() {
        System.out.println("Select the amount to withdraw");
        System.out.println("1 : 1000 \t\t\t\t2 : 2500\n3 : 5000\t\t\t\t4 : 10000");
        int select = sc.nextInt();

        if (select == 1) {
            if (customer.getAccountBalance() != null && customer.getAccountBalance() >= 1000) {
                Double balance = customer.getAccountBalance();
                customer.setAccountBalance(balance - 1000);
                System.out.println("Transaction Successful");
                recordTransaction("Withdrawal: - " + 1000);
                seeClearbalance();
                option();
            } else {
                System.out.println("You don't have sufficient balance in your Account");
            }
        }
        if (select == 2) {
            if (customer.getAccountBalance() != null && customer.getAccountBalance() >= 2500) {
                Double balance = customer.getAccountBalance();
                balance = balance - 2500;
                customer.setAccountBalance(balance);
                recordTransaction("Withdrawal: - " + 2500);
                System.out.println("Transaction Successful");
                seeClearbalance();
                option();
            } else {
                System.out.println("You don't have sufficient balance in your Account");
            }
        }
        if (select == 3) {
            if (customer.getAccountBalance() != null && customer.getAccountBalance() >= 5000) {
                Double balance = customer.getAccountBalance();
                balance = balance - 5000;
                customer.setAccountBalance(balance);
                System.out.println("Transaction Successful");
                recordTransaction("Withdrawal: - " + 5000);
                seeClearbalance();
                option();
            } else {
                System.out.println("You don't have sufficient balance in your Account");
            }
        }
        if (select == 4) {
            if (customer.getAccountBalance() != null && customer.getAccountBalance() >= 10000) {
                Double balance = customer.getAccountBalance();
                balance = balance - 10000;
                customer.setAccountBalance(balance);
                System.out.println("Transaction Successful");
                recordTransaction("Withdrawal: - " + 10000);
                seeClearbalance();
                option();
            } else {
                System.out.println("You don't have sufficient balance in your Account");
            }
        }
    }

    public static void seeClearbalance() {
        try {
            System.out.println("Do you want to see your clr balance");
            System.out.println("Yes");
            System.out.println("NO");
            String input = sc.next();
            if (input.trim().equalsIgnoreCase("yes")) {
                balanceInquiry();
            }
        } catch (Exception e) {
            System.out.println("An error occurred while processing your input.");
            e.printStackTrace();
        }
    }

    public static void option(){
        try {
            System.out.println("Do you wish to continue : yes / no ");
            String input = sc.next();
            if (input.trim().equalsIgnoreCase("yes")) {
                menu();
            } else {
                System.exit(100);
            }
        } catch (Exception e) {
            System.out.println("An error occurred while processing your input.");
            e.printStackTrace();
        }
    }

    public static void transfer() {
        System.out.println("Enter the amount to transfer");
        double amount = sc.nextDouble();
        double balance = customer.getAccountBalance();
        if (amount > balance) {
            System.out.println("Insufficient Balance\n Do you want to see your Account Balance");
            seeClearbalance();
            option();
        }
        double remBal = balance - amount;
        customer.setAccountBalance(remBal);
        System.out.println("Amount transfer successful");
        recordTransaction("Transfer: - " + amount);
        seeClearbalance();
    }

    public static void cashWithdrawal() {
        try {
            System.out.println("Please Enter Amount");
            double amount = sc.nextDouble();
            if (amount > customer.getAccountBalance()) {
                System.out.println("Insufficient Balance");
                System.out.println("Do you wish to continue : yes / no ");
                option();
            } else {
                System.out.println("Withdrawal successful");
                recordTransaction("Withdrawal: - " + amount);
                Double accountBalance = customer.getAccountBalance();
                customer.setAccountBalance(accountBalance - amount);
                seeClearbalance();
                option();
            }
        } catch (Exception e) {
            System.out.println("An error occurred while processing your input.");
            e.printStackTrace();
        }
    }

    public static void PIN_CHANGE() {
        System.out.println("ENTER YOUR NEW FOUR DIGIT PIN NUMBER");
        String input = sc.next();
        System.out.println("RE-ENTER YOUR NEW 4 - DIGIT PIN TO CONFIRM");
        String reinput = sc.next();
        if (input.equals(reinput)) {
            customer.setPIN(input);
            System.out.println("PIN CHANGE SUCCESSFUL.");
            option();
        }
        else {
            System.out.println("PIN MISMATCH : RE-ENTER AGAIN");
            PIN_CHANGE();
        }
    }

    public static void miniStatement() {
        for (String entry : transactionHistory) {
            System.out.println(entry);
        }
        option();
    }
    public static void recordTransaction(String transaction) {
        if (transactionHistory.size() >= MAX_MINI_STATEMENT_ENTRIES) {
            transactionHistory.removeFirst();
        }
        transactionHistory.add(transaction);
    }
}