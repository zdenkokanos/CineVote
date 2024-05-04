package CanBeVoted;

import VotingRoom.People;

import java.io.Serializable;

/**
 * Trieda Director reprezentuje jednotlivca, za, ktorého sa môže hlasovať ale ktorý sa zároveň môže zúčastniť hlasovacích procesov,
 * spravovať bankový účet a vykonávať platby. Táto trieda implementuje rozhrania {@link Serializable},
 * {@link Payable} a {@link People}.
 */
public class Director extends CanBeVoted implements Serializable, Payable, People, Votable {
    private String name;
    private String username;
    private String password;
    private int age;
    private boolean voted;
    private BankAccount bankAccount;

    /**
     * Konštruktor pre vytvorenie režiséra len s menom a vekom, bez bankového účtu.
     *
     * @param name meno režiséra
     * @param age vek režiséra
     */
    public Director(String name, int age) {
        this.name = name;
        this.age = age;
    }

    /**
     * Konštruktor pre vytvorenie režiséra s menom, vekom a priradeným bankovým účtom.
     *
     * @param name meno režiséra
     * @param age vek režiséra
     * @param bankAccount bankový účet režiséra
     */
    public Director(String name, int age, BankAccount bankAccount) {
        this.name = name;
        this.age = age;
        this.bankAccount = bankAccount;
        this.username = name;  //Pre prihlásenie je možné použiť meno režiséra s prázdnym heslom
        this.password = "";
    }

    /**
     * Vracia meno režiséra.
     *
     * @return meno režiséra
     */
    public String getName() {
        return name;
    }

    /**
     * Vracia vek režiséra.
     *
     * @return vek režiséra
     */
    public int getAge() {
        return age;
    }

    /**
     * Vracia bankový účet režiséra.
     *
     * @return bankový účet
     */
    public BankAccount getAccount() {
        return bankAccount;
    }

    /**
     * Vracia heslo režiséra.
     *
     * @return heslo režiséra
     */
    public String getPassword() {
        return password;
    }

    /**
     * Vracia používateľské meno režiséra.
     *
     * @return používateľské meno režiséra
     */
    public String getUsername() {
        return username;
    }

    /**
     * Vracia aktuálny zostatok na bankovom účte režiséra.
     *
     * @return zostatok na účte
     */
    public float getBalance() {
        return bankAccount.getBalance();
    }

    /**
     * Vracia informáciu, či režisér už hlasoval.
     *
     * @return true, ak režisér hlasoval, inak false
     */
    public boolean getVoted() {
        return voted;
    }

    /**
     * Umožňuje režisérovi hlasovať za kandidáta alebo položku, ktorá môže byť predmetom hlasovania.
     * Pridá jeden hlas zadanému objektu {@link CanBeVoted}.
     *
     * @param canBeVoted objekt, za ktorý sa hlasuje
     */
    public void vote(CanBeVoted canBeVoted) {
        canBeVoted.addVote(1);
        this.voted = true;
    }

    /**
     * Vracia bankový účet režiséra.
     *
     * @return bankový účet režiséra
     */
    public BankAccount getBankAccount(){
        return bankAccount;
    }
}
