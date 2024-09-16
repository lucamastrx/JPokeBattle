package it.jpokebattle.model.battle;


import it.jpokebattle.model.exceptions.MoveSetException;
import it.jpokebattle.model.player.BotPlayer;
import it.jpokebattle.model.player.Player;
import it.jpokebattle.model.pokemon.Move;
import it.jpokebattle.model.pokemon.OwnedPokemon;
import it.jpokebattle.model.pokemon.Pokemon;
import it.jpokebattle.model.type.TypeEffectiveness;
import java.util.ArrayList;
import java.util.List;


/**
 * La classe {@code MoveAction} rappresenta un'azione di attacco eseguita da un giocatore
 * durante una battaglia Pokémon. Questa classe gestisce l'intero processo di utilizzo di una mossa,
 * dal calcolo della precisione, all'applicazione del danno e degli effetti, fino alla gestione
 * delle condizioni di vittoria o sconfitta.
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
     * degli effetti speciali e l'aggiornamento degli stati di battaglia.
     *
     * @return Una lista di {@link BattleStatus} che rappresenta i vari cambiamenti di stato
     *         della battaglia, ad esempio se un Pokémon viene esausto o se il giocatore vince/perde.
     * @throws MoveSetException Se ci sono problemi con il set di mosse del Pokémon.
     */
    public List<BattleStatus> execute() throws MoveSetException {
        Pokemon user = actor.getTeam().getActivePokemon();
        Pokemon target = playerOpponent.getTeam().getActivePokemon();
        List<BattleStatus> status = new ArrayList<>();

        // Utilizzo di PP della mossa
        user.useMove(move);

        // Verifica se la mossa colpisce
        if (isHit(user, move, target)) {
            System.out.println("HIT!");
            applyMoveEffects(user, move, target, status);
        } else {
            System.out.println("MISS!");
            status.add(BattleStatus.CONTINUE_WITH_NO_DAMAGE);
        }

        return status;
    }

    /**
     * Applica gli effetti della mossa, inclusi danni e modificatori, e gestisce
     * l'evoluzione del Pokémon se applicabile.
     *
     * @param user Il Pokémon che utilizza la mossa.
     * @param move La mossa che viene utilizzata.
     * @param target Il Pokémon bersaglio della mossa.
     * @param status La lista degli stati della battaglia che viene aggiornata con gli effetti.
     * @throws MoveSetException Se ci sono problemi con il set di mosse del Pokémon.
     */
    private void applyMoveEffects(Pokemon user, Move move, Pokemon target, List<BattleStatus> status) throws MoveSetException {
        int damage = calculateDamage(user, move, target);
        target.takeDamage(Math.max(damage, 0));
        System.out.println(user.getName() + " usa " + move.getName() + " su " + target.getName() + " causando " + damage + " danni.");
        specialEffects(user, move, target);

        if (target.isFainted()) {
            handleFaintedTarget(user, target, status);
        } else {
            status.add(playerOpponent instanceof BotPlayer ? BattleStatus.CONTINUE_DAMAGE_TO_BOT_POKEMON : BattleStatus.CONTINUE_DAMAGE_TO_YOUR_POKEMON);
        }
    }


    /**
     * Gestisce la situazione in cui il bersaglio è esausto (fainted), aggiungendo esperienza al Pokémon
     * dell'attore e verificando se il giocatore ha vinto o se il Pokémon può evolvere.
     *
     * @param user Il Pokémon che ha sconfitto il bersaglio.
     * @param target Il Pokémon esausto.
     * @param status La lista degli stati di battaglia aggiornata con il risultato dell'esaustione.
     * @throws MoveSetException Se ci sono problemi con il set di mosse del Pokémon.
     */
    private void handleFaintedTarget(Pokemon user, Pokemon target, List<BattleStatus> status) throws MoveSetException {
        System.out.println(target.getName() + " è esausto!");

        if (user instanceof OwnedPokemon ownedPokemon) {
            ownedPokemon.collectExp(target.getStrength() + 500000);
            ownedPokemon.collectEVs(target.dropEVs());
            handleLevelUp(ownedPokemon, status);
        }

        // Verifica se la battaglia è finita
        if (battle.botLost()) {
            status.add(BattleStatus.YOU_WIN);
        } else if (battle.humanLost()) {
            status.add(BattleStatus.YOU_LOST);
        } else {
            handlePokemonChange(status);
        }
    }


    /**
     * Gestisce il livello del Pokémon dell'attore, verificando se può salire di livello
     * o evolvere.
     *
     * @param ownedPokemon Il Pokémon dell'attore.
     * @param status La lista degli stati di battaglia aggiornata con i risultati del livellamento.
     * @throws MoveSetException Se ci sono problemi con il set di mosse del Pokémon.
     */
    private void handleLevelUp(OwnedPokemon ownedPokemon, List<BattleStatus> status) throws MoveSetException {
        if (ownedPokemon.canLevelUp()) {
            ownedPokemon.levelUpAndStatsUp();
            status.add(BattleStatus.YES_LEVEL_UP);
            status.add(ownedPokemon.learnMove());
            status.add(ownedPokemon.evolve());
        } else {
            status.add(BattleStatus.NO_LEVEL_UP);
        }
    }


    /**
     * Gestisce il cambio di Pokémon se il bersaglio è esausto. Se l'avversario è un bot,
     * il bot sceglie automaticamente un nuovo Pokémon.
     *
     * @param status La lista degli stati di battaglia aggiornata con il risultato del cambio di Pokémon.
     */
    private void handlePokemonChange(List<BattleStatus> status) {
        if (playerOpponent instanceof BotPlayer) {
            status.add(BattleStatus.CHANGE_BOT_FAINTED_POKEMON);
            ((BotPlayer) playerOpponent).smartPokemonChange();
        } else {
            status.add(BattleStatus.CHANGE_YOUR_FAINTED_POKEMON);
        }
    }


    /**
     * Determina se la mossa colpisce il bersaglio, basandosi sulla precisione della mossa,
     * l'accuratezza del Pokémon attaccante e l'evasione del bersaglio.
     *
     * @param user Il Pokémon che esegue la mossa.
     * @param move La mossa utilizzata.
     * @param target Il Pokémon bersaglio.
     * @return {@code true} se la mossa colpisce, {@code false} altrimenti.
     */
    private boolean isHit(Pokemon user, Move move, Pokemon target) {
        double hitProbability = move.getPrecision() * ((double)user.getAccuracy() / 100) * (1 - ((double)target.getEvasion() / 100));
        // System.out.println(move.getPrecision()+" "+user.getAccuracy()+ " "+target.getEvasion());
        System.out.println("HIT PROB: "+hitProbability);
        double random = Math.random() * 100;
        return random <= hitProbability;
    }


    /**
     * Applica eventuali effetti speciali della mossa sul Pokémon utilizzatore o sul bersaglio.
     *
     * @param user Il Pokémon che esegue la mossa.
     * @param move La mossa utilizzata.
     * @param target Il Pokémon bersaglio.
     */
    private void specialEffects(Pokemon user, Move move, Pokemon target) {
        applyEffect(move.getOpponentModificatorType(), move.getOpponentModificatorValue(), target);
        applyEffect(move.getPersonalModificatorType(), move.getPersonalModificatorValue(), user);
    }


    /**
     * Applica un modificatore di stato a un Pokémon in base al tipo di effetto specificato.
     *
     * @param effectType Il tipo di effetto (es. "atk", "def", "speed").
     * @param value Il valore dell'effetto (positivo o negativo).
     * @param pokemon Il Pokémon su cui applicare l'effetto.
     */
    private void applyEffect(String effectType, int value, Pokemon pokemon) {
        if (effectType == null || effectType.isBlank()) return;

        switch (effectType) {
            case "atk" -> {
                pokemon.setAtk(pokemon.getAtk() + value);
                System.out.println("Attacco di " + pokemon.getName() + " modificato di " + value + " punti!");
            }
            case "def" -> {
                pokemon.setDef(pokemon.getDef() + value);
                System.out.println("Difesa di " + pokemon.getName() + " modificata di " + value + " punti!");
            }
            case "specialAtk" -> {
                pokemon.setSpecialAtk(pokemon.getSpecialAtk() + value);
                System.out.println("Attacco Speciale di " + pokemon.getName() + " modificato di " + value + " punti!");
            }
            case "specialDef" -> {
                pokemon.setSpecialDef(pokemon.getSpecialDef() + value);
                System.out.println("Difesa Speciale di " + pokemon.getName() + " modificata di " + value + " punti!");
            }
            case "speed" -> {
                pokemon.setSpeed(pokemon.getSpeed() + value);
                System.out.println("Velocità di " + pokemon.getName() +  " modificata di " + value + " punti!");
            }
            case "accuracy" -> {
                pokemon.modifyAccuracy(value);
                System.out.println("Precisione di " + pokemon.getName() + " modificata di " + value + " punti!");
            }
            case "evasion" -> {
                pokemon.modifyEvasion(value);
                System.out.println("Elusione di " + pokemon.getName() + " modificata di " + value + " punti!");
            }
            default -> throw new IllegalArgumentException("Unrecognized effect: " + effectType);
        }
    }


    /**
     * Calcola il danno inflitto al bersaglio in base alle statistiche dell'attaccante e del difensore,
     * al potere della mossa e a un modificatore casuale.
     *
     * @param user Il Pokémon che utilizza la mossa.
     * @param move La mossa utilizzata.
     * @param target Il Pokémon bersaglio.
     * @return Il danno calcolato.
     */
    private int calculateDamage(Pokemon user, Move move, Pokemon target) {
        if (move.getPower() == 0) return 0;

        int attackPower = move.isSpecial() ? user.getSpecialAtk() : user.getAtk();
        int defensePower = move.isSpecial() ? target.getSpecialDef() : target.getDef();
        double modifier = calculateModifier(user, move, target);

        return (int) (((((2 * user.getLevel() / 5 + 2) * move.getPower() * attackPower / defensePower) / 50) + 5) * modifier);
    }


    /**
     * Calcola il modificatore del danno basato su fattori come STAB, efficacia e probabilità di colpo critico.
     *
     * @param user Il Pokémon che utilizza la mossa.
     * @param move La mossa utilizzata.
     * @param target Il Pokémon bersaglio.
     * @return Il modificatore del danno.
     */
    private double calculateModifier(Pokemon user, Move move, Pokemon target) {
        double effectiveness = TypeEffectiveness.getInstance().getEffectiveness(move.getType(), target.getBreed().getTypes());
        return RNG() * STAB(user, move) * effectiveness * (critical() ? 2 : 1);
    }


    /**
     * Determina se la mossa è un colpo critico.
     *
     * @return {@code true} se la mossa è critica, {@code false} altrimenti.
     */
    private boolean critical() {
        return Math.random() < 0.0625; // 6,25% di probabilità
    }


    /**
     * Genera un numero casuale per calcolare il modificatore del danno.
     *
     * @return Un numero casuale tra 0,85 e 1,0.
     */
    private double RNG() {
        return 0.85 + (Math.random() * 0.15);
    }

    /**
     * Calcola il modificatore STAB (Same Type Attack Bonus), che aumenta il danno
     * se il tipo della mossa corrisponde al tipo del Pokémon.
     *
     * @param user Il Pokémon che utilizza la mossa.
     * @param move La mossa utilizzata.
     * @return Il valore del bonus STAB (1,5 se applicabile, 1,0 altrimenti).
     */
    private double STAB(Pokemon user, Move move) {
        return user.getBreed().getTypes().contains(move.getType()) ? 1.5 : 1.0;
    }


    /**
     * Restituisce una rappresentazione testuale dell'azione di attacco, indicando il Pokémon attaccante,
     * il bersaglio e la mossa utilizzata.
     *
     * @return Una stringa che descrive l'azione.
     */
    @Override
    public String toString(){
        return actor.getTeam().getActivePokemon().getName() + " attacca " + playerOpponent.getTeam().getActivePokemon().getName() + " con " + move.toString();
    }
}
