package it.jpokebattle.model.pokemon;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class WildPokemon extends Pokemon {

    public WildPokemon(Breed breed, int level) {
        super(breed);
        setLevel(level);
        setStatsFromLevel(level);
        setMovesFromLevel(level);
        setStrength( getHp() + getAtk() + getDef() + getSpecialAtk() + getSpecialDef() + getSpeed() + getLevel());
    }



    private void setStatsFromLevel(int lvl){
        setHp((((getBreed().getHpBaseStat() + getHpIV()) * 2 * lvl) / 100)+ lvl + 10);
        setAtk((((getBreed().getAtkBaseStat() + getAtkIV()) * 2 * lvl) / 100) + 5);
        setDef((((getBreed().getDefBaseStat() + getDefIV()) * 2 * lvl) / 100) + 5);
        setSpecialAtk((((getBreed().getSpecialAtkBaseStat() + getSpecialAtkIV()) * 2 * lvl) / 100) + 5);
        setSpecialDef((((getBreed().getSpecialDefBaseStat() + getSpecialDefIV()) * 2 * lvl) / 100) + 5);
        setSpeed((((getBreed().getSpeedBaseStat() + getSpeedIV()) * 2 * lvl) / 100) + 5);
    }

    private void setMovesFromLevel(int lvl){
        HashMap<Integer, String> breedMoves = getBreed().getMoves();

        HashMap<Integer, String> filteredBreedMoves = breedMoves.entrySet()
                .stream()                               // Otteniamo un flusso di entry della mappa (breedMoves.entrySet().stream())
                .filter(entry -> entry.getKey() <= lvl) // Applichiamo il filtro per mantenere solo le entry con chiave <= lvl
                .collect(Collectors.toMap(              // usiamo collect(Collectors.toMap()) per creare una nuova mappa
                        Map.Entry::getKey,              // Map.Entry::getKey specifica che vogliamo usare le chiavi originali come chiavi nella nuova mappa.
                        Map.Entry::getValue,            // specifica che vogliamo usare i valori originali come valori nella nuova mappa.
                        (v1, v2) -> v1,                 // In caso di chiavi duplicate, mantiene il primo valore (è una funzione di merge)
                        HashMap::new                    // Specifica che vogliamo una HashMap come tipo di mappa risultante
                ));


        if (filteredBreedMoves.size() <= 4 && !filteredBreedMoves.isEmpty()) {
            getMoves().clear();
            for (String moveID : filteredBreedMoves.values()) {
                addMove(moveID);
            }

        } else if (filteredBreedMoves.size() > 4) {
            getMoves().clear();

            List<Map.Entry<Integer, String>> sortedEntries = new ArrayList<>(filteredBreedMoves.entrySet());
            sortedEntries.sort(Map.Entry.comparingByKey());

            List<String> movesToAdd = new ArrayList<>();

            // Prendi le 2 mosse con chiave più piccola
            movesToAdd.add(sortedEntries.get(0).getValue());
            movesToAdd.add(sortedEntries.get(1).getValue());

            // Prendi le 2 mosse con chiave più grande
            movesToAdd.add(sortedEntries.get(sortedEntries.size() - 2).getValue());
            movesToAdd.add(sortedEntries.get(sortedEntries.size() - 1).getValue());

            // Usa il metodo addMove(String moveID) su ognuna di queste
            for (String moveID : movesToAdd) {
                addMove(moveID);
            }


        } else if (filteredBreedMoves.isEmpty()) {
            throw new RuntimeException("Errore: Non ci sono mosse disponibili per il livello specificato.");
        }


        System.out.println("Mosse possibili per il livello " + lvl + ": " + filteredBreedMoves);
        System.out.println(getMoves().keySet());
    }

}
