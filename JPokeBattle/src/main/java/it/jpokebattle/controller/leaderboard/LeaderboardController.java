package it.jpokebattle.controller.leaderboard;

import it.jpokebattle.controller.SceneManager;
import it.jpokebattle.model.leaderboard.LeaderboardEntry;
import it.jpokebattle.model.player.HumanPlayer;
import it.jpokebattle.model.player.Player;
import it.jpokebattle.model.pokemon.Pokemon;
import it.jpokebattle.util.SoundManager;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.time.LocalDateTime;

import java.io.IOException;
import java.util.List;
public class LeaderboardController {

    private SoundManager soundMan = SoundManager.getInstance();

    @FXML
    protected Button menuButton;

    @FXML
    private TableView<LeaderboardEntry> tableView;
    @FXML
    private TableColumn<LeaderboardEntry, LocalDateTime> Date;
    @FXML
    private TableColumn<LeaderboardEntry, String> Win;
    @FXML
    private TableColumn<LeaderboardEntry, String> pokemon1;
    @FXML
    private TableColumn<LeaderboardEntry, String> pokemon2;
    @FXML
    private TableColumn<LeaderboardEntry, String> pokemon3;
    @FXML
    private TableColumn<LeaderboardEntry, String> pokemon4;
    @FXML
    private TableColumn<LeaderboardEntry, String> pokemon5;
    @FXML
    private TableColumn<LeaderboardEntry, String> pokemon6;

    public void initialize() {
        Date.setCellValueFactory(new PropertyValueFactory<>("timestamp"));
        Win.setCellValueFactory(new PropertyValueFactory<>("win"));
        pokemon1.setCellValueFactory(new PropertyValueFactory<>("pokemon1"));
        pokemon2.setCellValueFactory(new PropertyValueFactory<>("pokemon2"));
        pokemon3.setCellValueFactory(new PropertyValueFactory<>("pokemon3"));
        pokemon4.setCellValueFactory(new PropertyValueFactory<>("pokemon4"));
        pokemon5.setCellValueFactory(new PropertyValueFactory<>("pokemon5"));
        pokemon6.setCellValueFactory(new PropertyValueFactory<>("pokemon6"));

        tableView.setItems(FXCollections.observableArrayList());
        addRow(5);
        addRow(5);
        addRow(5);
        addRow(5);
        addRow(5);
        addRow(5);
        addRow(5);
        addRow(5);
        addRow(5);
        addRow(5);
        addRow(5);
        addRow(5);
    }

    @FXML
    public void backToMenu(ActionEvent event) throws IOException {
        soundMan.buttonClick();
        SceneManager.switchScene(event, "fxml.menu", "css.menu");
        if (SoundManager.isVolumeOn()) soundMan.switchTrack("audio.menu");

    }

    public void addRow(int playerScore) {
        Player newPlayer = HumanPlayer.getInstance();
        LocalDateTime now = LocalDateTime.now();
        List<Pokemon> team = newPlayer.getTeam().getPokemonList();
        LeaderboardEntry newEntry = new LeaderboardEntry(now, String.valueOf(playerScore), team);
        tableView.getItems().add(newEntry);
    }
}
