package it.jpokebattle.model.pokemon;

import com.fasterxml.jackson.annotation.JsonProperty;

import it.jpokebattle.model.Identifiable;
import it.jpokebattle.model.type.Type;
import it.jpokebattle.util.FileManager;

import java.util.HashMap;

public class Move implements Identifiable {
    private final String name;          // è anche l'ID
    private Type type;
    private String description;
    private int power;
    private int precision;
    private int ppDefault;          // numero di utilizzi massimo di una mossa
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
     * @param ppDefault
     * @param isSpecial     la mossa è Speciale o Fisica
     */
    public Move (@JsonProperty("name") String name,
                 @JsonProperty("type") Type type,
                 @JsonProperty("description") String description,
                 @JsonProperty("power") int power,
                 @JsonProperty("precision") int precision,
                 @JsonProperty("ppDefault") int ppDefault,
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
        this.ppDefault = ppDefault;
        this.isSpecial = isSpecial;
        this.opponentModificatorType = opponentModificatorType;
        this.opponentModificatorValue = opponentModificatorValue;
        this.personalModificatorType = personalModificatorType;
        this.personalModificatorValue = personalModificatorValue;
    }


    public String getName() {return name;}

    public Type getType() {return type;}

    public String getDescription() {return description;}

    public int getDamage() {return power;}

    public int getPrecision() {return precision;}

    /**
     *
     * @return numero di utilizzi massimi di una mossa
     */
    public int getPPDefault() {return ppDefault;}


    /**
     *
     * @return valore booleano true se la mossa è speciale, altrimenti è fisica
     */
    public boolean isSpecial() {return isSpecial;}

    public int getPower() {return power;}

    public int getPpDefault() {return ppDefault;}

    public String getOpponentModificatorType() {return opponentModificatorType;}

    public int getOpponentModificatorValue() {return opponentModificatorValue;}

    public String getPersonalModificatorType() {return personalModificatorType;}

    public int getPersonalModificatorValue() {return personalModificatorValue;}

    public void setType(Type type) {this.type = type;}

    public void setDescription(String description) {this.description = description;}

    public void setPower(int power) {this.power = power;}

    public void setPrecision(int precision) {this.precision = precision;}

    public void setPpMAX(int ppDefault) {this.ppDefault = ppDefault;}

    public void setIsSpecial(boolean special) {isSpecial = special;}

    @Override
    public String getID() {
        return name;
    }



    public static void dumpJSON(){
        Move growl = new Move("Growl", Type.NORMAL, "Reduces the foe’s Attack.",0, 100,40, true, "atk", -10, null, 0 );
        Move growth = new Move("Growth", Type.NORMAL, "Raises the user's Attack.",0, 100,20, false, null, 0, "atk", 10 );
        Move tackle = new Move("Tackle", Type.NORMAL, "A full-body charge attack.",40, 100,35, false, null, 0, null, 0);
        Move vineWhip = new Move("Vine Whip", Type.GRASS, "Whips the foe with slender vines.",45, 100,25, true, null, 0, null, 0);
        Move poisonPowder = new Move("Poison Powder", Type.POISON, "A move that may poison the foe.",50, 75,30, true, null, 0, null, 0);
        Move solarBeam = new Move("Solar Beam", Type.GRASS, "Absorbs light in one turn, then attacks next turn",120, 100,10, true, null, 0, null, 0);
        Move razorLeaf = new Move("Razor Leaf", Type.GRASS, "Has a high critical hit ratio", 55, 95, 25, false, null, 0, "hit", 10);


        Move ember = new Move("Ember", Type.FIRE, "Burns the target",40, 100,25, true, null,0 , null, 0 );
        Move leer = new Move("Leer", Type.NORMAL, "Lowers the target's Defense",0, 100,30, true, "def", -10, null, 0);
        Move scratch = new Move("Scratch", Type.NORMAL, "Scratches with sharp claws.",40, 100,35, false, null, 0, null, 0);
        Move rage = new Move("Rage", Type.NORMAL, "Raises the user’s ATTACK every time it is hit.",20, 100,25, false, null, 0, "hit", 10);
        Move slash = new Move("Slash", Type.NORMAL, "Has a high critical hit ratio.",70, 75,20, false, null, 0, null, 0);
        Move flamethrower = new Move("Flame Thrower", Type.FIRE, "The foe is scorched with intense flames.",95, 100,15, true, null, 0, null, 0);


        Move bubble = new Move("Bubble", Type.WATER, "An attack using bubbles.",40, 100,30, true, null, 0, null, 0 );
        Move tailWhip = new Move("Tail Whip", Type.NORMAL, "Wags the tail to lower the foe’s defense.",0, 100,30, true, "def", -10, null, 0 );
        Move waterGun = new Move("Water Gun", Type.WATER, "Squirts water to attack.",40, 100,25, true, null, 0, null, 0);
        Move bite = new Move("Bite", Type.NORMAL, "Bites with vicious fangs.",60, 100,25, false, null, 0, null, 0);
        Move withdraw = new Move("Withdraw", Type.WATER, "Withdraws the body into its hard shell to raise defense.",0, 100,40, true, null, 0, "def", 10);
        Move skullBash = new Move("Skull Bash", Type.NORMAL, "Tucks in the head, then attacks.", 130, 100, 10, false, null, 0, "def", 10);
        Move hydroPump = new Move("Hydro Pump", Type.WATER, "Blasts water at high power to strike the foe.", 110, 80, 5, true, null, 0, null, 0);


        // Crea una HashMap di razze Pokémon con nome come chiave
        HashMap<String, Move> movesMap = new HashMap<>();
        movesMap.put("Growl", growl);
        movesMap.put("Growth", growth);
        movesMap.put("Tackle", tackle);
        movesMap.put("Vine Whip", vineWhip);
        movesMap.put("Poison Powder", poisonPowder);
        movesMap.put("Solar Beam", solarBeam);
        movesMap.put("Razor Leaf", razorLeaf);
        movesMap.put("Ember", ember);
        movesMap.put("Leer", leer);
        movesMap.put("Scratch", scratch);
        movesMap.put("Rage", rage);
        movesMap.put("Slash", slash);
        movesMap.put("Flame Thrower", flamethrower);
        movesMap.put("Bubble", bubble);
        movesMap.put("Tail Whip", tailWhip);
        movesMap.put("Water Gun", waterGun);
        movesMap.put("Bite", bite);
        movesMap.put("Withdraw", withdraw);
        movesMap.put("Skull Bash", skullBash);
        movesMap.put("Hydro Pump", hydroPump);

        // Serializza la mappa su un file JSON
        FileManager.writeJSON(movesMap, "json.moves");
    }
}
