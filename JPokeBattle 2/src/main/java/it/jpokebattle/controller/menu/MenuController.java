package it.jpokebattle.controller.menu;

import it.jpokebattle.controller.SceneManager;
import it.jpokebattle.model.player.HumanPlayer;
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


/**
 * Controller per la gestione del menu principale del gioco.
 * Questa classe si occupa di gestire le interazioni dell'utente con il menu principale,
 * inclusi i cambi di scena, la cura del team di Pokémon e il controllo del volume.
 */
public class MenuController {

    private SoundManager soundMan = SoundManager.getInstance();

    @FXML
    protected ImageView volumeImg, gengarGif, snorlaxGif;

    @FXML
    protected Button battleButton, cureTeamButton, leaderboardButton, exitBtn;

    @FXML
    protected Label titleLbl, mainMenuLbl;

    @FXML
    protected Line leftSeparatorLn, rightSeparatorLn;


    /**
     * Passa alla scena di battaglia.
     *
     * @param event L'evento che ha scatenato questa azione.
     * @throws IOException Se si verifica un errore durante il caricamento della nuova scena.
     */
    @FXML
    public void switchToBattle(ActionEvent event) throws IOException {
        soundMan.buttonClick();
        SceneManager.switchScene(event, "fxml.battle");
        if (SoundManager.isVolumeOn()) soundMan.switchTrack("audio.battle");
    }


    /**
     * Passa alla scena della classifica.
     *
     * @param event L'evento che ha scatenato questa azione.
     * @throws IOException Se si verifica un errore durante il caricamento della nuova scena.
     */
    @FXML
    public void switchToLeaderboard(ActionEvent event) throws IOException {
        soundMan.buttonClick();
        SceneManager.switchScene(event, "fxml.leaderboard");
        if (SoundManager.isVolumeOn()) soundMan.switchTrack("audio.leaderboard");
    }


    /**
     * Cura tutti i Pokémon nel team del giocatore.
     *
     * @param event L'evento che ha scatenato questa azione.
     * @throws InterruptedException Se il thread viene interrotto durante l'operazione.
     */
    @FXML
    public void cureTeam(ActionEvent event) throws InterruptedException {
        soundMan.buttonClick();
        soundMan.cureTeamClick();
        HumanPlayer.getInstance().getTeam().healAllPokemon();
    }

    /**
     * Chiude la finestra e termina il programma.
     *
     * @param event L'evento che ha scatenato questa azione.
     */
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
