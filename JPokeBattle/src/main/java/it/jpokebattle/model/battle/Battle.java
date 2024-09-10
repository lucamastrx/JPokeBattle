package it.jpokebattle.model.battle;

import it.jpokebattle.model.player.BotPlayerFactory;
import it.jpokebattle.model.player.HumanPlayer;
import it.jpokebattle.model.player.Player;
import it.jpokebattle.model.player.PlayerFactory;
import it.jpokebattle.model.pokemon.HighLevelPokemonFactory;
import it.jpokebattle.model.pokemon.PokemonFactory;
import it.jpokebattle.model.pokemon.RandomPokemonFactory;
import it.jpokebattle.model.pokemon.SameAverageLevelRandomPokemonFactory;

import java.util.ArrayList;
import java.util.List;


public class Battle {

    private Player humanPlayer;
    private Player currentBotPlayer;
    private static int battleCount = 0; // TODO se human perde bisogna rimettere a 0

    private PlayerFactory botPlayerFactory;
    private List<PokemonFactory> pokemonFactories;


    public Battle() {
        this.humanPlayer = HumanPlayer.getInstance();
        this.pokemonFactories = initializePokemonFactories();

        // Inizializza botPlayerFactory con una default PokemonFactory
        this.botPlayerFactory = new BotPlayerFactory(pokemonFactories.get(0));  // Usa una factory di default

        // Configura la factory del bot in base a una probabilità
        setPokemonFactoryWithProbability();

        // Crea il giocatore bot con la factory configurata
        this.currentBotPlayer = this.botPlayerFactory.createPlayer();

        // Aumenta il contatore delle battaglie
        battleCount++;

        // Avvia la battaglia
        startBattle();
    }


    private void startBattle() {
        while (!humanLost() && ! botLost()) {
            // cose
        }
        // cura tutti i tuoi pokemon
    }


    public boolean humanLost() {return humanPlayer.getTeam().allPokemonFainted();}

    public boolean botLost(){
        return currentBotPlayer.getTeam().allPokemonFainted();
    }

    public Player getHumanPlayer() {return humanPlayer;}

    public Player getCurrentBotPlayer() {return currentBotPlayer;}

    public int getBattleCount() {
        return battleCount;
    }




    private List<PokemonFactory> initializePokemonFactories() {
        List<PokemonFactory> factories = new ArrayList<>();
        factories.add(new SameAverageLevelRandomPokemonFactory());
        factories.add(new HighLevelPokemonFactory());
        factories.add(new RandomPokemonFactory());
        return factories;
    }



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
        System.out.println("Switched to " + newFactory.getClass().getSimpleName());
    }
}
