package Banking;

public class Account {
    private int accountNumber;
    private String accountHolderName;
    private double balance;

    public Account(int accountNumber, String accountHolderName, double balance) {
        this.accountNumber = accountNumber;
        this.accountHolderName = accountHolderName;
        this.balance = balance;
    }

    // Getters and Setters

    @Override
    public String toString() {
        return "Account [accountNumber=" + accountNumber + ", accountHolderName=" + accountHolderName + ", balance="
                + balance + "]";
    }
}
