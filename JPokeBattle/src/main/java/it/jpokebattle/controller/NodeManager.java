package it.jpokebattle.controller;


import javafx.scene.Parent;

public abstract class NodeManager {
    protected static Parent root;

    /**
     * Salva la scena principale.
     *
     * @param sceneRoot     Radice della scena
     */
    public static void setRoot(Parent sceneRoot) {
        root = sceneRoot;
    }

}
