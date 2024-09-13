package it.jpokebattle.model.team;

import it.jpokebattle.model.pokemon.Pokemon;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


/**
 * Classe che rappresenta una squadra di Pokémon appartenente a un giocatore. La squadra contiene un Pokémon attivo
 * e una lista di Pokémon disponibili.
 *
 * @param <T> Tipo di Pokémon contenuti nella squadra.
 */
public class Team <T extends Pokemon> {
    private List<T> pokemonList;
    private T activePokemon;


    /**
     * Costruttore della classe Team. Inizializza una squadra con un Pokémon attivo.
     *
     * @param activePokemon Il Pokémon attivo della squadra. Non può essere null.
     * @throws IllegalArgumentException Se il Pokémon attivo è null.
     */
    public Team(T activePokemon) {
        if (activePokemon == null) {
            throw new IllegalArgumentException("Il Pokémon attivo non può essere null");
        }
        this.pokemonList = new ArrayList<>();
        this.activePokemon = activePokemon;
        addPokemon(activePokemon);
    }


    /**
     * Aggiunge un nuovo Pokémon alla lista dei Pokémon disponibili della squadra.
     *
     * @param pokemon Il Pokémon da aggiungere alla squadra. Non può essere null.
     * @throws IllegalArgumentException Se il Pokémon è null.
     */
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


