package it.jpokebattle.model.pokemon;

/**
 * Classe Custom che rappresenta un oggetto contenente le informazioni sugli EV (Effort Values) rilasciati
 * quando un Pokémon viene sconfitto. Un oggetto di questa classe memorizza il tipo di EV e la quantità di EV rilasciati.
 */
public class EVDrop {

    private String evTypeDropped; // Tipo di EV che il Pokémon rilascia (es. "hp", "atk")
    private int evAmountDropped;  // Quantità di EV rilasciati

    /**
     * Costruttore della classe EVDrop.
     *
     * @param evTypeDropped Il tipo di EV rilasciato dal Pokémon (es. "hp", "atk").
     * @param evAmountDropped La quantità di EV rilasciati.
     */
    public EVDrop(String evTypeDropped, int evAmountDropped) {
        this.evTypeDropped = evTypeDropped;
        this.evAmountDropped = evAmountDropped;
    }

    public String getEvTypeDropped() {return evTypeDropped;}

    public int getEvAmountDropped() {return evAmountDropped;}
}
