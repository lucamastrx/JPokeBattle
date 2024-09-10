package it.jpokebattle.model.battle;

import it.jpokebattle.model.player.Player;
import it.jpokebattle.model.pokemon.Pokemon;


public class SwitchPokemonAction implements Action{

    private Player actor;
    private Pokemon substitutePokemon;
    private Battle battle;

    public SwitchPokemonAction(Player actor, Pokemon substitutePokemon, Battle battle) {
        this.actor = actor;
        this.substitutePokemon = substitutePokemon;
        this.battle = battle;
    }

    @Override
    public int getPriority() {
        return 6;
    }

    @Override
    public BattleStatus[] execute() {
        // Verifica se il Pokémon da sostituire è nella squadra del giocatore
        if (!actor.getTeam().getPokemonList().contains(substitutePokemon)) {
            System.out.println("Il Pokémon selezionato non fa parte della tua squadra.");
            execute(); // Permetti di rifare l'azione
            return null;
        }

        // Verifica se il Pokémon da sostituire è esausto
        if (substitutePokemon.isFainted()) {
            System.out.println("Il Pokémon selezionato è esausto e non può essere utilizzato.");
            execute(); // Permetti di rifare l'azione
            return null;
        }

        // Verifica se il Pokémon da sostituire è già in campo
        if (actor.getTeam().getActivePokemon().equals(substitutePokemon)) {
            System.out.println("Il Pokémon selezionato è già in campo.");
            execute(); // Permetti di rifare l'azione
            return null;
        }

        // Esegui l'azione di sostituzione
        actor.getTeam().setActivePokemon(substitutePokemon);
        System.out.println("Il Pokémon è stato sostituito con successo.");
        return null;
    }
}
