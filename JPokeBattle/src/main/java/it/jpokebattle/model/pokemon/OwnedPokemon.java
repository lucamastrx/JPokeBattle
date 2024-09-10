package it.jpokebattle.model.pokemon;

import it.jpokebattle.controller.SceneManager;
import it.jpokebattle.controller.battle.BattleController;
import it.jpokebattle.model.Identifiable;
import it.jpokebattle.model.Saveable;
import it.jpokebattle.model.exceptions.MoveSetException;

import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.time.Instant;
import java.util.Set;

// TODO equals toHash
public class OwnedPokemon extends Pokemon implements Serializable, Identifiable, Saveable<OwnedPokemon> {

    private final String SER_SRC_NAME = "ser.ownedPokemon";
    private final String ID;            // è composto dal nome del pokemon + il timestamp dell'istante di creazione

    private String nickname;    // soprannome, se presente
    private int exp = 0;            // esperienza attuale
    private int expForNextLevel;
    private int hpEV = 0;
    private int atkEV = 0;
    private int defEV = 0;
    private int specialAtkEV = 0;
    private int specialDefEV = 0;
    private int speedEV = 0;
    private int totalEVsUsed = 0;   // Contatore per il totale di EVs usati

    /**
     * Metodo Costruttore.
     * Crea il pokemon e lo aggiunge alla mappa che mantiene tutti i pokemon.
     *
     * @param breed    razza del pokemon
     * @param nickname soprannome del pokemon
     */
    public OwnedPokemon(Breed breed, String nickname, int level) {
        super(breed, level);
        if (nickname != null && !nickname.isEmpty() && !nickname.isBlank()) this.nickname = nickname;
        this.ID = getName() + Instant.now();
        this.expForNextLevel = calculateRequiredExpForNextLevel(getLevel());
        save();               // aggiunge il pokemon alla mappa e la salva nel file ser
    }

    // protected OwnedPokemon(){
    //     this.ID = getName() + Instant.now();
    // }

    /**
     * Restituisce il nome visualizzato del Pokémon. Se il Pokémon ha un soprannome, viene restituito il soprannome.
     * Altrimenti, viene restituito il nome della razza del Pokémon.
     *
     * @return Il soprannome se è impostato e non vuoto; altrimenti, il nome della razza del Pokémon.
     */
    @Override
    public String getName() {
        return (nickname != null && !nickname.isEmpty() && !nickname.isBlank()) ? nickname : super.getName();
    }

    public String getNickname() {return nickname;}

    public int getExp() {return this.exp;}

    public int getExpForNextLevel() {return expForNextLevel;}

    private void setExpForNextLevel(int expForNextLevel) {
        this.expForNextLevel = expForNextLevel;
    }



    public void collectExp(int dropStrenghtDefeated) {
        if (this.getLevel() < 100) {
            this.exp += dropStrenghtDefeated;
            save();
        }
    }


    public void collectEVs(EVDrop evDrop) {
        String evType = evDrop.getEvTypeDropped();
        if (evType == null || evType.isEmpty()) {
            return;
        }
        switch (evType) {
            case "hp":
                addHpEV(evDrop.getEvAmountDropped());
                break;
            case "atk":
                addAtkEV(evDrop.getEvAmountDropped());
                break;
            case "def":
                addDefEV(evDrop.getEvAmountDropped());
                break;
            case "specialAtk":
                addSpecialAtkEV(evDrop.getEvAmountDropped());
                break;
            case "specialDef":
                addSpecialDefEV(evDrop.getEvAmountDropped());
                break;
            case "speed":
                addSpeedEV(evDrop.getEvAmountDropped());
                break;
            default:
                throw new IllegalArgumentException("Tipo di EV non riconosciuto: " + evType);
        }
        save();
    }

    private void addHpEV(int evAmountDropped) {
        int total = hpEV + evAmountDropped;
        if (total < 256 && totalEVsUsed <= 510) hpEV += evAmountDropped;
    }

