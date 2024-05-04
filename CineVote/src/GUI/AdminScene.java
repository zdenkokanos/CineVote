package GUI;

import Voters.Admin;
import VotingRoom.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;


/**
 * Trieda AdminScene poskytuje grafické rozhranie pre administrátora hlasovacieho systému.
 * Administrátor môže pridať filmy do hlasovania, sledovať priebeh hlasovania, zastaviť hlasovanie
 * alebo sa odhlásiť z aplikácie. Scéna obsahuje rôzne grafické prvky ako tlačidlá, grafy a kombinované zoznamy.
 */
public class AdminScene extends Scene {
    private Button addMovie = new Button("Add movies");
    private Button startNew = new Button("Stop voting");
    private Button notifications = new Button("");
    private StackPane notificationStackPane = new StackPane();
    private Button exit = new Button("Exit");
    private Button logOut = new Button("Log Out");
    private Observer votingRoomObserver;

    private BarChart<String, Number> barChartMovies;
    private BarChart<String, Number> barChartActors;
    private BarChart<String, Number> barChartDirectors;
    private String css = this.getClass().getResource("AdminSceneCSS.css").toExternalForm();
    private ComboBox<String> graphSelector;

    /**
     * Konštruktor pre triedu AdminScene inicializuje grafické prvky a nastavuje
     * akcie pre tlačidlá a iné interaktívne komponenty. Scéna umožňuje administrátorovi
     * riadiť hlasovanie, sledovať priebežné výsledky a zasahovať do procesu podľa potreby.
     * <p>
     * Scéna obsahuje dynamické prvky ako grafy, ktoré sa aktualizujú v reálnom čase podľa
     * stavu v hlasovacej miestnosti a odozvy od používateľov.
     *
     * @param stage      hlavné okno aplikácie, kde bude scéna zobrazená.
     * @param votingRoom referencia na objekt VotingRoom, ktorý spravuje hlasovací proces.
     * @param admin      aktuálne prihlásený administrátor.
     */
    public AdminScene(Stage stage, VotingRoom votingRoom, Admin admin) {
        //sets the pane and the main elements
        super(new AnchorPane(), 500, 600);
        AnchorPane root = (AnchorPane) getRoot();
        VBox vbox = new VBox();
        vbox.setAlignment(Pos.CENTER);
        root.getStyleClass().add("background");

        Insets buttonMargin = new Insets(0, 0, 10, 0);  // Top, Right, Bottom, Left margins
        VBox.setMargin(addMovie, buttonMargin);
        VBox.setMargin(startNew, buttonMargin);
        VBox.setMargin(logOut, buttonMargin);
        VBox.setMargin(exit, buttonMargin);
        startNew.setId("buttons");
        exit.setId("Ebuttons");
        logOut.setId("Lbuttons");
        addMovie.setId("buttons");
        getStylesheets().add(css);

        VBox leftButtons = new VBox(10);  // Spacing between buttons
        VBox rightButtons = new VBox(10);

        leftButtons.getChildren().addAll(addMovie, startNew);
        rightButtons.getChildren().addAll(logOut, exit);

        HBox buttonGroups = new HBox(50);  // Increase spacing to spread groups apart
        buttonGroups.getChildren().addAll(leftButtons, rightButtons);
        buttonGroups.setAlignment(Pos.CENTER);

        // Load the notification icon and its count of notifications in small red circle
        Image notificationImage = new Image(getClass().getResourceAsStream("/notificationIcon.png"));
        if (notificationImage != null)
        {
            ImageView notifIcon = new ImageView(notificationImage);
            notifIcon.setFitWidth(24);
            notifIcon.setFitHeight(24);
            notifications.setGraphic(notifIcon);
            notifications.setStyle("-fx-background-color: #ececec");
            notifications.setOnMouseEntered(e -> notifications.setStyle("-fx-background-color: #a1a1a1;"));
            notifications.setOnMouseExited(e -> notifications.setStyle("-fx-background-color: #ececec;"));

            Circle counterCircle = new Circle(5, Color.RED);
            Label counterLabel = new Label();
            counterLabel.setText(String.valueOf(votingRoom.getNominationCount()));
            counterLabel.setTextFill(Color.BLACK);
            counterLabel.setFont(Font.font("Arial", FontWeight.BOLD, 10));
            StackPane labelPane = new StackPane(counterLabel); // Wrap label in StackPane to apply padding
            labelPane.setPadding(new Insets(-1, 2, 0, 0));

            notificationStackPane.getChildren().addAll(counterCircle, labelPane);
            StackPane.setAlignment(counterLabel, Pos.CENTER);
            StackPane.setAlignment(counterCircle, Pos.TOP_RIGHT);
            StackPane.setAlignment(counterLabel, Pos.TOP_RIGHT);

            // Position the notificationStackPane
            AnchorPane.setTopAnchor(notifications, 10.0);
            AnchorPane.setRightAnchor(notifications, 10.0);
            AnchorPane.setTopAnchor(notificationStackPane, 15.0);
            AnchorPane.setRightAnchor(notificationStackPane, 15.0);
        } else
        {
            System.err.println("Failed to load notification icon.");
        }

        //sets buttons on action
        addMovie.setOnAction(e -> {
            AddMovieScene addMovieScene = new AddMovieScene(stage, votingRoom, admin, "Nominate movies");
            stage.setScene(addMovieScene);
        });

        notifications.setOnAction(e -> {
            SuggestNotificationScene suggestNotificationScene = new SuggestNotificationScene(votingRoom, stage, admin);
            stage.setScene(suggestNotificationScene);
        });

        exit.setOnAction(e -> stage.close());

        startNew.setOnAction(e -> {
            //votingRoom.restartVoting();
            votingRoom.setStatus(false);
            votingRoom.saveVotingRoom();
            System.out.println(votingRoom.getStatus());
            Results results = new Results(stage, votingRoom);
            stage.setScene(results);
        });

        logOut.setOnAction(e -> stage.setScene(new LogInScene(stage, votingRoom)));
        //Observer
        votingRoomObserver = new Observer(votingRoom);
        votingRoom.addObserver(votingRoomObserver);

        // Bar Chart Movies
        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();
        barChartMovies = new BarChart<>(xAxis, yAxis);
        yAxis.setTickUnit(2);
        yAxis.setLabel("Movies");
        barChartMovies.setTitle("Voting is still running...");
        barChartMovies.getData().clear();
        barChartMovies.getData().add(votingRoomObserver.update(votingRoom.getMovies(), barChartMovies));
        VBox vBoxBarCharts = new VBox(barChartMovies);
        //Bar Chart Actors
        CategoryAxis xAxis2 = new CategoryAxis();
        NumberAxis yAxis2 = new NumberAxis();
        yAxis2.setTickUnit(2);
        barChartActors = new BarChart<>(xAxis2, yAxis2);
        yAxis2.setLabel("Actors");
        barChartActors.setTitle("Voting is still running...");
        //Bar Chart Directors
        CategoryAxis xAxis3 = new CategoryAxis();
        NumberAxis yAxis3 = new NumberAxis();
        yAxis3.setTickUnit(2);
        barChartDirectors = new BarChart<>(xAxis3, yAxis3);
        yAxis3.setLabel("Directors");
        barChartDirectors.setTitle("Voting is still running...");


        // Initialize the graph selector ComboBox
        graphSelector = new ComboBox<>();
        graphSelector.setPromptText("Movies");
        ObservableList<String> graphOptions = FXCollections.observableArrayList("Movies", "Actors", "Directors");
        graphSelector.setItems(graphOptions);
        //graphSelector to change which graph you want to look at
        graphSelector.setOnAction(e -> {
            String selectedGraph = graphSelector.getValue();
            if (selectedGraph != null)
            {
                switch (selectedGraph)
                {
                    case "Movies":
                        barChartMovies.getData().clear();
                        barChartMovies.getData().add(votingRoomObserver.update(votingRoom.getMovies(), barChartMovies));
                        vBoxBarCharts.getChildren().clear();
                        vBoxBarCharts.getChildren().add(barChartMovies);
                        break;
                    case "Actors":
                        barChartActors.getData().clear();
                        barChartActors.getData().add(votingRoomObserver.update(votingRoom.getActors(), barChartActors));
                        vBoxBarCharts.getChildren().clear();
                        vBoxBarCharts.getChildren().add(barChartActors);
                        break;
                    case "Directors":
                        barChartDirectors.getData().clear();
                        barChartDirectors.getData().add(votingRoomObserver.update(votingRoom.getDirectors(), barChartDirectors));
                        vBoxBarCharts.getChildren().clear();
                        vBoxBarCharts.getChildren().add(barChartDirectors);
                        break;
                }
            }
        });

        //adds the elements to the pane
        Insets chartMargin = new Insets(20, 0, 0, 0);  // Top, Right, Bottom, Left margins
        VBox.setMargin(vBoxBarCharts, chartMargin);
        vbox.getChildren().add(vBoxBarCharts);
        vbox.getChildren().add(buttonGroups);
        root.getChildren().addAll(vbox, notifications, notificationStackPane, graphSelector);


    }

}
