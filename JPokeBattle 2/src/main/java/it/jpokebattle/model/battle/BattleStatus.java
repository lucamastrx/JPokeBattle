package it.jpokebattle.model.battle;

/**
 * Enumerazione che rappresenta i possibili stati della battaglia in seguito
 * all'esecuzione di un'azione durante il combattimento.
 * Ogni valore dell'enum indica un cambiamento di stato che può verificarsi
 * durante la battaglia tra due giocatori o tra un giocatore e un bot.
 */
public enum BattleStatus {

    /**
     * Il giocatore deve continuare la battaglia cambiando il proprio Pokémon attivo.
     */
    CONTINUE_WITH_YOUR_CHANGE_POKEMON,

    /**
     * Il bot deve continuare la battaglia cambiando il proprio Pokémon attivo.
     */
    CONTINUE_WITH_BOT_CHANGE_POKEMON,

    /**
     * Nessun danno è stato inflitto nell'ultimo turno.
     */
    CONTINUE_WITH_NO_DAMAGE,

    /**
     * Danno inflitto al Pokémon del bot, la battaglia continua.
     */
    CONTINUE_DAMAGE_TO_BOT_POKEMON,

    /**
     * Danno inflitto al Pokémon del giocatore, la battaglia continua.
     */
    CONTINUE_DAMAGE_TO_YOUR_POKEMON,

    /**
     * Il Pokémon del giocatore è esausto e deve essere sostituito con un altro.
     */
    CHANGE_YOUR_FAINTED_POKEMON,

    /**
     * Il Pokémon del bot è esausto e deve essere sostituito con un altro.
     */
    CHANGE_BOT_FAINTED_POKEMON,

    /**
     * Il giocatore ha perso la battaglia.
     */
    YOU_LOST,

    /**
     * Il giocatore ha vinto la battaglia.
     */
    YOU_WIN,

    /**
     * Il Pokémon non può salire di livello dopo la battaglia.
     */
    NO_LEVEL_UP,

    /**
     * Il Pokémon è salito di livello dopo la battaglia.
     */
    YES_LEVEL_UP,

    /**
     * Il Pokémon può evolversi in seguito al livello raggiunto.
     */
    EVOLVE,

    /**
     * Il Pokémon può imparare una nuova mossa.
     */
    LEARN_MOVE,

    /**
     * Il Pokémon non può evolversi nonostante abbia raggiunto il livello richiesto.
     */
    NO_EVOLVE,

    /**
     * Il Pokémon non può imparare una nuova mossa.
     */
    NO_LEARN_MOVE,

    /**
     * Il giocatore può scegliere di cambiare una mossa del Pokémon attivo.
     */
    CHANGE_MOVE;

}