    private void addAtkEV(int evAmountDropped) {
        int total = atkEV + evAmountDropped;
        if (total < 256 && totalEVsUsed <= 510) atkEV += evAmountDropped;
    }

    private void addDefEV(int evAmountDropped) {
        int total = defEV + evAmountDropped;
        if (total < 256 && totalEVsUsed <= 510) defEV += evAmountDropped;
    }

    private void addSpecialAtkEV(int evAmountDropped) {
        int total = specialAtkEV + evAmountDropped;
        if (total < 256 && totalEVsUsed <= 510) specialAtkEV += evAmountDropped;
    }

    private void addSpecialDefEV(int evAmountDropped) {
        int total = specialDefEV + evAmountDropped;
        if (total < 256 && totalEVsUsed <= 510) specialDefEV += evAmountDropped;
    }

    private void addSpeedEV(int evAmountDropped) {
        int total = speedEV + evAmountDropped;
        if (total < 256 && totalEVsUsed <= 510) speedEV += evAmountDropped;
    }


    /**
     * Verifica e utilizza gli EV in multipli di 4, riducendo il totale degli EV disponibili e aggiornando il contatore totale degli EV utilizzati.
     *
     * @param evType Il tipo di EV (ad esempio, "hp", "atk", "def", ecc.)
     * @return Il numero di EV utilizzati.
     * @throws IllegalArgumentException se gli EV non sono di un tipo valido.
     */
    private int useEVs(String evType) {
        int ev = 0;

        // Ottieni il valore EV corrente basato sul tipo
        switch (evType) {
            case "hp":
                ev = hpEV;
                break;
            case "atk":
                ev = atkEV;
                break;
            case "def":
                ev = defEV;
                break;
            case "specialAtk":
                ev = specialAtkEV;
                break;
            case "specialDef":
                ev = specialDefEV;
                break;
            case "speed":
                ev = speedEV;
                break;
            default:
                throw new IllegalArgumentException("Tipo di EV non valido: " + evType);
        }

        // Verifica se ci sono abbastanza EV per essere utilizzati
        if (ev < 4) {
            return 0; // Non c'è abbastanza EV per essere utilizzato
        }

        // Trova il multiplo di 4 più vicino
        int usableEVs = (ev / 4) * 4;

        // Aggiorna gli EV disponibili sottraendo gli EV usati
        switch (evType) {
            case "hp":
                hpEV -= usableEVs;
                break;
            case "atk":
                atkEV -= usableEVs;
                break;
            case "def":
                defEV -= usableEVs;
                break;
            case "specialAtk":
                specialAtkEV -= usableEVs;
                break;
            case "specialDef":
                specialDefEV -= usableEVs;
                break;
            case "speed":
                speedEV -= usableEVs;
                break;
        }

        // Aggiungi gli EV usati al totale
        addToTotalEVsUsed(usableEVs);

        return usableEVs;
    }


    /**
     * Aggiunge il numero di EV utilizzati al contatore totale degli EV utilizzati.
     *
     * @param ev Il numero di EV da aggiungere al contatore.
     */
    private void addToTotalEVsUsed(int ev) {
        this.totalEVsUsed += ev;
    }




