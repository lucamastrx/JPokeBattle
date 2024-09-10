package it.jpokebattle.controller.battle;

import it.jpokebattle.controller.SceneManager;
import it.jpokebattle.model.battle.Battle;
import it.jpokebattle.model.exceptions.MoveSetException;
import it.jpokebattle.model.player.Player;
import it.jpokebattle.model.player.BotPlayerFactory;
import it.jpokebattle.model.player.HumanPlayer;
import it.jpokebattle.model.player.PlayerFactory;
import it.jpokebattle.model.pokemon.*;
import it.jpokebattle.util.SoundManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.control.Button;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.GridPane;

public class BattleController {

    private SoundManager soundMan = SoundManager.getInstance();
    private static Stage currentStage;
    private static Battle currentBattle;


    @FXML
    protected Button menuButton;

    @FXML
    protected Button attackButton;

    @FXML
    protected Button levelupButton;

    @FXML
    protected Button gameOverButton;

    private Popup movePopup;


    public BattleController(){
        currentBattle = new Battle();
    }


    public void battleStatus() {
        if (!currentBattle.humanLost()) {

        } else {
            // TODO switch to gameover
            System.out.println("Game Over! Hai perso dopo aver sconfitto " + (currentBattle.getBattleCount() - 1) + " avversari.");
        }
    }



    public static Battle getCurrentBattle() {
        return currentBattle;
    }




    @FXML
    void addlevel(ActionEvent event) throws MoveSetException { //TODO fix
        currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        OwnedPokemon pokemon = HumanPlayer.getInstance().getTeam().getActivePokemon();
        pokemon.collectExp(5000);
        pokemon.levelUpAndStatsUp();
        System.out.println(pokemon.getMoves());
        System.out.println(pokemon.getLevel());

        if (pokemon.canLearnMove()) {
            if (pokemon.has4moves()){
                String moveID = pokemon.getBreed().getMoves().get(pokemon.getLevel());
                Move move = Move.movesMap.get(moveID);
                showLearnMovePopup(move);
            } else {
                pokemon.learnMove();
                String moveID = pokemon.getBreed().getMoves().get(pokemon.getLevel());
                Move move = Move.movesMap.get(moveID);
                showAutomaticLearnedMovePopup(move); // popup che il pokemon ha imparato una nuova mossa in automatico
            }

        }
    }

    @FXML
    public void runAway(ActionEvent event) throws IOException {
        soundMan.buttonClick();
        SceneManager.switchScene(event, "fxml.menu", "css.menu");
        if (SoundManager.isVolumeOn()) soundMan.switchTrack("audio.menu");
    }

    // showLearnMovePopup(ActionEvent event, Move move)
    public void showLearnMovePopup(Move move) {
        Popup popup = new Popup();
        // Add a listener to track the stage's position
        currentStage.xProperty().addListener((obs, oldX, newX) -> updatePopupXPosition(popup, 90));
        currentStage.yProperty().addListener((obs, oldY, newY) -> updatePopupYPosition(popup, 550));

        // Create the message label
        Label messageLabel = new Label("The Pokémon can learn " + move.getID() + ", description:  \""+ move.getDescription() + "\", Type: "+  move.getType() + ", Power: "+  move.getPower()  +  ". Would you like to forget another move?");

        // Create Yes and No buttons
        Button yesButton = new Button("Yes");
        Button noButton = new Button("No");

        yesButton.setOnAction(e -> {
            popup.hide();  // Hide the first popup
            showMoveToForgetSelectionPopup(); // Show the move selection popup
        });

        noButton.setOnAction(e -> popup.hide());

        // Layout for the first popup
        HBox buttonBox = new HBox(10, yesButton, noButton);
        VBox popupContent = new VBox(10, messageLabel, buttonBox);
        popupContent.setStyle("-fx-background-color: lightyellow; -fx-padding: 20; -fx-border-color: black;");
        popupContent.setMinWidth(300);
        // Get the position of the scene within the stage
        Bounds sceneBounds = currentStage.getScene().getRoot().localToScreen(currentStage.getScene().getRoot().getBoundsInLocal());

        // Set popup position relative to the scene
        double xOffset = 90; // Desired x offset from the scene's top-left corner
        double yOffset = 550; // Desired y offset from the scene's top-left corner

        popup.setX(sceneBounds.getMinX() + xOffset);
        popup.setY(sceneBounds.getMinY() + yOffset);

        popup.getContent().add(popupContent);
        popup.show(currentStage);
    }

    private void updatePopupXPosition(Popup popup, double xOffset) {
        if (popup == null || currentStage == null) {
            return;
        }

        // Get the current position of the stage
        double stageX = currentStage.getX();
        popup.setX(stageX + xOffset);

    }
    private void updatePopupYPosition(Popup popup, double yOffset) {
        if (popup == null || currentStage == null) {
            return;
        }

        double stageY = currentStage.getY();

        popup.setY(stageY + yOffset);
    }

