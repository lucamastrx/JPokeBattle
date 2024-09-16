package it.jpokebattle.view;

import it.jpokebattle.controller.SceneManager;
import it.jpokebattle.controller.battle.BattleController;
import it.jpokebattle.model.battle.*;
import it.jpokebattle.model.exceptions.MoveSetException;
import it.jpokebattle.model.player.HumanPlayer;
import it.jpokebattle.model.pokemon.Move;
import it.jpokebattle.model.pokemon.OwnedPokemon;
import it.jpokebattle.model.pokemon.Pokemon;
import it.jpokebattle.util.FileManager;
import it.jpokebattle.util.SoundManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Bounds;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Popup;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


/**
 * La classe {@code BattleView} gestisce la visualizzazione della battaglia tra il giocatore umano
 * e il bot, aggiornando graficamente le immagini dei Pokémon, le barre di salute, e i livelli.
 * Fornisce inoltre popup di interazione come l'apprendimento di mosse o la selezione di Pokémon.
 */
public class BattleView {

    private Battle currentBattle;
    private Stage battleStage;
    private Scene youWinScene;
    private Scene battleScene;
    private SoundManager soundMan = SoundManager.getInstance();
    private HumanPlayer humanPlayer;
    private ImageView yourImage, botImage;
    private ProgressBar yourPokemonBar, botPokemonBar;
    private Label myPokemonLevel, botPokemonLevel, myPokemonHP, botPokemonHP;
    private BattleController battleController;


    /**
     * Costruttore della classe {@code BattleView}. Inizializza i riferimenti ai componenti della battaglia
     * e aggiorna le immagini, le barre di salute e i livelli dei Pokémon coinvolti nella battaglia.
     *
     * @param stage Lo {@code Stage} corrente in cui la battaglia viene visualizzata.
     * @param currentBattle La battaglia corrente gestita dal gioco.
     * @param humanPlayer Il giocatore umano che partecipa alla battaglia.
     * @param yourImage L'immagine del Pokémon del giocatore umano.
     * @param botImage L'immagine del Pokémon del bot.
     * @param yourPokemonBar La barra di salute del Pokémon del giocatore.
     * @param botPokemonBar La barra di salute del Pokémon del bot.
     * @param myPokemonLevel L'etichetta del livello del Pokémon del giocatore.
     * @param botPokemonLevel L'etichetta del livello del Pokémon del bot.
     * @param myPokemonHP L'etichetta dei punti salute del Pokémon del giocatore.
     * @param botPokemonHP L'etichetta dei punti salute del Pokémon del bot.
     * @param battleController Il controller della battaglia che gestisce la logica della stessa.
     */
    public BattleView(Stage stage, Battle currentBattle, HumanPlayer humanPlayer, ImageView yourImage, ImageView botImage, ProgressBar yourPokemonBar, ProgressBar botPokemonBar,
                      Label myPokemonLevel, Label botPokemonLevel, Label myPokemonHP, Label botPokemonHP, BattleController battleController) {
        this.battleController = battleController;
        this.currentBattle = currentBattle;
        this.battleStage = stage;
        this.humanPlayer = humanPlayer;

        this.yourImage = yourImage;
        this.botImage = botImage;
        this.yourPokemonBar = yourPokemonBar;
        this.botPokemonBar = botPokemonBar;
        this.myPokemonLevel = myPokemonLevel;
        this.botPokemonLevel = botPokemonLevel;
        this.myPokemonHP = myPokemonHP;
        this.botPokemonHP = botPokemonHP;
        initialize();
    }

    /**
     * Inizializza la vista della battaglia aggiornando le immagini dei Pokémon, le barre di salute e i livelli.
     */
    public void initialize() {
        Pokemon p1 = humanPlayer.getTeam().getActivePokemon();
        Pokemon p2 = currentBattle.getCurrentBotPlayer().getTeam().getActivePokemon();
        updatePokemonImages(p1);
        updatePokemonImages(p2);
        updatePokemonInBattle(p1);
        updatePokemonInBattle(p2);
        updatePokemonLevel(p1);
        updatePokemonLevel(p2);
    }


