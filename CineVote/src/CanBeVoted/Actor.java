package CanBeVoted;

import VotingRoom.People;
import java.io.Serializable;

/**
 * Trieda Actor reprezentuje jednotlivca, za, ktorého sa môže hlasovať ale ktorý sa zároveň môže zúčastniť hlasovacích procesov,
 * spravovať bankový účet a vykonávať platby. Táto trieda implementuje rozhrania {@link Serializable},
 * {@link Payable} a {@link People}.
 */
public class Actor extends CanBeVoted implements Serializable, Payable, People, Votable {
    private String name;
    private int age;
    private String username;
    private String password;
    private BankAccount bankAccount;
    private boolean voted;

    /**
     * Konštruktor vytvára nového herca so zadaným menom a vekom, pôvodne bez bankového účtu.
     *
     * @param name meno herca
     * @param age vek herca
     */
    public Actor(String name, int age) {
        this.name = name;
        this.age = age;
    }

    /**
     * Konštruktor vytvára nového herca so zadaným menom, vekom a priradeným bankovým účtom.
     *
     * @param name meno herca
     * @param age vek herca
     * @param bankAccount bankový účet priradený hercovi
     */
    public Actor(String name, int age, BankAccount bankAccount) {
        this.name = name;
        this.age = age;
        this.bankAccount = bankAccount;
        this.username = name; //Pre prihlásenie je možné použiť meno herca s prázdnym heslom
        this.password = "";
    }

    /**
     * Vracia meno herca.
     *
     * @return meno herca
     */
    public String getName() {
        return name;
    }

    /**
     * Vracia vek herca.
     *
     * @return vek herca
     */
    public int getAge() {
        return age;
    }

    /**
     * Vracia bankový účet herca.
     *
     * @return bankový účet herca
     */
    public BankAccount getAccount() {
        return bankAccount;
    }

    /**
     * Vracia heslo herca.
     *
     * @return heslo herca
     */
    public String getPassword() {
        return password;
    }

    /**
     * Vracia používateľské meno herca.
     *
     * @return používateľské meno herca
     */
    public String getUsername() {
        return username;
    }

    /**
     * Vracia aktuálny zostatok na bankovom účte herca.
     *
     * @return aktuálny zostatok na bankovom účte
     */
    public float getBalance() {
        return bankAccount.getBalance();
    }

    /**
     * Vracia stav hlasovania herca.
     *
     * @return true, ak herec už hlasoval, inak false
     */
    public boolean getVoted() {
        return voted;
    }

    /**
     * Umožňuje hercovi hlasovať za kandidáta alebo položku, ktorá môže byť predmetom hlasovania.
     * Táto metóda zaregistruje jeden hlas pre zadaný objekt {@code CanBeVoted}.
     *
     * @param canBeVoted kandidát alebo položka, za ktorú sa hlasuje
     */
    public void vote(CanBeVoted canBeVoted){
        canBeVoted.addVote(1);
        this.voted = true;
    }

    /**
     * Vracia bankový účet spojený s týmto hercom.
     *
     * @return bankový účet
     */
    public BankAccount getBankAccount(){
        return bankAccount;
    }
}
