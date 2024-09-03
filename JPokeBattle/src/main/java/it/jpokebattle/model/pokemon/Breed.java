package it.jpokebattle.model.pokemon;
import com.fasterxml.jackson.annotation.JsonProperty;


import it.jpokebattle.model.type.Type;

import java.util.HashMap;
import java.util.Set;
import java.util.HashSet;


import it.jpokebattle.util.FileManager;

public class Breed { //

    private String name;
    private HashSet<Type> types;
    private int evoThreshold;      // è il livello in cui il pokemon può evolve
    private Breed successor;
    private String evTypeDropped; // Tipo di EV che il Pokémon rilascia (es. "hp", "atk")
    private int evAmountDropped;  // Quantità di EV rilasciati
    private int baseStat;
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
                 @JsonProperty("baseStat") int baseStat,
                 @JsonProperty("moves") HashMap<Integer, Move> moves) {
        this.name = name;
        setTypes(types);
        this.evoThreshold = evoThreshold;                       //se 101 allora il pokemon è all'ultimo stadio evolutivo
        this.successor = successor;
        this.evTypeDropped = evTypeDropped;
        this.evAmountDropped = evAmountDropped;
        this.baseStat = baseStat;
        this.moves = moves;
    }


    public String getName() {return name;}

    public HashSet<Type> getTypes() {return types;}

    public int getEvoThreshold() {return evoThreshold;}

    public Breed getSuccessor() {return successor;}

    public void setName(String name) {this.name = name;}

    public String getEvTypeDropped() {return evTypeDropped;}

    public int getEvAmountDropped() {return evAmountDropped;}

    public int getBaseStat() {return this.baseStat;}

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







    public static void dumpJSON() {

        Move growl = new Move("Growl", Type.NORMAL, "Reduces the foe’s Attack.",0, 100,40, true, "atk", -10, null, 0 );
        Move growth = new Move("Growth", Type.NORMAL, "Raises the user's Attack.",0, 100,20, false, null, 0, "atk", 10 );
        Move tackle = new Move("Tackle", Type.NORMAL, "A full-body charge attack.",40, 100,35, false, null, 0, null, 0);
        Move vineWhip = new Move("Vine Whip", Type.GRASS, "Whips the foe with slender vines.",45, 100,25, true, null, 0, null, 0);
        Move poisonPowder = new Move("Poison Powder", Type.POISON, "A move that may poison the foe.",50, 75,30, true, null, 0, null, 0);
        Move solarBeam = new Move("Solar Beam", Type.GRASS, "Absorbs light in one turn, then attacks next turn",120, 100,10, true, null, 0, null, 0);
        Move razorLeaf = new Move("Razor Leaf", Type.GRASS, "Has a high critical hit ratio", 55, 95, 25, false, null, 0, "hit", 10);



        HashMap<Integer, Move> venusaurMoves = new HashMap<>();
        venusaurMoves.put(2, growl);
        venusaurMoves.put(1, tackle);
        venusaurMoves.put(3, vineWhip);
        venusaurMoves.put(22, poisonPowder);
        venusaurMoves.put(30, razorLeaf);
        venusaurMoves.put(40, growth);
        venusaurMoves.put(65, solarBeam);
        Breed venusaur = new Breed("Venusaur", Set.of(Type.GRASS, Type.POISON), 101, null, "specialAtk",2, 525, venusaurMoves);


        HashMap<Integer, Move> ivysaurMoves = new HashMap<>();
        ivysaurMoves.put(2, growl);
        ivysaurMoves.put(1, tackle);
        ivysaurMoves.put(13, vineWhip);
        ivysaurMoves.put(22, poisonPowder);
        ivysaurMoves.put(30, razorLeaf);
        venusaurMoves.put(38, growth);
        ivysaurMoves.put(50, solarBeam);
        Breed ivysaur = new Breed("Ivysaur", Set.of(Type.GRASS, Type.POISON), 32, venusaur, "specialAtk",1, 405, ivysaurMoves);


        HashMap<Integer, Move> bulbasaurMoves = new HashMap<>();
        bulbasaurMoves.put(-1, growl);
        bulbasaurMoves.put(-2, tackle);
        bulbasaurMoves.put(13, vineWhip);
        bulbasaurMoves.put(22, poisonPowder);
        bulbasaurMoves.put(30, razorLeaf);
        venusaurMoves.put(34, growth);
        bulbasaurMoves.put(50, solarBeam);
        Breed bulbasaur = new Breed("Bulbasaur", Set.of(Type.GRASS, Type.POISON), 16, ivysaur, "specialAtk",1, 318, bulbasaurMoves);


        // -------------------------------------------------------------------------------------------------------


        Move ember = new Move("Ember", Type.FIRE, "Burns the target",40, 100,25, true, null,0 , null, 0 );
        Move leer = new Move("Leer", Type.NORMAL, "Lowers the target's Defense",0, 100,30, true, "def", -10, null, 0);
        Move scratch = new Move("Scratch", Type.NORMAL, "Scratches with sharp claws.",40, 100,35, false, null, 0, null, 0);
        Move rage = new Move("Rage", Type.NORMAL, "Raises the user’s ATTACK every time it is hit.",20, 100,25, false, null, 0, "hit", 10);
        Move slash = new Move("Slash", Type.NORMAL, "Has a high critical hit ratio.",70, 75,20, false, null, 0, null, 0);
        Move flamethrower = new Move("Flame Thrower", Type.FIRE, "The foe is scorched with intense flames.",95, 100,15, true, null, 0, null, 0);


        HashMap<Integer, Move> charizardMoves = new HashMap<>();
        charizardMoves.put(-1, ember);
        charizardMoves.put(-2, leer);
        charizardMoves.put(-3, growl);
        charizardMoves.put(-4, scratch);
        charizardMoves.put(24, rage);
        charizardMoves.put(36, slash);
        charizardMoves.put(46, flamethrower);
        Breed charizard = new Breed("Charizard", Set.of(Type.FIRE, Type.FLYING), 101, null, "specialAtk", 3, 543, charizardMoves);



        // Crea la mappa delle mosse di Charmeleon
        HashMap<Integer, Move> charmeleonMoves = new HashMap<>();
        charmeleonMoves.put(-1, ember);
        charmeleonMoves.put(-2, scratch);
        charmeleonMoves.put(-3, growl);
        charmeleonMoves.put(15, leer);
        charmeleonMoves.put(24, rage);
        charmeleonMoves.put(36, slash);
        charmeleonMoves.put(43, flamethrower);
        // Crea l'oggetto Charmeleon
        Breed charmeleon = new Breed("Charmeleon", Set.of(Type.FIRE), 36, charizard,"atk",2,405, charmeleonMoves);




        // Crea la mappa delle mosse di Charmander
        HashMap<Integer, Move> charmanderMoves = new HashMap<>();
        charmanderMoves.put(-1, scratch);
        charmanderMoves.put(-2, growl);
        charmanderMoves.put(9, ember);
        charmanderMoves.put(15, leer);
        charmanderMoves.put(22, rage);
        charmanderMoves.put(30, slash);
        charmanderMoves.put(38, flamethrower);


        // Crea l'oggetto Charmander
        Breed charmander = new Breed("Charmander", Set.of(Type.FIRE), 16, charmeleon,"speed",1,309, charmanderMoves);

        // -------------------------------------------------------------------------------------------------------

        Move bubble = new Move("Bubble", Type.WATER, "An attack using bubbles.",40, 100,30, true, null, 0, null, 0 );
        Move tailWhip = new Move("Tail Whip", Type.NORMAL, "Wags the tail to lower the foe’s defense.",0, 100,30, true, "def", -10, null, 0 );
        Move waterGun = new Move("Water Gun", Type.WATER, "Squirts water to attack.",40, 100,25, true, null, 0, null, 0);
        Move bite = new Move("Bite", Type.NORMAL, "Bites with vicious fangs.",60, 100,25, false, null, 0, null, 0);
        Move withdraw = new Move("Withdraw", Type.WATER, "Withdraws the body into its hard shell to raise defense.",0, 100,40, true, null, 0, "def", 10);
        Move skullBash = new Move("Skull Bash", Type.NORMAL, "Tucks in the head, then attacks.", 130, 100, 10, false, null, 0, "def", 10);
        Move hydroPump = new Move("Hydro Pump", Type.WATER, "Blasts water at high power to strike the foe.", 110, 80, 5, true, null, 0, null, 0);



        HashMap<Integer, Move> blastoiseMoves = new HashMap<>();
        blastoiseMoves.put(-1, bubble);
        blastoiseMoves.put(-2, tackle);
        blastoiseMoves.put(-3, tailWhip);
        blastoiseMoves.put(-4, waterGun);
        blastoiseMoves.put(24, bite);
        blastoiseMoves.put(31, withdraw);
        blastoiseMoves.put(42, skullBash);
        blastoiseMoves.put(52, hydroPump);
        Breed blastoise = new Breed("Blastoise", Set.of(Type.WATER), 101, null, "specialDef", 3, 530, blastoiseMoves);



        HashMap<Integer, Move> wartortleMoves = new HashMap<>();
        wartortleMoves.put(-1, bubble);
        wartortleMoves.put(-2, tackle);
        wartortleMoves.put(-3, tailWhip);
        wartortleMoves.put(15, waterGun);
        wartortleMoves.put(24, bite);
        wartortleMoves.put(31, withdraw);
        wartortleMoves.put(39, skullBash);
        wartortleMoves.put(47, hydroPump);
        Breed wartortle = new Breed("Wartortle", Set.of(Type.WATER), 36, blastoise, "def", 1, 405, wartortleMoves);




        HashMap<Integer, Move> squirtleMoves = new HashMap<>();
        squirtleMoves.put(-2, tackle);
        squirtleMoves.put(-1, tailWhip);
        squirtleMoves.put(8, bubble);
        squirtleMoves.put(15, waterGun);
        squirtleMoves.put(22, bite);
        squirtleMoves.put(28, withdraw);
        squirtleMoves.put(35, skullBash);
        squirtleMoves.put(42, hydroPump);
        Breed squirtle = new Breed("Squirtle", Set.of(Type.WATER), 16, wartortle,"def", 1, 314, squirtleMoves);

        // --------------------------------------------------------------------------------------------------------


        // Crea una HashMap di razze Pokémon con nome come chiave
        HashMap<String, Breed> breedsMap = new HashMap<>();
        breedsMap.put(bulbasaur.getName(), bulbasaur);
        breedsMap.put(ivysaur.getName(), ivysaur);
        breedsMap.put(venusaur.getName(), venusaur);
        breedsMap.put(charmander.getName(), charmander);
        breedsMap.put(charmeleon.getName(), charmeleon);
        breedsMap.put(charizard.getName(), charizard);
        breedsMap.put(squirtle.getName(), squirtle);
        breedsMap.put(wartortle.getName(), wartortle);
        breedsMap.put(blastoise.getName(), blastoise);



        // Serializza la mappa su un file JSON
        FileManager.writeJSON(breedsMap, "json.breeds");
    }

}
