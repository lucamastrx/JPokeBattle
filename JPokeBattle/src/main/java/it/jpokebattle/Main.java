package it.jpokebattle;

import it.jpokebattle.model.pokemon.Move;
import it.jpokebattle.util.SoundManager;
import it.jpokebattle.util.FileManager;
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
    private FXMLLoader loader;
    private Stage mainStage;
    private Scene mainScene;
    private static Parent root;

    public static void main(String[] args) {
        // Breed.dumpJSON();
        Move.dumpJSON();
        launch(args);
    }


    @Override
    public void start(Stage stage) throws IOException {
        mainStage = stage;
        loader = FileManager.loadFXML("fxml.menu");
        setScene(loader.load());
        setStage();

        setRoot(mainScene.getRoot());


        SoundManager.getInstance();
    }

    /**
     * Imposta la scena iniziale.
     */
    private void setScene(Parent root) {
        mainScene = new Scene(root);
        mainScene.getStylesheets().add(FileManager.getResource("css.menu"));
        mainScene.setUserData(loader);
    }

    /**
     * Imposta parametri, loghi, immagini e valori della finestra principale.
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
     * Salva la scena principale.
     *
     * @param sceneRoot     Radice della scena
     */
    public static void setRoot(Parent sceneRoot) {
        root = sceneRoot;
    }
}
