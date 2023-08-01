package Bank;

class SavingsAccount extends BankAccount {
    private double interestRate;

    public SavingsAccount(int accountNumber, String accountHolderName, double balance, String phoneNumber, double interestRate) {
        super(accountNumber, accountHolderName, balance, phoneNumber);
        this.interestRate = interestRate;
    }

    public double getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(double interestRate) {
        this.interestRate = interestRate;
    }

    public double calculateInterest() {
        return balance * interestRate / 100;
    }

    @Override
    public String toString() {
        return "SavingsAccount [accountNumber=" + accountNumber + ", accountHolderName=" + accountHolderName
                + ", balance=" + balance + ", phoneNumber=" + phoneNumber + ", interestRate=" + interestRate + "]";
    }
}