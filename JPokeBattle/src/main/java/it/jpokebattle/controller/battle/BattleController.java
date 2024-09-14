package it.jpokebattle.controller.battle;

import it.jpokebattle.controller.SceneManager;
import it.jpokebattle.controller.leaderboard.LeaderboardController;
import it.jpokebattle.model.battle.*;
import it.jpokebattle.model.exceptions.MoveSetException;
import it.jpokebattle.model.player.HumanPlayer;
import it.jpokebattle.model.pokemon.*;
import it.jpokebattle.util.SoundManager;
import it.jpokebattle.view.BattleView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.stage.Window;


/**
 * La classe {@code BattleController} gestisce il flusso della battaglia tra il giocatore umano
 * e il bot, integrandosi con l'interfaccia grafica JavaFX e gestendo il suono, le scene,
 * e le interazioni con la vista della battaglia. Implementa {@code Initializable} per
 * inizializzare la schermata e la logica di battaglia all'avvio.
 */
public class BattleController implements Initializable {

    private SoundManager soundMan = SoundManager.getInstance();
    private static Stage currentStage;
    private static Battle currentBattle;


    @FXML
    private ImageView yourImage, botImage;

    @FXML
    private ProgressBar yourPokemonBar, botPokemonBar;

    @FXML
    protected Button menuButton, attackButton, changePokemonButton, levelupButton, gameOverButton;

    @FXML
    private Label myPokemonLevel, botPokemonLevel, myPokemonHP, botPokemonHP;

    private BattleView battleView;


    /**
     * Costruttore della classe {@code BattleController}.
     * Inizializza una nuova istanza della classe {@code Battle} che rappresenta la battaglia corrente.
     */
    public BattleController(){
        currentBattle = new Battle();
    }


    /**
     * Restituisce la battaglia corrente.
     *
     * @return La battaglia corrente.
     */
    public static Battle getCurrentBattle() {
        return currentBattle;
    }


    /**
     * Metodo di inizializzazione che viene chiamato automaticamente quando la scena è creata.
     * Inizializza la vista della battaglia e configura la scena corrente.
     *
     * @param url La posizione utilizzata per risolvere i percorsi relativi.
     * @param resourceBundle Le risorse locali utilizzate per questa applicazione.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        currentStage = (Stage) Window.getWindows().stream()
                .filter(window -> window instanceof Stage && window.isShowing())
                .findFirst()
                .orElse(null);

        battleView = new BattleView(currentStage, currentBattle, HumanPlayer.getInstance(), yourImage, botImage,yourPokemonBar, botPokemonBar,myPokemonLevel, botPokemonLevel, myPokemonHP, botPokemonHP, this );

    }


    /**
     * Metodo dimostrativo.
     * Aggiunge esperienza al Pokémon attivo del giocatore e gestisce il suo avanzamento di livello,
     * evoluzione e apprendimento di nuove mosse, aggiornando la vista di conseguenza.
     *
     * @param event L'evento che ha attivato l'azione (es. clic su un bottone).
     * @throws MoveSetException Se si verifica un errore durante l'aggiornamento delle mosse.
     */
    @FXML
    void addlevel(ActionEvent event) throws MoveSetException {
        currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        OwnedPokemon pokemon = HumanPlayer.getInstance().getTeam().getActivePokemon();
        pokemon.collectExp(5000);

        pokemon.levelUpAndStatsUp();
        pokemon.evolve();
        battleView.updatePokemonImages(pokemon);
        battleView.updatePokemonLevel(pokemon);
        battleView.updatePokemonInBattle(pokemon);
        System.out.println(pokemon.getMoves());
        System.out.println(pokemon.getLevel());

        if (pokemon.canLearnMove()) {
            if (pokemon.has4moves()){
                String moveID = pokemon.getBreed().getMoves().get(pokemon.getLevel());
                Move move = Move.movesMap.get(moveID);
                battleView.showLearnMovePopup(move);
            } else {
                pokemon.learnMove();
                String moveID = pokemon.getBreed().getMoves().get(pokemon.getLevel());
                Move move = Move.movesMap.get(moveID);
                battleView.showAutomaticLearnedMovePopup(move); // popup che il pokemon ha imparato una nuova mossa in automatico
            }

        }
    }


    /**
     * Metodo dimostrativo.
     * Permette al giocatore di scappare dalla battaglia, reimpostando il conteggio delle battaglie
     * e passando alla schermata del menu principale.
     *
     * @param event L'evento che ha attivato l'azione.
     * @throws IOException Se si verifica un errore nel caricamento della nuova scena.
     */
    @FXML
    public void runAway(ActionEvent event) throws IOException {
        currentBattle.resetBattleCount();
        soundMan.buttonClick();
        SceneManager.switchScene(event, "fxml.menu", "css.menu");
        if (SoundManager.isVolumeOn()) soundMan.switchTrack("audio.menu");
    }


    /**
     * Mostra un popup per cambiare il Pokémon attivo del giocatore.
     *
     * @param event L'evento che ha attivato l'azione.
     */
    @FXML
    public void showChangePokemonPopup(ActionEvent event){

        battleView.showChangePokemonPopup(event);
    }

