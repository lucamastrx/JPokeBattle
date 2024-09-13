package it.jpokebattle.model.battle;

import it.jpokebattle.model.player.Player;
import it.jpokebattle.model.pokemon.Pokemon;

import java.util.List;

/**
 * Classe che rappresenta l'azione di sostituzione di un Pokémon in una battaglia.
 * Questa azione consente a un giocatore di sostituire il proprio Pokémon attivo
 * con un altro della propria squadra.
 */
public class SwitchPokemonAction implements Action{

    private Player actor;
    private Pokemon substitutePokemon;
    private Battle battle;

    /**
     * Costruttore della classe {@code SwitchPokemonAction}.
     *
     * @param actor Il giocatore che effettua la sostituzione del Pokémon.
     * @param substitutePokemon Il Pokémon da mandare in campo come sostituto.
     * @param battle La battaglia in cui avviene la sostituzione.
     */
    public SwitchPokemonAction(Player actor, Pokemon substitutePokemon, Battle battle) {
        this.actor = actor;
        this.substitutePokemon = substitutePokemon;
        this.battle = battle;
    }

    /**
     * Restituisce la priorità dell'azione di sostituzione. In generale, la sostituzione ha
     * una priorità fissa di 6.
     *
     * @return 6, che rappresenta la priorità della sostituzione.
     */
    @Override
    public int getPriority() {
        return 6;
    }


    /**
     * Esegue l'azione di sostituzione del Pokémon. Verifica se il Pokémon selezionato può
     * essere sostituito, se è nella squadra del giocatore, se è esausto o se è già in campo.
     * Se tutte le condizioni sono soddisfatte, il Pokémon viene sostituito.
     *
     * @return null, in quanto questa azione non produce direttamente uno stato di battaglia.
     */
    @Override
    public List<BattleStatus> execute() {
        // Verifica se il Pokémon da sostituire è nella squadra del giocatore
        if (!actor.getTeam().getPokemonList().contains(substitutePokemon)) {
            System.out.println("Il Pokémon selezionato non fa parte della tua squadra.");
            return null;
        }

        // Verifica se il Pokémon da sostituire è esausto
        if (substitutePokemon.isFainted()) {
            System.out.println("Il Pokémon selezionato è esausto e non può essere utilizzato.");
            return null;
        }

        // Verifica se il Pokémon da sostituire è già in campo
        if (actor.getTeam().getActivePokemon().equals(substitutePokemon)) {
            System.out.println("Il Pokémon selezionato è già in campo.");
            return null;
        }

        // Esegui l'azione di sostituzione
        actor.getTeam().setActivePokemon(substitutePokemon);
        System.out.println("Il Pokémon è stato sostituito con successo.");
        return null;
    }
}
