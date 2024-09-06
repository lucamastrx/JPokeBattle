package it.jpokebattle.controller.menu;

import it.jpokebattle.controller.SceneManager;
import it.jpokebattle.util.FileManager;
import it.jpokebattle.util.SoundManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Line;
import javafx.stage.Stage;

import java.io.IOException;



public class MenuController { // TODO bottone per curare i pokemon

    private SoundManager soundMan = SoundManager.getInstance();

    @FXML
    protected ImageView volumeImg, gengarGif, snorlaxGif;

    @FXML
    protected Button battleButton, leaderboardButton, exitBtn;

    @FXML
    protected Label titleLbl, mainMenuLbl;

    @FXML
    protected Line leftSeparatorLn, rightSeparatorLn;



    @FXML
    public void switchToBattle(ActionEvent event) throws IOException {
        soundMan.buttonClick();
        SceneManager.switchScene(event, "fxml.battle");
        if (SoundManager.isVolumeOn()) soundMan.switchTrack("audio.battle");

    }


    @FXML
    public void switchToLeaderboard(ActionEvent event) throws IOException {
        soundMan.buttonClick();
        SceneManager.switchScene(event, "fxml.leaderboard");
        if (SoundManager.isVolumeOn()) soundMan.switchTrack("audio.leaderboard");
    }


    /**
     * Chiude la finestra e termina il programma.
     * */
    @FXML
    public void quit(ActionEvent event){
        soundMan.buttonClick();
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }


    /**
     *  Inverte il valore della variabile booleana associata all'immagine
     *  del volume e aggiorna l'immagine.
     *
     * @param e     Click del mouse sull'immagine.
     * @see SoundManager
     */
    @FXML
    public void switchVolume(MouseEvent e) {
        soundMan.buttonClick();
        soundMan.toggleVolume();
        updateVolumeIcon();
    }

    /**
     * Aggiorna l'immagine del volume in base al valore della variabile
     * booleana associata.
     *
     * @see SoundManager
     */
    private void updateVolumeIcon() {
        String volumeSrcName = soundMan.isVolumeOn() ? "img.volume" : "img.mute";
        Image img = FileManager.loadImage(volumeSrcName);
        volumeImg.setImage(img);
    }

}
