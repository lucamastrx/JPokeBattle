package it.jpokebattle.model.pokemon;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Classe che rappresenta un Pokémon con tutte le sue caratteristiche,
 * statistiche e funzionalità.
 * Ogni Pokémon ha un nome, una specie (Breed), un livello,
 * statistiche (HP, Attacco, Difesa, Attacco Speciale, Difesa Speciale, Velocità),
 * e può imparare un massimo di 4 mosse.
 */
public class Pokemon {
    private String name;
    private Breed breed;        // specie
    private HashMap<Move, Integer> moves;        // {mossa: pp attuali}
    private int currentHp;             // punti salute attuali (variabili)
    private int fullHp;                // punti salute del pokemon quando è curato
    private int atk;
    private int fullAtk;               // atk del pokemon quando è curato
    private int def;
    private int fullDef;               // def del pokemon quando è curato
    private int specialAtk;
    private int fullSpecialAtk;        // special atk salute del pokemon quando è curato
    private int specialDef;
    private int fullSpecialDef;        // special def salute del pokemon quando è curato
    private int speed;
    private int fullSpeed;             // speed del pokemon quando è curato
    private int level;
    private int strength;
    private int hpIV;
    private int atkIV;
    private int defIV;
    private int specialAtkIV;
    private int specialDefIV;
    private int speedIV;
    private int accuracy;
    private int evasion;


    /**
     * Costruttore per creare un Pokémon con una specie e un livello specificati.
     * Setta automaticamente il nome del Pokémon con il nome della specie e inizializza
     * altre statistiche e mosse in base al livello specificato.
     *
     * @param breed Specie del Pokémon.
     * @param level Livello del Pokémon.
     */
    public Pokemon(Breed breed, int level) {
        this.breed = breed;
        this.name = breed.getName();     // inizialmente, il nome del pokemon è la razza
        this.moves = new HashMap<>();
        this.level = level;
        this.accuracy = 100;
        this.evasion = 1;
        setRandomIVs();
        setStatsFromLevel(level);
        setMovesFromLevel(level);
        setStrength( getFullHp() + getFullAtk() + getFullDef() + getFullSpecialAtk() + getFullSpecialDef() + getFullSpeed() + getLevel());
    }

    /**
     * Costruttore protetto utilizzato per la serializzazione.
     */
    protected Pokemon(){}   // serve solo per la serializzazione di java

    /**
     * Controlla se il Pokémon è svenuto (HP <= 0).
     *
     * @return {@code true} se il Pokémon è svenuto, altrimenti {@code false}.
     */
    public boolean isFainted() {return currentHp <= 0;}

    /**
     * Cura completamente il Pokémon ripristinando HP, PP, e altre statistiche.
     */
    public void heal() {
        setCurrentHp(getFullHp());
        for (Move move : moves.keySet()) {
            moves.put(move, move.getUtilizzi());  // ripristina i PP al massimo
        }
        setEvasion(1);
        setAccuracy(100);
        setAtk(getFullAtk());
        setDef(getFullDef());
        setSpecialAtk(getFullSpecialAtk());
        setSpecialDef(getFullSpecialDef());
        setSpeed(getFullSpeed());
    }

    /**
     * Usa una mossa specificata dal Pokémon, decrementando i PP della mossa.
     *
     * @param move La mossa da usare.
     */
    public void useMove(Move move) {
        Integer currentValue = moves.get(move);
        if (currentValue != null && currentValue > 0) {
            moves.put(move, currentValue - 1);
        } else {
            // Gestione del caso in cui la mossa non esiste o il valore è già 0
            System.out.println("Impossibile usare la mossa: " + move);
        }
    }

    /**
     * Riduce gli HP del Pokémon in base al danno ricevuto.
     *
     * @param damage Il danno da infliggere.
     */
    public void takeDamage(int damage) {setCurrentHp(Math.max(0, currentHp - damage));}

    public String getName() {return name;}

    public Breed getBreed() {return breed;}

    public int getCurrentHp() {return currentHp;}

    public int getFullHp() {return fullHp;}

    public int getAtk() {return atk;}

    public int getDef() {return def;}

    public int getSpecialAtk() {return specialAtk;}

    public int getSpecialDef() {return specialDef;}

    public int getSpeed() {return speed;}

    public int getLevel() {return level;}

    public int getSpeedIV() {return speedIV;}

    public int getSpecialDefIV() {return specialDefIV;}

    public int getSpecialAtkIV() {return specialAtkIV;}

    public int getDefIV() {return defIV;}

