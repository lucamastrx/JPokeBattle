package it.jpokebattle.model.item;

import it.jpokebattle.model.Profile;
import it.jpokebattle.model.pokemon.Pokemon;

public class Potion implements ItemStrategy {

    private final int HP_HEALED = 20;
    private final int PURCHASE_PRICE = 50;

    @Override
    public void use(Profile profile, Pokemon pokemon) {
        // cura il pokemon
        // diminuisce il contatore delle pozioni della mappa degli item del giocatore
    }
}
