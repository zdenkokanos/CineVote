package CanBeVoted;

import java.io.Serializable;

public class BankAccount implements Serializable {
    private String IBAN;
    private String bank;
    private String BIC_CODE;
    private float balance;

    public BankAccount(float balance){ //each user with privilege to vote can donate up to 100$
        this.balance = balance;
    }
    public BankAccount(String IBAN, String bank, String BIC_CODE) {
        this.IBAN = IBAN;
        this.bank = bank;
        this.BIC_CODE = BIC_CODE;
        this.balance = 0; //each account is assigned 0 at start
    }

    public static BankAccount createAccount(float balance){
        BankAccount account = new BankAccount(balance);
        return account;
    }
    public float getBalance(){
        return balance;
    }
    public void payment(float value){
        this.balance+=value;
    }
    public void setBalance(float value){
        this.balance+=value;
    }
}
