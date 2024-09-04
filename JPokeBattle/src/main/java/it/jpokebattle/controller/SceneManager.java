package it.jpokebattle.controller;

import it.jpokebattle.util.FileManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class SceneManager {


    /**
     * Questo metodo consente il passaggio di una scena ad un'altra.
     *
     * @param fxmlSrcName   Nome della risorsa del file fxml che si vuole usare per cambiare scena.
     * @see it.jpokebattle.properties
     * @return Il loader del nuovo file FXML
     * */
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
     * Questo metodo consente il passaggio di una scena ad un'altra.
     *
     * @param fxmlSrcName   Nome della risorsa del file fxml che si vuole usare per cambiare scena.
     * @see it.jpokebattle.properties
     * @return Il loader del nuovo file FXML
     * */
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