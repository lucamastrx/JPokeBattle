package it.jpokebattle.model.player;

import it.jpokebattle.model.pokemon.Pokemon;
import it.jpokebattle.model.pokemon.PokemonFactory;
import it.jpokebattle.model.team.Team;

import java.util.Random;

public class BotPlayerFactory extends PlayerFactory{


    public BotPlayerFactory(PokemonFactory pokemonFactory) {
        super(pokemonFactory);
    }

    @Override
    public Player createPlayer() {
        // Inizializzazione Team vuoto
        Team team = new Team();

        // Numero di pokemon che l'avversario ha in squadra
        Random random = new Random();
        int numberOfLoops = random.nextInt(6) + 1; // Genera un numero randomico da 1 a 6
        for (int i = 0; i < numberOfLoops; i++) {
            Pokemon pokemon = pokemonFactory.createPokemon();
            team.addPokemon(pokemon);
        }
        String name = randomNameRole();

        Player player = new BotPlayer(name);
        player.setTeam(team);

        return player;
    }
}


/*
        PokemonFactory randomPokemonFactory = new RandomPokemonFactory();
        BotPlayerFactory botPlayerFactory = new BotPlayerFactory(randomPokemonFactory);
        Player botPlayer = botPlayerFactory.createPlayer();
        System.out.println(botPlayer.getTeam()); */