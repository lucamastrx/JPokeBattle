package it.jpokebattle.model.battle;


import it.jpokebattle.model.exceptions.MoveSetException;
import it.jpokebattle.model.player.BotPlayer;
import it.jpokebattle.model.player.Player;
import it.jpokebattle.model.pokemon.Move;
import it.jpokebattle.model.pokemon.OwnedPokemon;
import it.jpokebattle.model.pokemon.Pokemon;
import it.jpokebattle.model.type.Type;
import it.jpokebattle.model.type.TypeEffectiveness;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;


/**
 * Classe che rappresenta l'azione di utilizzo di una mossa durante una battaglia Pokémon.
 * Questa classe gestisce l'intera logica di esecuzione di una mossa, inclusi i calcoli
 * per la precisione, i danni, gli effetti speciali e le condizioni di vittoria o sconfitta.
 */
public class MoveAction implements Action{

    private Player actor;
    private Move move;
    private Player playerOpponent;
    private Battle battle;


    /**
     * Costruttore della classe {@code MoveAction}.
     *
     * @param actor Il giocatore che esegue l'azione.
     * @param move La mossa che il giocatore desidera utilizzare.
     * @param playerOpponent Il giocatore avversario contro cui viene eseguita la mossa.
     * @param battle La battaglia in cui viene eseguita l'azione.
     */
    public MoveAction(Player actor, Move move, Player playerOpponent, Battle battle) {
        this.actor = actor;
        this.move = move;
        this.playerOpponent = playerOpponent;
        this.battle = battle;
    }


    /**
     * Restituisce la priorità della mossa da eseguire. La priorità è determinata
     * dalla mossa stessa.
     *
     * @return Un valore intero che rappresenta la priorità della mossa.
     */
    @Override
    public int getPriority() {return move.getPriority();}


    /**
     * Esegue l'azione di attacco utilizzando la mossa selezionata. L'azione comprende
     * l'utilizzo di PP (punti potere) della mossa, il calcolo del danno, la gestione
     * degli effetti speciali e l'eventuale aggiornamento degli stati di battaglia.
     *
     * @return Una lista di {@link BattleStatus} che rappresenta i vari cambiamenti di stato
     *         della battaglia in seguito all'azione (ad esempio, se un Pokémon viene esausto
     *         o se il giocatore vince/perde).
     * @throws MoveSetException Se ci sono problemi con il set di mosse del Pokémon.
     */
    @Override
    public List<BattleStatus> execute() throws MoveSetException {
        boolean canLevelUpFlag = false;
        Pokemon user = actor.getTeam().getActivePokemon();
        Pokemon target = playerOpponent.getTeam().getActivePokemon();
        List<BattleStatus> status = new ArrayList<>();
        // Utilizzatore usa 1 pp della mossa
        user.useMove(move);

        // Verifica se la mossa colpisce il bersaglio
        if (isHit(user, move, target)) {
            System.out.println("HIT!");
            if (user.getMoves().get(move) > -1) {
                int damage = calculateDamage(user, move, target);
                if (damage < 0) damage = 0;
                target.takeDamage(damage);
                System.out.println(user.getName() + " usa " + move.getName() + " su " + target.getName() + " causando " + damage + " danni.");
                specialEffects(user, move, target);
            } else {
                System.out.println("Oh no! " + user.getName() + " è troppo stanco per usare questa mossa!");
            }

            // Verifica se il bersaglio è esausto
            if (target.isFainted()) {
                System.out.println(target.getName() + " è esausto!");
                if (user instanceof OwnedPokemon) {
                    // Se l'utilizzatore è un Pokémon posseduto da un giocatore
                    // Cast sicuro a OwnedPokemon
                    OwnedPokemon ownedPokemon = (OwnedPokemon) user;
                    ownedPokemon.collectExp(target.getStrength()+500000);  // Guadagna esperienza
                    ownedPokemon.collectEVs(target.dropEVs());      // Guadagna EVs
                    if (ownedPokemon.canLevelUp()) {
                        status.add(BattleStatus.YES_LEVEL_UP);
                        ownedPokemon.levelUpAndStatsUp();   // Aumenta di livello e incrementa le statistiche
                        status.add(ownedPokemon.learnMove()); // Insegna una nuova mossa se possibile
                        status.add(ownedPokemon.evolve()); // Fa evolvere il Pokémon se possibile
                    } else {
                        status.add(BattleStatus.NO_LEVEL_UP);
                    }
                }
                // Verifica lo stato della battaglia dopo che il Pokémon avversario è esausto
                if (battle.botLost()) {
                    status.add(BattleStatus.YOU_WIN);
                } else if (battle.humanLost()) {
                    status.add(BattleStatus.YOU_LOST);
                } else if (playerOpponent instanceof BotPlayer) {
                    status.add(BattleStatus.CHANGE_BOT_FAINTED_POKEMON);
                } else {
                    // humanplayer cambia pokemon
                    status.add(BattleStatus.CHANGE_YOUR_FAINTED_POKEMON);
                }
                return status;
            }
            // Aggiornamento dello stato in caso di danno inflitto al bersaglio
            if (playerOpponent instanceof BotPlayer) {
                status.add(BattleStatus.CONTINUE_DAMAGE_TO_BOT_POKEMON);
            } else {
                status.add(BattleStatus.CONTINUE_DAMAGE_TO_YOUR_POKEMON);
            }
            return status;
        }

        // Se la mossa non colpisce il bersaglio
        System.out.println("MISS!");
        status.add(BattleStatus.CONTINUE_WITH_NO_DAMAGE);
        return status;
    }


