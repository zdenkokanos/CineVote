package VotingRoom;

import CanBeVoted.Movie;
import CanBeVoted.Votable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;

import java.util.List;

/**
 * Rozhranie VotingObserver definuje metódy, ktoré musí implementovať každý pozorovateľ hlasovacieho procesu,
 * aby mohol reagovať na zmeny v zozname filmov alebo iných objektov a aktualizovať vizuálne zobrazenia.
 */
public interface VotingObserver {
    /**
     * Aktualizuje vizuálne zobrazenie na základe poskytnutého zoznamu filmov. Táto metóda
     * je určená na priame aktualizácie bez grafickej reprezentácie.
     *
     * @param movies Zoznam filmov, ktoré sa majú aktualizovať.
     */
    void update(List<Movie> movies);

    /**
     * Generická metóda na aktualizáciu a vytvorenie sérií dát pre zobrazenie v grafickom rozhraní
     * typu BarChart, založené na poskytnutom zozname objektov.
     * Táto metóda umožňuje vizualizáciu rôznych typov dát v jednom grafe.
     *
     * @param list Zoznam objektov, ktoré sa majú zobraziť (môžu to byť filmy, režiséri, herci atď.)
     * @param barChart Grafický komponent BarChart, kde sa majú dáta zobraziť
     * @return Séria dát pre BarChart, obsahujúca názvy a príslušné počty hlasov alebo iné numerické hodnoty
     * @param <T> Typ elementu, ktorý definuje, aký druh objektov je spracovaný
     */
    <T extends Votable> XYChart.Series<String, Number> update(List<T> list, BarChart<String, Number> barChart);
}
