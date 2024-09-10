package it.jpokebattle.model.battle;


import java.io.IOException;

public interface Action {

    int getPriority();

    void execute() throws IOException;
}

