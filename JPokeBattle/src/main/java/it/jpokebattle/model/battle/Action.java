package it.jpokebattle.model.battle;

import it.jpokebattle.model.exceptions.MoveSetException;
import java.io.IOException;
import java.util.List;


public interface Action {

    int getPriority();

    List<BattleStatus> execute() throws IOException, MoveSetException;
}

