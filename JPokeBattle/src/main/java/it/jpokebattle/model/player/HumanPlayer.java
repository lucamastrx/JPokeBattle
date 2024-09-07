package it.jpokebattle.model.player;


import it.jpokebattle.model.pokemon.Breed;

import it.jpokebattle.model.pokemon.OwnedPokemon;
import it.jpokebattle.model.team.Team;




/**
 * I profili consentono all'utente di interfacciarsi con il gioco. Grazie alle istanze di
 * questa classe si può memorizzare sempre lo stato corrente del gioco, sia per il pvp che
 * per l'arcade.
 */
public class HumanPlayer extends Player {

    // Campo statico privato per l'istanza Singleton
    private static HumanPlayer instance;

    // Costruttore privato per impedire l'istanziamento esterno
    private HumanPlayer(String name) {
        super(name, null);
        // initializeStartingTeam();
    }

    // Metodo statico per ottenere l'istanza unica
    public static synchronized HumanPlayer getInstance() {
        if (instance == null) {
            instance = new HumanPlayer("LucaMastrx");
        }
        return instance;
    }

    /*
    private void initializeStartingTeam() {
        Breed charmander = Breed.breedsMap.get("Charmander");
        OwnedPokemon ownedCharmander = new OwnedPokemon(charmander, "",10);

        Breed bulbasaur = Breed.breedsMap.get("Bulbasaur");
        OwnedPokemon ownedBulbasaur = new OwnedPokemon(bulbasaur, "",7);

        Breed squirtle = Breed.breedsMap.get("Squirtle");
        OwnedPokemon ownedSquirtle = new OwnedPokemon(squirtle, "",9);

        getTeam().addPokemon(ownedCharmander);
        getTeam().addPokemon(ownedBulbasaur);
        getTeam().addPokemon(ownedSquirtle);
    }*/

}

