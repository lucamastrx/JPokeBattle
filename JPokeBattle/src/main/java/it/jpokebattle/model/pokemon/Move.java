package it.jpokebattle.model.pokemon;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import it.jpokebattle.model.Identifiable;
import it.jpokebattle.model.type.Type;
import it.jpokebattle.util.FileManager;

import java.util.HashMap;
import java.util.Objects;


/**
 * La classe Move rappresenta una mossa di un combattente o Pokémon. Ogni mossa ha un tipo, un nome (ID), un potere,
 * una precisione, e può essere fisica o speciale. Inoltre, può avere effetti modificatori su sé stessi o
 * sull'avversario.
 *
 * Implementa l'interfaccia Identifiable, il che significa che ogni istanza di Move può essere identificata univocamente.
 */
public class Move implements Identifiable {

    @JsonIgnore
    public static HashMap<String, Move> movesMap = new HashMap<>(FileManager.readJSON("json.moves", Move.class));

    private final String name;          // Nome della mossa, utilizzato anche come ID.
    private Type type;                  // Tipo della mossa (es: FIRE, WATER, ecc.)
    private String description;         // Descrizione della mossa.
    private int power;                  // Potenza della mossa (0 se non infligge danno diretto).
    private int precision;              // Precisione della mossa (valore tra 0 e 100).
    private int utilizzi;               // Numero massimo di utilizzi della mossa.
    private boolean isSpecial;          // Indica se la mossa è speciale o fisica.
    private int priority;               // Priorità della mossa (più alta, maggiore priorità nell'esecuzione).
    private String opponentModificatorType;   // Modifica applicata all'avversario (se presente).
    private int opponentModificatorValue;     // Valore della modifica applicata all'avversario.
    private String personalModificatorType;   // Modifica applicata al giocatore (se presente).
    private int personalModificatorValue;     // Valore della modifica applicata al giocatore.

    /**
     * Costruttore della classe Move.
     *
     * @param name                  Nome della mossa (che funge anche da ID univoco).
     * @param type                  Tipo della mossa.
     * @param description           Descrizione della mossa.
     * @param power                 Potenza della mossa (0 se non infligge danno).
     * @param precision             Precisione della mossa (tra 0 e 100).
     * @param utilizzi              Numero massimo di utilizzi della mossa.
     * @param isSpecial             Indica se la mossa è speciale (true) o fisica (false).
     * @param priority              Priorità della mossa.
     * @param opponentModificatorType   Tipo di modifica che la mossa applica all'avversario (es. "ATTACK").
     * @param opponentModificatorValue  Valore della modifica applicata all'avversario.
     * @param personalModificatorType   Tipo di modifica che la mossa applica a sé stessi (es. "DEFENSE").
     * @param personalModificatorValue  Valore della modifica applicata a sé stessi.
     */
    public Move (@JsonProperty("name") String name,
                 @JsonProperty("type") Type type,
                 @JsonProperty("description") String description,
                 @JsonProperty("power") int power,
                 @JsonProperty("precision") int precision,
                 @JsonProperty("utilizzi") int utilizzi,
                 @JsonProperty("isSpecial") boolean isSpecial,
                 @JsonProperty("priority") int priority,
                 @JsonProperty("opponentModificatorType") String opponentModificatorType,
                 @JsonProperty("opponentModificatorValue") int opponentModificatorValue,
                 @JsonProperty("personalModificatorType") String personalModificatorType,
                 @JsonProperty("personalModificatorValue") int personalModificatorValue) {
        this.name = name;
        this.type = type;
        this.description = description;
        this.power = power;                     // se 0 è una mossa che non fa danno agli hp avversari
        this.precision = precision;
        this.utilizzi = utilizzi;
        this.isSpecial = isSpecial;
        this.priority = priority;
        this.opponentModificatorType = opponentModificatorType;
        this.opponentModificatorValue = opponentModificatorValue;
        this.personalModificatorType = personalModificatorType;
        this.personalModificatorValue = personalModificatorValue;
    }


    /**
     * Restituisce il nome della mossa, che funge anche da ID.
     *
     * @return Il nome della mossa.
     */
    public String getName() {return name;}

