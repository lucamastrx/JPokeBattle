package it.jpokebattle.model.battle;


import it.jpokebattle.model.exceptions.MoveSetException;
import it.jpokebattle.model.player.BotPlayer;
import it.jpokebattle.model.player.Player;
import it.jpokebattle.model.pokemon.Move;
import it.jpokebattle.model.pokemon.OwnedPokemon;
import it.jpokebattle.model.pokemon.Pokemon;
import it.jpokebattle.model.type.Type;
import it.jpokebattle.model.type.TypeEffectiveness;
import java.util.HashSet;

public class MoveAction implements Action{

    private Player actor;
    private Move move;
    private Player playerOpponent;
    private Battle battle;

    public MoveAction(Player actor, Move move, Player playerOpponent, Battle battle) {
        this.actor = actor;
        this.move = move;
        this.playerOpponent = playerOpponent;
        this.battle = battle;
    }

    @Override
    public int getPriority() {return move.getPriority();}

    @Override
    public BattleStatus[] execute() throws MoveSetException {
        boolean canLevelUpFlag = false;
        Pokemon user = actor.getTeam().getActivePokemon();
        Pokemon target = playerOpponent.getTeam().getActivePokemon();

        // Utilizzatore usa 1 pp della mossa
        user.useMove(move);

        if (isHit(user, move, target)) {
            System.out.println("è hit");
            if (user.getMoves().get(move) > -1) {
                int damage = calculateDamage(user, move, target);
                target.takeDamage(damage);
                System.out.println(user.getName() + " usa " + move.getName() + " su " + target.getName() + " causando " + damage + " danni.");
                specialEffects(user, move, target);
            } else {
                System.out.println("Oh no! " + user.getName() + " è troppo stanco per usare questa mossa!");
            }


            if (target.isFainted()) {
                System.out.println(target.getName() + " è esausto!");
                if (user instanceof OwnedPokemon) {
                    // Cast sicuro a OwnedPokemon
                    OwnedPokemon ownedPokemon = (OwnedPokemon) user;
                    ownedPokemon.collectExp(target.getStrength());  // Guadagna esperienza
                    ownedPokemon.collectEVs(target.dropEVs());      // Guadagna EVs
                    if (ownedPokemon.canLevelUp()) {
                        canLevelUpFlag = true; // TODO
                        //ownedPokemon.levelUpAndStatsUp();               // Metodo che fa salire di livello e aumenta le stats
                        //ownedPokemon.learnMove();                       // Metodo che insegna una nuova mossa se possibile e se voluto
                        //ownedPokemon.evolve();                          // Metodo che fa evolvere se possibile
                    }
                }
                if (battle.botLost()) {
                    // TODO vittoria e prossima partita
                    return canLevelUpFlag ? new BattleStatus[]{BattleStatus.YOU_WIN, BattleStatus.YES_LEVEL_UP} : new BattleStatus[]{BattleStatus.YOU_WIN, BattleStatus.NO_LEVEL_UP};
                }
                if (battle.humanLost()) {
                    // TODO salvare tutti i dati della partita e aggiornare leaderboard e game over screen
                    return canLevelUpFlag ? new BattleStatus[]{BattleStatus.YOU_LOST, BattleStatus.YES_LEVEL_UP} : new BattleStatus[]{BattleStatus.YOU_LOST, BattleStatus.NO_LEVEL_UP};

                }
                if (playerOpponent instanceof BotPlayer) {
                    // Cast sicuro a BotPlayer
                    // BotPlayer botPlayer = (BotPlayer) playerOpponent;
                    return canLevelUpFlag ? new BattleStatus[]{BattleStatus.CONTINUE_WITH_BOT_CHANGE_POKEMON, BattleStatus.YES_LEVEL_UP} : new BattleStatus[]{BattleStatus.CONTINUE_WITH_BOT_CHANGE_POKEMON, BattleStatus.NO_LEVEL_UP};
                    // TODO bisogna che cambia pokemon con botPlayer.smartPokemonChange();

                } else {
                    // humanplayer cambia pokemon
                    return canLevelUpFlag ? new BattleStatus[]{BattleStatus.CONTINUE_WITH_YOUR_CHANGE_POKEMON, BattleStatus.YES_LEVEL_UP} : new BattleStatus[]{BattleStatus.CONTINUE_WITH_YOUR_CHANGE_POKEMON, BattleStatus.NO_LEVEL_UP};
                    // TODO
                }
            }
            if (playerOpponent instanceof BotPlayer) {
                return new BattleStatus[]{BattleStatus.CONTINUE_DAMAGE_TO_BOT_POKEMON, BattleStatus.NO_LEVEL_UP};
            } else {
                return new BattleStatus[]{BattleStatus.CONTINUE_DAMAGE_TO_YOUR_POKEMON, BattleStatus.NO_LEVEL_UP};
            }
        }
        return new BattleStatus[]{BattleStatus.CONTINUE_WITH_NO_DAMAGE, BattleStatus.NO_LEVEL_UP};
    }

