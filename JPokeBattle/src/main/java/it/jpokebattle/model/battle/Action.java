package it.jpokebattle.model.battle;


import it.jpokebattle.model.exceptions.MoveSetException;

import java.io.IOException;

public interface Action {

    int getPriority();

    MoveAction.BattleStatus execute() throws IOException, MoveSetException;
}

