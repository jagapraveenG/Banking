package Banking;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class BankingSystem {
    private Connection connection;
    private int accountCounter = 1000;

    public BankingSystem(Connection connection) {
        this.connection = connection;
    }

    public int createAccount(String accountHolderName, double initialDeposit) throws SQLException {
        String sql = "INSERT INTO accounts (account_number, account_holder_name, balance) VALUES (?, ?, ?)";
        int accountNumber = generateAccountNumber();

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, accountNumber);
            preparedStatement.setString(2, accountHolderName);
            preparedStatement.setDouble(3, initialDeposit);
            preparedStatement.executeUpdate();
        }

        return accountNumber;
    }

    public boolean deposit(int accountNumber, double amount) throws SQLException {
        String sql = "UPDATE accounts SET balance = balance + ? WHERE account_number = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setDouble(1, amount);
            preparedStatement.setInt(2, accountNumber);
            int rowsUpdated = preparedStatement.executeUpdate();
            return rowsUpdated > 0;
        }
    }

    public boolean withdraw(int accountNumber, double amount) throws SQLException {
        String sql = "UPDATE accounts SET balance = balance - ? WHERE account_number = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setDouble(1, amount);
            preparedStatement.setInt(2, accountNumber);
            int rowsUpdated = preparedStatement.executeUpdate();
            return rowsUpdated > 0;
        }
    }

    public double getBalance(int accountNumber) throws SQLException {
        String sql = "SELECT balance FROM accounts WHERE account_number = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, accountNumber);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getDouble("balance");
                }
            }
        }
        return -1;
    }

    private int generateAccountNumber() throws SQLException {
        String sql = "SELECT MAX(account_number) FROM accounts";

        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            if (resultSet.next()) {
                int maxAccountNumber = resultSet.getInt(1);
                return maxAccountNumber + 1;
            }
        }

        // If the table is empty, start with account number 1000
        return 1000;
    }
}
