package CanBeVoted;

/**
 * Rozhranie Payable definuje základné metódy pre objekty, ktoré môžu vykonávať platobné operácie pre prijatie platby. ({@link Actor}, {@link Director})
 * Toto rozhranie sa zameriava na poskytnutie metód pre získanie mena a bankového účtu subjektu,
 * ktorý môže príjmať platby.
 */
public interface Payable {
    /**
     * Vráti meno subjektu schopného prijať platbu.
     *
     * @return meno subjektu príjmajúceho platbu.
     */
    String getName();

    /**
     * Vráti bankový účet subjektu schopného prijať platbu.
     *
     * @return bankový účet subjektu, ktorý príjma platbu.
     */
    BankAccount getAccount();

}
