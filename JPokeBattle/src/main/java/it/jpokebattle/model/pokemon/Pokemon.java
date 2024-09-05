package it.jpokebattle.model.pokemon;

import it.jpokebattle.util.EVDrop;

import java.util.HashMap;
import java.util.Random;


public abstract class Pokemon {
    protected String name;
    protected Breed breed;        // specie
    protected HashMap<Move, Integer> moves;        // {codice mossa: pp attuali}
    protected int hp;             // punti salute
    protected int atk;
    protected int def;
    protected int specialAtk;
    protected int specialDef;
    protected int speed;
    protected int level;
    protected int strength;
    protected int hpIV;
    protected int atkIV;
    protected int defIV;
    protected int specialAtkIV;
    protected int specialDefIV;
    protected int speedIV;



    /**
     * Metodo Costruttore.
     * Setta automaticamente il nome del pokemon con il nome della specie.
     * Gli altri attributi sono valori di default.
     *
     * @param breed Specie del pokemon
     */
    public Pokemon(Breed breed) {
        this.breed = breed;
        this.name = breed.getName();     // inizialmente, il nome del pokemon è la razza
        setRandomIVs();
    }


    /**
     * Controlla se il pokemon ha ancora le forze
     */
    public boolean isAlive() {
        return hp > 0;
    }

    public String getName() {return name;}

    public Breed getBreed() {return this.breed;}

    public int getHp() {return this.hp;}

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

    public void setHp(int hp) {this.hp = hp;}

    public void setAtk(int atk) {this.atk = atk;}

    public void setDef(int def) {this.def = def;}

    public void setSpecialAtk(int specialAtk) {this.specialAtk = specialAtk;}

    public void setSpecialDef(int specialDef) {this.specialDef = specialDef;}

    public void setSpeed(int speed) {this.speed = speed;}






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
}
