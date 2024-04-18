package Voters;

import CanBeVoted.BankAccount;

public class LowerClass extends Voters {

    //this class does not have any special methods or functionalities just have a right to vote
    public LowerClass(String username, String password){
        super(username, password);
    }
    public LowerClass(String username, String password, BankAccount bankAccount) {
        super(username, password, bankAccount);
    }

}
