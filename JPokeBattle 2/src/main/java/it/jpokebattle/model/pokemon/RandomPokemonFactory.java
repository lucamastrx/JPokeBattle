package it.jpokebattle.model.pokemon;

import java.util.*;


/**
 * Classe concreta che estende {@link PokemonFactory} per creare Pokémon di livello casuale.
 * I Pokémon creati avranno una razza casuale e un livello compreso tra 10 e 50.
 */
public class RandomPokemonFactory extends PokemonFactory {

    /**
     * Crea un nuovo oggetto {@link Pokemon} con razza casuale e livello casuale tra 10 e 50.
     *
     * @return Un nuovo oggetto {@link Pokemon} con razza e livello casuali.
     */
    @Override
    public Pokemon createPokemon() {
        // Genera una breed casuale
        Breed breed = createRandomBreed();

        // Generare un livello casuale da 10 a 50 (incluso)
        Random random = new Random();
        int randomLevel = random.nextInt(41) + 10;

        // Costruisce e ritorna un WildPokemon
        return new Pokemon(breed, randomLevel);
    }

}