    /**
     * Rimuove un Pokémon dalla squadra. Se la squadra contiene solo un Pokémon, la rimozione non è consentita.
     *
     * @param pokemon Il Pokémon da rimuovere dalla squadra. Non può essere null.
     * @throws IllegalArgumentException Se {@code pokemon} è null.
     */
    public void removePokemon(T pokemon) {
        if (pokemon == null) {
            throw new IllegalArgumentException("Il Pokémon da rimuovere non può essere null.");
        }
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


    /**
     * Restituisce il Pokémon attivo della squadra.
     *
     * @return Il Pokémon attivo della squadra.
     */
    public T getActivePokemon() {return activePokemon;}


    /**
     * Imposta un nuovo Pokémon come Pokémon attivo della squadra.
     *
     * @param pokemon Il nuovo Pokémon attivo della squadra. Non può essere null.
     * @throws IllegalArgumentException Se il Pokémon attivo è null.
     */
    public void setActivePokemon(T pokemon) {
        if (pokemonList.contains(pokemon)) {
            this.activePokemon = pokemon;
            System.out.println(pokemon.getName() + " è ora il Pokémon attivo.");
        } else {
            throw new IllegalArgumentException("Il Pokémon specificato non è presente nella squadra.");
        }
    }

    /**
     * Restituisce la lista di tutti i Pokémon disponibili NON svenuti nella squadra.
     *
     * @return La lista dei Pokémon disponibili.
     */
    public List<T> getAvailablePokemon() {
        return pokemonList.stream()            // Crea uno stream dalla lista di Pokémon
                .filter(pokemon -> !pokemon.isFainted())  // Filtra i Pokémon che non sono svenuti
                .toList();                      // Colleziona il risultato in una lista
    }


    /**
     * Restituisce una copia della lista dei Pokémon nella squadra.
     *
     * @return Una lista immutabile contenente i Pokémon nella squadra.
     */
    public List<T> getPokemonList() {
        return new ArrayList<>(pokemonList);
    }

    /**
     * Trova un Pokémon nella squadra in base al suo nome.
     *
     * @param name Il nome del Pokémon da cercare. Non può essere null.
     * @return Il Pokémon con il nome specificato, se presente nella squadra; altrimenti, null.
     * @throws IllegalArgumentException Se {@code name} è null.
     */
    public T getPokemonByName(String name) {
        return pokemonList.stream()
                .filter(p -> p.getName().equalsIgnoreCase(name))
                .findFirst()
                .orElse(null);
    }


    /**
     * Sostituisce un Pokémon in una posizione specifica della squadra con un nuovo Pokémon.
     * Se il Pokémon attivo viene sostituito, il nuovo Pokémon diventa il Pokémon attivo.
     *
     * @param index L'indice della posizione del Pokémon da sostituire. Deve essere compreso tra 0 e {@code pokemonList.size() - 1}.
     * @param newPokemon Il nuovo Pokémon da inserire nella posizione specificata. Non può essere null.
     * @throws IndexOutOfBoundsException Se {@code index} è al di fuori dei limiti della lista.
     * @throws IllegalArgumentException Se {@code newPokemon} è null.
     */
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


    /**
     * Guarisce tutti i Pokémon nella squadra.
     * Questo metodo chiama il metodo {@code heal} su ogni Pokémon nella lista della squadra per ripristinare il loro stato di salute.
     * Attualmente non produce alcun output a console, ma una riga di codice commentata è presente per informare che tutti i Pokémon sono stati curati.
     */
    public void healAllPokemon() {
        pokemonList.forEach(Pokemon::heal);
        // System.out.println("Tutti i Pokémon sono stati curati.");
    }


    /**
     * Calcola il livello medio dei Pokémon nella squadra.
     * Questo metodo utilizza uno stream per estrarre i livelli dei Pokémon e calcolare la media.
     * Se la squadra è vuota, ritorna {@code 0.0}.
     *
     * @return Il livello medio dei Pokémon nella squadra, o {@code 0.0} se la squadra è vuota.
     */
    public double getAverageLevel() {
        return pokemonList.stream().mapToInt(Pokemon::getLevel).average().orElse(0.0);
    }

    /**
     * Verifica se la squadra è piena.
     * Una squadra è considerata piena se contiene esattamente 6 Pokémon.
     *
     * @return {@code true} se la squadra contiene 6 Pokémon, {@code false} altrimenti.
     */
    public boolean isFull() {
        return pokemonList.size() == 6;
    }

    /**
     * Rimuove tutti i Pokémon dalla squadra tranne il Pokémon attivo.
     * Questo metodo svuota la lista dei Pokémon e poi aggiunge nuovamente il Pokémon attivo alla lista.
     * Viene stampato un messaggio che indica che la squadra è stata svuotata, ma il Pokémon attivo rimane.
     */
    public void clearTeamExceptActivePokemon() {
        T x = activePokemon;
        pokemonList.clear();
        pokemonList.add(x);
        System.out.println("La squadra è stata svuotata (Eccetto il Pokemon Attivo: " + activePokemon.getName() + ")");
    }

    /**
     * Scambia la posizione di due Pokémon nella lista della squadra.
     * Questo metodo scambia i Pokémon alle posizioni specificate dagli indici {@code index1} e {@code index2}.
     * Se gli indici sono validi, il metodo scambia i Pokémon e stampa un messaggio di conferma.
     * Se uno degli indici non è valido, viene stampato un messaggio di errore.
     *
     * @param index1 L'indice del primo Pokémon da scambiare.
     * @param index2 L'indice del secondo Pokémon da scambiare.
     */
    public void swapPokemon(int index1, int index2) {
        if (index1 >= 0 && index1 < pokemonList.size() && index2 >= 0 && index2 < pokemonList.size()) {
            Collections.swap(pokemonList, index1, index2);
            System.out.println("Scambiati " + pokemonList.get(index1).getName() + " e " + pokemonList.get(index2).getName());
        } else {
            System.out.println("Indice non valido.");
        }
    }

    /**
     * Verifica se tutti i Pokémon nella squadra sono svenuti.
     * Questo metodo utilizza uno stream per controllare se tutti i Pokémon nella lista sono svenuti (cioè, se {@code isFainted} restituisce {@code true} per ciascuno di essi).
     *
     * @return {@code true} se tutti i Pokémon sono svenuti, {@code false} altrimenti.
     */
    public boolean allPokemonFainted(){
        return pokemonList.stream()                            // crea uno stream dalla lista di Pokemon
                .allMatch(Pokemon::isFainted);                   // allMatch() verifica se tutti gli elementi dello stream soddisfano una certa condizione
    }


    /**
     * Restituisce una rappresentazione della squadra come stringa.
     * Questo metodo costruisce una stringa che rappresenta tutti i Pokémon nella squadra, ciascuno su una nuova riga.
     * La stringa finale rimuove l'ultima nuova riga in eccesso.
     *
     * @return Una stringa che rappresenta tutti i Pokémon nella squadra.
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (T pokemon : pokemonList) {
            sb.append(pokemon.toString()).append("\n");  // Aggiunge ogni Pokémon seguito da una nuova riga
        }
        return sb.toString().trim();  // Rimuove l'ultima nuova riga
    }
}
