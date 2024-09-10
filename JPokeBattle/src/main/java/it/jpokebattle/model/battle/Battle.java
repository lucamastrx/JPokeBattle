package it.jpokebattle.model.battle;

import it.jpokebattle.model.player.Player;


public class Battle {

    private boolean youLost = false;
    private Player playerYou;
    private Player playerOpponent;


    public Battle(Player playerYou, Player playerOpponent) {
        this.playerYou = playerYou;
        this.playerOpponent = playerOpponent;


        startBattle();
    }

    private void startBattle() {
        while (!youLost) {
            // cose
        }
        // cura tutti i tuoi pokemon
    }


    public boolean youLost(){
        return playerYou.getTeam().allPokemonFainted();
    }

    public boolean otherPlayerLost(){
        return playerOpponent.getTeam().allPokemonFainted();
    }

    public Player getPlayerYou() {return playerYou;}

    public Player getPlayerOpponent() {return playerOpponent;}
}
