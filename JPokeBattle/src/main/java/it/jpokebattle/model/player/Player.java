package it.jpokebattle.model.player;

import it.jpokebattle.model.pokemon.Pokemon;
import it.jpokebattle.model.team.Team;

public abstract class Player<T extends Pokemon> {

    private String name;
    private Team<T> team;


    public Player(String name, T activePokemon) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Il nome del giocatore non può essere null o vuoto");
        }
        if (activePokemon == null) {
            throw new IllegalArgumentException("Il Pokémon attivo non può essere null");
        }
        this.name = name;
        this.team = new Team<>(activePokemon);
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Team<T> getTeam() {
        return team;
    }

    public void setTeam(Team<T> team) {
        this.team = team;
    }
}
