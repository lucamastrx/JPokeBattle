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
        Pokemon frontman = pokemonFactory.createPokemon();
        // Inizializzazione Team solo col pokemon frontman
        Team team = new Team(frontman);

        // Altri pokemon che l'avversario ha in squadra
        Random random = new Random();
        int numberOfLoops = random.nextInt(6); // Genera un numero randomico da 0 a 5
        for (int i = 0; i < numberOfLoops; i++) {
            Pokemon pokemon = pokemonFactory.createPokemon();
            team.addPokemon(pokemon);
        }
        String name = randomNameRole();

        Player player = new BotPlayer(name, frontman);
        player.setTeam(team);

        return player;
    }
}


/*
        PokemonFactory randomPokemonFactory = new RandomPokemonFactory();
        BotPlayerFactory botPlayerFactory = new BotPlayerFactory(randomPokemonFactory);
        Player botPlayer = botPlayerFactory.createPlayer();
        System.out.println(botPlayer.getTeam()); */