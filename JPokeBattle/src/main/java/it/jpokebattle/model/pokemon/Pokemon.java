package it.jpokebattle.model.pokemon;

import it.jpokebattle.util.EVDrop;

import java.util.*;
import java.util.stream.Collectors;


public abstract class Pokemon {
    private String name;
    private Breed breed;        // specie
    private HashMap<Move, Integer> moves;        // {codice mossa: pp attuali}
    private int currentHp;             // punti salute attuali (variabili)
    private int fullHp;                // punti salute del pokemon quando è curato
    private int atk;
    private int def;
    private int specialAtk;
    private int specialDef;
    private int speed;
    private int level;
    private int strength;
    private int hpIV;
    private int atkIV;
    private int defIV;
    private int specialAtkIV;
    private int specialDefIV;
    private int speedIV;


    /**
     * Metodo Costruttore.
     * Setta automaticamente il nome del pokemon con il nome della specie.
     * Gli altri attributi sono valori di default.
     *
     * @param breed Specie del pokemon
     */
    public Pokemon(Breed breed, int level) {
        this.breed = breed;
        this.name = breed.getName();     // inizialmente, il nome del pokemon è la razza
        this.moves = new HashMap<>();
        this.level = level;
        setRandomIVs();
        setStatsFromLevel(level);
        setMovesFromLevel(level);
        setStrength( getFullHp() + getAtk() + getDef() + getSpecialAtk() + getSpecialDef() + getSpeed() + getLevel());
    }


    protected Pokemon(){}

    /**
     * Controlla se il pokemon ha ancora le forze
     */
    public boolean isAlive() {
        return currentHp > 0;
    }

    public String getName() {return name;}

    public Breed getBreed() {return this.breed;}

    public int getCurrentHp() {return this.currentHp;}

    public int getFullHp() {return this.fullHp;}

    public int getAtk() {return this.atk;}

    public int getDef() {return this.def;}

    public int getSpecialAtk() {return this.specialAtk;}

    public int getSpecialDef() {return this.specialDef;}

    public int getSpeed() {return this.speed;}

    public int getLevel() {return this.level;}

    public int getSpeedIV() {return speedIV;}

    public int getSpecialDefIV() {return specialDefIV;}

    public int getSpecialAtkIV() {return specialAtkIV;}

    public int getDefIV() {return defIV;}

    public int getAtkIV() {return atkIV;}

    public int getHpIV() {return hpIV;}

    public HashMap<Move, Integer> getMoves() {return moves;}

    public int getStrength() {return strength;}


    /**
     * Vengono impostati gli IV a valori randomici tra 0 e 15
     */
    private void setRandomIVs() {
        Random random = new Random();

        hpIV = random.nextInt(16);
        atkIV = random.nextInt(16);
        defIV = random.nextInt(16);
        specialAtkIV = random.nextInt(16);
        specialDefIV = random.nextInt(16);
        speedIV = random.nextInt(16);
    }


    public void setBreed(Breed breed) {this.breed = breed;}

    public void setName(String name) {this.name = name;}

    public void setCurrentHp(int currentHp) {this.currentHp = currentHp;}

    public void setFullHp(int fullHp) {this.fullHp = fullHp;}

    public void setAtk(int atk) {this.atk = atk;}

    public void setDef(int def) {this.def = def;}

    public void setSpecialAtk(int specialAtk) {this.specialAtk = specialAtk;}

    public void setSpecialDef(int specialDef) {this.specialDef = specialDef;}

    public void setSpeed(int speed) {this.speed = speed;}

    public void setLevel(int level) {this.level = level;}

    public void setStrength(int strength) {this.strength = strength;}

