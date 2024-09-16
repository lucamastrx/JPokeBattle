package it.jpokebattle.controller;

import it.jpokebattle.util.FileManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;


/**
 * La classe SceneManager gestisce il caricamento e il cambio delle scene in un'applicazione JavaFX.
 * Fornisce metodi per caricare file FXML, cambiare scene e applicare stili CSS.
 */
public class SceneManager {


    /**
     * Carica il file FXML specificato e restituisce il controller associato.
     *
     * @param <T>          Il tipo del controller associato alla scena FXML.
     * @param fxmlSrcName  Nome della risorsa del file FXML da caricare.
     * @return             Il controller della scena caricata.
     * @throws IOException Se si verifica un errore durante il caricamento del file FXML.
     */
    public static <T> T getController(String fxmlSrcName) throws IOException {
        FXMLLoader loader = FileManager.loadFXML(fxmlSrcName);
        loader.load();
        return loader.getController();
    }


    /**
     * Cambia la scena dello Stage specificato con quella caricata dal file FXML.
     *
     * @param stage        Lo Stage su cui impostare la nuova scena.
     * @param fxmlSrcName  Nome della risorsa del file FXML da caricare.
     * @return             Il loader del file FXML caricato.
     * @throws IOException Se si verifica un errore durante il caricamento del file FXML.
     */
    public static FXMLLoader switchScene(Stage stage, String fxmlSrcName) throws IOException {
        // Carica il file FXML
        FXMLLoader loader = FileManager.loadFXML(fxmlSrcName);
        Scene scene = new Scene(loader.load());

        // Imposta i dati utente sulla scena
        scene.setUserData(loader);

        // Cambia la scena dello Stage passato come parametro
        stage.setScene(scene);
        stage.show();

        return loader;
    }



    /**
     * Cambia la scena su un nuovo file FXML in risposta a un evento ActionEvent (ad esempio, un click su un bottone).
     *
     * @param e            L'ActionEvent che ha scatenato il cambiamento di scena.
     * @param fxmlSrcName  Nome della risorsa del file FXML da caricare.
     * @return             Il loader del file FXML caricato.
     * @throws IOException Se si verifica un errore durante il caricamento del file FXML.
     */
    public static FXMLLoader switchScene(ActionEvent e, String fxmlSrcName) throws IOException {
        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        FXMLLoader loader = FileManager.loadFXML(fxmlSrcName);
        Scene scene = new Scene(loader.load());

        scene.setUserData(loader);
        stage.setScene(scene);
        stage.show();

        return loader;
    }

    /**
     * Cambia la scena su un nuovo file FXML e applica un foglio di stile CSS specificato,
     * in risposta a un evento ActionEvent (ad esempio, un click su un bottone).
     *
     * @param e            L'ActionEvent che ha scatenato il cambiamento di scena.
     * @param fxmlSrcName  Nome della risorsa del file FXML da caricare.
     * @param cssSrcName   Nome della risorsa del file CSS da applicare alla scena.
     * @return             Il loader del file FXML caricato.
     * @throws IOException Se si verifica un errore durante il caricamento del file FXML o del foglio di stile CSS.
     */
    public static FXMLLoader switchScene(ActionEvent e, String fxmlSrcName, String cssSrcName) throws IOException {
        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        FXMLLoader loader = FileManager.loadFXML(fxmlSrcName);
        Scene scene = new Scene(loader.load());

        scene.setUserData(loader);
        scene.getStylesheets().add(FileManager.getResource(cssSrcName));
        stage.setScene(scene);
        stage.show();

        return loader;
    }
}