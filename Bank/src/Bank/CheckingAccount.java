package Bank;

class CheckingAccount extends BankAccount {
    private double overdraftLimit;

    public CheckingAccount(int accountNumber, String accountHolderName, double balance, String phoneNumber, double overdraftLimit) {
        super(accountNumber, accountHolderName, balance, phoneNumber);
        this.overdraftLimit = overdraftLimit;
    }

    public double getOverdraftLimit() {
        return overdraftLimit;
    }

    public void setOverdraftLimit(double overdraftLimit) {
        this.overdraftLimit = overdraftLimit;
    }

    public boolean hasOverdraft() {
        return balance < 0 && Math.abs(balance) <= overdraftLimit;
    }

    @Override
    public String toString() {
        return "CheckingAccount [accountNumber=" + accountNumber + ", accountHolderName=" + accountHolderName
                + ", balance=" + balance + ", phoneNumber=" + phoneNumber + ", overdraftLimit=" + overdraftLimit + "]";
    }
}