    private void setStatsFromLevel(int lvl){
        setCurrentHp((((getBreed().getHpBaseStat() + getHpIV()) * 2 * lvl) / 100)+ lvl + 10);
        setFullHp((((getBreed().getHpBaseStat() + getHpIV()) * 2 * lvl) / 100)+ lvl + 10);
        setAtk((((getBreed().getAtkBaseStat() + getAtkIV()) * 2 * lvl) / 100) + 5);
        setDef((((getBreed().getDefBaseStat() + getDefIV()) * 2 * lvl) / 100) + 5);
        setSpecialAtk((((getBreed().getSpecialAtkBaseStat() + getSpecialAtkIV()) * 2 * lvl) / 100) + 5);
        setSpecialDef((((getBreed().getSpecialDefBaseStat() + getSpecialDefIV()) * 2 * lvl) / 100) + 5);
        setSpeed((((getBreed().getSpeedBaseStat() + getSpeedIV()) * 2 * lvl) / 100) + 5);
    }

    private void setMovesFromLevel(int lvl){
        HashMap<Integer, String> breedMoves = getBreed().getMoves();

        HashMap<Integer, String> filteredBreedMoves = breedMoves.entrySet()
                .stream()                               // Otteniamo un flusso di entry della mappa (breedMoves.entrySet().stream())
                .filter(entry -> entry.getKey() <= lvl) // Applichiamo il filtro per mantenere solo le entry con chiave <= lvl
                .collect(Collectors.toMap(              // usiamo collect(Collectors.toMap()) per creare una nuova mappa
                        Map.Entry::getKey,              // Map.Entry::getKey specifica che vogliamo usare le chiavi originali come chiavi nella nuova mappa.
                        Map.Entry::getValue,            // specifica che vogliamo usare i valori originali come valori nella nuova mappa.
                        (v1, v2) -> v1,                 // In caso di chiavi duplicate, mantiene il primo valore (è una funzione di merge)
                        HashMap::new                    // Specifica che vogliamo una HashMap come tipo di mappa risultante
                ));


        if (filteredBreedMoves.size() <= 4 && !filteredBreedMoves.isEmpty()) {
            getMoves().clear();
            for (String moveID : filteredBreedMoves.values()) {
                addMove(moveID);
            }

        } else if (filteredBreedMoves.size() > 4) {
            getMoves().clear();

            List<Map.Entry<Integer, String>> sortedEntries = new ArrayList<>(filteredBreedMoves.entrySet());
            sortedEntries.sort(Map.Entry.comparingByKey());

            List<String> movesToAdd = new ArrayList<>();

            // Prendi le 2 mosse con chiave più piccola
            movesToAdd.add(sortedEntries.get(0).getValue());
            movesToAdd.add(sortedEntries.get(1).getValue());

            // Prendi le 2 mosse con chiave più grande
            movesToAdd.add(sortedEntries.get(sortedEntries.size() - 2).getValue());
            movesToAdd.add(sortedEntries.get(sortedEntries.size() - 1).getValue());

            // Usa il metodo addMove(String moveID) su ognuna di queste
            for (String moveID : movesToAdd) {
                addMove(moveID);
            }


        } else if (filteredBreedMoves.isEmpty()) {
            throw new RuntimeException("Errore: Non ci sono mosse disponibili per il livello specificato.");
        }


        // System.out.println("Mosse possibili per il livello " + lvl + ": " + filteredBreedMoves);
        // System.out.println(getMoves().keySet());
    }



    /**
     * Metodo che setta le mosse di un pokemon.
     * Possono essere al massimo 4.
     * @param moves HashMap<String, Integer> di mosse
     * @throws IllegalArgumentException
     */
    public void setMoves(HashMap<Move, Integer> moves) {
        if (moves.size() > 4) {
            throw new IllegalArgumentException("Un Pokémon può avere al massimo 4 mosse.");
        }
        this.moves = moves;

    }


    /**
     * Metodo che setta le mosse di un pokemon.
     * Possono essere al massimo 4.
     * @param moves Array di mosse
     * @throws IllegalArgumentException
     */
    public void setMoves(Move[] moves) {
        if (moves.length > 4) {
            throw new IllegalArgumentException("Un Pokémon può avere al massimo 4 mosse.");
        }

        for (Move move : moves) {
            this.moves.put(move, move.getUtilizzi());
        }
    }


