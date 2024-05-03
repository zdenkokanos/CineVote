package GUI;

import VotingRoom.VotingRoom;
import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.*;

/**
 * Hlavná trieda aplikácie CineVote, ktorá dedí od triedy Application.
 * Slúži ako vstupný bod pre JavaFX aplikáciu, kde inicializuje hlavné okno,
 * nastavuje ikonu, zisťuje stav hlasovania a zobrazuje príslušné scény podľa aktuálneho stavu hlasovania.
 */
public class MainGUI extends Application {

    private VotingRoom votingRoom = new VotingRoom(); // Objekt hlasovacej miestnosti, ktorý spravuje hlasovací proces.

    /**
     * Metóda start inicializuje hlavné okno aplikácie a nastaví základné vlastnosti ako titulok,
     * ikonu a nemožnosť zmeny veľkosti okna. Následne načíta stav hlasovacej miestnosti
     * a podľa toho rozhodne, ktorá scéna sa má zobraziť.
     *
     * @param primaryStage primárne okno aplikácie, ktoré JavaFX poskytuje ako hlavný stage.
     * @throws Exception v prípade, že nastane chyba pri načítaní ikony alebo inej súčasti aplikácie.
     */
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("CineVote"); // Nastavenie titulku okna
        Image icon = new Image("CineVote.png"); // Načítanie ikony aplikácie
        primaryStage.getIcons().add(icon); // Pridanie ikony do hlavného okna
        primaryStage.setResizable(false); // Zakázanie zmeny veľkosti okna

        votingRoom.loadVotingRoom(); // Načítanie stavu hlasovacej miestnosti
        if (votingRoom.getStatus()) { // Kontrola, či hlasovanie stále prebieha
            LogInScene logInScene = new LogInScene(primaryStage, votingRoom); // Vytvorenie scény pre prihlásenie
            primaryStage.setScene(logInScene); // Nastavenie scény pre prihlásenie
        } else {
            Results results = new Results(primaryStage, votingRoom); // Vytvorenie scény s výsledkami hlasovania
            primaryStage.setScene(results); // Nastavenie scény s výsledkami
        }
        primaryStage.show(); // Zobrazenie hlavného okna
    }

    /**
     * Hlavná metóda aplikácie, ktorá spúšťa JavaFX aplikáciu.
     *
     * @param args argumenty príkazového riadku
     */
    public static void main(String[] args) {
        launch(args); // Spustenie JavaFX aplikácie
    }
}
