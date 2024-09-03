package it.jpokebattle.model.pokemon;
import com.fasterxml.jackson.annotation.JsonProperty;


import it.jpokebattle.model.type.Type;
import java.util.HashMap;
import java.util.Set;
import java.util.HashSet;


public class Breed { // TODO le razze salvate JSON, aggiungere lista di mosse

    private String name;
    private HashSet<Type> types;
    private int evoThreshold;      // è il livello in cui il pokemon può evolve
    private Breed successor;
    private String evTypeDropped; // Tipo di EV che il Pokémon rilascia (es. "hp", "atk")
    private int evAmountDropped;  // Quantità di EV rilasciati
    private HashMap<Integer, Move> moves = new HashMap<>();         // livello a cui impara la mossa : che mossa è


    /**
     * Metodo costruttore.
     *
     * @param name          Nome della razza
     * @param types         Set dei tipi della razza (es: FIRE/FLYING)
     * @param evoThreshold  livello di prossima evoluzione
     * @param successor     Razza successiva, può essere null
     */
    public Breed(@JsonProperty("name") String name,
                 @JsonProperty("types") Set<Type> types,
                 @JsonProperty("evoThreshold") int evoThreshold,
                 @JsonProperty("successor") Breed successor,
                 @JsonProperty("evTypeDropped") String evTypeDropped,
                 @JsonProperty("evAmountDropped") int evAmountDropped,
                 @JsonProperty("moves") HashMap<Integer, Move> moves) {
        this.name = name;
        setTypes(types);
        this.evoThreshold = evoThreshold;
        this.successor = successor;
        this.evTypeDropped = evTypeDropped;
        this.evAmountDropped = evAmountDropped;

        this.moves = moves;
    }


    public String getName() {return name;}

    public HashSet<Type> getTypes() {return types;}

    public int getEvoThreshold() {return evoThreshold;}

    public Breed getSuccessor() {return successor;}

    public void setName(String name) {this.name = name;}

    public String getEvTypeDropped() {return evTypeDropped;}

    public int getEvAmountDropped() {return evAmountDropped;}

    public HashMap<Integer, Move> getMoves() {return moves;}

    /**
     * Metodo che setta i tipi della razza.
     *
     * @param types Set dei tipi della razza (es: FIRE/FLYING)
     * @throws IllegalArgumentException se la razza ha più di due tipi
     */
    private void setTypes(Set<Type> types) {
        if (types.size() > 2)
            throw new IllegalArgumentException("Una razza pokémon (Breed) può avere al massimo due tipi.");
        this.types = new HashSet<>(types);   // crea un HashSet a partire dal Set fornito
    }

    public void setThreshold(int threshold) {this.evoThreshold = threshold;}

    public void setSuccessor(Breed successor) {this.successor = successor;}

}
