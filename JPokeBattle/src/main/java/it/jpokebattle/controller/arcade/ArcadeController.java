package it.jpokebattle.controller.arcade;

import it.jpokebattle.controller.NodeManager;
import it.jpokebattle.controller.SceneManager;
import it.jpokebattle.controller.menu.MenuNodeManager;
import it.jpokebattle.model.Profile;
import it.jpokebattle.model.area.Area;
import it.jpokebattle.util.AudioTrack;
import it.jpokebattle.util.SoundManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;

import java.io.IOException;

public class ArcadeController {
    private static Profile profile;
    private ArcadeNodeManager nodeMan = ArcadeNodeManager.getIstance();
    private SoundManager soundMan = SoundManager.getInstance();

    @FXML
    protected Button nextAreaBtn, prevAreaBtn, specialAreaBtn;
    @FXML
    protected Label narratorLbl;
    @FXML
    protected GridPane gameSettingsPane, pokemonPane, invenctoryPane;
    @FXML
    protected ScrollPane narratorScrlPane;


    public static void setProfile(Profile lastPickedProfile) {
        profile = lastPickedProfile;
    }


    /**
     * Nasconde tutti i panelli e torna alla schermata di gioco attuale.
     */
    @FXML
    public void backToGame (ActionEvent e) {
        soundMan.buttonClick();
        nodeMan.hideAllPane();
    }

    /**
     * TODO:DA IMPLEMENTARE
     * Mostra la schermata di visualizzazione dei pokemon.
     */
    @FXML
    public void ownedPokemon(MouseEvent e) {
        soundMan.buttonClick();
        nodeMan.showOwnedPokemon();

    }

    /**
     * TODO:DA IMPLEMENTARE
     * Mostra la schermata di visualizzazione dell'inventario.
     */
    @FXML
    public void invenctory(MouseEvent e) {
        soundMan.buttonClick();
        nodeMan.showInvenctory();
    }

    /**
     * Torna al menù principale, carica la nuova scena, aggiorna la root nella classe
     * astratta NodeManager e aggiorna il file dei profili.
     * */
    @FXML
    public void mainMenu(ActionEvent e) throws IOException {
        soundMan.buttonClick();
        soundMan.switchTrack("mp3.title");

        FXMLLoader loader = SceneManager.switchScene(e, "fxml.menu", "css.menu");
        NodeManager.setRoot(loader.getRoot());
        MenuNodeManager.setController(loader.getController());
        profile.save("ser.prof", profile);
    }

    /**
     * Mostra la schermata di impostazioni.
     */
    @FXML
    public void settings (MouseEvent e) {
        soundMan.buttonClick();
        nodeMan.showSettingsPane();
    }

    /**
     *
     */
    @FXML
    public void nextArea(ActionEvent e) {
        soundMan.buttonClick();
        profile.goToNextArea();
        updateNarrator();
        nodeMan.updateNarratorScrollbarPosition();
    }

    /**
     *
     */
    @FXML
    public void prevArea(ActionEvent e) {
        soundMan.buttonClick();
        profile.goToPrevArea();
        updateNarrator();
        nodeMan.updateNarratorScrollbarPosition();
    }

    /**
     *
     */
    @FXML
    public void specialArea(ActionEvent e) {
        soundMan.buttonClick();
        profile.goToSpecialArea();
        updateNarrator();
        nodeMan.updateNarratorScrollbarPosition();
    }

    /**
     * Aggiorna il testo del narratore con la descrizione dell'area corrente in cui
     * il giocatore si trova.
     */
    private void updateNarrator() {
        Area area = profile.getCurrentArea();
        nodeMan.updateNarratorLbl(area.getDescription());
        profile.setNarratorTextHistory(narratorLbl.getText());
        nodeMan.updateChoiceButtons(profile.getCurrentArea());
    }

    /**
     * TODO: DA IMPLEMENTARE (PROVVISORIA)
     * @param e
     */
    @FXML
    public void volume(ActionEvent e) {
        soundMan.buttonClick();
        soundMan.toggleVolume();
    }

}
