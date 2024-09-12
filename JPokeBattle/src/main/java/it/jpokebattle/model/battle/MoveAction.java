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
    public List<BattleStatus> execute() throws MoveSetException {
        boolean canLevelUpFlag = false;
        Pokemon user = actor.getTeam().getActivePokemon();
        Pokemon target = playerOpponent.getTeam().getActivePokemon();
        List<BattleStatus> status = new ArrayList<>();
        // Utilizzatore usa 1 pp della mossa
        user.useMove(move);

        if (isHit(user, move, target)) {
            System.out.println("è hit");
            if (user.getMoves().get(move) > -1) {
                int damage = calculateDamage(user, move, target);
                if (damage < 0) damage = 0;
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
                    ownedPokemon.collectExp(target.getStrength()+500000);  // Guadagna esperienza
                    ownedPokemon.collectEVs(target.dropEVs());      // Guadagna EVs
                    if (ownedPokemon.canLevelUp()) {
                        status.add(BattleStatus.YES_LEVEL_UP);
                        ownedPokemon.levelUpAndStatsUp();   // Metodo che fa salire di livello e aumenta le stats
                        status.add(ownedPokemon.learnMove()); // Metodo che insegna una nuova mossa se possibile e se voluto
                        status.add(ownedPokemon.evolve()); // Metodo che fa evolvere se possibile
                    } else {
                        status.add(BattleStatus.NO_LEVEL_UP);
                    }
                }
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
            if (playerOpponent instanceof BotPlayer) {
                status.add(BattleStatus.CONTINUE_DAMAGE_TO_BOT_POKEMON);
            } else {
                status.add(BattleStatus.CONTINUE_DAMAGE_TO_YOUR_POKEMON);
            }
            return status;
        }
        status.add(BattleStatus.CONTINUE_WITH_NO_DAMAGE);
        return status;
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
            System.out.println("-------------------" + effettoSuAvversario + "----------");
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
                    break;
                case "evasion":
                    target.modifyEvasion(valore);
                    break;
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
                    break;
                case "evasion":
                    user.modifyEvasion(valore);
                    break;
                default:
                    throw new IllegalArgumentException("Tipo di Effetto su se stesso non riconosciuto: " + effettoSuSeStesso);
            }
        }
    }

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
