package it.jpokebattle.model.player;

import it.jpokebattle.model.pokemon.Pokemon;


public class BotPlayer extends Player<Pokemon> {
    public BotPlayer(String name, Pokemon activePokemon) {
        super(name, activePokemon);
    }
}
