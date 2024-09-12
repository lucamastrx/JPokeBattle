package it.jpokebattle.model.pokemon;


/**
 * Classe concreta che estende {@link PokemonFactory} per creare Pokémon di alto livello.
 * I Pokémon creati avranno una razza casuale e un livello predefinito di 80.
 */
public class HighLevelPokemonFactory extends PokemonFactory {

    private static final int HIGH_LEVEL = 80;

    /**
     * Crea un nuovo oggetto {@link Pokemon} con una razza casuale e un livello predefinito di 80.
     *
     * @return Un nuovo oggetto {@link Pokemon} con razza casuale e livello alto.
     */
    @Override
    public Pokemon createPokemon() {
        // Genera una breed casuale
        Breed breed = createRandomBreed();

        // Crea e ritorna un Pokemon di alto livello (80)
        return new Pokemon(breed, HIGH_LEVEL);
    }

}
