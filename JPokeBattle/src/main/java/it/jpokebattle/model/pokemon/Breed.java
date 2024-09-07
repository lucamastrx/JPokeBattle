package it.jpokebattle.model.pokemon;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;


import it.jpokebattle.model.Identifiable;
import it.jpokebattle.model.type.Type;
import it.jpokebattle.util.FileManager;

import java.util.HashMap;
import java.util.Set;
import java.util.HashSet;


public class Breed implements Identifiable {
    @JsonIgnore
    public static HashMap<String, Breed> breedsMap = new HashMap<>(FileManager.readJSON("json.breeds", Breed.class));

    private final String name;            // è anche l'ID
    private HashSet<Type> types;
    private int evoThreshold;      // è il livello in cui il pokemon può evolve
    private Breed successor;
    private String evTypeDropped; // Tipo di EV che il Pokémon rilascia (es. "hp", "atk")
    private int evAmountDropped;  // Quantità di EV rilasciati
    private int hpBaseStat;
    private int atkBaseStat;
    private int defBaseStat;
    private int specialAtkBaseStat;
    private int specialDefBaseStat;
    private int speedBaseStat;
    private int totalBaseStat;
    private HashMap<Integer, String> moves = new HashMap<>();         // livello a cui impara la mossa : ID mossa


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
                 @JsonProperty("hpBaseStat") int hpBaseStat,
                 @JsonProperty("atkBaseStat") int atkBaseStat,
                 @JsonProperty("defBaseStat") int defBaseStat,
                 @JsonProperty("specialAtkBaseStat") int specialAtkBaseStat,
                 @JsonProperty("specialDefBaseStat") int specialDefBaseStat,
                 @JsonProperty("speedBaseStat") int speedBaseStat,
                 @JsonProperty("moves") HashMap<Integer, String> moves) {
        this.name = name;
        setTypes(types);
        this.evoThreshold = evoThreshold;                       //se 101 allora il pokemon è all'ultimo stadio evolutivo
        this.successor = successor;
        this.evTypeDropped = evTypeDropped;
        this.evAmountDropped = evAmountDropped;
        this.hpBaseStat = hpBaseStat;
        this.atkBaseStat = atkBaseStat;
        this.defBaseStat = defBaseStat;
        this.specialAtkBaseStat = specialAtkBaseStat;
        this.specialDefBaseStat = specialDefBaseStat;
        this.speedBaseStat = speedBaseStat;
        this.totalBaseStat = hpBaseStat + atkBaseStat + defBaseStat + specialAtkBaseStat + specialDefBaseStat + speedBaseStat;
        this.moves = moves;
    }


    public String getName() {return name;}

    public HashSet<Type> getTypes() {return types;}

    public int getEvoThreshold() {return evoThreshold;}

    public Breed getSuccessor() {return successor;}

    public String getEvTypeDropped() {return evTypeDropped;}

    public int getEvAmountDropped() {return evAmountDropped;}

    public int getHpBaseStat() {return hpBaseStat;}

    public int getAtkBaseStat() {return atkBaseStat;}

    public int getDefBaseStat() {return defBaseStat;}

    public int getSpecialAtkBaseStat() {return specialAtkBaseStat;}

    public int getSpecialDefBaseStat() {return specialDefBaseStat;}

    public int getSpeedBaseStat() {return speedBaseStat;}

    public int getTotalBaseStat() {return totalBaseStat;}

    public HashMap<Integer, String> getMoves() {return moves;}

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


    @Override
    public String getID() {
        return name;
    }

    @Override
    public String toString(){
        return "Breed: " + name;
    }

}
