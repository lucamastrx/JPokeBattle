package it.jpokebattle.view;

import it.jpokebattle.util.SoundManager;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Popup;
import javafx.stage.Stage;

public class BattleView { 

    private Stage battleStage;
    private Scene battleScene;
    private SoundManager soundMan = SoundManager.getInstance();

    // Dummy variables for demo purposes (you can use real ones)
    private String pokemonName = "Pikachu";
    private String newMoveName = "Thunderbolt";
    private String[] currentMoves = {"Quick Attack", "Iron Tail", "Thunder Wave", "Double Team"};
    private boolean canLearnNewMove = true; // Trigger to show popup when needed

    public BattleView(Stage stage) {
        this.battleStage = stage;
        initializeBattleScene();
    }

    private void initializeBattleScene() {
        // Create a simple button to trigger the popup
        Button learnMoveButton = new Button("Trigger Learn Move Popup");
        learnMoveButton.setOnAction(e -> {
            if (canLearnNewMove) {
                showMoveLearnPopup();
            }
        });

        VBox root = new VBox(10, learnMoveButton);
        root.setAlignment(Pos.CENTER);

        battleScene = new Scene(root, 600, 400);
        battleStage.setScene(battleScene);
        battleStage.setTitle("Battle Scene");
    }

    // Method to show the popup asking if the player wants to forget a move
    private void showMoveLearnPopup() {
        Popup popup = new Popup();

        // Create the message
        Label messageLabel = new Label(pokemonName + " can learn a new move " + newMoveName + ". Would you like to forget another move?");

        // Create Yes and No buttons
        Button yesButton = new Button("Yes");
        Button noButton = new Button("No");

        // If Yes is clicked, show the second popup to select a move to forget
        yesButton.setOnAction(e -> {
            popup.hide();  // Hide the first popup
            showMoveSelectionPopup();
        });

        // If No is clicked, close the popup
        noButton.setOnAction(e -> popup.hide());

        // Layout for the popup
        HBox buttonBox = new HBox(10, yesButton, noButton);
        buttonBox.setAlignment(Pos.CENTER);
        VBox popupContent = new VBox(10, messageLabel, buttonBox);
        popupContent.setAlignment(Pos.CENTER);
        popupContent.setStyle("-fx-background-color: lightyellow; -fx-padding: 20; -fx-border-color: black;");

        popup.getContent().add(popupContent);

        // Show the popup
        popup.show(battleStage);
    }

    // Method to show the second popup for selecting which move to forget
    private void showMoveSelectionPopup() {
        Popup moveSelectionPopup = new Popup();

        // Create the message
        Label messageLabel = new Label("Select a move to forget:");

        // Create buttons for each current move
        VBox moveButtons = new VBox(10);
        moveButtons.setAlignment(Pos.CENTER);
        for (String move : currentMoves) {
            Button moveButton = new Button(move);
            moveButton.setOnAction(e -> {
                System.out.println(pokemonName + " forgot " + move + " and learned " + newMoveName + "!");
                moveSelectionPopup.hide();  // Close the popup after move selection
            });
            moveButtons.getChildren().add(moveButton);
        }

        // Layout for the second popup
        VBox popupContent = new VBox(10, messageLabel, moveButtons);
        popupContent.setAlignment(Pos.CENTER);
        popupContent.setStyle("-fx-background-color: lightblue; -fx-padding: 20; -fx-border-color: black;");

        moveSelectionPopup.getContent().add(popupContent);

        // Show the second popup
        moveSelectionPopup.show(battleStage);
    }

    // Switch the scene to the battle scene
    public void showBattleScene() {
        soundMan.switchTrack("audio.battle");
        battleStage.setScene(battleScene);
        battleStage.show();
    }
}

