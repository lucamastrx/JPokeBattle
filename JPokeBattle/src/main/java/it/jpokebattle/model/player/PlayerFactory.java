package it.jpokebattle.model.player;


import it.jpokebattle.model.pokemon.PokemonFactory;

import java.util.Random;

public abstract class PlayerFactory {

    protected PokemonFactory pokemonFactory;

    public PlayerFactory(PokemonFactory pokemonFactory) {
        this.pokemonFactory = pokemonFactory;
    }

    public abstract Player createPlayer();


    public void setPokemonFactory(PokemonFactory pokemonFactory) {
        this.pokemonFactory = pokemonFactory;
    }


    public static String randomNameRole() {
        String[] names = {"Paolo", "Luca", "Giulia", "Maria", "Francesco", "Matteo", "Mattia"};
        String[] roles = {"il cuoco", "l'insegnante", "il dottore", "l'ingegnere", "l'artista", "il bullo", "l'allenatore", "il campione"};

        Random random = new Random();
        String randomName = names[random.nextInt(names.length)];
        String randomRole = roles[random.nextInt(roles.length)];

        return randomName + " " + randomRole;
    }

}
