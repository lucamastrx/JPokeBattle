package it.jpokebattle.controller.battle;

import it.jpokebattle.controller.SceneManager;
import it.jpokebattle.model.battle.*;
import it.jpokebattle.model.exceptions.ForbiddenActionException;
import it.jpokebattle.model.exceptions.MoveSetException;
import it.jpokebattle.model.player.HumanPlayer;
import it.jpokebattle.model.pokemon.*;
import it.jpokebattle.util.FileManager;
import it.jpokebattle.util.SoundManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.geometry.Insets;
import javafx.geometry.Pos;


public class BattleController implements Initializable {

    private SoundManager soundMan = SoundManager.getInstance();
    private static Stage currentStage;
    private static Battle currentBattle;
    private Scene youWinScene;
    private Scene battleScene;


    @FXML
    private ImageView yourImage, botImage;

    @FXML
    private ProgressBar yourPokemonBar, botPokemonBar;

    @FXML
    protected Button menuButton, attackButton, changePokemonButton, levelupButton, gameOverButton;

    @FXML
    private Label myPokemonLevel, botPokemonLevel, myPokemonHP, botPokemonHP;

    private Popup changePokemonPopup;


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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Pokemon p1 = currentBattle.getHumanPlayer().getTeam().getActivePokemon();
        Pokemon p2 = currentBattle.getCurrentBotPlayer().getTeam().getActivePokemon();
        updatePokemonImages(p1);
        updatePokemonImages(p2);
        updatePokemonInBattle(p1);
        updatePokemonInBattle(p2);
        updatePokemonLevel(p1);
        updatePokemonLevel(p2);
    }

    private void updatePokemonLevel(Pokemon p){
        if (p instanceof OwnedPokemon) {

            this.myPokemonLevel.setText("Lvl. "+p.getLevel());
        } else {

            this.botPokemonLevel.setText("Lvl. "+p.getLevel());
        }
    }

    private void updatePokemonInBattle(Pokemon p) {
        String color = "green";
        double hpPerCent = (double)p.getCurrentHp() / p.getFullHp();
        if (hpPerCent > 0.25 && hpPerCent <= 0.60){
            color = "orange";
        }
        else if (hpPerCent <= 0.25){
            color = "red";
        }
        if (p instanceof OwnedPokemon) {
            this.yourPokemonBar.setStyle("-fx-accent: "+color+";");
            this.yourPokemonBar.setProgress(1.0 * p.getCurrentHp() / p.getFullHp());
            this.myPokemonHP.setText("HP: "+p.getCurrentHp()+"/"+p.getFullHp());
        } else {
            this.botPokemonBar.setStyle("-fx-accent: "+color+";");
            this.botPokemonBar.setProgress(1.0 * p.getCurrentHp() / p.getFullHp());
            this.botPokemonHP.setText("HP: "+p.getCurrentHp()+"/"+p.getFullHp());
        }
    }

    private void updatePokemonImages(Pokemon p){
        if (p instanceof OwnedPokemon) {
            this.yourImage.setImage(FileManager.loadImage("gif." + p.getBreed().getName()));
            this.yourImage.setScaleX(-1); // Fa mirror
        } else {
            this.botImage.setImage(FileManager.loadImage("gif." + p.getBreed().getName()));
        }
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
        if (changePokemonPopup == null) {
            changePokemonPopup = new Popup();
        }

        currentStage.xProperty().addListener((obs, oldX, newX) -> updatePopupXPosition(changePokemonPopup, 200));
        currentStage.yProperty().addListener((obs, oldY, newY) -> updatePopupYPosition(changePokemonPopup, 200));

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
                Action moveAction = new MoveAction(HumanPlayer.getInstance(), move, currentBattle.getCurrentBotPlayer(), currentBattle);
                Action botAction = new MoveAction(currentBattle.getCurrentBotPlayer(), currentBattle.getCurrentBotPlayer().smartPokemonMove(), HumanPlayer.getInstance(), currentBattle);
                try {
                    BattleStatus[] status1;
                    BattleStatus[] status2;
                    if (currentBattle.humanStarts(moveAction, botAction)) {


                        status1 = currentBattle.executeBattle(moveAction);
                        status2 = currentBattle.executeBattle(botAction);
                    } else {
                        status2 = currentBattle.executeBattle(botAction);
                        status1 = currentBattle.executeBattle(moveAction);

                    }
                    BattleStatus[] totalStatus = new BattleStatus[status1.length + status2.length];
                    System.arraycopy(status1, 0, totalStatus, 0, status1.length);
                    System.arraycopy(status2, 0, totalStatus, status1.length, status2.length);

                    OwnedPokemon p1 = HumanPlayer.getInstance().getTeam().getActivePokemon();
                    Pokemon p2 = currentBattle.getCurrentBotPlayer().getTeam().getActivePokemon();
                    executeMoveAction(totalStatus, p1, p2);
                } catch (MoveSetException | IOException | ForbiddenActionException ex) {
                    throw new RuntimeException(ex);
                }
                // eseguire entrambe le Action
                System.out.println("Selected move: " + moveID);  // Use the final variable 'move'
                // isdtanziare moveaction
                // chiamare execute
                // vedere cosa ritorna e controllare come andare avanti
                // vedi se passare ad altra schermata o continuare
                changePokemonPopup.hide();  // Close the popup after selecting a move
            });

            // Add buttons to the grid (2x2 layout)
            grid.add(moveButton, i % 2, i / 2);  // Place buttons in grid positions
        }


        // Optional: Add a "Cancel" button below the grid
        Button cancelButton = new Button("Cancel");
        cancelButton.setOnAction(e -> changePokemonPopup.hide());

        // Add the grid and the cancel button into a VBox
        VBox layout = new VBox(10);
        layout.setPadding(new Insets(20));
        layout.setAlignment(Pos.CENTER);
        layout.getChildren().addAll(grid, cancelButton);

        // Add the layout to the popup content
        changePokemonPopup.getContent().clear();
        changePokemonPopup.getContent().add(layout);

        // movePopup.getContent().add(popupContent);
        changePokemonPopup.show(currentStage);
    }




    public void showChangePokemonPopup(ActionEvent event){
        HumanPlayer humanPlayer = HumanPlayer.getInstance();
        List<OwnedPokemon> pokemonList = humanPlayer.getTeam().getPokemonList();
        currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        // Create the popup
        if (changePokemonPopup == null) {
            changePokemonPopup = new Popup();
        }

        currentStage.xProperty().addListener((obs, oldX, newX) -> updatePopupXPosition(changePokemonPopup, 200));
        currentStage.yProperty().addListener((obs, oldY, newY) -> updatePopupYPosition(changePokemonPopup, 200));

        // GridPane to arrange buttons in a 2x2 grid
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10));
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setAlignment(Pos.CENTER);

        // Create buttons for each move
        for (int i = 0; i < pokemonList.size(); i++) {
            String name = pokemonList.get(i).getName(); // Create a new final variable to capture the move
            Button pokemonButton = new Button();
            if (!pokemonList.get(i).isFainted()) {
                pokemonButton.setText(name + " hp: " + pokemonList.get(i).getCurrentHp() + "/" + pokemonList.get(i).getFullHp());
            } else {
                pokemonButton.setText(name + " is Fainted. " + pokemonList.get(i).getCurrentHp() + "/" + pokemonList.get(i).getFullHp());
                pokemonButton.setDisable(true);
            }

            pokemonButton.setPrefSize(150, 50); // Set button size
            final int j = i;
            // Add event handler for each button
            pokemonButton.setOnAction(e -> {
                Action switchPokemonAction = new SwitchPokemonAction(HumanPlayer.getInstance(), pokemonList.get(j), currentBattle);
                Action botAction = new MoveAction(currentBattle.getCurrentBotPlayer(), currentBattle.getCurrentBotPlayer().smartPokemonMove(), HumanPlayer.getInstance(), currentBattle);
                try {
                    if (currentBattle.humanStarts(switchPokemonAction, botAction)) {
                        switchPokemonAction.execute();
                        updatePokemonImages(humanPlayer.getTeam().getActivePokemon());
                        updatePokemonInBattle(humanPlayer.getTeam().getActivePokemon());
                        changePokemonPopup.hide();  // Close the popup after selecting a move
                        OwnedPokemon p1 = HumanPlayer.getInstance().getTeam().getActivePokemon();
                        Pokemon p2 = currentBattle.getCurrentBotPlayer().getTeam().getActivePokemon();
                        BattleStatus[] status1 = botAction.execute();
                        executeMoveAction(status1, p1, p2);
                    } else {
                        OwnedPokemon p1 = HumanPlayer.getInstance().getTeam().getActivePokemon();
                        Pokemon p2 = currentBattle.getCurrentBotPlayer().getTeam().getActivePokemon();
                        BattleStatus[] status1 = botAction.execute();
                        executeMoveAction(status1, p1, p2);
                        switchPokemonAction.execute();
                        updatePokemonImages(humanPlayer.getTeam().getActivePokemon());
                        updatePokemonInBattle(humanPlayer.getTeam().getActivePokemon());
                        changePokemonPopup.hide();
                    }
                } catch (IOException | MoveSetException ex) {
                    throw new RuntimeException(ex);
                }
                //humanPlayer.getTeam().setActivePokemon();

            });

            // Add buttons to the grid (3x2 layout)
            int row = i / 3;
            int column = i % 3;
            grid.add(pokemonButton, column, row);  // Place buttons in grid positions
        }


        // Optional: Add a "Cancel" button below the grid
        Button cancelButton = new Button("Cancel");
        cancelButton.setOnAction(e -> changePokemonPopup.hide());

        // Add the grid and the cancel button into a VBox
        VBox layout = new VBox(10);
        layout.setPadding(new Insets(20));
        layout.setAlignment(Pos.CENTER);
        layout.getChildren().addAll(grid, cancelButton);

        // Add the layout to the popup content
        changePokemonPopup.getContent().clear();
        changePokemonPopup.getContent().add(layout);

        // movePopup.getContent().add(popupContent);
        changePokemonPopup.show(currentStage);

    }


    private void executeMoveAction(BattleStatus[] status, Pokemon p1, Pokemon p2) throws IOException {
        for (BattleStatus battleStatus : status) {
            switch (battleStatus) {
                case CONTINUE_DAMAGE_TO_YOUR_POKEMON:
                    updatePokemonInBattle(p1);
                    break;
                case CONTINUE_DAMAGE_TO_BOT_POKEMON:
                    updatePokemonInBattle(p2);
                    break;
                case CONTINUE_WITH_BOT_CHANGE_POKEMON:
                    break;
                case CONTINUE_WITH_YOUR_CHANGE_POKEMON:
                    break;
                case YOU_WIN:
                    switchToYouWin();
                    break;
                case YES_LEVEL_UP:
                    break;
                case NO_LEVEL_UP:
                    break;
                case YOU_LOST:
                    break;
                case CONTINUE_WITH_NO_DAMAGE:
                    break;
            }
        }
    }


    public void switchToGameOver(ActionEvent event) throws IOException {
        currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        SceneManager.switchScene(currentStage, "fxml.gameover");
        if (SoundManager.isVolumeOn()) soundMan.switchTrack("audio.gameover");
    }

    public void switchToYouWin() throws IOException {
        battleScene = currentStage.getScene();
        FXMLLoader youWinLoader = FileManager.loadFXML("fxml.youwin");
        Parent youWinLayout = youWinLoader.load();
        youWinScene = new Scene(youWinLayout);

        // Verifica che il layout radice sia un BorderPane
        if (youWinLayout instanceof BorderPane) {
            BorderPane borderPane = (BorderPane) youWinLayout;

            // Creazione del bottone dinamicamente
            Button returnButton = new Button("CONTINA");

            // Configura l'azione del bottone per tornare alla scena di battaglia
            returnButton.setOnAction(event -> returnToBattleScene(event));

            // Centra il bottone in basso utilizzando un HBox
            HBox bottomBox = new HBox(returnButton);
            bottomBox.setAlignment(Pos.CENTER); // Allinea il bottone al centro
            bottomBox.setPadding(new Insets(10, 0, 20, 0)); // Margine dal bordo inferiore

            // Aggiungi l'HBox al layout inferiore del BorderPane
            borderPane.setBottom(bottomBox);
        }

        // Imposta e mostra la scena "You Win"
        Stage stage = currentStage; // Usa lo stage corrente
        stage.setScene(youWinScene);
        stage.show();
        if (SoundManager.isVolumeOn()) soundMan.switchTrack("audio.youwin");

    }

    @FXML
    public void returnToBattleScene(ActionEvent event) {
        if (currentStage != null && battleScene != null) {
            currentBattle.newBotPlayer();
            Pokemon p1 = HumanPlayer.getInstance().getTeam().getActivePokemon();
            Pokemon p2 = currentBattle.getCurrentBotPlayer().getTeam().getActivePokemon();
            updatePokemonInBattle(p1);
            updatePokemonInBattle(p2);
            updatePokemonImages(p1);
            updatePokemonImages(p2);
            currentStage.setScene(battleScene);  // Torna alla scena battaglia
            currentStage.show();
            if (SoundManager.isVolumeOn()) soundMan.switchTrack("audio.battle");
        }
    }
}

