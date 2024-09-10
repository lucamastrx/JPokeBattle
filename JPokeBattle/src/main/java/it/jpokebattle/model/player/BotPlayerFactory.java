package it.jpokebattle.model.player;

import it.jpokebattle.model.pokemon.Pokemon;
import it.jpokebattle.model.pokemon.PokemonFactory;

import java.util.Random;

public class BotPlayerFactory extends PlayerFactory{
    private static final Random random = new Random();

    public BotPlayerFactory(PokemonFactory pokemonFactory) {
        super(pokemonFactory);
    }

    @Override
    public BotPlayer createPlayer() {
        Pokemon activePokemon = pokemonFactory.createPokemon();

        String name = PlayerFactory.randomNameRole();
        BotPlayer player = new BotPlayer(name, activePokemon);

        // Genera un numero da 0 a 5 (questo determinerà quanti Pokémon aggiuntivi verranno creati)
        int additionalPokemon = random.nextInt(6);

        for (int i = 0; i < additionalPokemon; i++) {
            player.getTeam().addPokemon(pokemonFactory.createPokemon());
        }

        return player;
    }
}


/*
        PokemonFactory randomPokemonFactory = new RandomPokemonFactory();
        BotPlayerFactory botPlayerFactory = new BotPlayerFactory(randomPokemonFactory);
        Player botPlayer = botPlayerFactory.createPlayer();
        System.out.println(botPlayer.getTeam()); */