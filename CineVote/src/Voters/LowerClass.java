package Voters;

import CanBeVoted.BankAccount;

/**
 * Trieda LowerClass reprezentuje skupinu voličov, ktorá má základné právo hlasovať.
 * Táto trieda neobsahuje žiadne špeciálne metódy alebo funkcie, okrem základného práva na hlasovanie.
 */
public class LowerClass extends Voters {

    /**
     * Konštruktor pre vytvorenie voliča z nižšej triedy s používateľským menom a heslom.
     *
     * @param username Používateľské meno voliča
     * @param password Heslo voliča
     */
    public LowerClass(String username, String password){
        super(username, password);
    }

    /**
     * Konštruktor pre vytvorenie voliča z nižšej triedy s používateľským menom, heslom a bankovým účtom.
     *
     * @param username Používateľské meno voliča
     * @param password Heslo voliča
     * @param bankAccount Bankový účet voliča
     */
    public LowerClass(String username, String password, BankAccount bankAccount) {
        super(username, password, bankAccount);
    }

}