    private void showMoveToForgetSelectionPopup() {
        OwnedPokemon pokemon = HumanPlayer.getInstance().getTeam().getActivePokemon();
        List<String> moves = pokemon.getMoves().keySet().stream().map(Move::getID).toList();

        Popup popup = new Popup();

        // Create the message label
        Label messageLabel = new Label("Select a move to forget:");

        // Create buttons for each move
        VBox moveButtons = new VBox(10);

        // Variabile per salvare la mossa selezionata
        final String[] selectedMove = {null};

        for (String moveID : moves) {
            Button moveButton = new Button(moveID);
            moveButton.setOnAction(e -> {
                System.out.println("Selected move: " + moveID);
                // Salva la mossa selezionata
                selectedMove[0] = moveID;
                // Chiama il metodo per rimuovere la mossa
                Move moveToRemove = Move.movesMap.get(moveID);
                pokemon.removeMove(moveToRemove);
                try {
                    pokemon.learnMove();
                } catch (MoveSetException ex) {
                    throw new RuntimeException(ex);
                }
                popup.hide(); // Close the popup after move selection
            });
            moveButtons.getChildren().add(moveButton);
        }

        // Layout for the second popup
        VBox popupContent = new VBox(10, messageLabel, moveButtons);
        popupContent.setStyle("-fx-background-color: lightblue; -fx-padding: 20; -fx-border-color: black;");
        popupContent.setMinWidth(300);

        popup.getContent().add(popupContent);
        popup.show(currentStage);
    }


    public void showAutomaticLearnedMovePopup(Move move){
        Popup popup = new Popup();
        // Add a listener to track the stage's position
        currentStage.xProperty().addListener((obs, oldX, newX) -> updatePopupXPosition(popup, 90));
        currentStage.yProperty().addListener((obs, oldY, newY) -> updatePopupYPosition(popup, 550));

        // Create the message label
        Label messageLabel = new Label("The Pokémon has learned " + move.getID() + ", description:  \""+ move.getDescription() + "\", Type: "+  move.getType() + ", Power: "+  move.getPower()  +  ".");

        // Create Yes and No buttons
        Button okButton = new Button("Ok");


        okButton.setOnAction(e -> {
            popup.hide();  // Hide the first popup
        });

        // Layout for the first popup
        HBox buttonBox = new HBox(10, okButton);
        VBox popupContent = new VBox(10, messageLabel, buttonBox);
        popupContent.setStyle("-fx-background-color: lightyellow; -fx-padding: 20; -fx-border-color: black;");
        popupContent.setMinWidth(300);
        // Get the position of the scene within the stage
        Bounds sceneBounds = currentStage.getScene().getRoot().localToScreen(currentStage.getScene().getRoot().getBoundsInLocal());

        // Set popup position relative to the scene
        double xOffset = 90; // Desired x offset from the scene's top-left corner
        double yOffset = 550; // Desired y offset from the scene's top-left corner

        popup.setX(sceneBounds.getMinX() + xOffset);
        popup.setY(sceneBounds.getMinY() + yOffset);

        popup.getContent().add(popupContent);
        popup.show(currentStage);
    }


    /**
     * Show a popup with a grid of 4 buttons, each representing a Pokémon move.
     *
     */
    @FXML
    public void showMoveSelectionPopup(ActionEvent event) throws IOException {
        HumanPlayer humanPlayer = HumanPlayer.getInstance();
        String[] movesID = humanPlayer.getTeam().getActivePokemon().getMoves().keySet().stream().map(Move::getID).toArray(String[]::new);
        currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        // Create the popup
        if (movePopup == null) {
            movePopup = new Popup();
        }

        currentStage.xProperty().addListener((obs, oldX, newX) -> updatePopupXPosition(movePopup, 200));
        currentStage.yProperty().addListener((obs, oldY, newY) -> updatePopupYPosition(movePopup, 200));

        // GridPane to arrange buttons in a 2x2 grid
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10));
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setAlignment(Pos.CENTER);

        // Create buttons for each move
        for (int i = 0; i < movesID.length; i++) {
            String moveID = movesID[i]; // Create a new final variable to capture the move
            Move move = Move.movesMap.get(moveID);
            Button moveButton = new Button(moveID + " " + move.getPower() + " [" + move.getType() + "]");
            moveButton.setPrefSize(150, 50); // Set button size

            // Add event handler for each button
            moveButton.setOnAction(e -> {
                // controllare priorita e veolocita
                // eseguire entrambe le Action
                System.out.println("Selected move: " + moveID);  // Use the final variable 'move'
                // isdtanziare moveaction
                // chiamare execute
                // vedere cosa ritorna e controllare come andare avanti
                // vedi se passare ad altra schermata o continuare
                movePopup.hide();  // Close the popup after selecting a move
            });

            // Add buttons to the grid (2x2 layout)
            grid.add(moveButton, i % 2, i / 2);  // Place buttons in grid positions
        }


        // Optional: Add a "Cancel" button below the grid
        Button cancelButton = new Button("Cancel");
        cancelButton.setOnAction(e -> movePopup.hide());

        // Add the grid and the cancel button into a VBox
        VBox layout = new VBox(10);
        layout.setPadding(new Insets(20));
        layout.setAlignment(Pos.CENTER);
        layout.getChildren().addAll(grid, cancelButton);

        // Add the layout to the popup content
        movePopup.getContent().clear();
        movePopup.getContent().add(layout);

        // movePopup.getContent().add(popupContent);
        movePopup.show(currentStage);
    }



    public void switchToGameOver(ActionEvent event) throws IOException {
        currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        SceneManager.switchScene(currentStage, "fxml.gameover");
        if (SoundManager.isVolumeOn()) soundMan.switchTrack("audio.gameover");
    }

}

