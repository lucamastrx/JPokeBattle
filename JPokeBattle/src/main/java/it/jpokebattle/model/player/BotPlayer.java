package it.jpokebattle.model.player;

import it.jpokebattle.model.pokemon.Move;
import it.jpokebattle.model.pokemon.Pokemon;
import it.jpokebattle.model.type.Type;
import it.jpokebattle.model.type.TypeEffectiveness;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.ArrayList;


public class BotPlayer extends Player<Pokemon> {
    public BotPlayer(String name, Pokemon activePokemon) {
        super(name, activePokemon);
    }

    public void smartPokemonChange() {
        // Ottieni i tipi del Pokémon attivo dell'avversario umano
        HashSet<Type> opponentTypes = HumanPlayer.getInstance().getTeam().getActivePokemon().getBreed().getTypes();

        // Variabili per tenere traccia del Pokémon con la massima efficacia e del livello più alto in caso di parità
        Pokemon bestPokemon = null;
        double bestEffectiveness = 0;
        int highestLevel = 0;

        // Itera su tutti i Pokémon disponibili del bot
        for (Pokemon pokemon : getTeam().getAvailablePokemon()) {
            double totalEffectiveness = 0;

            // Ottieni le mosse del Pokémon
            List<Move> moves = new ArrayList<>(pokemon.getMoves().keySet());

            // Calcola l'efficacia totale delle mosse contro i tipi del Pokémon avversario
            for (Move move : moves) {
                double effectiveness = TypeEffectiveness.getInstance().getEffectiveness(move.getType(), opponentTypes);
                totalEffectiveness += effectiveness;
            }

            // Se l'efficacia totale è maggiore o in caso di parità di efficacia, scegli il Pokémon con il livello più alto
            if (bestPokemon == null || totalEffectiveness > bestEffectiveness ||
                    (totalEffectiveness == bestEffectiveness && pokemon.getLevel() > highestLevel)) {

                bestPokemon = pokemon;
                bestEffectiveness = totalEffectiveness;
                highestLevel = pokemon.getLevel();
            }
        }
        // Cambia il Pokémon attivo del bot con il Pokémon selezionato se trovato
        if (bestPokemon != null) {
            getTeam().setActivePokemon(bestPokemon);
        }
    }

    public Move smartPokemonMove(){
        // Ottieni i tipi del Pokémon avversario umano
        HashSet<Type> opponentTypes = HumanPlayer.getInstance().getTeam().getActivePokemon().getBreed().getTypes();

        // Ottieni le mosse del Pokémon attivo del bot
        List<Move> moves = new ArrayList<>(getTeam().getActivePokemon().getMoves().keySet());

        // Variabili per tenere traccia della mossa migliore
        Move bestMove = null;
        double bestEffectiveness = 0;
        int highestPower = 0;

        // Itera su tutte le mosse del Pokémon attivo del bot
        for (Move move : moves) {
            // Calcola l'efficacia della mossa contro i tipi del Pokémon avversario
            double effectiveness = TypeEffectiveness.getInstance().getEffectiveness(move.getType(), opponentTypes);

            // Se l'efficacia è maggiore o in caso di parità di efficacia, scegli la mossa con il danno più alto
            if (bestMove == null || effectiveness > bestEffectiveness ||
                    (effectiveness == bestEffectiveness && move.getPower() > highestPower)) {

                bestMove = move;
                bestEffectiveness = effectiveness;
                highestPower = move.getPower();
            }
        }

        // Ritorna la mossa migliore trovata
        if (bestMove != null) {
            System.out.println("Il Bot ha scelto la mossa: " + bestMove.getName());
        } else {
            System.out.println("Nessuna mossa disponibile. Potrebbe esserci un errore.");
        }

        return bestMove;
    }
}
