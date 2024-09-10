package it.jpokebattle.model.battle;

import it.jpokebattle.model.player.Player;


public class Battle {

    private boolean youLost = false;
    private Player humanPlayer;
    private static Player currentBotPlayer;


    public Battle(Player humanPlayer, Player currentBotPlayer) {
        this.humanPlayer = humanPlayer;
        Battle.currentBotPlayer = currentBotPlayer;


        startBattle();
    }

    private void startBattle() {
        while (!youLost) {
            // cose
        }
        // cura tutti i tuoi pokemon
    }


    public boolean youLost(){
        return humanPlayer.getTeam().allPokemonFainted();
    }

    public boolean botLost(){
        return currentBotPlayer.getTeam().allPokemonFainted();
    }

    public Player getHumanPlayer() {return humanPlayer;}

    public Player getCurrentBotPlayer() {return currentBotPlayer;}
}
