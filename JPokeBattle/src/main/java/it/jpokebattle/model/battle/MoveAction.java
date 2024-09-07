package it.jpokebattle.model.battle;

import it.jpokebattle.model.player.Player;
import it.jpokebattle.model.pokemon.Move;
import it.jpokebattle.model.pokemon.Pokemon;

public class MoveAction implements Action{
    private Move move;
    private Pokemon user;

    public MoveAction(Pokemon user, Move move) {
        this.user = user;
        this.move = move;
    }

    @Override
    public int getPriority() {return move.getPriority();}

    @Override
    public void execute(Battle battle, Player actor, Player opponent) {
        /* Pokemon target = opponent.getActivePokemon();
        int damage = move.getPower(); // Calcolo del danno semplice per esempio
        target.takeDamage(damage);
        System.out.println(user.getName() + " usa " + move.getName() + " su " + target.getName() + " causando " + damage + " danni.");
        if (target.isFainted()) {
            System.out.println(target.getName() + " è esausto!");
            user.gainExperience(50); // Guadagna esperienza
        } */
    }
}