    /**
     * Metodo che aggiunge una mossa al pokemon.
     * Se il pokemon ha già 4 mosse, viene chiesta quale mossa sostituire.
     *
     * @param move Mossa che si vuole aggiungere
     */
    public void addMove(Move move) {
        if (getMoves().size() > 3) {

            System.out.println(getName() + "conosce già 4 mosse, deve dimenticarne una..."); // TODO messaggio in GUI
            // click per rimuovere una mossa                                               // in GUI
            // removeMove(Move mossaDaDimenticare)
            System.out.println(getName() + "ha imparato la mossa: " + move + "\nFantastico!"); // messaggio in GUI
            // oppure "non ha imparato la mossa"!
        }
        else {
            int ppMax = move.getUtilizzi();
            getMoves().put(move, ppMax);
        }
    }

    public void addMove(String moveID) {
        if (getMoves().size() > 3) {
            System.out.println(getName() + "conosce già 4 mosse, deve dimenticarne una..."); // TODO messaggio in GUI
            // click per rimuovere una mossa                                               // in GUI
            // removeMove(Move mossaDaDimenticare)
            System.out.println(getName() + "ha imparato la mossa: " + moveID + "\nFantastico!"); // messaggio in GUI
            // oppure "non ha imparato la mossa"!

        }
        else {
            Move move = Move.movesMap.get(moveID);          // prende la mossa dalla mappa di tutte le mosse
            int ppMax = move.getUtilizzi();
            getMoves().put(move, ppMax);
        }
    }

    public void removeMove(Move move){
        getMoves().remove(move);
    }



    public void heal(){
        setCurrentHp(getFullHp());
    }


    /**
     * Rilascia EVs quando il Pokémon viene sconfitto.
     * Gli EVs rilasciati vengono aggiunti al conteggio totale di EVs del Pokémon ancora in forze.
     */
    public EVDrop dropEVs() {
        if (this.getBreed().getEvAmountDropped() <= 0) {
            return null;  // Non ci sono EV da rilasciare
        }

        switch (this.getBreed().getEvTypeDropped()) {
            case "hp":
                return new EVDrop("hp", this.getBreed().getEvAmountDropped());
            case "atk":
                return new EVDrop("atk", this.getBreed().getEvAmountDropped());
            case "def":
                return new EVDrop("def", this.getBreed().getEvAmountDropped());
            case "specialAtk":
                return new EVDrop("specialAtk", this.getBreed().getEvAmountDropped());
            case "specialDef":
                return new EVDrop("specialDef", this.getBreed().getEvAmountDropped());
            case "speed":
                return new EVDrop("speed", this.getBreed().getEvAmountDropped());
            default:
                throw new IllegalArgumentException("Tipo di EV non valido: " + this.getBreed().getEvTypeDropped());
        }
    }


    @Override
    public String toString() {
        return "Pokemon:{" +
                "name=" + name + ", " +
                getBreed().toString() + ", " +
                getMoves().toString() + ", " +
                "currentHp=" + currentHp + ", " +
                "fullHp=" + fullHp + ", " +
                "atk=" + atk + ", " +
                "def=" + def + ", " +
                "specialAtk=" + specialAtk + ", " +
                "specialDef=" + specialDef + ", " +
                "speed=" + speed + ", " +
                "level=" + level + ", " +
                "strength=" + strength + ", " +
                "hpIV=" + hpIV + ", " +
                "atkIV=" + atkIV + ", " +
                "defIV=" + defIV + ", " +
                "specialAtkIV=" + specialAtkIV + ", " +
                "specialDefIV=" + specialDefIV + ", " +
                "speedIV=" + speedIV +
                '}';
    }
}
