package it.jpokebattle.model;


import javafx.scene.image.Image;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;


/**
 * I profili consentono all'utente di interfacciarsi con il gioco. Grazie alle istanze di
 * questa classe si può memorizzare sempre lo stato corrente del gioco, sia per il pvp che
 * per l'arcade.
 */
public class HumanPlayer  {

    private String name;
    private ArrayList<String> pokemonTeam;
    private ArrayList<String> pokemonStorage;


    public HumanPlayer(String name, Image avatar) {
        this.name = name;
    }



    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }




}
