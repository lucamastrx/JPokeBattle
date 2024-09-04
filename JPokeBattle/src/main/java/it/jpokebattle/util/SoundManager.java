package it.jpokebattle.util;

import javafx.scene.media.MediaPlayer;

/**
 * Classe singleton per la gestione dell'audio del programma.
 */
public class SoundManager {
    private static SoundManager instance;    // Unica istanza della classe
    private static boolean isVolumeOn = true;
    private static AudioTrack currentTrack = AudioTrack.MENU;
    private static AudioTrack buttonPlayer = AudioTrack.BUTTON;

    private SoundManager() {
        currentTrack.playLoop();
    }

    /**
     * Se l'istanza ancora non esiste la crea, altrimenti ritorna
     * quella già esistente.
     *
     * @return      L'unica istanza della classe
     */
    public static SoundManager getInstance() {
        if (instance == null)
            instance = new SoundManager();

        return instance;
    }

    /**
     * Restituisce lo stato booleano (on/off) del volume.
     */
    public static boolean isVolumeOn() {
        return isVolumeOn;
    }


    public void switchTrack(String srcName) {
        AudioTrack newTrack = AudioTrack.fromSrcName(srcName);
        System.out.println(srcName);
        currentTrack.switchToTrack(newTrack);
        currentTrack = newTrack;
    }

    /**
     * Inverte il valore della variabile booleana associata allo
     * stato del volume.
     */
    public void toggleVolume() {
        isVolumeOn = !isVolumeOn;

        if (isVolumeOn)
            currentTrack.audioOn();
        else
            currentTrack.audioOff();
    }

    /**
     * Suono standard dei bottoni.
     */
    public void buttonClick() {
        buttonPlayer.playOnce();
    }
}
