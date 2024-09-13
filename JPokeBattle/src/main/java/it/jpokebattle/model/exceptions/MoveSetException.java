package it.jpokebattle.model.exceptions;

/**
 * Eccezione lanciata quando un Pokémon ha già imparato 4 mosse e non può impararne altre.
 */
public class MoveSetException extends Exception {

    /**
     * Costruttore di default che fornisce un messaggio di errore predefinito.
     */
    public MoveSetException() {
        super("Il tuo pokemon conosce già 4 mosse");
    }
}
