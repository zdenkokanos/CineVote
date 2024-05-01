package VotingRoom;

import CanBeVoted.BankAccount;
import CanBeVoted.CanBeVoted;

/**
 * Rozhranie People definuje základné metódy pre každého účastníka hlasovacieho procesu.
 * Každá osoba musí byť schopná získať svoje prihlasovacie údaje, spravovať svoj bankový účet,
 * hlasovať a sledovať, či už hlasovala.
 */
public interface People {
    /**
     * Vráti používateľské meno osoby.
     *
     * @return používateľské meno
     */
    String getUsername();

    /**
     * Vráti heslo osoby.
     *
     * @return heslo
     */
    String getPassword();

    /**
     * Vráti aktuálny zostatok na bankovom účte osoby.
     *
     * @return zostatok na účte
     */
    float getBalance();

    /**
     * Vráti informáciu o tom, či osoba už hlasovala.
     *
     * @return true, ak osoba už hlasovala, inak false
     */
    boolean getVoted();

    /**
     * Umožní osobe hlasovať za objekt, ktorý môže byť hlasovaný.
     *
     * @param canBeVoted objekt, za ktorý sa hlasuje
     */
    void vote(CanBeVoted canBeVoted);

    /**
     * Vykoná platbu z bankového účtu osoby na iný bankový účet.
     *
     * @param receiverBankAccount bankový účet príjemcu
     * @param value suma, ktorá sa má previesť
     * @param senderBankaccount bankový účet odosielateľa
     */
    default void pay(BankAccount receiverBankAccount, float value, BankAccount senderBankaccount) {
        senderBankaccount.setBalance(-value);
        receiverBankAccount.payment(value);
    }

    /**
     * Vráti bankový účet osoby.
     *
     * @return bankový účet
     */
    BankAccount getBankAccount();


}
