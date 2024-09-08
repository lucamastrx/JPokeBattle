package it.jpokebattle.model.battle;


public interface Action {

    int getPriority();

    void execute(Battle battle);
}

