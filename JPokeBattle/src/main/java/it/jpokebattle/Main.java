package it.jpokebattle;

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
        // HumanPlayer player = HumanPlayer.getInstance();
        //System.out.println(player.getTeam().getActivePokemon());

        launch(args);
    }



    @Override
    public void start(Stage stage) throws Exception {
        // Delegate the menu setup to MenuView
        new MenuView(stage);
    }
}


