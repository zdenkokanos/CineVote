package CanBeVoted;

import java.io.Serializable;

/**
 * Abstraktná trieda CanBeVoted predstavuje objekty, ktoré môžu byť predmetom hlasovania.
 * Obsahuje základnú funkcionalitu pre správu hlasov, ktoré môžu byť pridelené rôznym entitám.
 * Táto trieda je serializovateľná, čo umožňuje jej ukladanie a načítanie v rámci prúdov objektov.
 */
public abstract class CanBeVoted implements Serializable {
    private int votes;

    /**
     * Konštruktor, ktorý inicializuje počet hlasov na 0.
     */
    public CanBeVoted() {
        this.votes = 0;
    }

    /**
     * Pridá hlas(y) s určitou váhou k celkovému počtu hlasov.
     * Táto metóda umožňuje pridanie viacerých hlasov naraz, ak je to potrebné.
     *
     * @param weight váha pridávaného hlasu/hlasov
     */
    public void addVote(int weight) {
        this.votes += weight;
    }

    /**
     * Vracia celkový počet hlasov, ktoré boli pridelené objektu.
     *
     * @return počet hlasov pridelených objektu
     */
    public int getVotes() {
        return votes;
    }

}
