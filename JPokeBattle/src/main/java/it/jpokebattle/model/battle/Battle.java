package it.jpokebattle.model.battle;

import it.jpokebattle.model.Profile;
import it.jpokebattle.model.pokemon.Pokemon;

public class Battle {
    private static Battle istance;
    private Profile player1;
    private Profile player2;
    private Pokemon striker;
    private Pokemon defender;

    private Battle() { };

    public static Battle getIstance() {
        if (istance == null)
            return new Battle();
        return istance;
    }

    public void startNewBattle(Profile player, Pokemon pkmn1, Pokemon pkmn2) {
        this.player1 = player;
        this.striker = pkmn1;
        this.defender = pkmn2;
    }

    public void startNewBattle(Profile player1, Profile player2, Pokemon pkmn1, Pokemon pkmn2) {
        this.player1 = player1;
        this.player2 = player2;
        this.striker = pkmn1;
        this.defender = pkmn2;
    }

    private void nextRound() {
        Pokemon tmp = striker;
        striker = defender;
        defender = tmp;
    }

    public void setStriker(Pokemon newPokemon) {
        striker = newPokemon;
    }

    public void attack() {
        int damage = striker.getAtk(); // TODO FORMULA DEL DANNO
        defender.setHp(Math.max(defender.getHp() - damage, 0)); // TODO SOSTITUIRE CON UN METODO PER "PRENDERE DANNO" E CONTROLLARE CHE IL POKEMON SIA ANCORA VIVO
    }

    /**
     * TODO: CATTURA E CURA
     */
    public void useItem() {

    }

}
