package Bank;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

public class BankingApp {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        try (Connection connection = DatabaseConnector.getConnection()) {
            Bank bank = new Bank(connection);

            while (true) {
                System.out.println("\n1. Create Account");
                System.out.println("2. Deposit");
                System.out.println("3. Withdraw");
                System.out.println("4. Check Balance");
                System.out.println("5. Exit");
                System.out.print("Enter your choice: ");
                int choice = scanner.nextInt();
                scanner.nextLine(); // Consume the newline character

                switch (choice) {
                    case 1:
                        createAccount(bank, scanner);
                        break;
                    case 2:
                        performDeposit(bank, scanner);
                        break;
                    case 3:
                        performWithdrawal(bank, scanner);
                        break;
                    case 4:
                        checkBalance(bank, scanner);
                        break;
                    case 5:
                        System.out.println("Exiting the application.");
                        scanner.close();
                        System.exit(0);
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void createAccount(Bank bank, Scanner scanner) {
        System.out.print("Enter your name: ");
        String name = scanner.nextLine();

        System.out.print("Enter initial deposit amount: ");
        double initialDeposit = scanner.nextDouble();
        scanner.nextLine();

        System.out.print("Enter your mobile number: ");
        String phoneNumber = scanner.nextLine();

        try {
            BankAccount account = bank.createAccount(name, initialDeposit, phoneNumber);
            System.out.println("Account created successfully. Your account number is: " + account.getAccountNumber());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void performDeposit(Bank bank, Scanner scanner) {
        System.out.print("Enter your account number: ");
        int accountNumber = scanner.nextInt();
        scanner.nextLine(); // Consume the newline character

        System.out.print("Enter deposit amount: ");
        double amount = scanner.nextDouble();
        scanner.nextLine(); // Consume the newline character

        try {
            BankAccount account = bank.findAccountByNumber(accountNumber);
            if (account != null) {
                account.deposit(amount);
                System.out.println("Deposit successful. Updated balance: " + account.getBalance());
            } else {
                System.out.println("Account not found or invalid amount.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void performWithdrawal(Bank bank, Scanner scanner) {
        System.out.print("Enter your account number: ");
        int accountNumber = scanner.nextInt();
        scanner.nextLine(); // Consume the newline character

        System.out.print("Enter withdrawal amount: ");
        double amount = scanner.nextDouble();
        scanner.nextLine(); // Consume the newline character

        try {
            BankAccount account = bank.findAccountByNumber(accountNumber);
            if (account != null) {
                if (account.withdraw(amount)) {
                    System.out.println("Withdrawal successful. Updated balance: " + account.getBalance());
                } else {
                    System.out.println("Insufficient balance or invalid amount.");
                }
            } else {
                System.out.println("Account not found.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void checkBalance(Bank bank, Scanner scanner) {
        System.out.print("Enter your account number: ");
        int accountNumber = scanner.nextInt();
        scanner.nextLine(); // Consume the newline character

        try {
            BankAccount account = bank.findAccountByNumber(accountNumber);
            if (account != null) {
                System.out.println("Your account balance: " + account.getBalance());
            } else {
                System.out.println("Account not found.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
