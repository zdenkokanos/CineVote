package CanBeVoted;

import VotingRoom.People;

import java.io.Serializable;

/**
 * Trieda BankAccount reprezentuje bankový účet, ktorý uchováva informácie ako IBAN, názov banky,
 * BIC kód a zostatok na účte. Táto trieda je serializovateľná, čo umožňuje jej ukladanie a načítanie
 * v rámci prúdov objektov.
 */
public class BankAccount implements Serializable {
    private String IBAN;
    private String bank;
    private String BIC_CODE;
    private float balance;

    /**
     * Konštruktor pre vytvorenie bankového účtu s počiatočným zostatkom. Používa sa, keď
     * účet ešte nemá priradené bankové údaje, ale už má určitý zostatok.
     *
     * @param balance počiatočný zostatok na účte
     */
    public BankAccount(float balance){
        this.balance = balance;
    }

    /**
     * Konštruktor pre vytvorenie bankového účtu so špecifickými bankovými informáciami
     * a počiatočným zostatkom nastaveným na 0. Tento typ účtu využívajú {@link Actor}, {@link Director}.
     *
     * @param IBAN medzinárodné bankové číslo účtu
     * @param bank názov banky
     * @param BIC_CODE bankový identifikačný kód
     */
    public BankAccount(String IBAN, String bank, String BIC_CODE) {
        this.IBAN = IBAN;
        this.bank = bank;
        this.BIC_CODE = BIC_CODE;
        this.balance = 0; // Každý účet je na začiatku nastavený na 0
    }

    /**
     * Statická metóda na vytvorenie bankového účtu s počiatočným zostatkom.
     *
     * @param balance počiatočný zostatok, ktorý bude priradený k novému účtu
     * @return nový bankový účet s daným zostatkom
     */
    public static BankAccount createAccount(float balance){
        return new BankAccount(balance);
    }

    /**
     * Vracia aktuálny zostatok na bankovom účte.
     *
     * @return aktuálny zostatok na účte
     */
    public float getBalance(){
        return balance;
    }

    /**
     * Pripíše sumu k zostatku na účte.
     *
     * @param value suma, ktorá bude pripísaná k zostatku
     */
    public void payment(float value){
        this.balance += value;
    }

    /**
     * Nastaví alebo zmení zostatok na účte. Metóda aktuálne pripisuje sumu k existujúcemu
     * zostatku, čo môže byť zamýšľané ako pridanie alebo odobratie sumy v závislosti na znamienku sumy.
     *
     * @param value suma, ktorá sa pripočíta k aktuálnemu zostatku
     */
    public void setBalance(float value){
        this.balance += value;
    }
}
