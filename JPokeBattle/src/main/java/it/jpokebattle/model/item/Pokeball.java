package it.jpokebattle.model.item;

import it.jpokebattle.model.Profile;
import it.jpokebattle.model.pokemon.Pokemon;

import java.util.Random;

public class Pokeball implements ItemStrategy {
    private Random random = new Random();

    @Override
    public void use(Profile profile, Pokemon pokemon) {
        random.nextBoolean();
    }
}
