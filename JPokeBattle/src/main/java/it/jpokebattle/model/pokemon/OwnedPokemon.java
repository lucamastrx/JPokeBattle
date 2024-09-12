package it.jpokebattle.model.pokemon;

import it.jpokebattle.model.Identifiable;
import it.jpokebattle.model.Saveable;
import it.jpokebattle.model.exceptions.MoveSetException;
import java.io.Serializable;
import java.util.HashMap;
import java.time.Instant;
import java.util.Objects;
import java.util.Set;


/**
 * La classe OwnedPokemon rappresenta un Pokémon posseduto da un giocatore in un contesto di battaglia.
 * Questa classe estende la classe Pokemon e aggiunge funzionalità specifiche come la gestione dell'esperienza (EXP),
 * dei valori di sforzo (EV), dei soprannomi e dell'evoluzione.
 * Implementa le interfacce {@link Serializable}, {@link Identifiable}, e {@link Saveable}.
 * <p>
 * Un Pokémon posseduto mantiene informazioni sul proprio stato come esperienza, soprannome, ID unico
 * basato sul timestamp di creazione, e EV utilizzati.
 * </p>
 *
 * @see Pokemon
 * @see Serializable
 * @see Identifiable
 * @see Saveable
 */
public class OwnedPokemon extends Pokemon implements Serializable, Identifiable, Saveable<OwnedPokemon> {

    private final String SER_SRC_NAME = "ser.ownedPokemon";  // Percorso del file di serializzazione per i Pokémon posseduti
    private final String ID;  // Identificatore univoco per il Pokémon posseduto

    private String nickname;  // Soprannome del Pokémon, se presente
    private int exp = 0;  // Esperienza attuale del Pokémon
    private int expForNextLevel;  // Esperienza richiesta per il prossimo livello
    private int hpEV = 0;  // EV per i punti vita
    private int atkEV = 0;  // EV per l'attacco
    private int defEV = 0;  // EV per la difesa
    private int specialAtkEV = 0;  // EV per l'attacco speciale
    private int specialDefEV = 0;  // EV per la difesa speciale
    private int speedEV = 0;  // EV per la velocità
    private int totalEVsUsed = 0;  // Contatore per il totale di EV utilizzati

    /**
     * Metodo costruttore.
     * Crea il Pokémon posseduto e lo aggiunge alla mappa che mantiene tutti i Pokémon.
     *
     * @param breed    Razza del Pokémon
     * @param nickname Soprannome del Pokémon
     * @param level    Livello iniziale del Pokémon
     */
    public OwnedPokemon(Breed breed, String nickname, int level) {
        super(breed, level);
        if (nickname != null && !nickname.isEmpty() && !nickname.isBlank()) this.nickname = nickname;
        this.ID = getName() + Instant.now();
        this.expForNextLevel = calculateRequiredExpForNextLevel(getLevel());
        save();               // aggiunge il pokemon alla mappa e la salva nel file ser
    }

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

    /**
     * Restituisce il soprannome del Pokémon.
     *
     * @return Soprannome del Pokémon.
     */
    public String getNickname() {return nickname;}

    /**
     * Restituisce l'esperienza attuale del Pokémon.
     *
     * @return Esperienza attuale.
     */
    public int getExp() {return this.exp;}

    /**
     * Restituisce l'esperienza richiesta per il prossimo livello.
     *
     * @return Esperienza per il prossimo livello.
     */
    public int getExpForNextLevel() {return expForNextLevel;}

    /**
     * Imposta l'esperienza necessaria per il prossimo livello.
     *
     * @param expForNextLevel Esperienza per il prossimo livello.
     */
    private void setExpForNextLevel(int expForNextLevel) {
        this.expForNextLevel = expForNextLevel;
    }


    /**
     * Raccoglie esperienza dal Pokémon sconfitto in battaglia e la aggiunge al Pokémon posseduto.
     * Se l'esperienza accumulata è sufficiente, il Pokémon salirà di livello.
     *
     * @param dropStrenghtDefeated Quantità di esperienza guadagnata dal Pokémon sconfitto.
     */
    public void collectExp(int dropStrenghtDefeated) {
        if (this.getLevel() < 100) {
            this.exp += dropStrenghtDefeated;
            save();
        }
    }

    /**
     * Raccoglie gli EV dal drop di EV e li aggiunge al tipo di statistica appropriata.
     *
     * @param evDrop L'oggetto EVDrop contenente il tipo di EV e l'ammontare degli EV che verranno raccolti.
     * @throws IllegalArgumentException se il tipo di EV fornito non è riconosciuto.
     */
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

    /**
     * Aggiunge EV ai punti vita (HP) dell'entità, rispettando il limite massimo di 256 EV per statistica
     * e un totale massimo di 510 EV complessivi.
     *
     * @param evAmountDropped L'ammontare di EV da aggiungere.
     */
    private void addHpEV(int evAmountDropped) {
        int total = hpEV + evAmountDropped;
        if (total < 256 && totalEVsUsed <= 510) hpEV += evAmountDropped;
    }

