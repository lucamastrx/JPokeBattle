package it.jpokebattle.model.player;

import it.jpokebattle.model.pokemon.Pokemon;
import it.jpokebattle.model.pokemon.PokemonFactory;
import it.jpokebattle.model.team.Team;

import java.util.Random;

public class BotPlayerFactory extends PlayerFactory{
    private static final int MAX_TEAM_SIZE = 6;
    private static final Random random = new Random();

    public BotPlayerFactory(PokemonFactory pokemonFactory) {
        super(pokemonFactory);
    }

    @Override
    public BotPlayer  createPlayer() {
        Pokemon activePokemon = pokemonFactory.createPokemon();
        // Inizializzazione Team solo col pokemon attivo
        Team<Pokemon> team = new Team<>(activePokemon);

        // Altri pokemon che l'avversario ha in squadra
        int teamSize = random.nextInt(MAX_TEAM_SIZE - 1); // Genera un numero da 0 a 5
        for (int i = 1; i < teamSize; i++) {
            team.addPokemon(pokemonFactory.createPokemon());
        }

        String name = randomNameRole();
        BotPlayer player = new BotPlayer(name, activePokemon);
        player.setTeam(team);

        return player;
    }
}


/*
        PokemonFactory randomPokemonFactory = new RandomPokemonFactory();
        BotPlayerFactory botPlayerFactory = new BotPlayerFactory(randomPokemonFactory);
        Player botPlayer = botPlayerFactory.createPlayer();
        System.out.println(botPlayer.getTeam()); */