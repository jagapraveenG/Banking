package Bank;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

class Bank {
    private Connection connection;
    private int accountCounter = 1000;

    public Bank(Connection connection) {
        this.connection = connection;
        initializeAccountCounter();
    }

    private void initializeAccountCounter() {
        try (Statement statement = connection.createStatement()) {
            String sql = "SELECT MAX(account_number) AS max_account_number FROM accounts";
            try (ResultSet resultSet = statement.executeQuery(sql)) {
                if (resultSet.next()) {
                    accountCounter = resultSet.getInt("max_account_number") + 1;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public BankAccount createAccount(String accountHolderName, double initialDeposit, String phoneNumber) throws SQLException {
        String sql = "INSERT INTO accounts (account_number, account_holder_name, balance, phone_number) VALUES (?, ?, ?, ?)";
        int accountNumber = generateAccountNumber();

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, accountNumber);
            preparedStatement.setString(2, accountHolderName);
            preparedStatement.setDouble(3, initialDeposit);
            preparedStatement.setString(4, phoneNumber);
            preparedStatement.executeUpdate();
        }

        return new BankAccount(accountNumber, accountHolderName, initialDeposit, phoneNumber);
    }

    private int generateAccountNumber() {
        return ++accountCounter;
    }

    public BankAccount findAccountByNumber(int accountNumber) throws SQLException {
        String sql = "SELECT * FROM accounts WHERE account_number = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, accountNumber);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    String accountHolderName = resultSet.getString("account_holder_name");
                    double balance = resultSet.getDouble("balance");
                    String phoneNumber = resultSet.getString("phone_number");
                    return new BankAccount(accountNumber, accountHolderName, balance, phoneNumber);
                }
            }
        }

        return null;
    }
}
