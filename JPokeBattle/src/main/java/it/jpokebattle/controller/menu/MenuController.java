package it.jpokebattle.controller.menu;

import it.jpokebattle.util.FileManager;
import it.jpokebattle.util.SoundManager;
import it.jpokebattle.view.AlertMessage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Line;
import javafx.stage.Stage;


public class MenuController {
    private MenuNodeManager nodeMan = MenuNodeManager.getInstance();
    private SoundManager soundMan = SoundManager.getInstance();
    private AlertMessage alert = new AlertMessage();

    @FXML
    protected ImageView volumeImg, avatarViewImg, gengarGif, snorlaxGif;
    @FXML
    protected Button
            // Menu principale
            selectGameProfileBtn, exitBtn,
            // Impostazioni
            volumeBtn,
            // Menu di selezione del profilo di gioco
            selectGameModeBtn,
            // Ritorno
            mainMenuBtn, backToMainBtn2, backToMainBtn3, backToProfilesCancelBtn, backToProfilesBtn;
    @FXML
    protected Label
            titleLbl, mainMenuLbl, gameProfileLbl, gameModeLbl, profileLbl, settingsLbl,
            addProfLbl, nicknameLbl, nicknameProfCardLbl, avatarLbl, modifyProfLbl, displayProfInfoLbl;
    @FXML
    protected Line leftSeparatorLn, rightSeparatorLn;
    @FXML
    protected TextField nicknameTxtFd;
    @FXML
    protected GridPane profileCardGrid, avatarGrid;




    /**
     * Visualizza il menù principale.
     * */
    @FXML
    public void mainMenu(ActionEvent e){
        soundMan.buttonClick();
        nodeMan.switchToMainMenu();
    }



    /**
     * TODO: da implementare!
     * */
    @FXML
    public void pvp(ActionEvent e){
        soundMan.buttonClick();
        System.out.println("PvP");
    }

    /**
     * Chiude la finestra e termina il programma.
     * */
    @FXML
    public void quit(ActionEvent e){
        soundMan.buttonClick();
        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
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

    /**
     * TODO: da implementare!
     * */
    @FXML
    public void volume(ActionEvent e){
        soundMan.buttonClick();
        System.out.println("Volume");
    }
}