    /**
     * Restituisce il tipo della mossa (es. FIRE, WATER, ecc.).
     *
     * @return Il tipo della mossa.
     */
    public Type getType() {return type;}

    /**
     * Restituisce la descrizione della mossa.
     *
     * @return La descrizione della mossa.
     */
    public String getDescription() {return description;}

    /**
     * Restituisce la precisione della mossa.
     *
     * @return La precisione della mossa (valore tra 0 e 100).
     */
    public int getPrecision() {return precision;}

    /**
     * Indica se la mossa è speciale o fisica.
     *
     * @return {@code true} se la mossa è speciale, {@code false} altrimenti.
     */
    @JsonIgnore
    public boolean isSpecial() {return isSpecial;}

    /**
     * Restituisce la priorità della mossa.
     *
     * @return La priorità della mossa.
     */
    public int getPriority() {return priority;}

    /**
     * Restituisce la potenza della mossa.
     *
     * @return La potenza della mossa.
     */
    public int getPower() {return power;}

    /**
     * Restituisce il numero massimo di utilizzi della mossa.
     *
     * @return Il numero di utilizzi della mossa.
     */
    public int getUtilizzi() {return utilizzi;}

    /**
     * Restituisce il tipo di modifica applicata all'avversario.
     *
     * @return Il tipo di modifica applicata all'avversario.
     */
    public String getOpponentModificatorType() {return opponentModificatorType;}

    /**
     * Restituisce il valore della modifica applicata all'avversario.
     *
     * @return Il valore della modifica applicata all'avversario.
     */
    public int getOpponentModificatorValue() {return opponentModificatorValue;}

    /**
     * Restituisce il tipo di modifica personale applicata.
     *
     * @return Il tipo di modifica personale.
     */
    public String getPersonalModificatorType() {return personalModificatorType;}

    /**
     * Restituisce il valore della modifica personale applicata.
     *
     * @return Il valore della modifica personale.
     */
    public int getPersonalModificatorValue() {return personalModificatorValue;}

    /**
     * Imposta il tipo della mossa.
     *
     * @param type Il tipo della mossa.
     */
    public void setType(Type type) {this.type = type;}

    /**
     * Imposta la descrizione della mossa.
     *
     * @param description La nuova descrizione della mossa.
     */
    public void setDescription(String description) {this.description = description;}

    /**
     * Imposta la potenza della mossa.
     *
     * @param power La nuova potenza della mossa.
     */
    public void setPower(int power) {this.power = power;}

    /**
     * Imposta la precisione della mossa.
     *
     * @param precision La nuova precisione della mossa.
     */
    public void setPrecision(int precision) {this.precision = precision;}

    /**
     * Imposta se la mossa è speciale o fisica.
     *
     * @param special {@code true} se la mossa è speciale, {@code false} se è fisica.
     */
    public void setSpecial(boolean special) {isSpecial = special;}

    /**
     * Restituisce l'ID della mossa, che è uguale al nome.
     *
     * @return L'ID della mossa.
     */
    @Override @JsonIgnore
    public String getID() {
        return name;
    }

    /**
     * Ritorna una stringa che rappresenta l'oggetto Move.
     *
     * @return La stringa che rappresenta la mossa.
     */
    @Override
    public String toString(){
        return "Move:{" + name + "} ";
    }

    /*
    public static void dumpJSON() {
        Move growl = new Move("Growl", Type.NORMAL, "lalala", 40, 100, 10, true, null, 0, null, 0);

        // Crea una HashMap di razze Pokémon con nome come chiave
        HashMap<String, Move> movesMap = new HashMap<>();           // {ID : Mossa}
        movesMap.put(getID(), growl);


        // Serializza la mappa su un file JSON
        FileManager.writeJSON(movesMap, "json.moves") oppure "json.prova";
    }*/

    /**
     * Verifica se l'oggetto corrente è uguale a un altro oggetto.
     *
     * @param obj L'oggetto con cui confrontare.
     * @return {@code true} se sono uguali, {@code false} altrimenti.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        Move move = (Move) obj;

        return Objects.equals(name, move.name);
    }

    /**
     * Calcola l'hash code dell'oggetto basato sul nome.
     *
     * @return L'hash code dell'oggetto.
     */
    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

}
