package it.jpokebattle.view;

import it.jpokebattle.util.FileManager;
import it.jpokebattle.util.SoundManager;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;


/**
 * Classe responsabile della creazione e gestione della vista del menu principale.
 * Questa classe si occupa di caricare il file FXML, impostare la scena e configurare la finestra principale.
 */
public class MenuView {

    private FXMLLoader loader;
    private Stage mainStage;
    private Scene mainScene;
    private static Parent root;


    /**
     * Costruttore della classe MenuView.
     * Inizializza la vista del menu per lo stage fornito.
     *
     * @param stage Lo stage principale dell'applicazione.
     * @throws IOException Se si verifica un errore durante il caricamento del file FXML.
     */
    public MenuView(Stage stage) throws IOException {
        this.mainStage = stage;
        setupMenu();
    }


    /**
     * Configura e visualizza il menu.
     * Questo metodo carica il file FXML, imposta la scena e lo stage, e inizializza il gestore del suono.
     *
     * @throws IOException Se si verifica un errore durante il caricamento del file FXML.
     */
    private void setupMenu() throws IOException {
        loader = FileManager.loadFXML("fxml.menu");
        setScene(loader.load());
        setStage();
        setRoot(mainScene.getRoot());
        SoundManager.getInstance();
    }


    /**
     * Imposta la scena iniziale.
     * Crea una nuova scena con la radice fornita e aggiunge il foglio di stile CSS.
     *
     * @param root La radice della scena.
     */
    private void setScene(Parent root) {
        mainScene = new Scene(root);
        mainScene.getStylesheets().add(FileManager.getResource("css.menu"));
        mainScene.setUserData(loader);
    }

    /**
     * Imposta i parametri, il logo, le immagini e i valori per la finestra principale.
     * Configura lo stage principale con il titolo, l'icona e altre proprietà.
     */
    private void setStage() {
        Image icon = FileManager.loadImage("img.logo");
        mainStage.getIcons().add(icon);
        mainStage.setTitle("JPokeBattle");
        mainStage.getIcons().add(icon);
        mainStage.setResizable(false);
        mainStage.setScene(mainScene);
        mainStage.show();
    }


    /**
     * Salva la radice della scena principale.
     * Questo metodo statico permette di aggiornare la radice della scena da altre parti dell'applicazione.
     *
     * @param sceneRoot La radice della scena da salvare.
     */
    public static void setRoot(Parent sceneRoot) {
        root = sceneRoot;
    }
}
