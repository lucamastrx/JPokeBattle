package it.jpokebattle.model.pokemon;
import com.fasterxml.jackson.annotation.JsonProperty;


import it.jpokebattle.model.type.Type;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Set;
import java.util.HashSet;

import com.fasterxml.jackson.databind.ObjectMapper;

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



    private static void myMetod() {
        // Creiamo mosse per Charmeleon
        Move fireFang = new Move(
                "Fire Fang",               // Nome della mossa
                Type.FIRE,                 // Tipo della mossa
                "A physical attack with sharp fangs",  // Descrizione
                65,                        // Potenza
                95,                      // Precisione
                15,                        // PP (Punti Potenza) di default
                false                      // È una mossa speciale?
        );

        Move slash = new Move(
                "Slash",                   // Nome della mossa
                Type.STEEL,                // Tipo della mossa (per esempio)
                "A quick slash attack",    // Descrizione
                70,                        // Potenza
                100,                     // Precisione
                20,                        // PP (Punti Potenza) di default
                false                      // È una mossa speciale?
        );

        Move flamethrower = new Move(
                "Flame Thrower",            // Nome della mossa
                Type.FIRE,                 // Tipo della mossa
                "A powerful fire attack",  // Descrizione
                90,                        // Potenza
                100,                     // Precisione
                15,                        // PP (Punti Potenza) di default
                true                       // È una mossa speciale?
        );



        // Crea la mappa delle mosse di Charmeleon
        HashMap<Integer, Move> charmeleonMoves = new HashMap<>();
        charmeleonMoves.put(16, fireFang);    // Charmeleon impara Fire Fang al livello 16
        charmeleonMoves.put(24, slash);       // Charmeleon impara Slash al livello 24
        charmeleonMoves.put(34, flamethrower);// Charmeleon impara Flamethrower al livello 34


        // Crea l'oggetto Charmeleon
        Breed charmeleon = new Breed(
                "Charmeleon",                   // Nome
                Set.of(Type.FIRE),              // Set dei tipi (solo FIRE)
                36,                             // Livello evoluzione
                null,                           // Evoluzione successiva (per esempio)
                "atk",                          // Statistica principale
                2,                              // Numero per esempio
                charmeleonMoves                 // Mappa delle mosse
        );



        // -------------------------------------------------------------------------------------------------------


        // Creiamo mosse per Charmander
        Move fireBlast = new Move(
                "Fire Blast",         // Nome della mossa
                Type.FIRE,                 // Tipo della mossa
                "A powerful fire attack",  // Descrizione
                110,                       // Potenza
                100,                       // Precisione
                5,                         // PP (Punti Potenza) di default
                true                       // È una mossa speciale?
        );

        // Move flamethrower già definita sopra

        // Crea la mappa delle mosse di Charmander
        HashMap<Integer, Move> charmanderMoves = new HashMap<>();
        charmanderMoves.put(15, fireBlast);
        charmanderMoves.put(20, flamethrower);


        // Crea l'oggetto Charmander
        Breed charmander = new Breed(
                "Charmander",
                Set.of(Type.FIRE),
                16,
                charmeleon,
                "atk",
                1,
                charmanderMoves
        );



        // -------------------------------------------------------------------------------------------------------



        // Crea mosse per Bulbasaur
        Move vineWhip = new Move(
                "Vine Whip",               // Nome della mossa
                Type.GRASS,                // Tipo della mossa
                "A sharp whip-like attack with vines", // Descrizione
                45,                        // Potenza
                100,                     // Precisione
                25,                        // PP (Punti Potenza) di default
                false                      // È una mossa speciale?
        );

        Move razorLeaf = new Move(
                "Razor Leaf",              // Nome della mossa
                Type.GRASS,                // Tipo della mossa
                "A sharp-edged leaf is launched", // Descrizione
                55,                        // Potenza
                95,                      // Precisione
                25,                        // PP (Punti Potenza) di default
                false                      // È una mossa speciale?
        );

        Move solarBeam = new Move(
                "Solar Beam",              // Nome della mossa
                Type.GRASS,                // Tipo della mossa
                "A two-turn powerful beam attack", // Descrizione
                120,                       // Potenza
                100,                     // Precisione
                10,                        // PP (Punti Potenza) di default
                true                       // È una mossa speciale?
        );



        // Crea la mappa delle mosse di Bulbasaur
        HashMap<Integer, Move> bulbasaurMoves = new HashMap<>();
        bulbasaurMoves.put(7, vineWhip);       // Bulbasaur impara Vine Whip al livello 7
        bulbasaurMoves.put(13, razorLeaf);     // Bulbasaur impara Razor Leaf al livello 13
        bulbasaurMoves.put(31, solarBeam);     // Bulbasaur impara Solar Beam al livello 31

        // Crea l'oggetto Bulbasaur
        Breed bulbasaur = new Breed(
                "Bulbasaur",                    // Nome
                Set.of(Type.GRASS),             // Set dei tipi (solo GRASS)
                16,                             // Livello evoluzione
                null,                           // Evoluzione successiva (per esempio)
                "atk",                          // Statistica principale
                1,                              // Numero per esempio
                bulbasaurMoves                  // Mappa delle mosse
        );



        // --------------------------------------------------------------------------------------------------------


        // Crea una HashMap di razze Pokémon con nome come chiave
        HashMap<String, Breed> breedsMap = new HashMap<>();
        breedsMap.put(charmander.getName(), charmander);
        breedsMap.put(charmeleon.getName(), charmeleon);
        breedsMap.put(bulbasaur.getName(), bulbasaur);




        // Serializza la mappa su un file JSON
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            objectMapper.writeValue(new File("breeds.json"), breedsMap);
            System.out.println("Razze salvate con successo su breeds.json");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



}
