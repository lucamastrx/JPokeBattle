package it.jpokebattle.model.exceptions;

/**
 * Eccezione lanciata quando un'azione proibita viene tentata nel contesto della battaglia.
 */
public class ForbiddenActionException extends Exception {

    /**
     * Costruttore di default che fornisce un messaggio di errore predefinito.
     */
    public ForbiddenActionException() {
        super("Non è possibile eseguire questa azione");
    }
}
