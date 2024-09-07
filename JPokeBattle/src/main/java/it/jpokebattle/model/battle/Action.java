package it.jpokebattle.model.battle;

import it.jpokebattle.model.player.Player;

public interface Action {

    int getPriority();

    void execute(Battle battle, Player actor, Player opponent);
}

