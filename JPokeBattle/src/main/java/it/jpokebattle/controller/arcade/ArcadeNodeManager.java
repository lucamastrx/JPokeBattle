package it.jpokebattle.controller.arcade;

import it.jpokebattle.controller.NodeManager;
import it.jpokebattle.controller.menu.MenuController;
import it.jpokebattle.model.Profile;
import javafx.application.Platform;

/**
 * Classe singleton per la gestione delle componenti nella scena dell'arcade. Lavora a
 * stretto contatto con la classe ArcadeController per l'accensione, lo spegnimento e
 * la modifica dei nodi (componenti).
 *
 * @see ArcadeController
 */
public class ArcadeNodeManager extends NodeManager {
    private static ArcadeNodeManager istance;
    private static ArcadeController ctrl;
    private static final String SEPARATOR = "\n———————————————————————————————\n\n";

    private ArcadeNodeManager() { }

    /**
     * Se l'istanza ancora non esiste la crea, altrimenti ritorna quella già esistente.
     *
     * @return L'unica istanza del node manager
     */
    public static ArcadeNodeManager getIstance() {
        if (istance == null)
            istance = new ArcadeNodeManager();

        return istance;
    }

    /**
     * Salva il controller passato in input come attributo, in modo da poterlo usare
     * successivamente per ricavare le variabili delle componenti.
     *
     * @param controller ArcadeController istanziato dal menù controller
     */
    public static void setController(ArcadeController controller) {
        ctrl = controller;
    }

    /**
     * Imposta il testo del narratore al testo passato in input. Sposta la scrollbar
     * in basso.
     *
     * @param narratorText Testo con cui settare la narratorLbl
     */
    public void setNarratorLbl(String narratorText) {
        ctrl.narratorLbl.setText(narratorText);
        // Imposta la barra di scorrimento dopo che la GUI è pronta
        updateNarratorScrollbarPosition();
    }

    /**
     * Aggiorna il testo del narratore con il testo passato in input. Aggiunge il nuovo
     * testo in fondo e sposta la scrollbar sul fondo.
     *
     * @param newNarratorText Testo con cui aggiornare la narratorLbl
     */
    public void updateNarratorLbl(String newNarratorText) {
        String text = ctrl.narratorLbl.getText() + SEPARATOR + newNarratorText + SEPARATOR;
        ctrl.narratorLbl.setText(text);
    }

    public void updateNarratorScrollbarPosition() {
        new Thread(() -> {
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            ctrl.narratorScrlPane.setVvalue(1.0);
        }).start();
    }




    /**
     * Mostra il pannelo delle impostazioni.
     */
    public void showSettingsPane() {
        ctrl.gameSettingsPane.setVisible(true);
    }

    /**
     * TODO:DA IMPLEMENTARE
     */
    public void showOwnedPokemon() {
        ctrl.pokemonPane.setVisible(true);
    }

    /**
     * TODO:DA IMPLEMENTARE
     */
    public void showInvenctory() {
        ctrl.invenctoryPane.setVisible(true);
    }


    /**
     * Nasconde tutti i panneli al di fuori dal principale
     */
    public void hideAllPane() {
        ctrl.gameSettingsPane.setVisible(false);
        ctrl.pokemonPane.setVisible(false);
        ctrl.invenctoryPane.setVisible(false);
    }

}
