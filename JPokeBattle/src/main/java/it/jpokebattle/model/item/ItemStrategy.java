package it.jpokebattle.model.item;

import it.jpokebattle.model.Profile;
import it.jpokebattle.model.pokemon.Pokemon;

public interface ItemStrategy {
    public void use(Profile profile, Pokemon pokemon);
}
