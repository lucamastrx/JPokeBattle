package it.jpokebattle.model.pokemon;

import java.util.HashMap;

public class WildPokemon extends Pokemon {

    public WildPokemon(Breed breed, int level) {
        super(breed);
        this.level = level;



        this.strength = hp + atk + def + specialAtk + specialDef + speed + level;
    }



    private void calculateStatsFromLevel(int level){  }// TODO

/*
        protected int hp;             // punti salute
        protected int atk;
        protected int def;
        protected int specialAtk;
        protected int specialDef;
        protected int speed;
        protected int level;
    }

    private void addMovesFromLevel(int level){  // TODO

        protected HashMap<Move, Integer> moves;
    }*/
}
