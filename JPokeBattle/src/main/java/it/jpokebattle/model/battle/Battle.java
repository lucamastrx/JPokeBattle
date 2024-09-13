package it.jpokebattle.model.battle;

import it.jpokebattle.model.exceptions.ForbiddenActionException;
import it.jpokebattle.model.exceptions.MoveSetException;
import it.jpokebattle.model.player.*;
import it.jpokebattle.model.pokemon.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


/**
 * La classe Battle rappresenta una battaglia tra un giocatore umano e un bot,
 * permettendo di eseguire azioni di battaglia, determinare i turni, gestire i cambiamenti
 * dei Pokémon e determinare il vincitore.
 * Ogni battaglia contiene informazioni sui giocatori, sulle fabbriche di Pokémon e sulla gestione
 * degli stati della battaglia.
 */
public class Battle {

    private HumanPlayer humanPlayer;
    private BotPlayer currentBotPlayer;
    private int battleCount = 0;

    private PlayerFactory botPlayerFactory;
    private List<PokemonFactory> pokemonFactories;


    /**
     * Costruttore della classe Battle.
     * Inizializza il giocatore umano, il bot e le fabbriche di Pokémon, oltre a configurare il bot
     * con i suoi Pokémon iniziali.
     */
    public Battle() {
        this.humanPlayer = HumanPlayer.getInstance();
        this.pokemonFactories = initializePokemonFactories();


        // Inizializza la factory del bot con una default (SameAverageLevelRandomPokemonFactory)
        this.botPlayerFactory = new BotPlayerFactory(pokemonFactories.get(0));

        // Configura la factory del bot in base a una probabilità
        // setPokemonFactoryWithProbability();

        // Crea il giocatore bot con la factory configurata
        // this.currentBotPlayer = (BotPlayer) botPlayerFactory.createPlayer();


        // Primo BotPlayer Pippo (sempre lui) con Pokémon predefiniti (Squirtle e Garchomp)
        this.currentBotPlayer = new BotPlayer("Pippo", new Pokemon(Breed.breedsMap.get("Squirtle"), 4));
        Breed garchompBreed = Breed.breedsMap.get("Garchomp");
        Pokemon garchompPokemon = new Pokemon(garchompBreed, 14);
        currentBotPlayer.getTeam().addPokemon(garchompPokemon);


    }




    /**
     * Esegue l'azione fornita nel contesto della battaglia e aggiorna lo stato della battaglia.
     * Gestisce cambiamenti di Pokémon e controlla se qualcuno ha vinto.
     *
     * @param action L'azione da eseguire.
     * @return Una lista di stati della battaglia risultanti dall'azione.
     * @throws MoveSetException Se si verifica un errore relativo alle mosse dei Pokémon.
     * @throws IOException Se si verifica un errore di I/O durante l'esecuzione.
     * @throws ForbiddenActionException Se l'azione eseguita non è consentita.
     */
    public List<BattleStatus> executeBattle(Action action) throws MoveSetException, IOException, ForbiddenActionException {

        List<BattleStatus> result = action.execute();
        for (BattleStatus battleStatus : result) {
            System.out.println(battleStatus);
            switch (battleStatus) {
                case CHANGE_BOT_FAINTED_POKEMON:
                    currentBotPlayer.smartPokemonChange();
                    break;

                case CONTINUE_WITH_BOT_CHANGE_POKEMON:
                    currentBotPlayer.smartPokemonChange();
                    break;


                case YOU_WIN:
                    break;


                case CONTINUE_DAMAGE_TO_BOT_POKEMON:
                    break;

                case CONTINUE_DAMAGE_TO_YOUR_POKEMON:
                    break;

            }
        }
        return result;
    }


    /**
     * Determina se il giocatore umano agisce per primo nel turno in base alla priorità
     * dell'azione e alla velocità dei Pokémon.
     *
     * @param action L'azione del giocatore umano.
     * @param botAction L'azione del bot.
     * @return {@code true} se l'umano agisce per primo, {@code false} altrimenti.
     */
    public boolean humanStarts(Action action, Action botAction){
        boolean x = action.getPriority() == botAction.getPriority();
        if (x){
            return humanPlayer.getTeam().getActivePokemon().getSpeed() >= currentBotPlayer.getTeam().getActivePokemon().getSpeed();
        }
        return action.getPriority() >= botAction.getPriority();
    }


    /**
     * Verifica se il giocatore umano ha perso la battaglia.
     *
     * @return {@code true} se il giocatore umano ha perso, {@code false} altrimenti.
     */
    public boolean humanLost() {return humanPlayer.getTeam().allPokemonFainted();}


    /**
     * Verifica se il bot ha perso la battaglia.
     *
     * @return {@code true} se il bot ha perso, {@code false} altrimenti.
     */
    public boolean botLost(){return currentBotPlayer.getTeam().allPokemonFainted();}

    /**
     * Restituisce il giocatore umano.
     *
     * @return Il giocatore umano.
     */
    public HumanPlayer getHumanPlayer() {return humanPlayer;}


    /**
     * Restituisce il bot attualmente in battaglia.
     *
     * @return Il bot attuale.
     */
    public BotPlayer getCurrentBotPlayer() {return currentBotPlayer;}


    /**
     * Crea un nuovo giocatore bot con una factory configurata casualmente.
     */
    public void newBotPlayer(){
        setPokemonFactoryWithProbability();
        currentBotPlayer = (BotPlayer) botPlayerFactory.createPlayer();
        System.out.println(currentBotPlayer.getTeam().getActivePokemon());
    }





    /**
     * Inizializza la lista di fabbriche di Pokémon che possono essere utilizzate per creare i Pokémon dei bot.
     *
     * @return Una lista di fabbriche di Pokémon.
     */
    private List<PokemonFactory> initializePokemonFactories() {
        List<PokemonFactory> factories = new ArrayList<>();
        factories.add(new SameAverageLevelRandomPokemonFactory());
        factories.add(new HighLevelPokemonFactory());
        factories.add(new RandomPokemonFactory());
        return factories;
    }


    /**
     * Configura la factory dei Pokémon del bot in base a una probabilità casuale.
     */
    private void setPokemonFactoryWithProbability() {
        double random = Math.random();
        PokemonFactory newFactory;

        if (random < 0.7) {
            newFactory = pokemonFactories.get(0); // SameAverageLevelRandomPokemonFactory
        } else if (random < 0.9) {
            newFactory = pokemonFactories.get(1); // RandomPokemonFactory
        } else {
            newFactory = pokemonFactories.get(2); // HighLevelPokemonFactory
        }

        botPlayerFactory.setPokemonFactory(newFactory);
        System.out.println("New Bot switched to " + newFactory.getClass().getSimpleName());
    }

    /**
     * Restituisce il numero di battaglie attualmente combattute.
     *
     * @return Il numero di battaglie.
     */
    public int getBattleCount() {
        return battleCount;
    }


    /**
     * Imposta il conteggio delle battaglie.
     *
     * @param increment Il nuovo valore del conteggio delle battaglie.
     */
    public void setBattleCount(int increment){
        this.battleCount = increment;
    }


    /**
     * Resetta il conteggio delle battaglie a zero.
     */
    public void resetBattleCount() {
        battleCount = 0;
    }
}
