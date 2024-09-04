package it.jpokebattle.model;

import it.jpokebattle.util.FileManager;
import java.util.HashMap;
import java.util.Optional;


/**
 * L'interfaccia Identifiable rappresenta un contratto per oggetti che devono avere un identificatore univoco.
 * Gli oggetti che implementano questa interfaccia devono fornire un metodo per ottenere l'ID.
 */
public interface Identifiable {

    /**
     * Restituisce l'ID univoco dell'oggetto.
     *
     * @return Una stringa che rappresenta l'ID dell'oggetto.
     */
    String getID();

    /**
     * Genera un nuovo ID univoco basato sui profili esistenti memorizzati in un file serializzato.
     * Questo metodo carica una mappa di profili da un file specificato, trova l'ID numerico massimo tra i profili
     * esistenti, e restituisce un nuovo ID incrementato di uno rispetto al massimo trovato.
     * Se la mappa dei profili è vuota o non esiste, restituisce "1" come ID iniziale.
     *
     * @param serSrcName Il nome del file serializzato da cui caricare la mappa dei profili.
     * @return Una stringa che rappresenta il nuovo ID generato.
     *
     * @see FileManager
     */
    default String setID(String serSrcName){
        HashMap<String, HumanPlayer> map = FileManager.loadSER(serSrcName);
        Optional<Integer> max;

        if (map == null)
            return "1"; // Restituisce "1" se la mappa è vuota o non esiste
        else {
            // Converte ogni chiave in intero, trova il massimo e restituisce il nuovo ID incrementato di uno
            max = map.keySet().stream().map(Integer::parseInt).max(Integer::compareTo);
            return String.valueOf(max.get() + 1);
        }
    }
}
