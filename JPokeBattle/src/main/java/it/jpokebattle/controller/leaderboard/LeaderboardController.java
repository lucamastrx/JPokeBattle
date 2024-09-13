package it.jpokebattle.controller.leaderboard;

import com.fasterxml.jackson.annotation.JsonIgnore;
import it.jpokebattle.controller.SceneManager;
import it.jpokebattle.model.leaderboard.LeaderboardEntry;
import it.jpokebattle.model.player.HumanPlayer;
import it.jpokebattle.model.player.Player;
import it.jpokebattle.model.pokemon.Breed;
import it.jpokebattle.model.pokemon.Pokemon;
import it.jpokebattle.util.FileManager;
import it.jpokebattle.util.SoundManager;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.File;
import java.io.FileReader;
import java.time.LocalDateTime;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class LeaderboardController {

    private SoundManager soundMan = SoundManager.getInstance();
    @JsonIgnore
    public static HashMap<String, LeaderboardEntry> leaderboardEntryHashMap = new HashMap<>(FileManager.readJSON("json.leaderboard", LeaderboardEntry.class));
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



    @FXML
    public void initialize() {
        Date.setCellValueFactory(cellData -> cellData.getValue().getTimestampProperty());
        Win.setCellValueFactory(cellData -> cellData.getValue().getWinStatusProperty());
        pokemon1.setCellValueFactory(cellData -> cellData.getValue().getPokemon1Property());
        pokemon2.setCellValueFactory(cellData -> cellData.getValue().getPokemon2Property());
        pokemon3.setCellValueFactory(cellData -> cellData.getValue().getPokemon3Property());
        pokemon4.setCellValueFactory(cellData -> cellData.getValue().getPokemon4Property());
        pokemon5.setCellValueFactory(cellData -> cellData.getValue().getPokemon5Property());
        pokemon6.setCellValueFactory(cellData -> cellData.getValue().getPokemon6Property());
        // Leggi i dati JSON
        HashMap<String, LeaderboardEntry> leaderboardEntriesHashMap = FileManager.readJSON("json.leaderboard", LeaderboardEntry.class);

        // Ordina le voci
        List<LeaderboardEntry> sortedEntries = leaderboardEntriesHashMap.values().stream()
                .sorted((e1, e2) -> {
                    int winCompare = Integer.compare(getWinStatusValue(e2), getWinStatusValue(e1));
                    if (winCompare == 0) {
                        return e2.getTimestamp().compareTo(e1.getTimestamp());
                    }
                    return winCompare;
                })
                .collect(Collectors.toList());

        // Aggiungi le righe ordinate alla tabella
        for (LeaderboardEntry entry : sortedEntries) {
            addRow(entry);
        }
    }


    // Metodo per ottenere il valore intero di winStatus
    public int getWinStatusValue(LeaderboardEntry entry) {
        try {
            // Assumendo che winStatus sia una stringa numerica
            return Integer.parseInt(entry.getWinStatus());
        } catch (NumberFormatException e) {
            // Gestisci il caso in cui winStatus non è numerico
            return 0; // O qualsiasi valore predefinito
        }
    }


    @FXML
    public void backToMenu(ActionEvent event) throws IOException {
        soundMan.buttonClick();
        SceneManager.switchScene(event, "fxml.menu", "css.menu");
        if (SoundManager.isVolumeOn()) soundMan.switchTrack("audio.menu");

    }

    public void addRow(LeaderboardEntry newEntry) {
        tableView.getItems().add(newEntry);
    }

    public static void updateLeaderboard(int playerScore) {
        // Leggi il file JSON esistente
        HashMap<String, LeaderboardEntry> leaderboardEntriesHashMap = FileManager.readJSON("json.leaderboard", LeaderboardEntry.class);
        Player newPlayer = HumanPlayer.getInstance();
        LocalDateTime now = LocalDateTime.now();

        // Estrai il team di Pokémon del nuovo giocatore
        List<Pokemon> pokemonTeam = newPlayer.getTeam().getPokemonList();
        List<String> pokemonNames = pokemonTeam.stream().map(Pokemon::getName).collect(Collectors.toList());

        // Crea una nuova voce per la leaderboard
        LeaderboardEntry newEntry = new LeaderboardEntry(now, String.valueOf(playerScore), pokemonNames);

        // Aggiungi la nuova voce alla mappa
        leaderboardEntriesHashMap.put(now.toString(), newEntry);

        // Salva la mappa aggiornata nel file JSON
        FileManager.writeJSON(leaderboardEntriesHashMap, "json.leaderboard");

    }
}
