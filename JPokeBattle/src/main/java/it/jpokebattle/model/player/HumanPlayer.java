package it.jpokebattle.model.player;

import it.jpokebattle.model.pokemon.Breed;
import it.jpokebattle.model.pokemon.OwnedPokemon;

import java.util.ArrayList;
import java.util.List;


/**
 * I profili consentono all'utente di interfacciarsi con il gioco. Grazie alle istanze di
 * questa classe si può memorizzare sempre lo stato corrente del gioco, sia per il pvp che
 * per l'arcade.
 */
public class HumanPlayer extends Player<OwnedPokemon> {

    // Campo statico privato per l'istanza Singleton
    private static HumanPlayer instance;

    // Costruttore privato per impedire l'istanziamento esterno
    private HumanPlayer(String name, List<OwnedPokemon> startingTeam) {
        super(name, startingTeam.get(0));  // Imposta il primo Pokémon come attivo
        initializeTeam(startingTeam);
    }


    // Metodo statico per ottenere l'istanza unica con una squadra di default
    public static synchronized HumanPlayer getInstance() { // TODO codice verrà rimpiazzato con un loadSER
        if (instance == null) {
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


            instance = new HumanPlayer("LucaMastrx", defaultTeam);
        }
        return instance;
    }


    // Metodo statico per ottenere l'istanza unica con una squadra personalizzata
    public static synchronized HumanPlayer getInstance(List<OwnedPokemon> startingTeam) {
        if (instance == null) {
            if (startingTeam == null || startingTeam.isEmpty()) {
                throw new IllegalArgumentException("La squadra iniziale non può essere null o vuota");
            }
            instance = new HumanPlayer("LucaMastrx", startingTeam);
        }
        return instance;
    }

    // Metodo per inizializzare la squadra con una lista di Pokémon
    private void initializeTeam(List<OwnedPokemon> startingTeam) {
        // Salta il primo Pokémon (indice 0) perché è già stato aggiunto come attivo
        for (int i = 1; i < startingTeam.size(); i++) {
            getTeam().addPokemon(startingTeam.get(i));
        }
    }

}