    public int getAtkIV() {return atkIV;}

    public int getHpIV() {return hpIV;}

    public HashMap<Move, Integer> getMoves() {return moves;}

    public int getStrength() {return strength;}

    public int getFullSpeed() {return fullSpeed;}

    public int getFullSpecialDef() {return fullSpecialDef;}

    public int getFullSpecialAtk() {return fullSpecialAtk;}

    public int getFullDef() {return fullDef;}

    public int getFullAtk() {return fullAtk;}

    public int getAccuracy() {return accuracy;}

    public int getEvasion() {return evasion;}

    /**
     * Imposta gli IV a valori randomici tra 0 e 15.
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

    public void setAtk(int atk) {this.atk = atk;}

    public void setDef(int def) {this.def = def;}

    public void setSpecialAtk(int specialAtk) {this.specialAtk = specialAtk;}

    public void setSpecialDef(int specialDef) {this.specialDef = specialDef;}

    public void setSpeed(int speed) {this.speed = speed;}

    public void setLevel(int level) {this.level = level;}

    public void setFullHp(int fullHp) {this.fullHp = fullHp;}

    public void setFullAtk(int fullAtk) {this.fullAtk = fullAtk;}

    public void setFullDef(int fullDef) {this.fullDef = fullDef;}

    public void setFullSpecialAtk(int fullSpecialAtk) {this.fullSpecialAtk = fullSpecialAtk;}

    public void setFullSpecialDef(int fullSpecialDef) {this.fullSpecialDef = fullSpecialDef;}

    public void setFullSpeed(int fullSpeed) {this.fullSpeed = fullSpeed;}

    public void setStrength(int strength) {this.strength = strength;}

    // Setter per Accuracy con limiti tra 40 e 160
    private void setAccuracy(int accuracy) {
        this.accuracy = Math.max(60, Math.min(accuracy, 160));  // Assicura che l'accuratezza sia tra 60 e 160
    }

    // Metodo per incrementare l'Accuracy di 10
    public void modifyAccuracy(int modificatorValue) {
        setAccuracy(this.accuracy + modificatorValue);  // Incrementa di 10 usando il setter per garantire i limiti
    }


    // Setter per Evasion con limiti tra 1 e 61
    private void setEvasion(int evasion) {
        this.evasion = Math.max(1, Math.min(evasion, 61));  // Assicura che l'evasione sia tra 1 e 61
    }

    // Metodo per decrementare/incrementare l'Evasion di 10
    public void modifyEvasion(int modificatorValue) {
        setEvasion(this.evasion + modificatorValue);  // Decrementa di 10 usando il setter per garantire i limiti
    }

    /**
     * Imposta le statistiche del Pokémon in base al livello specificato.
     *
     * @param lvl Il livello del Pokémon.
     */
    private void setStatsFromLevel(int lvl){
        setCurrentHp((((getBreed().getHpBaseStat() + getHpIV()) * 2 * lvl) / 100)+ lvl + 10);
        setFullHp((((getBreed().getHpBaseStat() + getHpIV()) * 2 * lvl) / 100)+ lvl + 10);
        setFullAtk((((getBreed().getAtkBaseStat() + getAtkIV()) * 2 * lvl) / 100) + 5);
        setAtk(getFullAtk());
        setFullDef((((getBreed().getDefBaseStat() + getDefIV()) * 2 * lvl) / 100) + 5);
        setDef(getFullDef());
        setFullSpecialAtk((((getBreed().getSpecialAtkBaseStat() + getSpecialAtkIV()) * 2 * lvl) / 100) + 5);
        setSpecialAtk(getFullSpecialAtk());
        setFullSpecialDef((((getBreed().getSpecialDefBaseStat() + getSpecialDefIV()) * 2 * lvl) / 100) + 5);
        setSpecialDef(getFullSpecialDef());
        setFullSpeed((((getBreed().getSpeedBaseStat() + getSpeedIV()) * 2 * lvl) / 100) + 5);
        setSpeed(getFullSpeed());
    }

    /**
     * Imposta le mosse del Pokémon in base al livello specificato.
     *
     * @param lvl Il livello del Pokémon.
     */
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
     * Setta le mosse del Pokémon. Possono essere al massimo 4.
     *
     * @param moves HashMap di mosse.
     * @throws IllegalArgumentException se il numero di mosse è superiore a 4.
     */
    public void setMoves(HashMap<Move, Integer> moves) {
        if (moves.size() > 4) {
            throw new IllegalArgumentException("Un Pokémon può avere al massimo 4 mosse.");
        }
        this.moves = moves;

    }


