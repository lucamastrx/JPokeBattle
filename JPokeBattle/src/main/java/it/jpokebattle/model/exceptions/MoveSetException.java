package it.jpokebattle.model.exceptions;

public class MoveSetException extends Exception{

    public MoveSetException(){
        super("Il tuo pokemon conosce già 4 mosse");
    }
}
