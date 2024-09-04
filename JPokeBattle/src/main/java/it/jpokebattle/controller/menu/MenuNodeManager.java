package it.jpokebattle.controller.menu;

import it.jpokebattle.controller.NodeManager;


/**
 * Classe singleton per la gestione delle componenti nella scena del menù principale.
 * Lavora a stretto contatto con la classe ArcadeController per l'accensione, lo
 * spegnimento e la modifica dei nodi (componenti).
 *
 * @see MenuController
 */
public class MenuNodeManager extends NodeManager {
    private static MenuNodeManager instance;
    private static MenuController ctrl;

    private MenuNodeManager() { }

    /**
     * Se l'istanza ancora non esiste la crea, altrimenti ritorna quella già esistente.
     *
     * @return L'unica istanza del node manager
     */
    public static MenuNodeManager getInstance() {
        if (instance == null)
            instance = new MenuNodeManager();

        return instance;
    }

    /**
     * Salva il controller passato in input come attributo, in modo da poterlo usare
     * successivamente per ricavare le variabili delle componenti.
     *
     * @param controller MenuController istanziato nel main
     */
    public static void setController(MenuController controller) {
        ctrl = controller;
    }



    /**
     * Nasconde tutte le componenti della scena e mostra solo quelle del menù principale.
     *
     * @see MenuController
     */
    public void switchToMainMenu(){
        hideAll();
        lengthensSeparators();
        ctrl.selectGameProfileBtn.setVisible(true);
        ctrl.exitBtn.setVisible(true);
        ctrl.mainMenuLbl.setVisible(true);
        ctrl.gengarGif.setVisible(true);
        ctrl.snorlaxGif.setVisible(true);
    }


    /**
     * Nasconde tutte le componenti della scena e mostra solo quelle del menù principale
     * dei profili (dove vengono visualizzati i profili per aggiunta e modifica).
     *
     * @see MenuController
     */
    public void switchToProfilesMenu(){
        hideAll();
        shortensSeparators();
        ctrl.backToMainBtn3.setVisible(true);


        ctrl.gengarGif.setVisible(true);
        ctrl.snorlaxGif.setVisible(true);
        ctrl.nicknameProfCardLbl.textProperty().unbind();
        ctrl.nicknameProfCardLbl.setText("");
        ctrl.nicknameTxtFd.clear();
    }


    /**
     * Allunga i separatori ai lati del menù.
     */
    private void lengthensSeparators() {
        ctrl.leftSeparatorLn.setEndX(280);
        ctrl.rightSeparatorLn.setEndX(280);
    }

    /**
     * Accorcia i separatori ai lati del menù.
     */
    private void shortensSeparators() {
        ctrl.leftSeparatorLn.setEndX(220);
        ctrl.rightSeparatorLn.setEndX(220);
    }


    /**
     * Nasconde tutti gli oggetti della scena (al di fuori dei separatori e
     * delle imageView).
     */
    private void hideAll() {
        hideAllButtons();
        hideAllLabels();
        hideAllTextFields();
    }

    /**
     * Nasconde e tutte le label della scena, escluso il titolo e le
     * label presenti nei pannelli.
     *
     * @see MenuController
     */
    private void hideAllLabels(){
        ctrl.mainMenuLbl.setVisible(false);
        ctrl.gameProfileLbl.setVisible(false);
        ctrl.gameModeLbl.setVisible(false);
        ctrl.nicknameLbl.setVisible(false);

    }


    /**
     * Nasconde e resetta tutti i campi di testo della scena la cui
     * variabile è stata definita nel controller del menù.
     *
     * @see MenuController
     */
    private void hideAllTextFields() {
        ctrl.nicknameTxtFd.setVisible(false);
    }
}

