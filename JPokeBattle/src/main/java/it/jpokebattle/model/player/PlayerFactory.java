package it.jpokebattle.model.player;


import it.jpokebattle.model.pokemon.PokemonFactory;
import java.util.Random;

/**
 * Classe astratta che funge da factory per la creazione di giocatori.
 *
 * Questa classe astratta definisce un modello per le factory di giocatori, inclusa la creazione di un giocatore e la gestione della factory di Pokémon associata.
 * Le classi concrete dovrebbero estendere questa classe e implementare il metodo {@link #createPlayer()} per fornire l'implementazione specifica di un tipo di giocatore.
 */
public abstract class PlayerFactory {

    /**
     * La factory di Pokémon utilizzata per creare Pokémon per il giocatore.
     */
    protected PokemonFactory pokemonFactory;

    /**
     * Costruttore della factory del giocatore.
     *
     * @param pokemonFactory La factory di Pokémon da utilizzare per creare Pokémon per il giocatore.
     */
    public PlayerFactory(PokemonFactory pokemonFactory) {
        this.pokemonFactory = pokemonFactory;
    }

    /**
     * Crea un nuovo giocatore.
     *
     * Questo metodo deve essere implementato dalle sottoclassi concrete per creare e restituire un'istanza specifica di {@link Player}.
     *
     * @return Un'istanza di {@link Player} creata dalla factory.
     */
    public abstract Player createPlayer();


    /**
     * Imposta una nuova factory di Pokémon.
     *
     * @param pokemonFactory La nuova factory di Pokémon da utilizzare per creare Pokémon per il giocatore.
     */
    public void setPokemonFactory(PokemonFactory pokemonFactory) {
        this.pokemonFactory = pokemonFactory;
    }


    /**
     * Genera un nome casuale per un giocatore, combinando un nome e un ruolo scelti casualmente da elenchi predefiniti.
     *
     * @return Una stringa che rappresenta un nome casuale e un ruolo per un giocatore.
     */
    public static String randomNameRole() {
        String[] names = {"Paolo", "Luca", "Giulia", "Maria", "Francesco", "Matteo", "Mattia"};
        String[] roles = {"il cuoco", "l'insegnante", "il dottore", "l'ingegnere", "l'artista", "il bullo", "l'allenatore", "il campione"};

        Random random = new Random();
        String randomName = names[random.nextInt(names.length)];
        String randomRole = roles[random.nextInt(roles.length)];

        return randomName + " " + randomRole;
    }

}
