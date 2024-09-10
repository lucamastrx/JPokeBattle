package it.jpokebattle.controller.battle;

import it.jpokebattle.controller.SceneManager;
import it.jpokebattle.model.exceptions.MoveSetException;
import it.jpokebattle.model.player.HumanPlayer;
import it.jpokebattle.model.pokemon.Move;
import it.jpokebattle.model.pokemon.OwnedPokemon;
import it.jpokebattle.model.pokemon.Pokemon;
import it.jpokebattle.util.SoundManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.control.Button;

import java.io.IOException;
import java.util.HashMap;
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

    @FXML
    protected Button menuButton;

    @FXML
    protected Button attackButton;

    @FXML
    protected Button levelupButton;

    @FXML
    public Button staticButton;

    private Popup movePopup;




    @FXML
    void addlevel(ActionEvent event) throws IOException, MoveSetException {
        currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        System.out.println("siamo entrati");
        OwnedPokemon pokemon = HumanPlayer.getInstance().getTeam().getActivePokemon();
        System.out.println("ha istanziato opkemon");
        pokemon.collectExp(5000);
        System.out.println("ha dato exp");
        pokemon.levelUpAndStatsUp();
        System.out.println("levelup e stat up");
        System.out.println(pokemon.getLevel());
        if (pokemon.canLearnMove()) {
            if (pokemon.has4moves()){
                String moveID = pokemon.getBreed().getMoves().get(pokemon.getLevel());
                Move move = Move.movesMap.get(moveID);
                showLearnMovePopup(move);
            } else {
                pokemon.learnMove();
                // TODO popup che il pokemon ha imparato una nuova mossa
            }

        }


        System.out.println("chiama popup");
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
        // currentStage = (Stage) menuButton.getScene().getWindow();

        // Add a listener to track the stage's position
        currentStage.xProperty().addListener((obs, oldX, newX) -> updatePopupPosition(popup));
        currentStage.yProperty().addListener((obs, oldY, newY) -> updatePopupPosition(popup));

        // Create the message label
        Label messageLabel = new Label("The Pokémon can learn " + move.getID() +  ". Would you like to forget another move?");

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
        double xOffset = 100; // Desired x offset from the scene's top-left corner
        double yOffset = 100; // Desired y offset from the scene's top-left corner

        popup.setX(sceneBounds.getMinX() + xOffset);
        popup.setY(sceneBounds.getMinY() + yOffset);

        popup.getContent().add(popupContent);
        popup.show(currentStage);
    }

    private void updatePopupPosition(Popup popup) {
        if (popup == null || currentStage == null) {
            return;
        }

        // Get the current position of the stage
        double stageX = currentStage.getX();
        double stageY = currentStage.getY();

        // Set the popup position relative to the stage
        double xOffset = 100; // Offset from the top-left corner of the stage
        double yOffset = 100; // Offset from the top-left corner of the stage

        popup.setX(stageX + xOffset);
        popup.setY(stageY + yOffset);
    }

    private void showMoveToForgetSelectionPopup() {
        Pokemon pokemon = HumanPlayer.getInstance().getTeam().getActivePokemon();
        List<String> moves = pokemon.getMoves().keySet().stream().map(Move::getID).toList();

        Popup popup = new Popup();

        // Create the message label
        Label messageLabel = new Label("Select a move to forget:");

        // Create buttons for each move
        VBox moveButtons = new VBox(10);

        for (String move : moves) {
            Button moveButton = new Button(move);
            moveButton.setOnAction(e -> {
                System.out.println("Selected move: " + move);
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

    /**
     * Show a popup with a grid of 4 buttons, each representing a Pokémon move.
     *
     */
    @FXML
    public void showMoveSelectionPopup(ActionEvent event) throws IOException {
        String[] moves = {"Quick Attack", "Iron Tail", "Thunder Wave", "Double Team"};
        currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        // Create the popup
        if (movePopup == null) {
            movePopup = new Popup();
        }

        // GridPane to arrange buttons in a 2x2 grid
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10));
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setAlignment(Pos.CENTER);

        // Create buttons for each move
        for (int i = 0; i < moves.length; i++) {
            String move = moves[i]; // Create a new final variable to capture the move
            Button moveButton = new Button(move);
            moveButton.setPrefSize(100, 50); // Set button size

            // Add event handler for each button
            moveButton.setOnAction(e -> {
                System.out.println("Selected move: " + move);  // Use the final variable 'move'
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

        // Show the popup at the stage position, offset slightly
        movePopup.show(currentStage);
        movePopup.centerOnScreen(); // Alternatively, you can position it as you need
    }



    public void switchToGameOver() throws IOException {
        Stage currentStage = (Stage) staticButton.getScene().getWindow();
        SceneManager.switchScene(currentStage, "fxml.gameover");
        if (SoundManager.isVolumeOn()) soundMan.switchTrack("audio.gameover");
    }

}

