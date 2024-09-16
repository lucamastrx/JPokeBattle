package it.jpokebattle.model.player;

import it.jpokebattle.model.pokemon.Pokemon;
import it.jpokebattle.model.team.Team;


/**
 * Classe astratta che rappresenta un giocatore generico in un gioco di Pokémon. Ogni giocatore ha un nome
 * e una squadra di Pokémon.
 *
 * @param <T> Tipo di Pokémon che il giocatore può avere nella sua squadra.
 */
public abstract class Player<T extends Pokemon> {

    private String name;
    private Team<T> team;

    /**
     * Costruttore della classe Player. Inizializza un giocatore con un nome e un Pokémon attivo.
     *
     * @param name          Il nome del giocatore. Non può essere null o vuoto.
     * @param activePokemon Il Pokémon attivo del giocatore. Non può essere null.
     * @throws IllegalArgumentException Se il nome è null o vuoto, oppure se il Pokémon attivo è null.
     */
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

    /**
     * Restituisce il nome del giocatore.
     *
     * @return Il nome del giocatore.
     */
    public String getName() {
        return name;
    }

    /**
     * Imposta il nome del giocatore.
     *
     * @param name Il nome del giocatore.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Restituisce la squadra di Pokémon del giocatore.
     *
     * @return La squadra del giocatore.
     */
    public Team<T> getTeam() {
        return team;
    }

    /**
     * Imposta la squadra di Pokémon del giocatore.
     *
     * @param team La nuova squadra del giocatore.
     */
    public void setTeam(Team<T> team) {
        this.team = team;
    }
}
