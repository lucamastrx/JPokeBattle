package it.jpokebattle.model.pokemon;


import it.jpokebattle.model.player.HumanPlayer;

public class SameAverageLevelRandomPokemonFactory extends PokemonFactory {

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
