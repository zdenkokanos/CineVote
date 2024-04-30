package GUI;

import CanBeVoted.Payable;

public class AccountItem {
    private String displayString;
    private Payable payable;

    public AccountItem(String displayString, Payable payable) {
        this.displayString = displayString;
        this.payable = payable;
    }

    public String getDisplayString() {
        return displayString;
    }

    public Payable getPayable() {
        return payable;
    }

    @Override
    public String toString() {
        return displayString; // This ensures the ComboBox shows the string we want
    }
}