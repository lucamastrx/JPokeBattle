package it.jpokebattle.model.battle;

import it.jpokebattle.model.exceptions.ForbiddenActionException;
import it.jpokebattle.model.exceptions.MoveSetException;
import it.jpokebattle.model.player.*;
import it.jpokebattle.model.pokemon.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


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
        // setPokemonFactoryWithProbability();

        // Crea il giocatore bot con la factory configurata
        this.currentBotPlayer = (BotPlayer) botPlayerFactory.createPlayer();


        // TODO da togliere dopo test
        this.currentBotPlayer = new BotPlayer("Pippo", new Pokemon(Breed.breedsMap.get("Squirtle"), 4));
        Breed garchompBreed = Breed.breedsMap.get("Garchomp");
        Pokemon garchompPokemon = new Pokemon(garchompBreed, 14);
        currentBotPlayer.getTeam().addPokemon(garchompPokemon);



        System.out.println(currentBotPlayer.getTeam().getActivePokemon());

        // Aumenta il contatore delle battaglie
        battleCount++;

    }





    public List<BattleStatus> executeBattle(Action action) throws MoveSetException, IOException, ForbiddenActionException {

        List<BattleStatus> result = action.execute();
        for (BattleStatus battleStatus : result) {
            System.out.println(battleStatus);
            switch (battleStatus) {


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

    public void newBotPlayer(){ // TODO
        // setPokemonFactoryWithProbability();
        currentBotPlayer = (BotPlayer) botPlayerFactory.createPlayer();
        System.out.println(currentBotPlayer.getTeam().getActivePokemon());
    }

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
