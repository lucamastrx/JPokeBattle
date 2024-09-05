package it.jpokebattle.model.pokemon;

import java.util.HashMap;

public class WildPokemon extends Pokemon {

    public WildPokemon(Breed breed, int level) {
        super(breed);
        setLevel(level);
        setStatsFromLevel(level);



        setStrength( getHp() + getAtk() + getDef() + getSpecialAtk() + getSpecialDef() + getSpeed() + getLevel());
    }



    private void setStatsFromLevel(int lvl){
        setHp((((getBreed().getHpBaseStat() + getHpIV()) * 2 * lvl) / 100)+ lvl + 10);
        setAtk((((getBreed().getAtkBaseStat() + getAtkIV()) * 2 * lvl) / 100) + 5);
        setDef((((getBreed().getDefBaseStat() + getDefIV()) * 2 * lvl) / 100) + 5);
        setSpecialAtk((((getBreed().getSpecialAtkBaseStat() + getSpecialAtkIV()) * 2 * lvl) / 100) + 5);
        setSpecialDef((((getBreed().getSpecialDefBaseStat() + getSpecialDefIV()) * 2 * lvl) / 100) + 5);
        setSpeed((((getBreed().getSpeedBaseStat() + getSpeedIV()) * 2 * lvl) / 100) + 5);
    }

    private void addMovesFromLevel(int level){  // TODO
        /*
        protected HashMap<Move, Integer> moves;*/
    }
}