    /**
     * Determina se la mossa colpisce il bersaglio in base alla precisione della mossa,
     * all'accuratezza dell'utilizzatore e all'evasione del bersaglio.
     *
     * @param user Il Pokémon che utilizza la mossa.
     * @param move La mossa utilizzata.
     * @param target Il Pokémon bersaglio.
     * @return {@code true} se la mossa colpisce, {@code false} altrimenti.
     */
    private boolean isHit(Pokemon user, Move move, Pokemon target) {
        double hitProbability = move.getPrecision() * ((double)user.getAccuracy() / 100) * (1 - ((double)target.getEvasion() / 100));
        System.out.println(move.getPrecision()+" "+user.getAccuracy()+ " "+target.getEvasion());
        System.out.println("HIT PROB: "+hitProbability);
        double random = Math.random() * 100;
        return random <= hitProbability;
    }


    /**
     * Applica gli effetti speciali della mossa, se presenti, sia sull'utilizzatore che
     * sul bersaglio.
     *
     * @param user Il Pokémon che utilizza la mossa.
     * @param move La mossa utilizzata.
     * @param target Il Pokémon bersaglio.
     */
    private void specialEffects(Pokemon user, Move move, Pokemon target){
        // Applicazione degli effetti sul bersaglio
        String effettoSuAvversario = move.getOpponentModificatorType();
        if (effettoSuAvversario != null && !effettoSuAvversario.isEmpty() && !effettoSuAvversario.isBlank()) {
            int valore = move.getOpponentModificatorValue();
            System.out.println("---------------" + effettoSuAvversario + "----------");
            switch (effettoSuAvversario) {
                case "atk": target.setAtk(target.getAtk() + valore); break;
                case "def": target.setDef(target.getDef() + valore); break;
                case "specialAtk": target.setSpecialAtk(target.getSpecialAtk() + valore); break;
                case "specialDef": target.setSpecialDef(target.getSpecialDef() + valore); break;
                case "speed": target.setSpeed(target.getSpeed() + valore); break;
                case "accuracy": target.modifyAccuracy(valore); break;
                case "evasion": target.modifyEvasion(valore); break;
                default:
                    throw new IllegalArgumentException("Tipo di Effetto su avversario non riconosciuto: " + effettoSuAvversario);
            }
        }
        // Applicazione degli effetti sull'utilizzatore
        String effettoSuSeStesso = move.getPersonalModificatorType();
        if (effettoSuSeStesso != null && !effettoSuSeStesso.isEmpty() && !effettoSuSeStesso.isBlank()) {
            int valore = move.getPersonalModificatorValue();
            switch (effettoSuSeStesso) {
                case "atk": user.setAtk(user.getAtk() + valore); break;
                case "def": user.setDef(user.getDef() + valore); break;
                case "specialAtk": user.setSpecialAtk(user.getSpecialAtk() + valore); break;
                case "specialDef": user.setSpecialDef(user.getSpecialDef() + valore); break;
                case "speed": user.setSpeed(user.getSpeed() + valore); break;
                case "accuracy": user.modifyAccuracy(valore); break;
                case "evasion": user.modifyEvasion(valore); break;
                default:
                    throw new IllegalArgumentException("Tipo di Effetto su se stesso non riconosciuto: " + effettoSuSeStesso);
            }
        }
    }

