package it.jpokebattle.model.player;

import it.jpokebattle.model.pokemon.Pokemon;
import it.jpokebattle.model.pokemon.PokemonFactory;

import java.util.Random;


/**
 * Classe factory per la creazione di oggetti {@link BotPlayer}. Utilizza una {@link PokemonFactory} per generare
 * Pokémon e aggiungerli alla squadra del bot. La classe gestisce la creazione di un Pokémon attivo e di un numero
 * variabile di Pokémon aggiuntivi.
 */
public class BotPlayerFactory extends PlayerFactory{
    private static final Random random = new Random();

    /**
     * Costruttore della classe {@code BotPlayerFactory}.
     *
     * @param pokemonFactory La factory utilizzata per creare i Pokémon. Non può essere null.
     * @throws IllegalArgumentException Se {@code pokemonFactory} è null.
     */
    public BotPlayerFactory(PokemonFactory pokemonFactory) {
        super(pokemonFactory);
    }

    /**
     * Crea un nuovo oggetto {@link BotPlayer}. Il metodo genera un Pokémon attivo e un numero casuale di Pokémon
     * aggiuntivi, che vengono poi aggiunti alla squadra del bot. Il nome del bot viene generato tramite il metodo
     * {@link PlayerFactory#randomNameRole()}.
     *
     * @return Un nuovo {@link BotPlayer} con una squadra di Pokémon.
     */
    @Override
    public BotPlayer createPlayer() {
        Pokemon activePokemon = pokemonFactory.createPokemon();

        String name = PlayerFactory.randomNameRole();
        BotPlayer player = new BotPlayer(name, activePokemon);

        // Genera un numero da 0 a 5 (questo determinerà quanti Pokémon aggiuntivi verranno creati)
        int additionalPokemon = random.nextInt(6);

        for (int i = 0; i < additionalPokemon; i++) {
            player.getTeam().addPokemon(pokemonFactory.createPokemon());
        }

        return player;
    }
}
