package Voters;

import CanBeVoted.CanBeVoted;
import CanBeVoted.Movie;
import CanBeVoted.BankAccount;

/**
 * Trieda MovieExpert reprezentuje experta na filmy v hlasovacom systéme.
 * Tento expert má schopnosť hlasovať s váhou 2, čo znamená, že jeho hlas má dvojnásobný vplyv
 * oproti štandardným hlasom.
 */
public class MovieExpert extends MiddleClass {

    /**
     * Konštruktor pre vytvorenie filmového experta s používateľským menom a heslom.
     *
     * @param username Používateľské meno experta
     * @param password Heslo experta
     */
    public MovieExpert(String username, String password) {
        super(username, password);
    }

    /**
     * Konštruktor pre vytvorenie filmového experta s používateľským menom, heslom a bankovým účtom.
     *
     * @param username Používateľské meno experta
     * @param password Heslo experta
     * @param bankAccount Bankový účet experta
     */
    public MovieExpert(String username, String password, BankAccount bankAccount) {
        super(username, password, bankAccount);
    }

    /**
     * Metóda umožňuje expertovi hlasovať za objekt CanBeVoted s váhou 2.
     * Tento hlas má väčší vplyv v porovnaní s bežnými hlasmi.
     *
     * @param canBeVoted Objekt, za ktorý sa hlasuje
     */
    @Override
    public void vote(CanBeVoted canBeVoted) {
        canBeVoted.addVote(2); // Pridanie dvojnásobného hlasu
        voted(); // Zaznamenanie, že expert už hlasoval
    }
}
