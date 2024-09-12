package it.jpokebattle.model.pokemon;

import it.jpokebattle.util.FileManager;

import java.util.*;

/**
 * Classe astratta che rappresenta una fabbrica di oggetti {@link Pokemon}.
 * Definisce il metodo astratto {@link #createPokemon()} che deve essere implementato dalle classi concrete.
 * Fornisce un metodo protetto per creare una razza ({@link Breed}) casuale.
 */
public abstract class PokemonFactory {

    /**
     * Metodo astratto per la creazione di un oggetto {@link Pokemon}.
     * Ogni sottoclasse fornirà la propria implementazione per creare un Pokémon con caratteristiche specifiche.
     *
     * @return Un nuovo oggetto {@link Pokemon}.
     */
    public abstract Pokemon createPokemon();

    /**
     * Metodo protetto per generare una razza di Pokémon ({@link Breed}) casuale.
     * Carica le razze disponibili da un file JSON e seleziona casualmente una di esse.
     *
     * @return Un oggetto {@link Breed} casuale.
     */
    protected Breed createRandomBreed() {
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
