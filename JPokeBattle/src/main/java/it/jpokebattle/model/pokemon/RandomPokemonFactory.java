package it.jpokebattle.model.pokemon;

import it.jpokebattle.util.FileManager;

import java.util.*;

public class RandomPokemonFactory extends PokemonFactory {


    /*  Codice che verrra usato per creare pokemon:

        RandomPokemonFactory rwpf = new RandomPokemonFactory();
        Pokemon pokmn= rwpf.createPokemon();

        bla bla
     */


    @Override
    public Pokemon createPokemon() {
        // Genera una breed casuale
        Breed breed = createRandomBreed();

        // Generare un livello casuale da 10 a 70 (incluso)
        Random random = new Random();
        int randomLevel = random.nextInt(61) + 10;

        // Costruisce e ritorna un WildPokemon
        return new WildPokemon(breed, randomLevel);
    }



    private Breed createRandomBreed() {
        // Carica il JSON specificando il tipo di valore Breed
        HashMap<String, Breed> breedMap = FileManager.readJSON("json.breeds", Breed.class);

        // Ottieni l'insieme delle chiavi
        Set<String> keySet = breedMap.keySet();

        // Converti l'insieme in una lista
        List<String> keyList = new ArrayList<>(keySet);

        // Genera un indice casuale
        Random random = new Random();
        int randomIndex = random.nextInt(keyList.size());

        // Seleziona la chiave casuale
        String randomKey = keyList.get(randomIndex);
        return breedMap.get(randomKey);
    }


}
