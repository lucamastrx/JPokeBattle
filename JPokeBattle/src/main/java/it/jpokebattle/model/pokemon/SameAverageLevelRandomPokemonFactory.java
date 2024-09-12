package it.jpokebattle.model.pokemon;

import it.jpokebattle.model.player.HumanPlayer;


/**
 * Classe concreta che estende {@link PokemonFactory} per creare Pokémon con un livello basato
 * sulla media dei livelli della squadra del giocatore umano.
 * I Pokémon creati avranno una razza casuale e un livello fisso.
 */
public class SameAverageLevelRandomPokemonFactory extends PokemonFactory {

    /**
     * Crea un nuovo oggetto {@link Pokemon} con una razza casuale e un livello basato sulla media dei livelli
     * dei Pokémon della squadra del giocatore umano.
     *
     * @return Un nuovo oggetto {@link Pokemon} con razza casuale e livello fisso.
     */
    @Override
    public Pokemon createPokemon() {
        // Genera una breed casuale
        Breed breed = createRandomBreed();

        // Usa il livello fisso dalla classe GameConfig
        int fixedLevel = (int) HumanPlayer.getInstance().getTeam().getAverageLevel() + 1;

        // Costruisce e ritorna un Pokemon con il livello fisso
        return new Pokemon(breed, fixedLevel);
    }

}
