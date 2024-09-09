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
 * This class handles the setup and display of the main menu.
 */
public class MenuView {

    private FXMLLoader loader;
    private Stage mainStage;
    private Scene mainScene;
    private static Parent root;

    public MenuView(Stage stage) throws IOException {
        this.mainStage = stage;
        setupMenu();
    }

    /**
     * Setup and display the menu.
     */
    private void setupMenu() throws IOException {
        loader = FileManager.loadFXML("fxml.menu");
        setScene(loader.load());
        setStage();
        setRoot(mainScene.getRoot());
        SoundManager.getInstance();
    }

    /**
     * Set up the initial scene.
     */
    private void setScene(Parent root) {
        mainScene = new Scene(root);
        mainScene.getStylesheets().add(FileManager.getResource("css.menu"));
        mainScene.setUserData(loader);
    }

    /**
     * Set parameters, logo, images, and values for the main window.
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
     * Save the root of the main scene.
     *
     * @param sceneRoot The root of the scene.
     */
    public static void setRoot(Parent sceneRoot) {
        root = sceneRoot;
    }
}
