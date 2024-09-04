package it.jpokebattle.controller.leaderboard;

import it.jpokebattle.controller.SceneManager;
import it.jpokebattle.util.SoundManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import java.io.IOException;

public class LeaderboardController {

    private SoundManager soundMan = SoundManager.getInstance();

    @FXML
    protected Button menuButton;

    @FXML
    public void backToMenu(ActionEvent event) throws IOException {
        soundMan.buttonClick();
        SceneManager.switchScene(event, "fxml.menu", "css.menu");
        if (SoundManager.isVolumeOn()) soundMan.switchTrack("audio.menu");

    }
}
