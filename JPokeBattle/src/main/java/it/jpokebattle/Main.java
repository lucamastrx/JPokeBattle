package it.jpokebattle;

import it.jpokebattle.model.player.HumanPlayer;
import it.jpokebattle.util.SoundManager;
import it.jpokebattle.util.FileManager;
import it.jpokebattle.view.MenuView;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;


/**
 * Classe principale da cui parte l'esecuzione del codice.
 *
 * @author Luca Mastrantonio
 */
public class Main extends Application {

    public static void main(String[] args) {


        FileManager.clearSER("ser.ownedPokemon");
        HumanPlayer player = HumanPlayer.getInstance();
        System.out.println(player.getTeam().getPokemonList().size());
        System.out.println(player.getTeam().getPokemonList());
        System.out.println(player.getTeam().getActivePokemon());
        System.out.println(player.getTeam().getAverageLevel());

        FileManager.clearSER("ser.ownedPokemon");



        launch(args);
    }



    /*
        Move mv = createRandomMove();
        System.out.println(mv.getID());

    private static Move createRandomMove() {
        // Carica il JSON specificando il tipo di valore Move
        HashMap<String, Move> moveMap = FileManager.readJSON("json.moves", Move.class);

        // Controlla se la mappa è nulla o vuota
        if (moveMap == null || moveMap.isEmpty()) {
            System.err.println("Errore: mappa delle mosse vuota o non trovata.");
            return null;  // O gestisci il caso in cui non ci sono mosse disponibili
        }

        // Ottieni l'insieme delle chiavi
        Set<String> keySet = moveMap.keySet();

        // Converti l'insieme in una lista
        List<String> keyList = new ArrayList<>(keySet);

        // Genera un indice casuale
        Random random = new Random();
        int randomIndex = random.nextInt(keyList.size());

        // Seleziona la chiave casuale
        String randomKey = keyList.get(randomIndex);

        // Restituisci la mossa corrispondente alla chiave casuale
        return moveMap.get(randomKey);
    }*/


    @Override
    public void start(Stage stage) throws Exception {
        // Delegate the menu setup to MenuView
        new MenuView(stage);
    }
}


