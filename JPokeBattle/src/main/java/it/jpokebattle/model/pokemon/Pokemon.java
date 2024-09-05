package it.jpokebattle.model.pokemon;

import java.util.HashMap;
import java.util.Random;


public class Pokemon {
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
    protected int hpIV;
    protected int atkIV;
    protected int defIV;
    protected int specialAtkIV;
    protected int specialDefIV;
    protected int speedIV;
    protected int strength;



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
        setDefaultValues();               // TODO
        setRandomIVs();
        this.strength = hp + atk + def + specialAtk + specialDef + speed + level;
    }

    /**
     * Metodo Costruttore.
     * Setta automaticamente il nome del pokemon con il nome della specie.
     * Gli altri attributi sono valori di default.
     *
     * @param breed Specie del pokemon
     */
    protected Pokemon(Breed breed) {
        this.breed = breed;
        this.name = breed.getName();     // inizialmente, il nome del pokemon è la razza
        setDefaultValues();               // TODO
        setRandomIVs();
        this.strength = hp + atk + def + specialAtk + specialDef + speed + level;
    }

    /**
     * Controlla se il pokemon ha ancora le forze
     */
    public boolean isAlive() {
        return hp > 0;
    }

    public void takeDamage(int damage) {
        this.hp -= damage;
        if (this.hp < 0) this.hp = 0;
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
     * Setta i valori degli attributi del pokemon con i valori di default
     */
    private void setDefaultValues(){
        this.moves = new HashMap<>();
        this.hp = 1;
        this.atk = 1;
        this.def = 1;
        this.specialAtk = 1;
        this.specialDef = 1;
        this.speed = 1;
        this.level = 1;
    }


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
     * Gli EVs rilasciati vengono aggiunti al conteggio totale di EVs del giocatore o di un altro Pokémon.
     *
     * @param receivingPokemon Il Pokémon che riceve gli EV rilasciati.
     */

    public void dropEVs(OwnedPokemon receivingPokemon) {
        if (this.getBreed().getEvAmountDropped() <= 0) {
            return;  // Non ci sono EV da rilasciare
        }

        switch (this.getBreed().getEvTypeDropped()) {
            case "hp":
                receivingPokemon.addHpEV(getBreed().getEvAmountDropped());
                break;
            case "atk":
                receivingPokemon.addAtkEV(getBreed().getEvAmountDropped());
                break;
            case "def":
                receivingPokemon.addDefEV(getBreed().getEvAmountDropped());
                break;
            case "specialAtk":
                receivingPokemon.addSpecialAtkEV(getBreed().getEvAmountDropped());
                break;
            case "specialDef":
                receivingPokemon.addSpecialDefEV(getBreed().getEvAmountDropped());
                break;
            case "speed":
                receivingPokemon.addSpeedEV(getBreed().getEvAmountDropped());
                break;
            default:
                throw new IllegalArgumentException("Tipo di EV non valido: " + this.getBreed().getEvTypeDropped());
        }
    }


}