    /**
     * Aggiorna il valore di una statistica specifica dell'entità basandosi su IV (Individual Values),
     * base stat (statistica di base) e il livello dell'entità.
     *
     * Il valore calcolato viene utilizzato per aggiornare la statistica corrispondente. Le statistiche
     * che possono essere aggiornate includono punti vita (hp), attacco (atk), difesa (def), attacco speciale
     * (specialAtk), difesa speciale (specialDef) e velocità (speed).
     *
     * @param statType Il tipo di statistica da aggiornare. Deve essere uno dei seguenti valori:
     *                 <ul>
     *                   <li>"hp" - Punti vita</li>
     *                   <li>"atk" - Attacco</li>
     *                   <li>"def" - Difesa</li>
     *                   <li>"specialAtk" - Attacco speciale</li>
     *                   <li>"specialDef" - Difesa speciale</li>
     *                   <li>"speed" - Velocità</li>
     *                 </ul>
     * @param iv Il valore IV (Individual Value) della statistica. Rappresenta la variabilità individuale
     *           della statistica e influisce sul calcolo finale.
     * @param baseStat Il valore di base della statistica. Questo valore è comune per tutte le entità
     *                 della stessa specie e viene utilizzato come base per il calcolo del valore finale.
     *
     * @throws IllegalArgumentException Se il valore di `statType` non è uno dei valori previsti.
     */
    private void updateStat(String statType, int iv, int baseStat) {
        int usedEVs = useEVs(statType);
        int newValue = (int) (((baseStat + iv) * 2 + Math.sqrt(usedEVs) / 4 * getLevel()) / 100) + 5;

        switch (statType) {
            case "hp":
                newValue += getLevel() + 5;
                setCurrentHp(newValue);
                setFullHp(newValue);
                break;
            case "atk":
                setAtk(newValue);
                break;
            case "def":
                setDef(newValue);
                break;
            case "specialAtk":
                setSpecialAtk(newValue);
                break;
            case "specialDef":
                setSpecialDef(newValue);
                break;
            case "speed":
                setSpeed(newValue);
                break;
            default:
                throw new IllegalArgumentException("Tipo di statistica non riconosciuto: " + statType);
        }
    }


    /**
     * Funzione di CRESCITA pokemon.
     * È la funzione per calcolare quanti punti exp serviranno per salire al prossimo livello.
     * @param currentLevel
     * @return totale
     */
    private int calculateRequiredExpForNextLevel(int currentLevel) {
        currentLevel++;
        return 4 * (currentLevel * currentLevel * currentLevel) / 5;
    }


    /**
     * Controlla se il pokemon ha raggiunto la soglia di ex per salire di livello.
     * Il livello massimo è 100, dopo la quale il livello non aumenterà.
     */
    public boolean canLevelUp() {
        return this.exp >= this.expForNextLevel && getLevel() < 100;
    }

    /**
     * Incrementa il livello del Pokémon e aggiorna i relativi valori di esperienza.
     */
    private void levelUp() {
        setLevel(getLevel() + 1);
        this.exp = 0;
        if (getLevel() < 100) {
            setExpForNextLevel(calculateRequiredExpForNextLevel(getLevel()));
        }
    }

    /**
     * Metodo principale per aumentare il livello e le statistiche del Pokémon.
     */
    public void levelUpAndStatsUp() {
        if (canLevelUp()) {
            levelUp();
            updateStat("hp", getHpIV(), this.getBreed().getHpBaseStat());
            updateStat("atk", getAtkIV(), this.getBreed().getAtkBaseStat());
            updateStat("def", getDefIV(), this.getBreed().getDefBaseStat());
            updateStat("specialAtk", getSpecialAtkIV(), this.getBreed().getSpecialAtkBaseStat());
            updateStat("specialDef", getSpecialDefIV(), this.getBreed().getSpecialDefBaseStat());
            updateStat("speed", getSpeedIV(), this.getBreed().getSpeedBaseStat());
        }
        setStrength( getFullHp() + getAtk() + getDef() + getSpecialAtk() + getSpecialDef() + getSpeed() + getLevel());
        save();
    }



    public boolean canLearnMove(){
        // Ottieni i livelli in cui il Pokémon può imparare nuove mosse
        Set<Integer> moveLevels = getBreed().getMoves().keySet();

        // Verifica se il livello corrente è tra i livelli in cui può imparare mosse
        if (moveLevels.contains(this.getLevel())) {
            // Ottieni la mossa che può essere imparata al livello attuale
            String potentialMoveID = getBreed().getMoves().get(getLevel());
            Move potentialMove = Move.movesMap.get(potentialMoveID);

            // Verifica se la mossa non è già presente tra le mosse del Pokémon
            return !this.getMoves().containsKey(potentialMove);
        }

        return false;  // Il livello non è un livello in cui può imparare nuove mosse
    }

//    public boolean canLearnMove(){
//        Set<Integer> moveLevels =  getBreed().getMoves().keySet();
//        return moveLevels.contains(this.getLevel());
//    }

