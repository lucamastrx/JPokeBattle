package it.jpokebattle.model.pokemon;

public class HighLevelPokemonFactory extends PokemonFactory {

    private static final int HIGH_LEVEL = 80;

    @Override
    public Pokemon createPokemon() {
        // Genera una breed casuale
        Breed breed = createRandomBreed();

        // Crea e ritorna un Pokemon di alto livello (80)
        return new Pokemon(breed, HIGH_LEVEL);
    }

}