    /**
     * Aggiorna il livello del Pokémon visualizzato.
     *
     * @param p Il Pokémon di cui aggiornare il livello (può essere del giocatore o del bot).
     */
    public void updatePokemonLevel(Pokemon p){
        if (p instanceof OwnedPokemon) {

            this.myPokemonLevel.setText("Lvl. "+p.getLevel());
        } else {

            this.botPokemonLevel.setText("Lvl. "+p.getLevel());
        }
    }


    /**
     * Aggiorna la barra di salute e il testo dei punti salute (HP) del Pokémon visualizzato.
     *
     * @param p Il Pokémon di cui aggiornare la barra di salute e gli HP.
     */
    public void updatePokemonInBattle(Pokemon p) {
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
            // System.out.println("HP TOTALI: "+p.getFullHp());
            this.myPokemonHP.setText("HP: "+p.getCurrentHp()+"/"+p.getFullHp());
        } else {
            this.botPokemonBar.setStyle("-fx-accent: "+color+";");
            this.botPokemonBar.setProgress(1.0 * p.getCurrentHp() / p.getFullHp());
            this.botPokemonHP.setText("HP: "+p.getCurrentHp()+"/"+p.getFullHp());
        }
    }


    /**
     * Aggiorna l'immagine del Pokémon attualmente in battaglia.
     *
     * @param p Il Pokémon di cui aggiornare l'immagine.
     */
    public void updatePokemonImages(Pokemon p){
        if (p instanceof OwnedPokemon) {
            this.yourImage.setImage(FileManager.loadImage("gif." + p.getBreed().getName()));
            this.yourImage.setScaleX(-1); // Fa mirror
        } else {
            this.botImage.setImage(FileManager.loadImage("gif." + p.getBreed().getName()));
        }
    }


    /**
     * Crea e configura un popup personalizzato che segue la posizione della finestra principale.
     *
     * @param xOffset Offset orizzontale del popup rispetto alla finestra principale.
     * @param yOffset Offset verticale del popup rispetto alla finestra principale.
     * @return Il popup configurato.
     */
    private Popup createPopup(double xOffset, double yOffset) {
        Popup popup = new Popup();
        battleStage.xProperty().addListener((obs, oldX, newX) -> updatePopupPosition(popup, xOffset, yOffset));
        battleStage.yProperty().addListener((obs, oldY, newY) -> updatePopupPosition(popup, xOffset, yOffset));
        return popup;
    }


    /**
     * Aggiorna la posizione di un popup in base alla posizione della finestra principale.
     *
     * @param popup Il popup da aggiornare.
     * @param xOffset L'offset orizzontale.
     * @param yOffset L'offset verticale.
     */
    private void updatePopupPosition(Popup popup, double xOffset, double yOffset) {
        if (popup == null || battleStage == null) {
            return;
        }
        Bounds sceneBounds = battleStage.getScene().getRoot().localToScreen(battleStage.getScene().getRoot().getBoundsInLocal());
        popup.setX(sceneBounds.getMinX() + xOffset);
        popup.setY(sceneBounds.getMinY() + yOffset);
    }


    /**
     * Crea il contenuto di un popup con un messaggio e una serie di bottoni per le azioni.
     *
     * @param message Il messaggio da visualizzare nel popup.
     * @param buttons I bottoni che permettono di compiere azioni nel popup.
     * @return Un contenitore verticale contenente il messaggio e i bottoni.
     */
    private VBox createPopupContent(String message, Node... buttons) {
        Label messageLabel = new Label(message);
        HBox buttonBox = new HBox(10, buttons);
        VBox popupContent = new VBox(10, messageLabel, buttonBox);
        popupContent.setStyle("-fx-background-color: lightyellow; -fx-padding: 20; -fx-border-color: black;");
        popupContent.setMinWidth(300);
        return popupContent;
    }


    /**
     * Mostra un popup che permette al giocatore di decidere se dimenticare una mossa per impararne una nuova.
     *
     * @param move La nuova mossa che il Pokémon può imparare.
     */
    public void showLearnMovePopup(Move move) {
        Popup popup = createPopup(90, 550);
        String message = String.format("The Pokémon can learn %s, description: \"%s\", Type: %s, Power: %d. Would you like to forget another move?",
                move.getID(), move.getDescription(), move.getType(), move.getPower());

        Button yesButton = new Button("Yes");
        Button noButton = new Button("No");
        yesButton.setOnAction(e -> {
            popup.hide();
            showMoveToForgetSelectionPopup();
        });
        noButton.setOnAction(e -> popup.hide());

        VBox popupContent = createPopupContent(message, yesButton, noButton);
        popup.getContent().add(popupContent);
        popup.show(battleStage);
    }


    /**
     * Mostra un popup che permette al giocatore di selezionare una mossa da dimenticare per apprendere una nuova mossa.
     */
    private void showMoveToForgetSelectionPopup() {
        OwnedPokemon pokemon = humanPlayer.getTeam().getActivePokemon();
        List<String> moves = pokemon.getMoves().keySet().stream().map(Move::getID).toList();

        Popup popup = createPopup(90, 550);
        Label messageLabel = new Label("Select a move to forget:");
        VBox moveButtons = new VBox(10);

        for (String moveID : moves) {
            Button moveButton = new Button(moveID);
            moveButton.setOnAction(e -> handleMoveSelection(moveID, popup, pokemon));
            moveButtons.getChildren().add(moveButton);
        }

        VBox popupContent = createPopupContent("Select a move to forget:", moveButtons);
        popupContent.setStyle("-fx-background-color: lightblue; -fx-padding: 20; -fx-border-color: black;");
        popup.getContent().add(popupContent);
        popup.show(battleStage);
    }


    /**
     * Gestisce l'azione di selezione di una mossa da dimenticare. Rimuove la mossa selezionata dal Pokémon.
     *
     * @param moveID L'ID della mossa selezionata.
     * @param popup Il popup attualmente visualizzato.
     * @param pokemon Il Pokémon che deve dimenticare una mossa.
     */
    private void handleMoveSelection(String moveID, Popup popup, OwnedPokemon pokemon) {
        Move moveToRemove = Move.movesMap.get(moveID);
        pokemon.removeMove(moveToRemove);
        try {
            pokemon.learnMove();
        } catch (MoveSetException ex) {
            throw new RuntimeException(ex);
        }
        popup.hide();
    }


    /**
     * Mostra un popup che informa il giocatore che il Pokémon ha appreso una nuova mossa automaticamente.
     *
     * @param move La nuova mossa appresa.
     */
    public void showAutomaticLearnedMovePopup(Move move) {
        Popup popup = createPopup(90, 550);
        String message = String.format("The Pokémon has learned %s, description: \"%s\", Type: %s, Power: %d.",
                move.getID(), move.getDescription(), move.getType(), move.getPower());

        Button okButton = new Button("Ok");
        okButton.setOnAction(e -> popup.hide());

        VBox popupContent = createPopupContent(message, okButton);
        popupContent.setStyle("-fx-background-color: lightyellow; -fx-padding: 20; -fx-border-color: black;");
        popup.getContent().add(popupContent);
        popup.show(battleStage);
    }


    /**
     * Mostra un popup per la selezione di una mossa da utilizzare durante la battaglia.
     *
     * @param event L'evento che ha scatenato l'azione.
     * @throws IOException Se si verifica un errore nel caricamento delle risorse.
     */
    @FXML
    public void showMoveSelectionPopup(ActionEvent event) throws IOException {
        String[] movesID = humanPlayer.getTeam().getActivePokemon().getMoves().keySet().stream().map(Move::getID).toArray(String[]::new);
        battleStage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        Popup popup = createPopup(200, 200);
        GridPane grid = createMoveSelectionGrid(movesID, popup);

        Button cancelButton = new Button("Cancel");
        cancelButton.setOnAction(e -> popup.hide());

        VBox layout = new VBox(10, grid, cancelButton);
        layout.setPadding(new Insets(20));
        layout.setAlignment(Pos.CENTER);

        popup.getContent().add(layout);
        popup.show(battleStage);
    }


    /**
     * Crea una griglia per la selezione delle mosse disponibili per il Pokémon attivo.
     *
     * @param movesID Gli ID delle mosse disponibili.
     * @param popup Il popup di selezione mosse.
     * @return Una griglia contenente i bottoni per selezionare le mosse.
     */
    private GridPane createMoveSelectionGrid(String[] movesID, Popup popup) {
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10));
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setAlignment(Pos.CENTER);

        for (int i = 0; i < movesID.length; i++) {
            String moveID = movesID[i];
            Move move = Move.movesMap.get(moveID);
            Button moveButton = new Button(String.format("%s %d [%s]", moveID, move.getPower(), move.getType()));
            moveButton.setPrefSize(150, 50);
            moveButton.setOnAction(e -> handleMoveAction(move, popup));
            grid.add(moveButton, i % 2, i / 2);
        }
        // System.out.println("E FINO A QUA");
        return grid;
    }


    /**
     * Gestisce l'azione della selezione di una mossa durante la battaglia. Esegue la mossa selezionata.
     *
     * @param move La mossa selezionata.
     * @param popup Il popup da chiudere dopo la selezione.
     */
    private void handleMoveAction(Move move, Popup popup) {
        // System.out.println("INIZIO TURNO");
        Action moveAction = new MoveAction(humanPlayer, move, currentBattle.getCurrentBotPlayer(), currentBattle);
        Action botAction = new MoveAction(currentBattle.getCurrentBotPlayer(), currentBattle.getCurrentBotPlayer().smartPokemonMove(), humanPlayer, currentBattle);
        try {
            List<BattleStatus> status = new ArrayList<>();
            if (currentBattle.humanStarts(moveAction, botAction)) {
                System.out.println(HumanPlayer.getInstance().getTeam().getActivePokemon().getName() + " è più veloce!");
                status.addAll(moveAction.execute());
                if (status.stream().noneMatch(x -> x == BattleStatus.CHANGE_BOT_FAINTED_POKEMON)) {
                    status.addAll(botAction.execute());
                }
            } else {
                System.out.println("Il pokemon avversario è più veloce!");
                status.addAll(botAction.execute());
                if (status.stream().noneMatch(x -> x == BattleStatus.CHANGE_YOUR_FAINTED_POKEMON)) {
                    status.addAll(moveAction.execute());
                }
            }
            OwnedPokemon p1 = humanPlayer.getTeam().getActivePokemon();
            Pokemon p2 = currentBattle.getCurrentBotPlayer().getTeam().getActivePokemon();
            // System.out.println("PRIMA DI EXECUTEMOVE");
            battleController.executeMoveAction(status, p1, p2);
            // System.out.println("DOPO EXECUTEMOVE");
        } catch (MoveSetException | IOException ex) {
            throw new RuntimeException(ex);
        }
        popup.hide();
    }


    /**
     * Mostra un popup per cambiare Pokémon durante la battaglia.
     *
     * @param event L'evento che ha scatenato l'azione.
     */
    public void showChangePokemonPopup(ActionEvent event) {
        // Stage battleStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        showChangePokemonPopup(battleStage, false);
    }


    /**
     * Mostra un popup per cambiare Pokémon se necessario.
     *
     * @param yourchange Se vero, indica che è il giocatore umano a dover cambiare Pokémon.
     */
    public void showChangePokemonPopup(boolean yourchange) {
        if (battleStage == null) {
            throw new IllegalStateException("Current stage must be set before showing popup.");
        }
        showChangePokemonPopup(battleStage, true);
    }


    /**
     * Mostra un popup per cambiare Pokémon, fornendo l'opzione di scegliere tra i Pokémon disponibili del giocatore.
     *
     * @param stage Il {@code Stage} corrente della battaglia.
     * @param yourchange Se vero, indica che è il giocatore umano a dover cambiare Pokémon.
     */
    private void showChangePokemonPopup(Stage stage, boolean yourchange) {
        List<OwnedPokemon> pokemonList = humanPlayer.getTeam().getPokemonList();

        // Create the popup
        Popup changePokemonPopup = createPopup(200, 200);

        // Create buttons and grid
        GridPane grid = createPokemonGrid(pokemonList, changePokemonPopup, yourchange);

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

        // Show the popup
        changePokemonPopup.show(stage);
    }


    /**
     * Crea una griglia con i Pokémon disponibili del giocatore per la selezione.
     *
     * @param pokemonList La lista di Pokémon posseduti dal giocatore.
     * @param changePokemonPopup Il popup di selezione Pokémon.
     * @param yourchange Se vero, il giocatore umano può cambiare Pokémon.
     * @return Una griglia contenente i bottoni per la selezione dei Pokémon.
     */
    private GridPane createPokemonGrid(List<OwnedPokemon> pokemonList, Popup changePokemonPopup, boolean yourchange) {
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10));
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setAlignment(Pos.CENTER);

        for (int i = 0; i < pokemonList.size(); i++) {
            OwnedPokemon pokemon = pokemonList.get(i);
            Button pokemonButton = createPokemonButton(pokemon, i, changePokemonPopup, yourchange);
            int row = i / 3;
            int column = i % 3;
            grid.add(pokemonButton, column, row);
        }

        return grid;
    }


    /**
     * Crea un bottone che rappresenta un Pokémon, mostrando il suo nome e i suoi HP.
     * Se il Pokémon è svenuto, disabilita il bottone.
     *
     * @param pokemon Il Pokémon per cui creare il bottone.
     * @param index L'indice del Pokémon nella lista.
     * @param changePokemonPopup Il popup di selezione Pokémon attualmente visualizzato.
     * @param yourchange Indica se è il giocatore umano a dover cambiare Pokémon.
     * @return Il bottone creato per il Pokémon.
     */
    private Button createPokemonButton(OwnedPokemon pokemon, int index, Popup changePokemonPopup, boolean yourchange) {
        Button pokemonButton = new Button();
        String name = pokemon.getName();
        if (!pokemon.isFainted()) {
            pokemonButton.setText(name + " hp: " + pokemon.getCurrentHp() + "/" + pokemon.getFullHp());
        } else {
            pokemonButton.setText(name + " is Fainted. " + pokemon.getCurrentHp() + "/" + pokemon.getFullHp());
            pokemonButton.setDisable(true);
        }
        pokemonButton.setPrefSize(150, 50);
        if (yourchange) {
            pokemonButton.setOnAction(e ->  handleYourPokemonSelection(pokemon, changePokemonPopup));
        }
        else{
            pokemonButton.setOnAction(e ->  handlePokemonSelection(pokemon, changePokemonPopup));
        }

        return pokemonButton;
    }


    /**
     * Gestisce la selezione del Pokémon da parte del giocatore umano per il cambio.
     * Esegue l'azione di cambio Pokémon e aggiorna le immagini e i livelli dei Pokémon attivi.
     *
     * @param selectedPokemon Il Pokémon selezionato.
     * @param changePokemonPopup Il popup attualmente visualizzato.
     */
    private void handleYourPokemonSelection(OwnedPokemon selectedPokemon, Popup changePokemonPopup) {
        Action switchPokemonAction = new SwitchPokemonAction(HumanPlayer.getInstance(), selectedPokemon, currentBattle);

        try {
            switchPokemonAction.execute();
            updatePokemonImages(humanPlayer.getTeam().getActivePokemon());
            updatePokemonLevel(humanPlayer.getTeam().getActivePokemon());
            updatePokemonInBattle(humanPlayer.getTeam().getActivePokemon());
            changePokemonPopup.hide();  // Close the popup after selecting a move
        } catch (IOException | MoveSetException ex) {
            throw new RuntimeException(ex);
        }
    }


    /**
     * Gestisce la selezione del Pokémon da parte del giocatore umano, durante la battaglia.
     * Controlla se il giocatore umano agisce per primo e aggiorna lo stato della battaglia di conseguenza.
     *
     * @param selectedPokemon Il Pokémon selezionato.
     * @param changePokemonPopup Il popup attualmente visualizzato.
     */
    private void handlePokemonSelection(OwnedPokemon selectedPokemon, Popup changePokemonPopup) {
        Battle battle = currentBattle; // You may need to handle currentBattle more effectively

        Action switchPokemonAction = new SwitchPokemonAction(humanPlayer, selectedPokemon, battle);
        Action botAction = new MoveAction(currentBattle.getCurrentBotPlayer(), currentBattle.getCurrentBotPlayer().smartPokemonMove(), humanPlayer, battle);

        try {
            if (battle.humanStarts(switchPokemonAction, botAction)) {
                switchPokemonAction.execute();
                updatePokemonImages(humanPlayer.getTeam().getActivePokemon());
                updatePokemonLevel(humanPlayer.getTeam().getActivePokemon());
                updatePokemonInBattle(humanPlayer.getTeam().getActivePokemon());
                OwnedPokemon p1 = humanPlayer.getTeam().getActivePokemon();
                Pokemon p2 = currentBattle.getCurrentBotPlayer().getTeam().getActivePokemon();
                List<BattleStatus> status = botAction.execute();
                battleController.executeMoveAction(status, p1, p2);
            } else {
                OwnedPokemon p1 = humanPlayer.getTeam().getActivePokemon();
                Pokemon p2 = currentBattle.getCurrentBotPlayer().getTeam().getActivePokemon();
                List<BattleStatus> status = botAction.execute();
                battleController.executeMoveAction(status, p1, p2);
                switchPokemonAction.execute();
                updatePokemonImages(humanPlayer.getTeam().getActivePokemon());
                updatePokemonInBattle(humanPlayer.getTeam().getActivePokemon());
                updatePokemonImages(humanPlayer.getTeam().getActivePokemon());
            }
        } catch (IOException | MoveSetException ex) {
            throw new RuntimeException(ex);
        } finally {
            changePokemonPopup.hide();
        }
    }

    /**
     * Cambia la scena a quella di "Game Over" al termine della battaglia.
     * Resetta il conteggio delle battaglie e, se il suono è attivo, cambia la traccia audio.
     *
     * @throws IOException Se si verifica un errore durante il cambio di scena.
     */
    public void switchToGameOver() throws IOException {
        currentBattle.resetBattleCount();
        SceneManager.switchScene(battleStage, "fxml.gameover");
        if (SoundManager.isVolumeOn()) soundMan.switchTrack("audio.gameover");
    }


    /**
     * Cambia la scena a quella di "You Win" quando il giocatore vince la battaglia.
     * Aggiunge un bottone "CONTINUA" che permette di tornare alla battaglia.
     *
     * @throws IOException Se si verifica un errore durante il caricamento della scena.
     */
    public void switchToYouWin() throws IOException {
        battleScene = battleStage.getScene();
        FXMLLoader youWinLoader = FileManager.loadFXML("fxml.youwin");
        Parent youWinLayout = youWinLoader.load();
        youWinScene = new Scene(youWinLayout);

        // Verifica che il layout radice sia un BorderPane
        if (youWinLayout instanceof BorderPane) {
            BorderPane borderPane = (BorderPane) youWinLayout;

            // Creazione del bottone dinamicamente
            Button returnButton = new Button("CONTINA");

            // Configura l'azione del bottone per tornare alla scena di battaglia
            returnButton.setOnAction(this::returnToBattleScene);

            // Centra il bottone in basso utilizzando un HBox
            HBox bottomBox = new HBox(returnButton);
            bottomBox.setAlignment(Pos.CENTER); // Allinea il bottone al centro
            bottomBox.setPadding(new Insets(10, 0, 20, 0)); // Margine dal bordo inferiore

            // Aggiungi l'HBox al layout inferiore del BorderPane
            borderPane.setBottom(bottomBox);
        }

        // Imposta e mostra la scena "You Win"
        Stage stage = battleStage; // Usa lo stage corrente
        stage.setScene(youWinScene);
        stage.show();
        if (SoundManager.isVolumeOn()) soundMan.switchTrack("audio.youwin");

    }


    /**
     * Ritorna alla scena della battaglia dalla schermata di "You Win".
     * Ripristina lo stato della battaglia e aggiorna le immagini e i livelli dei Pokémon attivi.
     *
     * @param event L'evento che ha scatenato l'azione.
     */
    public void returnToBattleScene(ActionEvent event) {
        // System.out.println("RETURN TO BATTLE");
        if (battleStage != null && battleScene != null) {
            currentBattle.newBotPlayer();
            Pokemon p1 = humanPlayer.getTeam().getActivePokemon();
            Pokemon p2 = currentBattle.getCurrentBotPlayer().getTeam().getActivePokemon();
            // System.out.println("ACTIVE POKEMON: "+p2);
            updatePokemonInBattle(p1);
            updatePokemonInBattle(p2);
            updatePokemonImages(p1);
            updatePokemonImages(p2);
            updatePokemonLevel(p1);
            updatePokemonLevel(p2);
            battleStage.setScene(battleScene);  // Torna alla scena battaglia
            battleStage.show();
            if (SoundManager.isVolumeOn()) soundMan.switchTrack("audio.battle");
        }
    }

}

