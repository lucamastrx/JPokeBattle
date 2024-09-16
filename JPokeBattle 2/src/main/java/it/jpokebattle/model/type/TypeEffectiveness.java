package it.jpokebattle.model.type;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;


/**
 * Classe Singleton che gestisce l'efficacia dei tipi di Pokémon durante i combattimenti.
 *
 * Questa classe fornisce informazioni su come le mosse di un tipo specifico influenzano i Pokémon di altri tipi.
 * Utilizza una mappa per mantenere le relazioni di efficacia tra i diversi tipi di Pokémon.
 */
public class TypeEffectiveness {

    private static final TypeEffectiveness instance = new TypeEffectiveness();          // per singleton
    private final Map<Type, Map<Type, Double>> effectivenessMap = new HashMap<>();      // mappa con le efficacie


    /**
     * Costruttore privato per il Singleton.
     *
     * Inizializza la mappa dell'efficacia con i valori predefiniti per tutti i tipi di Pokémon.
     * Questo metodo popola la mappa {@code effectivenessMap} con i valori di efficacia tra i diversi tipi di Pokémon.
     */
    private TypeEffectiveness() {   // costruttore privato
        // Inizializza l'efficacia per ogni tipo

        // Normal
        Map<Type, Double> normalEffectiveness = new HashMap<>();
        normalEffectiveness.put(Type.ROCK, 0.5);
        normalEffectiveness.put(Type.GHOST, 0.0);
        normalEffectiveness.put(Type.STEEL, 0.5);
        effectivenessMap.put(Type.NORMAL, normalEffectiveness);

        // Fighting
        Map<Type, Double> fightingEffectiveness = new HashMap<>();
        fightingEffectiveness.put(Type.NORMAL, 2.0);
        fightingEffectiveness.put(Type.FLYING, 0.5);
        fightingEffectiveness.put(Type.POISON, 0.5);
        fightingEffectiveness.put(Type.ROCK, 2.0);
        fightingEffectiveness.put(Type.BUG, 0.5);
        fightingEffectiveness.put(Type.GHOST, 0.0);
        fightingEffectiveness.put(Type.PSYCHIC, 0.5);
        fightingEffectiveness.put(Type.ICE, 2.0);
        fightingEffectiveness.put(Type.STEEL, 2.0);
        fightingEffectiveness.put(Type.DARK, 2.0);
        effectivenessMap.put(Type.FIGHTING, fightingEffectiveness);

        // Flying
        Map<Type, Double> flyingEffectiveness = new HashMap<>();
        flyingEffectiveness.put(Type.FIGHTING, 2.0);
        flyingEffectiveness.put(Type.ROCK, 0.5);
        flyingEffectiveness.put(Type.BUG, 2.0);
        flyingEffectiveness.put(Type.GRASS, 2.0);
        flyingEffectiveness.put(Type.ELECTRIC, 0.5);
        flyingEffectiveness.put(Type.STEEL, 0.5);
        effectivenessMap.put(Type.FLYING, flyingEffectiveness);

        // Poison
        Map<Type, Double> poisonEffectiveness = new HashMap<>();
        poisonEffectiveness.put(Type.POISON, 0.5);
        poisonEffectiveness.put(Type.GROUND, 0.5);
        poisonEffectiveness.put(Type.ROCK, 0.5);
        poisonEffectiveness.put(Type.GHOST, 0.5);
        poisonEffectiveness.put(Type.GRASS, 2.0);
        poisonEffectiveness.put(Type.STEEL, 0.0);
        effectivenessMap.put(Type.POISON, poisonEffectiveness);

        // Ground
        Map<Type, Double> groundEffectiveness = new HashMap<>();
        groundEffectiveness.put(Type.FLYING, 0.0);
        groundEffectiveness.put(Type.POISON, 2.0);
        groundEffectiveness.put(Type.ROCK, 2.0);
        groundEffectiveness.put(Type.BUG, 0.5);
        groundEffectiveness.put(Type.FIRE, 2.0);
        groundEffectiveness.put(Type.GRASS, 0.5);
        groundEffectiveness.put(Type.ELECTRIC, 2.0);
        groundEffectiveness.put(Type.STEEL, 2.0);
        effectivenessMap.put(Type.GROUND, groundEffectiveness);

        // Rock
        Map<Type, Double> rockEffectiveness = new HashMap<>();
        rockEffectiveness.put(Type.FIGHTING, 0.5);
        rockEffectiveness.put(Type.FLYING, 2.0);
        rockEffectiveness.put(Type.GROUND, 0.5);
        rockEffectiveness.put(Type.BUG, 2.0);
        rockEffectiveness.put(Type.FIRE, 2.0);
        rockEffectiveness.put(Type.ICE, 2.0);
        rockEffectiveness.put(Type.STEEL, 0.5);
        effectivenessMap.put(Type.ROCK, rockEffectiveness);

        // Bug
        Map<Type, Double> bugEffectiveness = new HashMap<>();
        bugEffectiveness.put(Type.FIGHTING, 0.5);
        bugEffectiveness.put(Type.FLYING, 0.5);
        bugEffectiveness.put(Type.POISON, 0.5);
        bugEffectiveness.put(Type.GHOST, 0.5);
        bugEffectiveness.put(Type.FIRE, 0.5);
        bugEffectiveness.put(Type.GRASS, 2.0);
        bugEffectiveness.put(Type.PSYCHIC, 2.0);
        bugEffectiveness.put(Type.STEEL, 0.5);
        bugEffectiveness.put(Type.DARK, 2.0);
        effectivenessMap.put(Type.BUG, bugEffectiveness);

        // Ghost
        Map<Type, Double> ghostEffectiveness = new HashMap<>();
        ghostEffectiveness.put(Type.NORMAL, 0.0);
        ghostEffectiveness.put(Type.GHOST, 2.0);
        ghostEffectiveness.put(Type.PSYCHIC, 2.0);
        ghostEffectiveness.put(Type.STEEL, 0.5);
        ghostEffectiveness.put(Type.DARK, 0.5);
        effectivenessMap.put(Type.GHOST, ghostEffectiveness);

        // Fire
        Map<Type, Double> fireEffectiveness = new HashMap<>();
        fireEffectiveness.put(Type.ROCK, 0.5);
        fireEffectiveness.put(Type.BUG, 2.0);
        fireEffectiveness.put(Type.FIRE, 0.5);
        fireEffectiveness.put(Type.WATER, 0.5);
        fireEffectiveness.put(Type.GRASS, 2.0);
        fireEffectiveness.put(Type.ICE, 2.0);
        fireEffectiveness.put(Type.DRAGON, 0.5);
        fireEffectiveness.put(Type.STEEL, 2.0);
        effectivenessMap.put(Type.FIRE, fireEffectiveness);

        // Water
        Map<Type, Double> waterEffectiveness = new HashMap<>();
        waterEffectiveness.put(Type.GROUND, 2.0);
        waterEffectiveness.put(Type.ROCK, 2.0);
        waterEffectiveness.put(Type.FIRE, 2.0);
        waterEffectiveness.put(Type.WATER, 0.5);
        waterEffectiveness.put(Type.GRASS, 0.5);
        waterEffectiveness.put(Type.DRAGON, 0.5);
        effectivenessMap.put(Type.WATER, waterEffectiveness);

        // Grass
        Map<Type, Double> grassEffectiveness = new HashMap<>();
        grassEffectiveness.put(Type.FLYING, 0.5);
        grassEffectiveness.put(Type.POISON, 0.5);
        grassEffectiveness.put(Type.GROUND, 2.0);
        grassEffectiveness.put(Type.ROCK, 2.0);
        grassEffectiveness.put(Type.BUG, 0.5);
        grassEffectiveness.put(Type.FIRE, 0.5);
        grassEffectiveness.put(Type.WATER, 2.0);
        grassEffectiveness.put(Type.GRASS, 0.5);
        grassEffectiveness.put(Type.DRAGON, 0.5);
        grassEffectiveness.put(Type.STEEL, 0.5);
        effectivenessMap.put(Type.GRASS, grassEffectiveness);

        // Electric
        Map<Type, Double> electricEffectiveness = new HashMap<>();
        electricEffectiveness.put(Type.FLYING, 2.0);
        electricEffectiveness.put(Type.GROUND, 0.0);
        electricEffectiveness.put(Type.WATER, 2.0);
        electricEffectiveness.put(Type.GRASS, 0.5);
        electricEffectiveness.put(Type.ELECTRIC, 0.5);
        electricEffectiveness.put(Type.DRAGON, 0.5);
        effectivenessMap.put(Type.ELECTRIC, electricEffectiveness);

        // Psychic
        Map<Type, Double> psychicEffectiveness = new HashMap<>();
        psychicEffectiveness.put(Type.FIGHTING, 2.0);
        psychicEffectiveness.put(Type.POISON, 2.0);
        psychicEffectiveness.put(Type.PSYCHIC, 0.5);
        psychicEffectiveness.put(Type.STEEL, 0.5);
        psychicEffectiveness.put(Type.DARK, 0.0);
        effectivenessMap.put(Type.PSYCHIC, psychicEffectiveness);

        // Ice
        Map<Type, Double> iceEffectiveness = new HashMap<>();
        iceEffectiveness.put(Type.FLYING, 2.0);
        iceEffectiveness.put(Type.GROUND, 2.0);
        iceEffectiveness.put(Type.FIRE, 0.5);
        iceEffectiveness.put(Type.WATER, 0.5);
        iceEffectiveness.put(Type.GRASS, 2.0);
        iceEffectiveness.put(Type.ICE, 0.5);
        iceEffectiveness.put(Type.DRAGON, 2.0);
        iceEffectiveness.put(Type.STEEL, 0.5);
        effectivenessMap.put(Type.ICE, iceEffectiveness);

        // Dragon
        Map<Type, Double> dragonEffectiveness = new HashMap<>();
        dragonEffectiveness.put(Type.DRAGON, 2.0);
        dragonEffectiveness.put(Type.STEEL, 0.5);
        effectivenessMap.put(Type.DRAGON, dragonEffectiveness);

        // Steel
        Map<Type, Double> steelEffectiveness = new HashMap<>();
        steelEffectiveness.put(Type.ROCK, 2.0);
        steelEffectiveness.put(Type.FIRE, 0.5);
        steelEffectiveness.put(Type.WATER, 0.5);
        steelEffectiveness.put(Type.ELECTRIC, 0.5);
        steelEffectiveness.put(Type.ICE, 2.0);
        steelEffectiveness.put(Type.STEEL, 0.5);
        effectivenessMap.put(Type.STEEL, steelEffectiveness);

        // Dark
        Map<Type, Double> darkEffectiveness = new HashMap<>();
        darkEffectiveness.put(Type.FIGHTING, 0.5);
        darkEffectiveness.put(Type.GHOST, 2.0);
        darkEffectiveness.put(Type.PSYCHIC, 2.0);
        darkEffectiveness.put(Type.STEEL, 0.5);
        darkEffectiveness.put(Type.DARK, 0.5);
        effectivenessMap.put(Type.DARK, darkEffectiveness);

        // Fairy (se applicabile nel gioco, altrimenti ignora)
        // Map<Type, Double> fairyEffectiveness = new HashMap<>();
        // fairyEffectiveness.put(Type.FIGHTING, 2.0);
        // fairyEffectiveness.put(Type.POISON, 0.5);
        // fairyEffectiveness.put(Type.STEEL, 0.5);
        // fairyEffectiveness.put(Type.FIRE, 0.5);
        // fairyEffectiveness.put(Type.DRAGON, 2.0);
        // fairyEffectiveness.put(Type.DARK, 2.0);
        // fairyEffectiveness.put(Type.BUG, 2.0);
        // effectivenessMap.put(Type.FAIRY, fairyEffectiveness);
    }

