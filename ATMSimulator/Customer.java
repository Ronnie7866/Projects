package ATMSimulator;

import ATMSimulator.BankCustomer;

public class Customer implements BankCustomer {
    private Double accountBalance;
    private String PIN;

    public Customer(String PIN) {
        this.PIN = PIN;
    }
    public String getPIN() {
        return PIN;
    }

    public void setPIN(String PIN) {
        this.PIN = PIN;
    }

    public Double getAccountBalance() {
        return accountBalance;
    }

    public void setAccountBalance(Double accountBalance) {
        this.accountBalance = accountBalance;
    }
}
