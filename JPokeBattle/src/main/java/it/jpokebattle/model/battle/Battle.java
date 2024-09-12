package it.jpokebattle.model.battle;

import it.jpokebattle.model.exceptions.ForbiddenActionException;
import it.jpokebattle.model.exceptions.MoveSetException;
import it.jpokebattle.model.player.*;
import it.jpokebattle.model.pokemon.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static it.jpokebattle.model.battle.BattleStatus.*;


public class Battle {

    private HumanPlayer humanPlayer;
    private BotPlayer currentBotPlayer;
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
        this.currentBotPlayer = (BotPlayer) botPlayerFactory.createPlayer();
        this.currentBotPlayer = new BotPlayer("Pippo", new Pokemon(Breed.breedsMap.get("Squirtle"), 4)); // TODO da togliere dopo test

        // Aumenta il contatore delle battaglie
        battleCount++;

    }





    public BattleStatus[] executeBattle(Action action) throws MoveSetException, IOException, ForbiddenActionException {

        BattleStatus[] result = action.execute();
        for (BattleStatus battleStatus : result) {
            System.out.println(battleStatus);
            switch (battleStatus) {


                case CONTINUE_WITH_BOT_CHANGE_POKEMON:
                    currentBotPlayer.smartPokemonChange();
                    return new BattleStatus[]{CONTINUE_WITH_BOT_CHANGE_POKEMON, YES_LEVEL_UP};


                case YOU_WIN:
                    return new BattleStatus[]{BattleStatus.YOU_WIN, BattleStatus.YES_LEVEL_UP};


                case CONTINUE_DAMAGE_TO_BOT_POKEMON:
                    return new BattleStatus[]{CONTINUE_DAMAGE_TO_BOT_POKEMON};

                case CONTINUE_DAMAGE_TO_YOUR_POKEMON:
                    return new BattleStatus[]{CONTINUE_DAMAGE_TO_YOUR_POKEMON};

                default:
                    throw new ForbiddenActionException();
            }
        }
        return null;
    }

    public boolean humanStarts(Action action, Action botAction){
        boolean x = action.getPriority() == botAction.getPriority();
        if (x){
            return humanPlayer.getTeam().getActivePokemon().getSpeed() >= currentBotPlayer.getTeam().getActivePokemon().getSpeed();
        }
        return action.getPriority() >= botAction.getPriority();
    }

    public boolean humanLost() {return humanPlayer.getTeam().allPokemonFainted();}

    public boolean botLost(){
        return currentBotPlayer.getTeam().allPokemonFainted();
    }

    public HumanPlayer getHumanPlayer() {return humanPlayer;}

    public BotPlayer getCurrentBotPlayer() {return currentBotPlayer;}

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