    /**
     * Calcola il danno inflitto dalla mossa al Pokémon bersaglio.
     *
     * @param user Il Pokémon che utilizza la mossa.
     * @param move La mossa utilizzata.
     * @param target Il Pokémon bersaglio.
     * @return Il danno inflitto come valore intero.
     */
    private int calculateDamage(Pokemon user, Move move, Pokemon target) {
        int lvl = user.getLevel();
        int movePower = move.getPower();
        if (movePower > 0) {
            System.out.println("la mossa di base ha power: " + movePower + " lvl: " + lvl);
            int userAttackPower;
            int targerDefensePower;
            if (move.isSpecial()) {
                userAttackPower = user.getSpecialAtk();
                targerDefensePower = target.getSpecialDef();
            } else {
                userAttackPower = user.getAtk();
                targerDefensePower = target.getDef();
            }
            double modificator = calculateModificator(user, move, target);
            System.out.println(" --------" + movePower + " " + userAttackPower + " " + targerDefensePower);
            return (int) ((((double) (((2 * lvl / 5) + 2) * movePower * (userAttackPower / targerDefensePower)) / 50) + 5) * modificator);
        }
        return 0;
    }


    /**
     * Calcola il modificatore di danno in base all'efficacia del tipo della mossa,
     * al colpo critico, al STAB e ad altri fattori.
     *
     * @param user Il Pokémon che utilizza la mossa.
     * @param move La mossa utilizzata.
     * @param target Il Pokémon bersaglio.
     * @return Il modificatore di danno.
     */
    private double calculateModificator(Pokemon user, Move move, Pokemon target) {
        double typeEfficacy = TypeEffectiveness.getInstance().getEffectiveness(move.getType(), target.getBreed().getTypes());

        if (critical()){
            return 2 * RNG() * STAB(user, move) * typeEfficacy;
        }
        return RNG() * STAB(user, move) * typeEfficacy;
    }

    /**
     * Determina se il colpo è critico.
     *
     * @return {@code true} se il colpo è critico, {@code false} altrimenti.
     */
    private boolean critical() {
        return Math.random() < 0.0625; // 6,25% di probabilità
    }


    /**
     * Genera un valore casuale per il calcolo del danno (Random Number Generator).
     *
     * @return Un valore casuale tra 0.85 e 1.0.
     */
    private double RNG() {
        return 0.85 + (Math.random() * 0.15);
    }


    /**
     * Calcola il modificatore STAB (Same Type Attack Bonus).
     * Se il tipo della mossa coincide con uno dei tipi del Pokémon utilizzatore,
     * il danno viene aumentato del 50%.
     *
     * @param user Il Pokémon che utilizza la mossa.
     * @param move La mossa utilizzata.
     * @return 1.5 se il Pokémon beneficia di STAB, 1.0 altrimenti.
     */
    private double STAB(Pokemon user, Move move) {
        HashSet<Type> userTypes = user.getBreed().getTypes();
        Type moveType = move.getType();
        if (userTypes.contains(moveType)) {
            return 1.5;
        }
        return 1.0;
    }
    @Override
    public String toString(){
        return actor.getTeam().getActivePokemon().getName() + " attacca " + playerOpponent.getTeam().getActivePokemon().getName() + " con " + move.toString();
    }
}