    /**
     * Aggiunge EV all'attacco (ATK) dell'entità, rispettando il limite massimo di 256 EV per statistica
     * e un totale massimo di 510 EV complessivi.
     *
     * @param evAmountDropped L'ammontare di EV da aggiungere.
     */
    private void addAtkEV(int evAmountDropped) {
        int total = atkEV + evAmountDropped;
        if (total < 256 && totalEVsUsed <= 510) atkEV += evAmountDropped;
    }

    /**
     * Aggiunge EV all'attacco (ATK) dell'entità, rispettando il limite massimo di 256 EV per statistica
     * e un totale massimo di 510 EV complessivi.
     *
     * @param evAmountDropped L'ammontare di EV da aggiungere.
     */
    private void addDefEV(int evAmountDropped) {
        int total = defEV + evAmountDropped;
        if (total < 256 && totalEVsUsed <= 510) defEV += evAmountDropped;
    }

    /**
     * Aggiunge EV all'attacco speciale (SPATK) dell'entità, rispettando il limite massimo di 256 EV per statistica
     * e un totale massimo di 510 EV complessivi.
     *
     * @param evAmountDropped L'ammontare di EV da aggiungere.
     */
    private void addSpecialAtkEV(int evAmountDropped) {
        int total = specialAtkEV + evAmountDropped;
        if (total < 256 && totalEVsUsed <= 510) specialAtkEV += evAmountDropped;
    }

    /**
     * Aggiunge EV alla difesa speciale (SPDEF) dell'entità, rispettando il limite massimo di 256 EV per statistica
     * e un totale massimo di 510 EV complessivi.
     *
     * @param evAmountDropped L'ammontare di EV da aggiungere.
     */
    private void addSpecialDefEV(int evAmountDropped) {
        int total = specialDefEV + evAmountDropped;
        if (total < 256 && totalEVsUsed <= 510) specialDefEV += evAmountDropped;
    }

    /**
     * Aggiunge EV alla velocità (SPD) dell'entità, rispettando il limite massimo di 256 EV per statistica
     * e un totale massimo di 510 EV complessivi.
     *
     * @param evAmountDropped L'ammontare di EV da aggiungere.
     */
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
                setFullHp(newValue);
                break;
            case "atk":
                setFullAtk(newValue);
                break;
            case "def":
                setFullDef(newValue);
                break;
            case "specialAtk":
                setFullSpecialAtk(newValue);
                break;
            case "specialDef":
                setFullSpecialDef(newValue);
                break;
            case "speed":
                setFullSpeed(newValue);
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
        setStrength(getFullHp() + getFullAtk() + getFullDef() + getFullSpecialAtk() + getFullSpecialDef() + getFullSpeed() + getLevel());
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
        return this.getLevel() >= this.getBreed().getEvoThreshold() && getBreed().getSuccessor() != null;
    }

    /**
     * Metodo che evolve il pokemon.
     * Cambia il breed del pokemon con quella del suo successore,
     * e inoltre modifica il nome del pokemon se non ha soprannome.
     */
    public void evolve() {
        if (this.canEvolve() && getBreed().getSuccessor() != null) {
            Breed succ = this.getBreed().getSuccessor();
            this.setBreed(succ);   // cambia il Breed del Pokemon

            if (this.nickname == null || this.nickname.isEmpty()) {
                this.setName(succ.getName());  // cambia il nome solo se non c'è un soprannome
            }
            setStrength(getFullHp() + getFullAtk() + getFullDef() + getFullSpecialAtk() + getFullSpecialDef() + getFullSpeed() + getLevel());
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

    /**
     * Metodo per salvare lo stato del Pokémon nel file di serializzazione.
     */
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
                "atk=" + getFullAtk() + ", " +
                "def=" + getFullDef() + ", " +
                "specialAtk=" + getFullSpecialAtk() + ", " +
                "specialDef=" + getFullSpecialDef() + ", " +
                "speed=" + getFullSpeed() + ", " +
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

    /**
     * Restituisce una rappresentazione stringa del Pokémon posseduto.
     *
     * @return Stringa che rappresenta lo stato corrente del Pokémon.
     */
    @Override
    public void save() {
        save(SER_SRC_NAME, this);
    }

    /**
     * Controlla l'uguaglianza tra due oggetti OwnedPokemon.
     *
     * @param obj L'oggetto da confrontare con l'oggetto corrente.
     * @return true se gli oggetti sono uguali; false altrimenti.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        OwnedPokemon that = (OwnedPokemon) obj;

        return Objects.equals(ID, that.ID);
    }

    /**
     * Restituisce il codice hash per l'oggetto OwnedPokemon.
     *
     * @return Codice hash calcolato in base all'ID.
     */
    @Override
    public int hashCode() {
        return Objects.hash(ID);
    }
}
