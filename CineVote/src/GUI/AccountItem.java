package GUI;

import CanBeVoted.Payable;

/**
 * Trieda AccountItem slúži ako dátový kontajner pre účty, ktoré môžu byť zobrazené a vybrané v rozbaľovacom zozname (ComboBox).
 * Umožňuje spojenie zobrazovacieho reťazca s objektom implementujúcim rozhranie Payable.
 */
public class AccountItem {
    private String displayString;
    private Payable payable;

    /**
     * Konštruktor vytvára inštanciu AccountItem s daným zobrazovacím reťazcom a platobným účtom.
     *
     * @param displayString Reťazec, ktorý sa zobrazí v rozbaľovacom zozname.
     * @param payable       Objekt, ktorý umožňuje platby, priradený k tomuto účtu.
     */
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