    private boolean isHit(Pokemon user, Move move, Pokemon target) {
        double hitProbability = move.getPrecision() * (user.getAccuracy() / 100) * (1 - (target.getEvasion() / 100));
        double random = Math.random() * 100;
        return random <= hitProbability;
    }

    private void specialEffects(Pokemon user, Move move, Pokemon target){
        String effettoSuAvversario = move.getOpponentModificatorType();
        if (effettoSuAvversario != null && !effettoSuAvversario.isEmpty() && !effettoSuAvversario.isBlank()) {
            int valore = move.getOpponentModificatorValue();
            switch (effettoSuAvversario) {
                case "atk":
                    target.setAtk(target.getAtk() + valore); // modificatore di atk durante la battaglia
                    break;
                case "def":
                    target.setDef(target.getDef() + valore);
                    break;
                case "specialAtk":
                    target.setSpecialAtk(target.getSpecialAtk() + valore);
                    break;
                case "specialDef":
                    target.setSpecialDef(target.getSpecialDef() + valore);
                    break;
                case "speed":
                    target.setSpeed(target.getSpeed() + valore);
                    break;
                case "accuracy":
                    target.modifyAccuracy(valore);
                case "evasion":
                    target.modifyEvasion(valore);
                default:
                    throw new IllegalArgumentException("Tipo di Effetto su avversario non riconosciuto: " + effettoSuAvversario);

            }
        }

        String effettoSuSeStesso = move.getPersonalModificatorType();
        if (effettoSuSeStesso != null && !effettoSuSeStesso.isEmpty() && !effettoSuSeStesso.isBlank()) {
            int valore = move.getPersonalModificatorValue();
            switch (effettoSuSeStesso) {
                case "atk":
                    user.setAtk(user.getAtk() + valore); // modificatore di atk durante la battaglia
                    break;
                case "def":
                    user.setDef(user.getDef() + valore);
                    break;
                case "specialAtk":
                    user.setSpecialAtk(user.getSpecialAtk() + valore);
                    break;
                case "specialDef":
                    user.setSpecialDef(user.getSpecialDef() + valore);
                    break;
                case "speed":
                    user.setSpeed(user.getSpeed() + valore);
                    break;
                case "accuracy":
                    user.modifyAccuracy(valore);
                case "evasion":
                    user.modifyEvasion(valore);

                default:
                    throw new IllegalArgumentException("Tipo di Effetto su se stesso non riconosciuto: " + effettoSuSeStesso);
            }
        }
    }

    private int calculateDamage(Pokemon user, Move move, Pokemon target) {
        int lvl = user.getLevel();
        int movePower = move.getPower();
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
        System.out.println(" --------" + movePower + " " + userAttackPower +" " + targerDefensePower);
        return (int) ((((double) (((2 * lvl / 5) + 2) * movePower * (userAttackPower / targerDefensePower)) / 50)) * modificator);
    }

    private double calculateModificator(Pokemon user, Move move, Pokemon target) {
        double typeEfficacy = TypeEffectiveness.getInstance().getEffectiveness(move.getType(), target.getBreed().getTypes());

        if (critical()){
            return 2 * RNG() * STAB(user, move) * typeEfficacy;
        }
        return RNG() * STAB(user, move) * typeEfficacy;
    }

    private boolean critical() {
        return Math.random() < 0.0625; // 6,25% di probabilità
    }

    private double RNG() {
        return 0.85 + (Math.random() * 0.15);
    }

    private double STAB(Pokemon user, Move move) {
        HashSet<Type> userTypes = user.getBreed().getTypes();
        Type moveType = move.getType();
        if (userTypes.contains(moveType)) {
            return 1.5;
        }
        return 1.0;
    }
}
