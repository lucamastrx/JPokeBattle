package it.jpokebattle.model.pokemon;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import it.jpokebattle.model.Identifiable;
import it.jpokebattle.model.type.Type;
import it.jpokebattle.util.FileManager;

import java.util.HashMap;


public class Move implements Identifiable { // TODO aggiungere priorità
    @JsonIgnore
    public static HashMap<String, Move> movesMap = new HashMap<>(FileManager.readJSON("json.moves", Move.class));

    private final String name;          // è anche l'ID
    private Type type;
    private String description;
    private int power;
    private int precision;
    private int utilizzi;          // numero di utilizzi massimo di una mossa
    private boolean isSpecial;  // la mossa è speciale o fisica
    private String opponentModificatorType;     // la mossa modifica un qualche valore all'avversario
    private int opponentModificatorValue;       // di quanto la modifica
    private String personalModificatorType;     // la mossa modifica un qualche valore personale
    private int personalModificatorValue;       // di quanto la modifica

    /**
     * Metodo Costruttore.
     *
     * @param name
     * @param type          il tipo della mossa (es: FIRE)
     * @param description   descrizione della mossa
     * @param power
     * @param precision     precisione della mossa (compreso tra 0.0 e 1.0)
     * @param utilizzi
     * @param isSpecial     la mossa è Speciale o Fisica
     */
    public Move (@JsonProperty("name") String name,
                 @JsonProperty("type") Type type,
                 @JsonProperty("description") String description,
                 @JsonProperty("power") int power,
                 @JsonProperty("precision") int precision,
                 @JsonProperty("utilizzi") int utilizzi,
                 @JsonProperty("isSpecial") boolean isSpecial,
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
        this.opponentModificatorType = opponentModificatorType;
        this.opponentModificatorValue = opponentModificatorValue;
        this.personalModificatorType = personalModificatorType;
        this.personalModificatorValue = personalModificatorValue;
    }


    public String getName() {return name;}

    public Type getType() {return type;}

    public String getDescription() {return description;}

    @JsonIgnore
    public int getDamage() {return power;}

    public int getPrecision() {return precision;}

    /**
     *
     * @return valore booleano true se la mossa è speciale, altrimenti è fisica
     */
    @JsonIgnore
    public boolean isSpecial() {return isSpecial;}

    public int getPower() {return power;}

    public int getUtilizzi() {return utilizzi;}

    public String getOpponentModificatorType() {return opponentModificatorType;}

    public int getOpponentModificatorValue() {return opponentModificatorValue;}

    public String getPersonalModificatorType() {return personalModificatorType;}

    public int getPersonalModificatorValue() {return personalModificatorValue;}

    public void setType(Type type) {this.type = type;}

    public void setDescription(String description) {this.description = description;}

    public void setPower(int power) {this.power = power;}

    public void setPrecision(int precision) {this.precision = precision;}

    public void setSpecial(boolean special) {isSpecial = special;}

    @Override @JsonIgnore
    public String getID() {
        return name;
    }

    @Override
    public String toString(){
        return "Move: " + name;
    }

    /*
    public static void dumpJSON() {
        Move growl = new Move("Growl", Type.NORMAL, "lalala", 40, 100, 10, true, null, 0, null, 0);

        // Crea una HashMap di razze Pokémon con nome come chiave
        HashMap<String, Move> movesMap = new HashMap<>();           // {ID : Mossa}
        movesMap.put("Growl", growl);


        // Serializza la mappa su un file JSON
        FileManager.writeJSON(movesMap, "json.moves") oppure "json.prova";
    }*/


    // TODO equals toHash
}
