package it.jpokebattle.model.pokemon;

import java.util.HashMap;

public class WildPokemon extends Pokemon {

    public WildPokemon(Breed breed, int level) {
        super(breed);
        setLevel(level);




        setStrength( getHp() + getAtk() + getDef() + getSpecialAtk() + getSpecialDef() + getSpeed() + getLevel());
    }



    private void calculateStatsFromLevel(int lvl){  // TODO
        setHp(getBreed().getHpBaseStat() + getHpIV() * 2 * lvl);
        /*protected int hp;             // punti salute
        protected int atk;
        protected int def;
        protected int specialAtk;
        protected int specialDef;
        protected int speed;
        protected int level;*/
    }

    private void addMovesFromLevel(int level){  // TODO
        /*
        protected HashMap<Move, Integer> moves;*/
    }
}
