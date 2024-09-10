package it.jpokebattle.model.pokemon;



import java.util.*;

public class RandomPokemonFactory extends PokemonFactory {


    @Override
    public Pokemon createPokemon() {
        // Genera una breed casuale
        Breed breed = createRandomBreed();

        // Generare un livello casuale da 10 a 70 (incluso)
        Random random = new Random();
        int randomLevel = random.nextInt(61) + 10;

        // Costruisce e ritorna un WildPokemon
        return new Pokemon(breed, randomLevel);
    }


}

    /*  Codice che verrra usato per creare pokemon:

        RandomPokemonFactory rwpf = new RandomPokemonFactory();
        Pokemon pokmn= rwpf.createPokemon();

        bla bla
     */