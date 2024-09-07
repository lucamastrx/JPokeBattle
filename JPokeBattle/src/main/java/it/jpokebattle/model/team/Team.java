package it.jpokebattle.model.team;

import it.jpokebattle.model.pokemon.Pokemon;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Team {
    private List<Pokemon> pokemonList;

    public Team() {
        pokemonList = new ArrayList<>();
    }

    // Metodo per aggiungere un Pokémon alla squadra
    public void addPokemon(Pokemon pokemon) {
        if (pokemonList.size() < 6) {  // Limitiamo la squadra a un massimo di 6 Pokémon, come nei giochi originali
            pokemonList.add(pokemon);
            // System.out.println(pokemon.getName() + " aggiunto alla squadra.");
        } else {
            System.out.println("La squadra è già completa. Non puoi aggiungere altri Pokémon.");
        }
    }

    // Metodo per rimuovere un Pokémon dalla squadra
    public void removePokemon(Pokemon pokemon) {
        if (pokemonList.remove(pokemon)) {
            System.out.println(pokemon.getName() + " rimosso dalla squadra.");
        } else {
            System.out.println(pokemon.getName() + " non è presente nella squadra.");
        }
    }

    // Metodo per ottenere la lista di Pokémon nella squadra
    public List<Pokemon> getPokemonList() {
        return pokemonList;
    }

    // Trova Pokémon per nome
    public Pokemon getPokemonByName(String name) {
        for (Pokemon pokemon : pokemonList) {
            if (pokemon.getName().equalsIgnoreCase(name)) {
                return pokemon;
            }
        }
        System.out.println("Nessun Pokémon trovato con il nome: " + name);
        return null;
    }

    // Sostituisci Pokémon in una posizione specifica
    public void replacePokemon(int index, Pokemon newPokemon) {
        if (index >= 0 && index < pokemonList.size()) {
            System.out.println(pokemonList.get(index).getName() + " sostituito da " + newPokemon.getName());
            pokemonList.set(index, newPokemon);
        } else {
            System.out.println("Indice non valido.");
        }
    }

    // Guarisci tutti i Pokémon
    public void healAllPokemon() {
        for (Pokemon pokemon : pokemonList) {
            pokemon.heal();
        }
        System.out.println("Tutti i Pokémon sono stati curati.");
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
    public void clearTeam() {
        pokemonList.clear();
        System.out.println("La squadra è stata svuotata.");
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

    // Metodo per visualizzare la squadra
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Pokemon pokemon : pokemonList) {
            sb.append(pokemon.toString()).append("\n");  // Aggiunge ogni Pokémon seguito da una nuova riga
        }
        return sb.toString().trim();  // Rimuove l'ultima nuova riga
    }
}
