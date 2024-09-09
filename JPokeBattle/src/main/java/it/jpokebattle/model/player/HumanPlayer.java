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
public class HumanPlayer extends Player<OwnedPokemon> { // TODO fix bug 4 pokemons

    // Campo statico privato per l'istanza Singleton
    private static HumanPlayer instance;

    // Costruttore privato per impedire l'istanziamento esterno
    private HumanPlayer(String name, List<OwnedPokemon> startingTeam) {
        super(name, startingTeam.get(0));  // Imposta il primo Pokémon come attivo
        initializeTeam(startingTeam);
    }


    // Metodo statico per ottenere l'istanza unica con una squadra di default
    public static synchronized HumanPlayer getInstance() {
        if (instance == null) {
            // Inizializza la squadra di default con Charmander, Bulbasaur e Squirtle
            List<OwnedPokemon> defaultTeam = new ArrayList<>();

            Breed charmander = Breed.breedsMap.get("Charmander");
            OwnedPokemon ownedCharmander = new OwnedPokemon(charmander, "", 10);

            Breed bulbasaur = Breed.breedsMap.get("Bulbasaur");
            OwnedPokemon ownedBulbasaur = new OwnedPokemon(bulbasaur, "", 7);

            Breed squirtle = Breed.breedsMap.get("Squirtle");
            OwnedPokemon ownedSquirtle = new OwnedPokemon(squirtle, "", 9);

            defaultTeam.add(ownedCharmander);
            defaultTeam.add(ownedBulbasaur);
            defaultTeam.add(ownedSquirtle);

            instance = new HumanPlayer("LucaMastrx", defaultTeam);
        }
        return instance;
    }


    // Metodo statico per ottenere l'istanza unica con una squadra personalizzata
    public static synchronized HumanPlayer getInstance(List<OwnedPokemon> startingTeam) {
        if (instance == null) {
            instance = new HumanPlayer("LucaMastrx", startingTeam);
        }
        return instance;
    }

    // Metodo per inizializzare la squadra con una lista di Pokémon
    private void initializeTeam(List<OwnedPokemon> startingTeam) {
        startingTeam.forEach(pokemon -> getTeam().addPokemon(pokemon));
    }

}