    /**
     * Setta le mosse del Pokémon. Possono essere al massimo 4.
     *
     * @param moves Array di mosse.
     * @throws IllegalArgumentException se il numero di mosse è superiore a 4.
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
     * Aggiunge una mossa al Pokémon. Se il Pokémon ha già 4 mosse, viene chiesta quale mossa sostituire.
     *
     * @param move La mossa che si vuole aggiungere.
     */
    public void addMove(Move move) {    // Questi controli li lascio perche se un giorno volessi implementare le MT
        if (getMoves().size() > 3) {    // non passerò tramite i metodi canLearnMove ecc, mantengo sicurezza

            System.out.println(getName() + "conosce già 4 mosse, deve dimenticarne una...");
            // click per rimuovere una mossa                                               // in GUI
            // removeMove(Move mossaDaDimenticare)
            System.out.println(getName() + "ha imparato la mossa: " + move + "\nFantastico!");
            // oppure "non ha imparato la mossa"!
        }
        else {
            int ppMax = move.getUtilizzi();
            getMoves().put(move, ppMax);
        }
    }

    public void addMove(String moveID) {
        if (getMoves().size() > 3) {
            System.out.println(getName() + "conosce già 4 mosse, deve dimenticarne una...");
            // click per rimuovere una mossa                                               // in GUI
            // removeMove(Move mossaDaDimenticare)
            System.out.println(getName() + "ha imparato la mossa: " + moveID + "\nFantastico!");
            // oppure "non ha imparato la mossa"!

        }
        else {
            Move move = Move.movesMap.get(moveID);          // prende la mossa dalla mappa di tutte le mosse
            int ppMax = move.getUtilizzi();
            getMoves().put(move, ppMax);
        }
    }

    /**
     * Rimuove una mossa dal Pokémon.
     *
     * @param move La mossa da rimuovere.
     */
    public void removeMove(Move move){
        getMoves().remove(move);
    }



    /**
     * Rilascia EVs quando il Pokémon viene sconfitto.
     *
     * @return Gli EV rilasciati dal Pokémon.
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


    /**
     * Restituisce una rappresentazione stringa del Pokémon con tutte le sue caratteristiche.
     *
     * @return Una stringa che rappresenta il Pokémon.
     */
    @Override
    public String toString() {
        return "Pokemon:{" +
                "name=" + name + ", " +
                getBreed().toString() + ", " +
                getMoves().toString() + ", " +
                "currentHp=" + currentHp + ", " +
                "fullHp=" + fullHp + ", " +
                "atk=" + fullAtk + ", " +
                "def=" + fullDef + ", " +
                "specialAtk=" + fullSpecialAtk + ", " +
                "specialDef=" + fullSpecialDef + ", " +
                "speed=" + fullSpeed + ", " +
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

    /**
     * Compara questo Pokémon con un altro oggetto per verificare l'uguaglianza.
     *
     * @param obj L'oggetto con cui confrontare.
     * @return {@code true} se gli oggetti sono uguali, altrimenti {@code false}.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        Pokemon pokemon = (Pokemon) obj;

        return currentHp == pokemon.currentHp &&
                fullHp == pokemon.fullHp &&
                fullAtk == pokemon.fullAtk &&
                fullDef == pokemon.fullDef &&
                fullSpecialAtk == pokemon.fullSpecialAtk &&
                fullSpecialDef == pokemon.fullSpecialDef &&
                fullSpeed == pokemon.fullSpeed &&
                level == pokemon.level &&
                strength == pokemon.strength &&
                hpIV == pokemon.hpIV &&
                atkIV == pokemon.atkIV &&
                defIV == pokemon.defIV &&
                specialAtkIV == pokemon.specialAtkIV &&
                specialDefIV == pokemon.specialDefIV &&
                speedIV == pokemon.speedIV &&
                accuracy == pokemon.accuracy &&
                evasion == pokemon.evasion &&
                Objects.equals(name, pokemon.name) &&
                Objects.equals(breed, pokemon.breed) &&
                Objects.equals(moves, pokemon.moves);
    }

    /**
     * Calcola l'hash code per il Pokémon.
     *
     * @return L'hash code del Pokémon.
     */
    @Override
    public int hashCode() {
        return Objects.hash(name, breed, moves, currentHp, fullHp, fullAtk, fullDef, fullSpecialAtk, fullSpecialDef, fullSpeed, level,
                strength, hpIV, atkIV, defIV, specialAtkIV, specialDefIV, speedIV, accuracy, evasion);
    }
}