    /**
     * Ottiene l'istanza unica della classe {@code TypeEffectiveness}.
     *
     * @return L'istanza unica della classe {@code TypeEffectiveness}.
     */
    public static TypeEffectiveness getInstance() {         // getInstance leggermente diverso perché la classe ha final
        return instance;
    }

    /**
     * Calcola l'efficacia totale di una mossa di un certo tipo su un Pokémon con più tipi.
     *
     * Questo metodo considera tutti i tipi del Pokémon difensore e calcola il moltiplicatore totale dell'efficacia.
     *
     * @param moveAttacker Il tipo della mossa del Pokémon attaccante.
     * @param defenderTypes Il set dei tipi del Pokémon difensore.
     * @return Il valore del moltiplicatore di danni.
     */
    public double getEffectiveness(Type moveAttacker, Set<Type> defenderTypes) {
        double totalEffectiveness = 1.0;        // valore base dell'efficacia
        for (Type defenderType : defenderTypes) {
            totalEffectiveness *= getEffectiveness(moveAttacker, defenderType);   // moltiplica l'efficacia per ogni tipo del difensore
        }
        return totalEffectiveness;
    }

    /**
     * Ottiene il valore di efficacia di una mossa di un tipo su un Pokémon di un altro tipo.
     *
     * @param attacker Il tipo della mossa del Pokémon attaccante.
     * @param defender Il tipo del Pokémon difensore.
     * @return Il valore del moltiplicatore di danni.
     */
    private double getEffectiveness(Type attacker, Type defender) {
        return effectivenessMap.getOrDefault(attacker, new HashMap<>()).getOrDefault(defender, 1.0);
    }
}
