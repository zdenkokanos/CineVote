package GUI;

import CanBeVoted.Actor;
import CanBeVoted.Director;
import CanBeVoted.Movie;
import VotingRoom.VotingRoom;
import VotingRoom.Observer;
import VotingRoom.VotingRoom.Winners;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

import java.util.List;

/**
 * Trieda Results zobrazuje grafické výsledky hlasovania v grafoch.
 * Umožňuje používateľom zobraziť výsledky pre filmy, režisérov a hercov,
 * a poskytuje možnosť reštartovať hlasovanie po poskytnutí hesla admina.
 */
public class Results extends Scene {
    private Observer votingRoomObserver;
    private BarChart<String, Number> barChartMovies;
    private BarChart<String, Number> barChartActors;
    private BarChart<String, Number> barChartDirectors;
    private Button tableView = new Button("Table View");
    private Button startNewVote = new Button("Start new voting");
    private ComboBox<String> graphSelector;
    private String css = this.getClass().getResource("AdminSceneCSS.css").toExternalForm();
    private Winners winners;

    /**
     * Konštruktor pre triedu Results, ktorý inicializuje grafické komponenty a nastavuje správanie tlačidiel.
     * Vytvára grafy pre zobrazenie hlasovacích výsledkov a obsahuje logiku pre spustenie nového hlasovania.
     * @param stage hlavné okno, na ktorom sa zobrazuje scéna
     * @param votingRoom referencia na objekt hlasovacej miestnosti, ktorý obsahuje údaje o hlasovaní
     */
    public Results(Stage stage, VotingRoom votingRoom) {
        super(new VBox(), 800, 600);
        VBox vBox = (VBox) getRoot();
        //Observer
        votingRoomObserver = new Observer(votingRoom);
        votingRoom.addObserver(votingRoomObserver);
        getStylesheets().add(css);
        vBox.getStyleClass().add("backgroundR");
        winners = votingRoom.getWinners();
        // Bar Chart Movies
        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();
        barChartMovies = new BarChart<>(xAxis, yAxis);
        yAxis.setTickUnit(2);
        yAxis.setLabel("Movies");
        barChartMovies.setTitle("Voting has ended!");
        barChartMovies.getData().clear();
        barChartMovies.getData().add(votingRoomObserver.update(winners.getWinnersMovies(), barChartMovies));
        VBox vBoxBarCharts = new VBox(barChartMovies);
        //Bar Chart Actors
        CategoryAxis xAxis2 = new CategoryAxis();
        NumberAxis yAxis2 = new NumberAxis();
        yAxis2.setTickUnit(2);
        barChartActors = new BarChart<>(xAxis2, yAxis2);
        yAxis2.setLabel("Actors");
        barChartActors.setTitle("Voting has ended!");
        //Bar Chart Directors
        CategoryAxis xAxis3 = new CategoryAxis();
        NumberAxis yAxis3 = new NumberAxis();
        yAxis3.setTickUnit(2);
        barChartDirectors = new BarChart<>(xAxis3, yAxis3);
        yAxis3.setLabel("Directors");
        barChartDirectors.setTitle("Voting has ended!");
        tableView.setId("Lbuttons");
        startNewVote.setId("buttons");

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
                        barChartMovies.getData().add(votingRoomObserver.update(winners.getWinnersMovies(), barChartMovies));
                        vBoxBarCharts.getChildren().clear();
                        vBoxBarCharts.getChildren().add(barChartMovies);
                        break;
                    case "Actors":
                        barChartActors.getData().clear();
                        barChartActors.getData().add(votingRoomObserver.update(winners.getWinnersActors(), barChartActors));
                        vBoxBarCharts.getChildren().clear();
                        vBoxBarCharts.getChildren().add(barChartActors);
                        break;
                    case "Directors":
                        barChartDirectors.getData().clear();
                        barChartDirectors.getData().add(votingRoomObserver.update(winners.getWinnersDirectors(), barChartDirectors));
                        vBoxBarCharts.getChildren().clear();
                        vBoxBarCharts.getChildren().add(barChartDirectors);
                        break;
                }
            }
        });

        tableView.setOnAction(e -> {
            DetailScene detailScene = new DetailScene(stage, votingRoom, this);
            stage.setScene(detailScene);
        });

        HBox hBox = new HBox(tableView, startNewVote);
        hBox.setAlignment(Pos.CENTER);
        HBox.setMargin(tableView, new Insets(5, 15, 5, 5)); // top, right, bottom, left
        HBox.setMargin(startNewVote, new Insets(5, 15, 5, 5));

        startNewVote.setOnAction(e -> {
            showAdminPasswordDialog(stage, votingRoom);
        });


        vBox.getChildren().addAll(graphSelector, vBoxBarCharts, hBox);
    }

    private void showAdminPasswordDialog(Stage parentStage, VotingRoom votingRoom) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Admin Authentication");
        dialog.setHeaderText("Enter Admin Password");
        dialog.setContentText("Password:");

        // Hide the input with bullet characters for privacy
        PasswordField pwdField = new PasswordField();
        dialog.getEditor().setText("Enter your password");
        dialog.getEditor().setManaged(false);
        dialog.getEditor().setVisible(false);
        dialog.getDialogPane().setContentText("Password:");
        dialog.getDialogPane().setContent(pwdField);


        // Focus on the password field by default
        dialog.getDialogPane().expandedProperty().addListener((observable, oldValue, newValue) -> pwdField.requestFocus());

        dialog.showAndWait().ifPresent(password -> {
            password = pwdField.getText();
            if (password.equals("admin"))
            {  // Assuming "admin" is the correct password.
                votingRoom.restartVoting();
                Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION, "Voting has been restarted successfully.");
                confirmation.showAndWait();
                parentStage.close();
            } else
            {
                Alert error = new Alert(Alert.AlertType.ERROR, "Invalid password.");
                error.showAndWait();
            }
        });
    }

