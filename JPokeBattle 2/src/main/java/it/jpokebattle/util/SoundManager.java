package it.jpokebattle.util;


/**
 * Classe singleton per la gestione dell'audio del programma.
 * Questa classe gestisce la riproduzione di tracce audio e il controllo del volume attraverso un'unica istanza.
 * Fornisce metodi per la riproduzione di tracce audio specifiche, il controllo del volume e l'interazione con suoni di interfaccia.
 */
public class SoundManager {
    private static SoundManager instance;    // Unica istanza della classe
    private static boolean isVolumeOn = true;       // Stato del volume (on/off)
    private static AudioTrack currentTrack = AudioTrack.MENU;    // Traccia audio corrente
    private static AudioTrack buttonPlayer = AudioTrack.BUTTON;    // Suono per i clic sui bottoni
    private static AudioTrack cureTeam = AudioTrack.CURETEAM;    // Suono per la guarigione della squadra
    private static AudioTrack gameOver = AudioTrack.GAMEOVER;    // Suono per la fine del gioco


    /**
     * Costruttore privato per impedire la creazione di più istanze della classe.
     * Inizializza e avvia la riproduzione in loop della traccia audio del menu.
     */
    private SoundManager() {
        currentTrack.playLoop();
    }

    /**
     * Restituisce l'unica istanza della classe {@code SoundManager}.
     * Se l'istanza non esiste, viene creata una nuova istanza.
     *
     * @return L'unica istanza della classe {@code SoundManager}.
     */
    public static SoundManager getInstance() {
        if (instance == null)
            instance = new SoundManager();

        return instance;
    }

    /**
     * Restituisce lo stato del volume (attivo o disattivo).
     *
     * @return {@code true} se il volume è attivo, {@code false} altrimenti.
     */
    public static boolean isVolumeOn() {
        return isVolumeOn;
    }


    /**
     * Cambia la traccia audio corrente con una nuova traccia specificata dal nome della risorsa audio.
     *
     * @param srcName Il nome della risorsa audio della nuova traccia.
     */
    public void switchTrack(String srcName) {
        AudioTrack newTrack = AudioTrack.fromSrcName(srcName);
        System.out.println("Traccia Audio: " + srcName);
        currentTrack.switchToTrack(newTrack);
        currentTrack = newTrack;
    }

    /**
     * Inverte il valore della variabile booleana associata allo stato del volume.
     * Se il volume era attivo, viene disattivato e viceversa.
     */
    public void toggleVolume() {
        isVolumeOn = !isVolumeOn;

        if (isVolumeOn)
            currentTrack.audioOn();
        else
            currentTrack.audioOff();
    }

    /**
     * Riproduce il suono associato ai clic sui bottoni.
     */
    public void buttonClick() {
        buttonPlayer.playOnce();
    }

    /**
     * Riproduce il suono associato alla guarigione della squadra.
     * Disattiva il volume prima di riprodurre il suono e lo riattiva dopo un breve intervallo.
     *
     * @throws InterruptedException Se l'interruzione del thread avviene durante il ritardo.
     */
    public void cureTeamClick() throws InterruptedException {
        toggleVolume();
        cureTeam.playOnce();
        Thread.sleep(2000);
        toggleVolume();
    }
}
