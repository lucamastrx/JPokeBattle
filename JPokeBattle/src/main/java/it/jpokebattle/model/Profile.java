package it.jpokebattle.model;

import it.jpokebattle.model.area.Area;
import it.jpokebattle.util.FileManager;
import javafx.scene.image.Image;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;


/**
 * I profili consentono all'utente di interfacciarsi con il gioco. Grazie alle istanze di
 * questa classe si può memorizzare sempre lo stato corrente del gioco, sia per il pvp che
 * per l'arcade.
 */
public class Profile implements Serializable, Identifiable, Saveable<Profile> {
    private final String SER_SRC_NAME = "ser.prof";
    private final String ID = setID(SER_SRC_NAME);
    private String name;
    private String avatarSrcName;
    private String currentArea;
    private String narratorTextHistory;
    private HashMap<String, Integer> itemsMap;
    private ArrayList<String> pokemonTeam;
    private ArrayList<String> pokemonStorage;


    public Profile(String name, Image avatar) {
        this.name = name;
        this.currentArea = Area.START.getName();
        this.narratorTextHistory = Area.START.getDescription();
        setItemsMap();
        setAvatarSrcName(avatar.getUrl());
        save();
    }


    public String getAvatarSrcName() {
        return avatarSrcName;
    }

    public Area getCurrentArea() {
        return Area.fromName(currentArea);
    }

    @Override
    public String getID() {
        return this.ID;
    }

    public String getName() {
        return name;
    }

    public String getNarratorTextHistory() {
        return narratorTextHistory;
    }

    public int getItemCounter(String itemName) {
        return itemsMap.getOrDefault(itemName, 0);
    }

    public void setAvatarSrcName(String avatarPath) {
        String[] path = avatarPath.split("jpokebattle/images/avatar/");
        this.avatarSrcName = path[1];
    }

    public void setCurrentArea(Area area) {
        this.currentArea = area.getName();
    }

    private void setItemsMap() {
        this.itemsMap.put("potion", 5);
        this.itemsMap.put("pokeball", 5);
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setNarratorTextHistory(String narratorTextHistory) {
        this.narratorTextHistory = narratorTextHistory;
        save();
    }

    public void addToItemCounter(String itemName, int valueToAdd) {
        int total = getItemCounter(itemName) + valueToAdd;

        if (total < 0 || total > 99) {  // TODO DA GESTIRE IN LOGICA
            System.out.println("\nVALORE NON VALIDO, SUPERIORE A 100 O INFERIORE A 0: " + total);
        } else {
            itemsMap.replace(itemName, total);
        }
    }

    /**
     * Viene impostata l'area corrente al nome dell'area successiva.
     *
     * @see Area
     */
    public void goToNextArea() {
        setCurrentArea(Area.fromName(this.currentArea).getNextArea());
    }

    /**
     * Viene impostata l'area corrente al nome dell'area successiva.
     *
     * @see Area
     */
    public void goToPrevArea() {
        setCurrentArea(Area.fromName(this.currentArea).getPrevArea());
    }

    /**
     * Viene impostata l'area corrente al nome dell'area successiva.
     *
     * @see Area
     */
    public void goToSpecialArea() {
        setCurrentArea(Area.fromName(this.currentArea).getSpecialArea());
    }


    /**
     * Imposta il nome e il percorso dell'avatar, dopodiché aggiorna il file.
     *
     * @param name       Nome del profilo
     * @param avatarPath Percorso all'immagine dell'avatar.
     */
    public void updateProfileInfo(String name, String avatarPath) {
        setName(name);
        setAvatarSrcName(avatarPath);
        save();
    }

    public void save() {
        save(SER_SRC_NAME, this);
    }

}



/*
    private HashMap<String, Boolean> visitedAreas;


    private void setVisitedAreas() {
        for(Area area : Area.values()) {
            visitedAreas.put(area.getName(), false);
        }
    }

    public void setVisited(Area area) {
        if (!isVisited(area)) {
            visitedAreas.replace(area.getName(), true);
            save();
        }
    }

    public boolean isVisited(Area area) {
        return visitedAreas.get(area.getName());
    }
*/