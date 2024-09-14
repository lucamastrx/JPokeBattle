package it.jpokebattle;

import it.jpokebattle.view.MenuView;
import javafx.application.Application;
import javafx.stage.Stage;


/**
 * Classe principale da cui parte l'esecuzione del codice.
 *
 * @author Luca Mastrantonio
 * @version 1.0
 */
public class Main extends Application {


    public static void main(String[] args) {
        // HumanPlayer player = HumanPlayer.getInstance();
        //System.out.println(player.getTeam().getActivePokemon());

        launch(args);
    }


    /**
     * Metodo di avvio dell'applicazione JavaFX.
     * Questo metodo viene chiamato internamente dal framework JavaFX dopo che l'ambiente di runtime
     * è stato inizializzato. Delega la configurazione del menu alla classe MenuView.
     *
     * @param stage Lo stage principale dell'applicazione.
     * @throws Exception Se si verifica un errore durante l'inizializzazione dell'applicazione.
     */
    @Override
    public void start(Stage stage) throws Exception {
        // Delega la configurazione del menu a MenuView
        new MenuView(stage);
    }
}