/**
 * DetailScene je vnorená trieda v Results, ktorá poskytuje detailné tabuľkové zobrazenie výsledkov.
 * Táto scéna zobrazuje mená a počet hlasov pre top 3 kandidátov v každej kategórii.
 */
    public class DetailScene extends Scene {
        private Winners winners;
        private Button graphView = new Button("Graph View");

        public DetailScene(Stage stage, VotingRoom votingRoom, Results results) {
            super(new VBox(20), 800, 600); // VBox as the root node with spacing of 20
            VBox root = (VBox) getRoot();
            root.setPadding(new Insets(25, 25, 25, 25));
            root.setAlignment(Pos.TOP_CENTER);
            getStylesheets().add(css);
            graphView.setId("Lbuttons");
            winners = votingRoom.getWinners();
            List<Movie> movies = winners.getWinnersMovies();
            List<Actor> actors = winners.getWinnersActors();
            List<Director> directors = winners.getWinnersDirectors();

            // Category sections
            String[] categoryNames = {"Movies", "Actors", "Directors"};

            for (String categoryName : categoryNames)
            {
                VBox categoryBox = new VBox(5);

                Label categoryLabel = new Label(categoryName);
                categoryLabel.setFont(Font.font("Arial", FontWeight.BOLD, 24)); // Set font to Arial, bold, 24 points
                categoryLabel.setTextFill(Color.GREEN); // Set text color to green
                categoryBox.getChildren().add(categoryLabel);


                switch (categoryName)
                {
                    case "Movies":
                        for (int j = 0; j < 3; j++)
                        {
                            if (movies.get(j).getVotes() != 0)
                            {
                                addVoteDetails(categoryBox, movies.get(j).getVotes(), movies.get(j).getTitle(), j);
                            }
                        }
                        break;
                    case "Actors":
                        for (int j = 0; j < 3; j++)
                        {
                            if (actors.get(j).getVotes() != 0)
                            {
                                addVoteDetails(categoryBox, actors.get(j).getVotes(), actors.get(j).getName(), j);
                            }
                        }
                        break;
                    case "Directors":
                        for (int j = 0; j < 3; j++)
                        {
                            if (directors.get(j).getVotes() != 0)
                            {
                                addVoteDetails(categoryBox, directors.get(j).getVotes(), directors.get(j).getName(), j);
                            }
                        }
                        break;
                }


                graphView.setOnAction(e -> {
                    stage.setScene(results);
                });

                root.getChildren().add(categoryBox);
            }
            root.getChildren().add(graphView);
        }

        private void addVoteDetails(VBox categoryBox, int votes, String name, int rank) {
            Text text1 = new Text("\tVotes: " + votes + "\t\t" + (rank + 1) + "." + " Place: ");
            Text text2 = new Text(name);
            text1.setFont(Font.font("Arial", 18));
            text2.setFont(Font.font("Arial", FontWeight.BOLD, 18));
            TextFlow textFlow = new TextFlow(text1, text2);
            categoryBox.getChildren().add(textFlow);
        }
    }
}
