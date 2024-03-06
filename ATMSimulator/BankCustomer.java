package ATMSimulator;

public interface BankCustomer {
    String getPIN();

    void setPIN(String PIN);
    Double getAccountBalance();

    void setAccountBalance(Double accountBalance);
}
