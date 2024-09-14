package it.jpokebattle.model.battle;

import it.jpokebattle.model.player.*;
import it.jpokebattle.model.pokemon.*;
import java.util.ArrayList;
import java.util.List;


/**
 * La classe {@code Battle} gestisce una battaglia Pokémon tra un giocatore umano e un bot.
 * La classe gestisce la creazione dei giocatori, l'inizializzazione dei Pokémon, e determina
 * le condizioni di vittoria o sconfitta durante il combattimento.
 */
public class Battle {

    private HumanPlayer humanPlayer;
    private BotPlayer currentBotPlayer;
    private int battleCount = 0;

    private PlayerFactory botPlayerFactory;
    private List<PokemonFactory> pokemonFactories;


    /**
     * Costruttore della classe {@code Battle}.
     * Inizializza il giocatore umano, il giocatore bot, e le fabbriche di Pokémon.
     * Configura un bot predefinito con i suoi Pokémon iniziali.
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
     * Determina se il giocatore umano agisce per primo nel turno in base alla priorità delle azioni
     * e alla velocità dei Pokémon coinvolti.
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
     * Verifica se il giocatore umano ha perso la battaglia. Un giocatore ha perso se tutti i suoi Pokémon sono esausti.
     *
     * @return {@code true} se il giocatore umano ha perso, {@code false} altrimenti.
     */
    public boolean humanLost() {return humanPlayer.getTeam().allPokemonFainted();}


    /**
     * Verifica se il bot ha perso la battaglia. Un bot ha perso se tutti i suoi Pokémon sono esausti.
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
     * Crea un nuovo giocatore bot con una factory configurata in base a una probabilità casuale.
     * Questo cambia il set di Pokémon che il bot utilizzerà.
     */
    public void newBotPlayer(){
        setPokemonFactoryWithProbability();
        currentBotPlayer = (BotPlayer) botPlayerFactory.createPlayer();
        System.out.println(currentBotPlayer.getTeam().getActivePokemon());
    }


    /**
     * Inizializza la lista di fabbriche di Pokémon che possono essere utilizzate per creare
     * i Pokémon del bot durante le battaglie.
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
     * Configura la factory dei Pokémon del bot in base a una probabilità casuale, determinando
     * quali Pokémon il bot utilizzerà.
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
     * @return Il numero di battaglie attualmente combattute.
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
