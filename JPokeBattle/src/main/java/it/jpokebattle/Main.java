package it.jpokebattle;

import it.jpokebattle.controller.NodeManager;
import it.jpokebattle.controller.menu.MenuNodeManager;
import it.jpokebattle.model.pokemon.Breed;
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

    public static void main(String[] args) {
        // Breed.dumpJSON();
        launch(args);
    }


    @Override
    public void start(Stage stage) throws IOException {
        mainStage = stage;
        loader = FileManager.loadFXML("fxml.menu");
        setScene(loader.load());
        setMainStage();

        NodeManager.setRoot(mainScene.getRoot());
        MenuNodeManager.setController(loader.getController());

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
     * Imposta parametri e valori della finestra principale.
     */
    private void setMainStage() {
        Image icon = FileManager.loadImage("img.logo");
        mainStage.getIcons().add(icon);
        mainStage.setTitle("JPokeBattle");
        mainStage.getIcons().add(icon);
        mainStage.setResizable(false);
        mainStage.setScene(mainScene);
        mainStage.show();
    }
}
