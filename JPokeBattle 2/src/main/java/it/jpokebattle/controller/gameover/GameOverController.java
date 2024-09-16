package it.jpokebattle.controller.gameover;

import it.jpokebattle.controller.SceneManager;
import it.jpokebattle.model.player.HumanPlayer;
import it.jpokebattle.model.pokemon.Pokemon;
import it.jpokebattle.util.SoundManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;


import java.io.IOException;


/**
 * La classe {@code GameOverController} gestisce le interazioni alla fine di una partita,
 * come la guarigione della squadra del giocatore e il ritorno al menu principale.
 * Utilizza il framework JavaFX per la gestione dell'interfaccia utente e
 * si occupa del passaggio tra scene dopo che la partita è terminata.
 */
public class GameOverController {

    @FXML
    protected Button goBackToMainMenuButton;
    private SoundManager soundMan = SoundManager.getInstance();

    /**
     * Cura la squadra sconfitta ripristinando la salute di tutti i Pokémon.
     * Questo metodo viene chiamato quando si torna al menu principale dopo una sconfitta.
     */
    public void cureDefeatedTeam(){
        HumanPlayer.getInstance().getTeam().getPokemonList().forEach(Pokemon::heal);
    }

    /**
     * Gestisce il passaggio alla schermata del menu principale.
     * Questo metodo viene chiamato quando l'utente preme il pulsante per tornare al menu principale.
     *
     * @param event L'evento di azione che ha attivato questo metodo.
     * @throws IOException Se si verifica un errore durante il caricamento della nuova scena.
     */
    @FXML
    public void switchToMainMenu(ActionEvent event) throws IOException {
        cureDefeatedTeam();
        soundMan.buttonClick();
        SceneManager.switchScene(event, "fxml.menu", "css.menu");
        if (SoundManager.isVolumeOn()) soundMan.switchTrack("audio.menu");
    }
}
