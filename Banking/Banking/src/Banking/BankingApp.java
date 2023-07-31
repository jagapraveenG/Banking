package Banking;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

public class BankingApp {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        try (Connection connection = DatabaseConnector.getConnection()) {
            BankingSystem bankingSystem = new BankingSystem(connection);

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
                        createAccount(bankingSystem, scanner);
                        break;
                    case 2:
                        performDeposit(bankingSystem, scanner);
                        break;
                    case 3:
                        performWithdrawal(bankingSystem, scanner);
                        break;
                    case 4:
                        checkBalance(bankingSystem, scanner);
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

    private static void createAccount(BankingSystem bankingSystem, Scanner scanner) {
        System.out.print("Enter your name: ");
        String name = scanner.nextLine();

        System.out.print("Enter initial deposit amount: ");
        double initialDeposit = scanner.nextDouble();
        scanner.nextLine(); // Consume the newline character

        try {
            int accountId = bankingSystem.createAccount(name, initialDeposit);
            System.out.println("Account created successfully. Your account number is: " + accountId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void performDeposit(BankingSystem bankingSystem, Scanner scanner) {
        System.out.print("Enter your account number: ");
        int accountNumber = scanner.nextInt();
        scanner.nextLine(); // Consume the newline character

        System.out.print("Enter deposit amount: ");
        double amount = scanner.nextDouble();
        scanner.nextLine(); // Consume the newline character

        try {
            if (bankingSystem.deposit(accountNumber, amount)) {
                System.out.println("Deposit successful. Updated balance: " + bankingSystem.getBalance(accountNumber));
            } else {
                System.out.println("Account not found or invalid amount.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void performWithdrawal(BankingSystem bankingSystem, Scanner scanner) {
        System.out.print("Enter your account number: ");
        int accountNumber = scanner.nextInt();
        scanner.nextLine(); // Consume the newline character

        System.out.print("Enter withdrawal amount: ");
        double amount = scanner.nextDouble();
        scanner.nextLine(); // Consume the newline character

        try {
            if (bankingSystem.withdraw(accountNumber, amount)) {
                System.out.println("Withdrawal successful. Updated balance: " + bankingSystem.getBalance(accountNumber));
            } else {
                System.out.println("Account not found, insufficient balance, or invalid amount.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void checkBalance(BankingSystem bankingSystem, Scanner scanner) {
        System.out.print("Enter your account number: ");
        int accountNumber = scanner.nextInt();
        scanner.nextLine(); // Consume the newline character

        try {
            double balance = bankingSystem.getBalance(accountNumber);
            if (balance != -1) {
                System.out.println("Your account balance: " + balance);
            } else {
                System.out.println("Account not found.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
