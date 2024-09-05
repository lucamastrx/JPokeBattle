package it.jpokebattle.model.pokemon;

import it.jpokebattle.util.FileManager;

import java.util.*;

public class RandomWildPokemonFactory extends PokemonFactory {



    @Override
    public Pokemon createPokemon() {
        Breed breed = createRandomBreed();
        return new WildPokemon(breed, 0);
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
