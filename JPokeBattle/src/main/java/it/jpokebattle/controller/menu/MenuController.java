package it.jpokebattle.controller.menu;

import it.jpokebattle.util.FileManager;
import it.jpokebattle.model.Profile;
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
    private Profile lastPickedProfile;            // Ultimo profilo selezionato nella schermata di visualizzazione profili

    @FXML
    protected ImageView volumeImg, avatarViewImg, gengarGif, snorlaxGif;
    @FXML
    protected Button
            // Menu principale
            selectGameProfileBtn, profileBtn, settingsBtn, exitBtn,
            // Impostazioni
            volumeBtn, creditBtn, languageBtn,
            // Menu di selezione del profilo di gioco
            selectGameModeBtn,
            // Menu di selezione modalità
            arcadeBtn, pvpBtn, changeProfileBtn,
            // Visualizzazione profili
            addProfBtn, modifyProfBtn, displayProfInfoBtn,
            // Aggiungi/modifica profilo
            confirmCreationBtn, confirmChangesBtn, deleteProfBtn,
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
    @FXML
    protected ScrollPane profilesScrlPane;


    /**
     * Metodo chiamato dal bottone 'aggiungi' nel menù di visualizzazione dei profili:
     * passa alla schermata di aggiunta del profilo.
     * */
    @FXML
    public void addProf(ActionEvent e){
        soundMan.buttonClick();
        nodeMan.switchToAddProfile();
    }





    /**
     * TODO: da implementare!
     * */
    @FXML
    public void credits(ActionEvent e){
        soundMan.buttonClick();
        System.out.println("Crediti");
    }



    /**
     * TODO: DA IMPLEMENTARE
     * Metodo chiamato dal bottone 'visualizza' nel menù di selezione dei
     * profili: passa alla schermata di visualizzazioni delle informazioni del
     * profilo selezionato.
     */
    @FXML
    public void displayProfileInfo(ActionEvent e) {
        nodeMan.switchToProfileInfo();
        soundMan.buttonClick();
    }

    /**
     * TODO: da implementare!
     * */
    @FXML
    public void language(ActionEvent e){
        soundMan.buttonClick();
        System.out.println("Lingua");
    }

    /**
     * Visualizza il menù principale.
     * */
    @FXML
    public void mainMenu(ActionEvent e){
        soundMan.buttonClick();
        nodeMan.switchToMainMenu();
    }

    /**
     * Metodo chiamato dal bottone 'modifica' nel menù di selezione dei
     * profili: passa alla schermata di modifica del profilo.
     */
    @FXML
    public void modifyProf(ActionEvent e) {
        soundMan.buttonClick();
        nodeMan.switchToModifyProfile(lastPickedProfile);
    }

    /**
     * Si attiva quando viene selezionato un profilo nel menù di selezione dei
     * profili. Mostra la scheda del profilo insieme ai bottoni 'modifica' e
     * 'visualizza' e memorizza l'ultimo profilo selezionato.
     *
     * @param prof Profilo da cui prendere avatar e nome
     */
    public void profileModifyPreview(Profile prof) {
        soundMan.buttonClick();
        nodeMan.showModifyProfPreview(prof);
        lastPickedProfile = prof;
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
     * Metodo chiamato dalle ImageView nel menù di creazione/modifica del
     * profilo: imposta l'immagine della scheda del profilo con l'immagine
     * dell'avatar selezionato nella griglia.
     */
    @FXML
    public void setAvatarProfileCard(MouseEvent e) {
        soundMan.buttonClick();
        ImageView clickedImageView = (ImageView) e.getSource();
        avatarViewImg.setImage(clickedImageView.getImage());
    }

    /**
     * Metodo chiamato dal bottone 'impostazioni' nel menù pricipale: passa
     * alla schermata delle impostazioni.
     * */
    @FXML
    public void settings(ActionEvent e) {
        soundMan.buttonClick();
        nodeMan.switchToSettsMenu();
    }

    public void startPreview(Profile prof) {
        soundMan.buttonClick();
        nodeMan.showStartPreview(prof);
        lastPickedProfile = prof;
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
