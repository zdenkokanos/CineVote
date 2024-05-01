package CanBeVoted;
/**
 * Rozhranie Votable je určené pre objekty, ktoré môžu byť hlasované.
 * Poskytuje metódy na získanie názvu objektu a počtu hlasov, ktoré získal.
 */
public interface Votable {
    /**
     * Vráti názov objektu, ktorý môže byť hlasovaný.
     *
     * @return Názov objektu.
     */
    String getName();

    /**
     * Vráti počet hlasov, ktoré objekt získal.
     *
     * @return Počet hlasov.
     */
    int getVotes();
}