    public boolean has4moves() {
        return getMoves().size() == 4;
    }

    // (ActionEvent event) throws IOException
    public void learnMove() throws MoveSetException {
        if (canLearnMove()) {
            String moveID = getBreed().getMoves().get(getLevel());
            Move move = Move.movesMap.get(moveID);
            if (getMoves().size() < 4) {
                addMove(move);
            } else {
                throw new MoveSetException();
            }
        }
        save();
    }



    /**
     * Metodo che controlla se il livello del pokemon è sufficiente a
     * permettere l'evoluzione.
     *
     * @return valore booleano che determina se il pokemon può evolvere
     */
    private boolean canEvolve(){
        return this.getLevel() >= this.getBreed().getEvoThreshold();
    }

    /**
     * Metodo che evolve il pokemon.
     * Cambia il breed del pokemon con quella del suo successore,
     * e inoltre modifica il nome del pokemon se non ha soprannome.
     */
    public void evolve() {
        if (this.canEvolve()) {
            Breed succ = this.getBreed().getSuccessor();
            this.setBreed(succ);   // cambia il Breed del Pokemon

            if (this.nickname == null || this.nickname.isEmpty()) {
                this.setName(succ.getName());  // cambia il nome solo se non c'è un soprannome
            }
            setStrength( getFullHp() + getAtk() + getDef() + getSpecialAtk() + getSpecialDef() + getSpeed() + getLevel());
            save();
        }
    }





    @Override
    public void addMove(Move move){
        super.addMove(move);
        save();
    }

    @Override
    public void addMove(String moveID) {
        super.addMove(moveID);
        save();
    }

    @Override
    public void removeMove(Move move){
        super.removeMove(move);
        save();
    }

    @Override
    public void setMoves(HashMap<Move, Integer> moves){
        super.setMoves(moves);
        save();
    }

    @Override
    public void setMoves(Move[] moves) {
        super.setMoves(moves);
        save();
    }

    @Override
    public String getID() {
        return this.ID;
    }

    @Override
    public void save() {
        save(SER_SRC_NAME, this);
    }

    @Override
    public String toString() {
        return "OwnedPokemon{" +
                "ID=" + ID + ", " +
                "nickname=" + nickname + ", " +
                "name=" + getName() + ", " +
                getBreed().toString() + ", " +
                getMoves().toString() + ", " +          // in alternativa getMoves().keySet()
                "currentHp=" + getCurrentHp() + ", " +
                "fullHp=" + getFullHp() + ", " +
                "atk=" + getAtk() + ", " +
                "def=" + getDef() + ", " +
                "specialAtk=" + getSpecialAtk() + ", " +
                "specialDef=" + getSpecialDef() + ", " +
                "speed=" + getSpeed() + ", " +
                "level=" + getLevel() + ", " +
                "strength=" + getStrength() + ", " +
                "hpIV=" + getHpIV() + ", " +
                "atkIV=" + getAtkIV() + ", " +
                "defIV=" + getDefIV() + ", " +
                "specialAtkIV=" + getSpecialAtkIV() + ", " +
                "specialDefIV=" + getSpecialDefIV() + ", " +
                "speedIV=" + getSpeedIV() + ", " +
                "exp=" + exp + ", " +
                "expForNextLevel=" + expForNextLevel + ", " +
                "hpEV=" + hpEV + ", " +
                "atkEV=" + atkEV + ", " +
                "defEV=" + defEV + ", " +
                "specialAtkEV=" + specialAtkEV + ", " +
                "specialDefEV=" + specialDefEV + ", " +
                "speedEV=" + speedEV + ", " +
                "totalEVsUsed= " + totalEVsUsed +
                "} ";
    }
}