    /**
     * Mostra un popup per selezionare una mossa da utilizzare in battaglia.
     *
     * @param event L'evento che ha attivato l'azione.
     * @throws IOException Se si verifica un errore durante la visualizzazione del popup.
     */
    @FXML
    public void showMoveSelectionPopup(ActionEvent event) throws IOException {
        battleView.showMoveSelectionPopup(event);
    }



    /**
     * Esegue le azioni di battaglia in base agli stati specificati, aggiornando la vista e
     * gestendo le eventuali evoluzioni, cambiamenti di Pokémon o mosse apprese.
     *
     * @param status La lista di stati della battaglia.
     * @param p1 Il Pokémon del giocatore umano.
     * @param p2 Il Pokémon del bot.
     * @throws IOException Se si verifica un errore nel processo di aggiornamento della vista.
     * @throws MoveSetException Se si verifica un errore nell'aggiornamento delle mosse.
     */
    public void executeMoveAction(List<BattleStatus> status, Pokemon p1, Pokemon p2) throws IOException, MoveSetException {
        OwnedPokemon pokemon;
        String moveID;
        Move mov;
        for (BattleStatus battleStatus : status) {
            switch (battleStatus) {
                case CONTINUE_DAMAGE_TO_YOUR_POKEMON:
                    battleView.updatePokemonInBattle(p1);
                    break;
                case CONTINUE_DAMAGE_TO_BOT_POKEMON:
                    battleView.updatePokemonInBattle(p2);
                    break;
                case CONTINUE_WITH_BOT_CHANGE_POKEMON:
                    battleView.updatePokemonImages(p2);
                    battleView.updatePokemonLevel(p2);
                    battleView.updatePokemonInBattle(p2);
                    break;
                case CONTINUE_WITH_YOUR_CHANGE_POKEMON:
                    battleView.showChangePokemonPopup(true);
                    battleView.updatePokemonImages(p1);
                    battleView.updatePokemonLevel(p1);
                    battleView.updatePokemonInBattle(p1);
                    break;
                case CHANGE_YOUR_FAINTED_POKEMON:
                    battleView.showChangePokemonPopup(true);
                    battleView.updatePokemonImages(p1);
                    battleView.updatePokemonLevel(p1);
                    battleView.updatePokemonInBattle(p1);
                    break;
                case CHANGE_BOT_FAINTED_POKEMON:
                    battleView.updatePokemonImages(p2);
                    battleView.updatePokemonLevel(p2);
                    battleView.updatePokemonInBattle(p2);
                    break;
                case YOU_WIN:
                    currentBattle.setBattleCount(getCurrentBattle().getBattleCount() + 1);
                    System.out.println("VINTOOOO");
                    battleView.switchToYouWin();

                    break;
                case YES_LEVEL_UP:
                    battleView.updatePokemonLevel(p1);
                    battleView.updatePokemonInBattle(p1);
                    break;
                case NO_LEVEL_UP:
                    break;
                case YOU_LOST:
                    LeaderboardController.updateLeaderboard(currentBattle.getBattleCount());
                    currentBattle.resetBattleCount();
                    battleView.switchToGameOver();
                    break;
                case CONTINUE_WITH_NO_DAMAGE:
                    break;
                case CHANGE_MOVE:

                    if (p1 instanceof OwnedPokemon){
                        pokemon = (OwnedPokemon)p1;
                    } else {
                        pokemon = (OwnedPokemon)p2;
                    }
                    System.out.println(pokemon);
                    moveID = pokemon.getBreed().getMoves().get(pokemon.getLevel());
                    System.out.println(moveID);
                    Move move = Move.movesMap.get(moveID);
                    System.out.println(move);
                    battleView.showLearnMovePopup(move);
                    break;
                case LEARN_MOVE:

                    if (p1 instanceof OwnedPokemon){
                        pokemon = (OwnedPokemon)p1;
                    } else {
                        pokemon = (OwnedPokemon)p2;
                    }
                    pokemon.learnMove();
                    moveID = pokemon.getBreed().getMoves().get(pokemon.getLevel());
                    move = Move.movesMap.get(moveID);
                    battleView.showAutomaticLearnedMovePopup(move); // popup che il pokemon ha imparato una nuova mossa in automatico
                    break;
                case NO_LEARN_MOVE:
                    break;
                case EVOLVE:
                    battleView.updatePokemonImages(p1);
                    battleView.updatePokemonInBattle(p1);
                    break;
                case NO_EVOLVE:
                    break;
            }
        }
    }

    /**
     * Metodo dimostrativo.
     * Passa alla schermata di Game Over quando la battaglia è terminata.
     *
     * @param actionEvent L'evento che ha attivato l'azione.
     * @throws IOException Se si verifica un errore durante la transizione alla schermata di Game Over.
     */
    public void switchToGameOver(ActionEvent actionEvent) throws IOException {
        battleView.switchToGameOver();
    }
}

