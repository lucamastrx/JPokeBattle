package it.jpokebattle.model.battle;

import it.jpokebattle.model.player.Player;

public class Battle {

    private boolean youLost = false;
    private Player playerYou;
    private Player playerOther;

    public Battle(Player playerYou, Player playerOther) {
        this.playerYou = playerYou;
        this.playerOther = playerOther;
    }

    public void startBattle() {
        while (!youLost) {
            // cose
        }
    }


    private boolean youLost(){
        return playerYou.getTeam().allPokemonFainted();
    }

    private boolean otherPlayerLost(){
        return playerOther.getTeam().allPokemonFainted();
    }

}
