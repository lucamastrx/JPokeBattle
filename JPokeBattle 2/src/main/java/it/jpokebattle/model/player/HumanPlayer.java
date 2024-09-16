package it.jpokebattle.model.player;

import it.jpokebattle.model.pokemon.Breed;
import it.jpokebattle.model.pokemon.OwnedPokemon;
import it.jpokebattle.model.pokemon.Pokemon;
import it.jpokebattle.util.FileManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;


/**
 * Classe che rappresenta un giocatore umano nel gioco. Implementa il pattern Singleton per assicurare
 * che esista solo un'istanza del giocatore umano durante il gioco.
 */
public class HumanPlayer extends Player<OwnedPokemon> {

    // Campo statico privato per l'istanza Singleton
    private static HumanPlayer instance;

    /**
     * Costruttore privato per impedire l'istanziamento esterno. Inizializza un giocatore umano con un nome
     * e una squadra di Pokémon.
     *
     * @param name         Il nome del giocatore.
     * @param startingTeam La squadra iniziale di Pokémon del giocatore.
     */
    private HumanPlayer(String name, List<OwnedPokemon> startingTeam) {
        super(name, startingTeam.get(0));  // Imposta il primo Pokémon come attivo
        initializeTeam(startingTeam);
    }


    /**
     * Metodo statico per ottenere l'istanza Singleton del giocatore umano. Se l'istanza non è stata ancora
     * creata, la inizializza con una squadra predefinita.
            *
            * @return L'istanza Singleton di HumanPlayer.
            */
    public static synchronized HumanPlayer getInstance() {
        if (instance == null) {

            /*
            HashMap<String, OwnedPokemon> defaultTeamHashMap = FileManager.loadSER("ser.ownedPokemon");
            List<OwnedPokemon> defaultTeam = new ArrayList<>(defaultTeamHashMap.values());
            */

            // Inizializza la squadra di default con Charmander, Bulbasaur e Squirtle
            List<OwnedPokemon> defaultTeam = new ArrayList<>();

            Breed charmanderBreed = Breed.breedsMap.get("Charmander");
            OwnedPokemon ownedCharmander = new OwnedPokemon(charmanderBreed, "", 10);

            Breed bulbasaurBreed = Breed.breedsMap.get("Bulbasaur");
            OwnedPokemon ownedBulbasaur = new OwnedPokemon(bulbasaurBreed, "", 7);

            Breed squirtleBreed = Breed.breedsMap.get("Squirtle");
            OwnedPokemon ownedSquirtle = new OwnedPokemon(squirtleBreed, "", 9);

            Breed tyranitarBreed = Breed.breedsMap.get("Tyranitar");
            OwnedPokemon ownedTyranitar = new OwnedPokemon(tyranitarBreed, "", 20);

            Breed garchompBreed = Breed.breedsMap.get("Garchomp");
            OwnedPokemon ownedGarchomp = new OwnedPokemon(garchompBreed, "", 90);

            Breed dragoniteBreed = Breed.breedsMap.get("Dragonite");
            OwnedPokemon ownedDragonite = new OwnedPokemon(dragoniteBreed, "", 80);

            defaultTeam.add(ownedCharmander);
            defaultTeam.add(ownedBulbasaur);
            defaultTeam.add(ownedSquirtle);
            defaultTeam.add(ownedTyranitar);
            defaultTeam.add(ownedGarchomp);
            defaultTeam.add(ownedDragonite);

            /*
            HashMap<String, OwnedPokemon> pokemonMap = new HashMap<>();
            pokemonMap.put("Charmander", ownedCharmander);
            pokemonMap.put("Bulbasaur", ownedBulbasaur);
            pokemonMap.put("Squirtle", ownedSquirtle);
            pokemonMap.put("Tyranitar", ownedTyranitar);
            pokemonMap.put("Garchomp", ownedGarchomp);
            pokemonMap.put("Dragonite", ownedDragonite);

            FileManager.writeSER(pokemonMap, "ser.ownedPokemon");
            */
            instance = new HumanPlayer("LucaMastrx", defaultTeam);
        }
        return instance;
    }


    /**
     * Metodo statico per ottenere l'istanza Singleton del giocatore umano con una squadra personalizzata.
     * Se l'istanza non è stata ancora creata, la inizializza con la squadra fornita.
     *
     * @param startingTeam La squadra iniziale di Pokémon del giocatore.
     * @return L'istanza Singleton di HumanPlayer.
     * @throws IllegalArgumentException Se la squadra iniziale è null o vuota.
     */
    public static synchronized HumanPlayer getInstance(List<OwnedPokemon> startingTeam) {
        if (instance == null) {
            if (startingTeam == null || startingTeam.isEmpty()) {
                throw new IllegalArgumentException("La squadra iniziale non può essere null o vuota");
            }
            instance = new HumanPlayer("LucaMastrx", startingTeam);
        }
        return instance;
    }


    /**
     * Inizializza la squadra del giocatore umano con una lista di Pokémon. Salta il primo Pokémon
     * perché è già stato impostato come attivo.
     *
     * @param startingTeam La lista dei Pokémon con cui inizializzare la squadra.
     */
    private void initializeTeam(List<OwnedPokemon> startingTeam) {
        // Salta il primo Pokémon (indice 0) perché è già stato aggiunto come attivo
        for (int i = 1; i < startingTeam.size(); i++) {
            getTeam().addPokemon(startingTeam.get(i));
        }
    }

}

