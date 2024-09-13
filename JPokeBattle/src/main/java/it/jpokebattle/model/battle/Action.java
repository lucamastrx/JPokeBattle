package it.jpokebattle.model.battle;

import it.jpokebattle.model.exceptions.MoveSetException;
import java.io.IOException;
import java.util.List;

/**
 * Interfaccia che rappresenta un'azione eseguibile in una battaglia Pokémon.
 * Un'azione può essere qualsiasi mossa o interazione che un giocatore o un Pokémon esegue
 * durante una battaglia.
 */
public interface Action {


    /**
     * Restituisce la priorità dell'azione. La priorità determina l'ordine di esecuzione
     * delle azioni in una battaglia.
     *
     * @return un intero che rappresenta la priorità dell'azione. Valori più alti indicano
     *         che l'azione verrà eseguita prima.
     */
    int getPriority();



    /**
     * Esegue l'azione e restituisce lo stato della battaglia dopo l'esecuzione.
     *
     * @return una lista di {@link BattleStatus} che rappresenta l'effetto dell'azione sulla
     *         battaglia, oppure null se l'azione non può essere eseguita.
     * @throws IOException se si verifica un errore di input/output durante l'esecuzione.
     * @throws MoveSetException se si verifica un errore relativo al set di mosse del Pokémon.
     */
    List<BattleStatus> execute() throws IOException, MoveSetException;
}

