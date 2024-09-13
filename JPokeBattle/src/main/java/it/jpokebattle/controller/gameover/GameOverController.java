package it.jpokebattle.controller.gameover;

import it.jpokebattle.controller.SceneManager;
import it.jpokebattle.model.player.HumanPlayer;
import it.jpokebattle.model.pokemon.Pokemon;
import it.jpokebattle.util.SoundManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class GameOverController {

    private SoundManager soundMan = SoundManager.getInstance();
    private Stage stage;

    @FXML
    protected Button goBackToMainMenuButton;


    public void cureDefeatedTeam(){
        HumanPlayer.getInstance().getTeam().getPokemonList().forEach(Pokemon::heal);
    }


    @FXML
    public void switchToMainMenu(ActionEvent event) throws IOException {
        cureDefeatedTeam();
        soundMan.buttonClick();
        SceneManager.switchScene(event, "fxml.menu", "css.menu");
        if (SoundManager.isVolumeOn()) soundMan.switchTrack("audio.menu");
    }
}
