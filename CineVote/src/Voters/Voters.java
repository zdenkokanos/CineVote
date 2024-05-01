package Voters;

import CanBeVoted.*;
import VotingRoom.*;

import java.io.*;

/**
 * Abstraktná trieda Voters definuje základnú štruktúru a funkcie pre všetkých účastníkov hlasovacieho procesu.
 * Každý volič má prístup k hlasovaniu, správe svojho bankového účtu.
 */
public abstract class Voters implements VotingProcess, Serializable, People {
    private String username;
    private String password;
    private boolean voted;
    private BankAccount bankAccount;

    /**
     * Konštruktor pre voliča s používateľským menom a heslom.
     *
     * @param username Používateľské meno voliča
     * @param password Heslo voliča
     */
    public Voters(String username, String password) {
        this.username = username;
        this.password = password;
    }

    /**
     * Konštruktor pre voliča s používateľským menom, heslom a bankovým účtom.
     *
     * @param username Používateľské meno voliča
     * @param password Heslo voliča
     * @param bankAccount Bankový účet voliča
     */
    public Voters(String username, String password, BankAccount bankAccount) {
        this.username = username;
        this.password = password;
        this.bankAccount = bankAccount;
    }

    /**
     * Metóda umožňuje voličovi hlasovať za objekt typu CanBeVoted.
     *
     * @param canBeVoted Objekt, za ktorý sa hlasuje
     */
    public void vote(CanBeVoted canBeVoted) {
        canBeVoted.addVote(1);
        voted();
    }

    /**
     * Vracia používateľské meno voliča.
     *
     * @return Používateľské meno
     */
    public String getUsername() {
        return username;
    }

    /**
     * Vracia heslo voliča.
     *
     * @return Heslo
     */
    public String getPassword() {
        return password;
    }

    /**
     * Vracia informáciu, či volič už hlasoval.
     *
     * @return true, ak volič už hlasoval, inak false
     */
    public boolean getVoted() {
        return voted;
    }

    /**
     * Zaznamenáva, že volič už hlasoval.
     */
    public void voted() {
        this.voted = true;
    }

    /**
     * Vracia zostatok na bankovom účte voliča.
     *
     * @return Zostatok na účte
     */
    public float getBalance() {
        return bankAccount.getBalance();
    }

    /**
     * Vracia bankový účet voliča.
     *
     * @return Bankový účet
     */
    public BankAccount getBankAccount(){
        return bankAccount;
    }
}
