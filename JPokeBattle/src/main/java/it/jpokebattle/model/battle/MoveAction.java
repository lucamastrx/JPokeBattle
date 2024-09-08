package it.jpokebattle.model.battle;

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

    public MoveAction(Player actor, Move move, Player playerOpponent) {
        this.actor = actor;
        this.move = move;
        this.playerOpponent = playerOpponent;
    }

    @Override
    public int getPriority() {return move.getPriority();}

    @Override
    public void execute(Battle battle) {
        Pokemon user = actor.getTeam().getActivePokemon();
        Pokemon target = playerOpponent.getTeam().getActivePokemon();

        // Utilizzatore usa 1 pp della mossa
        user.useMove(move);


        if (user.getMoves().get(move) > -1) {
            int damage = calculateDamage(user, move, target);
            target.takeDamage(damage);
            System.out.println(user.getName() + " usa " + move.getName() + " su " + target.getName() + " causando " + damage + " danni.");
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
                ownedPokemon.levelUpAndStatsUp();               // Metodo che fa salire di livello se possibile, e aumenta le stats
                ownedPokemon.learnMove();                       // Metodo che insegna una nuova mossa se possibile e se voluto
                ownedPokemon.evolve();                          // Metodo che fa evolvere se possibile
            }
        }
        if (battle.youLost()) {
            // TODO
        } else if (battle.otherPlayerLost()) {
            // TODO
        }
    }

    private boolean isMissed() {
        return false; // TODO
    }

    private int calculateDamage(Pokemon user, Move move, Pokemon target) {
        int lvl = user.getLevel();
        int movePower = move.getPower();
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
        return (int) ((((double) (((2 * lvl / 5) + 2) * movePower * (userAttackPower / targerDefensePower)) / 50) + 2) * modificator);
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
