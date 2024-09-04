package it.jpokebattle.model;

import it.jpokebattle.model.player.HumanPlayer;
import it.jpokebattle.util.FileManager;
import java.util.HashMap;


/** L'interfaccia Saveable rappresenta un contratto per oggetti che possono essere salvati su un supporto di persistenza.
 * Questa interfaccia utilizza oggetti di tipo V che estendono l'interfaccia Identifiable,
 * il che significa che ogni oggetto salvabile deve avere un identificatore unico.
 * @param <V> il tipo degli oggetti salvabili, che deve implementare l'interfaccia Identifiable.
 */
public interface Saveable<V extends Identifiable> {
    /**
     * Metodo astratto per il salvataggio generico.
     * Implementazioni specifiche definiranno la logica dettagliata di salvataggio.
     */
    void save();

    /**
     * Salva un'istanza specifica del tipo V su un file serializzato.
     * Questo metodo carica una mappa esistente di oggetti dal file specificato,
     * aggiorna la mappa con l'istanza fornita e riscrive il file con la mappa aggiornata.
     * Se la mappa non esiste, viene creata una nuova mappa.
     *
     * @param serSrcName Il nome del file di origine serializzato da cui caricare e in cui salvare la mappa.
     * @param istance L'istanza dell'oggetto di tipo V da salvare nella mappa.
     *
     * @see FileManager
     */
    default void save(String serSrcName, V istance) {
        HashMap<String, V> map = FileManager.loadSER(serSrcName);

        if (map == null)
            map = new HashMap<>();

        map.put(istance.getID(), istance);  // aggiunge o sostituisce il profilo

        FileManager.writeSER(map, serSrcName);
    }


    /**
     * Elimina un'istanza specifica di tipo V da un file serializzato.
     * Questo metodo carica una mappa di oggetti dal file specificato, rimuove l'istanza
     * corrispondente all'ID dell'oggetto passato come parametro, e aggiorna il file.
     * Se la mappa diventa vuota dopo la rimozione, il file viene cancellato.
     *
     * @param serSrcName Il nome del file serializzato da cui caricare e in cui salvare la mappa.
     * @param istance L'istanza dell'oggetto di tipo V da eliminare dalla mappa.
     *
     * @see FileManager
     */
    default void delete(String serSrcName, V istance) {
        HashMap<String, HumanPlayer> map = FileManager.loadSER(serSrcName);
        map.remove(istance.getID()); // Rimuove l'istanza dalla mappa utilizzando il suo ID

        if (map.isEmpty()) {
            FileManager.clearSER(serSrcName); // Cancella il file se la mappa è vuota
        } else {
            FileManager.writeSER(map, serSrcName); // Scrive la mappa aggiornata nel file
        }
    }

}
