package it.jpokebattle.util;

/**
 * Classe Custom che contiene una Stringa (evTypeDropped) e un intero (evAmountDropped)
 * viene utilizzata quando un pokemon è sconfitto e quindi rilascia degli EVs
 */
public class EVDrop {

    private String evTypeDropped; // Tipo di EV che il Pokémon rilascia (es. "hp", "atk")
    private int evAmountDropped;  // Quantità di EV rilasciati

    public EVDrop(String evTypeDropped, int evAmountDropped) {
        this.evTypeDropped = evTypeDropped;
        this.evAmountDropped = evAmountDropped;
    }

    public String getEvTypeDropped() {return evTypeDropped;}

    public int getEvAmountDropped() {return evAmountDropped;}
}
