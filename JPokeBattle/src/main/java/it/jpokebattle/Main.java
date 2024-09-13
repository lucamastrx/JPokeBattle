package it.jpokebattle;

import it.jpokebattle.model.player.HumanPlayer;
import it.jpokebattle.util.FileManager;
import it.jpokebattle.view.MenuView;
import javafx.application.Application;
import javafx.stage.Stage;


/**
 * Classe principale da cui parte l'esecuzione del codice.
 *
 * @author Luca Mastrantonio
 */
public class Main extends Application {

    public static void main(String[] args) {
//
        //Breed breed = Breed.breedsMap.get("Charmander");
        //Breed breed1 = Breed.breedsMap.get("Squirtle");
        //Breed breed2 = Breed.breedsMap.get("Bulbasaur");
        //LocalDateTime now = LocalDateTime.now();
//
        //List<String> teamList = List.of("ciaoo", "hello", "hiiii");
        //LeaderboardEntry entry = new LeaderboardEntry(now, "5", teamList);
        //HashMap<String, LeaderboardEntry> leaderboard = new HashMap<>();
        //leaderboard.put("ciao", entry);
//
        //FileManager.writeJSON(leaderboard, "json.leaderboard");
//


        FileManager.clearSER("ser.ownedPokemon");
        HumanPlayer player = HumanPlayer.getInstance();

        System.out.println(player.getTeam().getActivePokemon());


        FileManager.clearSER("ser.ownedPokemon");



        launch(args);
    }



    @Override
    public void start(Stage stage) throws Exception {
        // Delegate the menu setup to MenuView
        new MenuView(stage);
    }
}


