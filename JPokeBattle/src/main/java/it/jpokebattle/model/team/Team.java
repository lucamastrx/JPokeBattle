package it.jpokebattle.model.team;

import it.jpokebattle.model.pokemon.Pokemon;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Team <T extends Pokemon> {
    private List<T> pokemonList;
    private T activePokemon;

    public Team(T activePokemon) {
        if (activePokemon == null) {
            throw new IllegalArgumentException("Il Pokémon attivo non può essere null");
        }
        this.pokemonList = new ArrayList<>();
        this.activePokemon = activePokemon;
        addPokemon(activePokemon);
    }

    // Metodo per aggiungere un Pokémon alla squadra
    public void addPokemon(T pokemon) {
        if (pokemon == null) {
            throw new IllegalArgumentException("Il Pokémon non può essere null");
        }
        if (pokemonList.size() < 6) {
            pokemonList.add(pokemon);
        } else {
            System.out.println("La squadra è già completa. Non puoi aggiungere altri Pokémon.");
        }
    }
    // Metodo per rimuovere un Pokémon dalla squadra
    public void removePokemon(T pokemon) {
        if (pokemonList.size() == 1) {
            System.out.println("Non puoi rimanere senza compagni in squadra!");
            return;
        }
        if (pokemonList.remove(pokemon)) {
            System.out.println(pokemon.getName() + " rimosso dalla squadra.");
        } else {
            System.out.println(pokemon.getName() + " non è presente nella squadra.");
        }
    }

    public T getActivePokemon() {return activePokemon;}

    public void setActivePokemon(T pokemon) {
        if (pokemonList.contains(pokemon)) {
            this.activePokemon = pokemon;
            System.out.println(pokemon.getName() + " è ora il Pokémon attivo.");
        } else {
            throw new IllegalArgumentException("Il Pokémon specificato non è presente nella squadra.");
        }
    }

    // Metodo per ottenere una lista di Pokémon che non sono svenuti
    public List<T> getAvailablePokemon() {
        return pokemonList.stream()            // Crea uno stream dalla lista di Pokémon
                .filter(pokemon -> !pokemon.isFainted())  // Filtra i Pokémon che non sono svenuti
                .toList();                      // Colleziona il risultato in una lista
    }

    // Metodo per ottenere la lista di Pokémon nella squadra
    public List<T> getPokemonList() {
        return new ArrayList<>(pokemonList);
    }

    // Trova Pokémon per nome
    public T getPokemonByName(String name) {
        return pokemonList.stream()
                .filter(p -> p.getName().equalsIgnoreCase(name))
                .findFirst()
                .orElse(null);
    }

    // Sostituisci Pokémon in una posizione specifica
    public void removeAndReplacePokemon(int index, T newPokemon) {
        if (index >= 0 && index < pokemonList.size()) {
            System.out.println(pokemonList.get(index).getName() + " sostituito da " + newPokemon.getName());
            pokemonList.set(index, newPokemon);
            if (activePokemon.equals(pokemonList.get(index))) {
                activePokemon = newPokemon;
            }
        } else {
            System.out.println("Indice non valido.");
        }
    }

    // Guarisci tutti i Pokémon
    public void healAllPokemon() {
        pokemonList.forEach(Pokemon::heal);
        // System.out.println("Tutti i Pokémon sono stati curati.");
    }


    // Calcola il livello medio dei Pokémon nella squadra
    public double getAverageLevel() {
        return pokemonList.stream().mapToInt(Pokemon::getLevel).average().orElse(0.0);
    }

    // Verifica se la squadra è piena
    public boolean isFull() {
        return pokemonList.size() == 6;
    }

    // Rimuovi tutti i Pokémon dalla squadra
    public void clearTeamExceptActivePokemon() {
        T x = activePokemon;
        pokemonList.clear();
        pokemonList.add(x);
        System.out.println("La squadra è stata svuotata (Eccetto il Pokemon Attivo: " + activePokemon.getName() + ")");
    }

    // Scambia la posizione di due Pokémon
    public void swapPokemon(int index1, int index2) {
        if (index1 >= 0 && index1 < pokemonList.size() && index2 >= 0 && index2 < pokemonList.size()) {
            Collections.swap(pokemonList, index1, index2);
            System.out.println("Scambiati " + pokemonList.get(index1).getName() + " e " + pokemonList.get(index2).getName());
        } else {
            System.out.println("Indice non valido.");
        }
    }

    public boolean allPokemonFainted(){
        return pokemonList.stream()                            // crea uno stream dalla lista di Pokemon
                .allMatch(Pokemon::isFainted);                   // allMatch() verifica se tutti gli elementi dello stream soddisfano una certa condizione
    }

    // Metodo per visualizzare la squadra
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (T pokemon : pokemonList) {
            sb.append(pokemon.toString()).append("\n");  // Aggiunge ogni Pokémon seguito da una nuova riga
        }
        return sb.toString().trim();  // Rimuove l'ultima nuova riga
    }
}
