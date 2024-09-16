package it.jpokebattle.model.pokemon;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;


import it.jpokebattle.model.Identifiable;
import it.jpokebattle.model.type.Type;
import it.jpokebattle.util.FileManager;

import java.util.HashMap;
import java.util.Objects;
import java.util.Set;
import java.util.HashSet;

/**
 * Classe che rappresenta una razza (Breed) di Pokémon. Contiene informazioni sulle statistiche base del Pokémon,
 * le sue evoluzioni, i tipi, le mosse e gli Effort Values (EV) rilasciati quando viene sconfitto.
 */
public class Breed implements Identifiable {
    /**
     * Mappa statica che contiene tutte le razze di Pokémon (Breed) lette da un file JSON.
     * La chiave è il nome della razza e il valore è l'oggetto {@link Breed}.
     */
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
     * Costruttore della classe Breed.
     *
     * @param name Nome della razza del Pokémon.
     * @param types Set dei tipi della razza (es: FIRE/FLYING).
     * @param evoThreshold Livello di prossima evoluzione; se è 101, il Pokémon è all'ultimo stadio evolutivo.
     * @param successor Razza successiva nell'evoluzione, può essere null.
     * @param evTypeDropped Tipo di EV che il Pokémon rilascia (es. "hp", "atk").
     * @param evAmountDropped Quantità di EV rilasciati.
     * @param hpBaseStat Statistica base dei punti salute (HP) del Pokémon.
     * @param atkBaseStat Statistica base dell'attacco fisico del Pokémon.
     * @param defBaseStat Statistica base della difesa fisica del Pokémon.
     * @param specialAtkBaseStat Statistica base dell'attacco speciale del Pokémon.
     * @param specialDefBaseStat Statistica base della difesa speciale del Pokémon.
     * @param speedBaseStat Statistica base della velocità del Pokémon.
     * @param moves Mappa delle mosse che il Pokémon può imparare, con il livello come chiave e l'ID della mossa come valore.
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

    /**
     * Restituisce il nome della razza del Pokémon.
     *
     * @return Nome della razza.
     */
    public String getName() {return name;}

    /**
     * Restituisce il set dei tipi del Pokémon.
     *
     * @return Set dei tipi della razza.
     */
    public HashSet<Type> getTypes() {return types;}

    /**
     * Restituisce la soglia di evoluzione della razza.
     *
     * @return Livello di evoluzione.
     */
    public int getEvoThreshold() {return evoThreshold;}

    /**
     * Restituisce la razza successiva nell'evoluzione.
     *
     * @return Razza successiva, può essere null.
     */
    public Breed getSuccessor() {return successor;}

    /**
     * Restituisce il tipo di Effort Value (EV) rilasciato dal Pokémon.
     *
     * @return Tipo di EV rilasciato.
     */
    public String getEvTypeDropped() {return evTypeDropped;}

    /**
     * Restituisce la quantità di Effort Value (EV) rilasciati dal Pokémon.
     *
     * @return Quantità di EV rilasciati.
     */
    public int getEvAmountDropped() {return evAmountDropped;}

    /**
     * Restituisce la statistica base dei punti salute (HP) del Pokémon.
     *
     * @return Statistica base degli HP.
     */
    public int getHpBaseStat() {return hpBaseStat;}

    /**
     * Restituisce la statistica base dell'attacco fisico del Pokémon.
     *
     * @return Statistica base dell'attacco.
     */
    public int getAtkBaseStat() {return atkBaseStat;}

    /**
     * Restituisce la statistica base della difesa fisica del Pokémon.
     *
     * @return Statistica base della difesa.
     */
    public int getDefBaseStat() {return defBaseStat;}

    /**
     * Restituisce la statistica base dell'attacco speciale del Pokémon.
     *
     * @return Statistica base dell'attacco speciale.
     */
    public int getSpecialAtkBaseStat() {return specialAtkBaseStat;}

    /**
     * Restituisce la statistica base della difesa speciale del Pokémon.
     *
     * @return Statistica base della difesa speciale.
     */
    public int getSpecialDefBaseStat() {return specialDefBaseStat;}

    /**
     * Restituisce la statistica base della velocità del Pokémon.
     *
     * @return Statistica base della velocità.
     */
    public int getSpeedBaseStat() {return speedBaseStat;}

    /**
     * Restituisce la somma totale delle statistiche base del Pokémon.
     *
     * @return Somma delle statistiche base.
     */
    public int getTotalBaseStat() {return totalBaseStat;}

    /**
     * Restituisce la mappa delle mosse che il Pokémon può imparare.
     *
     * @return Mappa dei livelli e delle rispettive mosse.
     */
    public HashMap<Integer, String> getMoves() {return moves;}

    /**
     * Metodo che imposta i tipi della razza.
     *
     * @param types Set dei tipi della razza (es: FIRE/FLYING).
     * @throws IllegalArgumentException se la razza ha più di due tipi.
     */
    private void setTypes(Set<Type> types) {
        if (types.size() > 2)
            throw new IllegalArgumentException("Una razza pokémon (Breed) può avere al massimo due tipi.");
        this.types = new HashSet<>(types);   // crea un HashSet a partire dal Set fornito
    }

    /**
     * Restituisce l'ID univoco della razza, che è rappresentato dal nome.
     *
     * @return L'ID della razza.
     */
    @Override
    public String getID() {
        return name;
    }

    /**
     * Restituisce una rappresentazione testuale dell'oggetto Breed.
     *
     * @return Una stringa che rappresenta la razza.
     */
    @Override
    public String toString(){
        return "Breed:{" + name + "} ";
    }

    /**
     * Verifica l'uguaglianza tra due oggetti Breed basata sul nome.
     *
     * @param obj L'oggetto da confrontare.
     * @return true se gli oggetti sono uguali, false altrimenti.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        Breed breed = (Breed) obj;

        return Objects.equals(name, breed.name);
    }

    /**
     * Calcola il codice hash dell'oggetto Breed basato sul nome.
     *
     * @return Codice hash dell'oggetto.
     */
    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
