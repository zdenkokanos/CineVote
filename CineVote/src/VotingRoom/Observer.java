package VotingRoom;

import CanBeVoted.Movie;
import CanBeVoted.Votable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import java.util.List;

/**
 * Trieda Observer slúži ako pozorovateľ, ktorý monitoruje a aktualizuje vizuálne zobrazenia
 * hlasovacích výsledkov v hlasovacej miestnosti. Táto trieda používa JavaFX komponenty na grafické
 * znázornenie hlasov.
 */
public class Observer implements VotingObserver {
    private VotingRoom votingRoom;

    /**
     * Konštruktor pre vytvorenie inštancie pozorovateľa, ktorý je priradený k určitej hlasovacej miestnosti.
     *
     * @param votingRoom Hlasovacia miestnosť, ku ktorej je pozorovateľ priradený
     */
    public Observer(VotingRoom votingRoom) {
        this.votingRoom = votingRoom;
    }

    /**
     * Aktualizuje a vráti sériu dát pre zobrazenie v grafe BarChart na základe zoznamu hlasovaných objektov.
     * Táto metóda je generická a môže obsahovať rôzne typy objektov (filmy, režisérov, hercov).
     *
     * @param list Zoznam objektov (Movie, Director, Actor), ktoré sa majú zobraziť
     * @param barChart Graf BarChart, do ktorého sa dáta vkladajú
     * @return Séria dát typu XYChart.Series, ktorá obsahuje názvy a počet hlasov
     */
    public <T extends Votable> XYChart.Series<String, Number> update(List<T> list, BarChart<String, Number> barChart) {
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        for (T item : list) {
            series.getData().add(new XYChart.Data<>(item.getName(), item.getVotes()));
        }
        return series;
    }

    /**
     * Aktualizuje zobrazenie informácií o filmoch v grafe.
     *
     * @param movie Zoznam filmov, ktoré sa majú aktualizovať
     */
    public void update(List<Movie> movie) {
    }

}
