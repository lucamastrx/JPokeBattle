package it.jpokebattle.model.leaderboard;

import it.jpokebattle.model.pokemon.Pokemon;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.ObjectProperty;

import java.time.LocalDateTime;
import java.util.List;

public class LeaderboardEntry {
    private final ObjectProperty<LocalDateTime> timestamp;
    private final SimpleStringProperty win;
    private final SimpleStringProperty pokemon1;
    private final SimpleStringProperty pokemon2;
    private final SimpleStringProperty pokemon3;
    private final SimpleStringProperty pokemon4;
    private final SimpleStringProperty pokemon5;
    private final SimpleStringProperty pokemon6;

    private static final int MAX_POKEMON = 6;


    public LeaderboardEntry(LocalDateTime timestamp, String winStatus,
                            List<Pokemon> team) {
        this.timestamp = new SimpleObjectProperty<>(timestamp);
        this.win = new SimpleStringProperty(winStatus);
        this.pokemon1 = new SimpleStringProperty("-");
        this.pokemon2 = new SimpleStringProperty("-");
        this.pokemon3 = new SimpleStringProperty("-");
        this.pokemon4 = new SimpleStringProperty("-");
        this.pokemon5 = new SimpleStringProperty("-");
        this.pokemon6 = new SimpleStringProperty("-");
        populatePokemons(team);
    }

    private void populatePokemons(List<Pokemon> team) {
        for (int i = 0; i < team.size() && i < MAX_POKEMON; i++) {
            switch (i) {
                case 0: pokemon1.set(team.get(i).getName()); break;
                case 1: pokemon2.set(team.get(i).getName()); break;
                case 2: pokemon3.set(team.get(i).getName()); break;
                case 3: pokemon4.set(team.get(i).getName()); break;
                case 4: pokemon5.set(team.get(i).getName()); break;
                case 5: pokemon6.set(team.get(i).getName()); break;
            }
        }
    }

    // Getters and setters
    public LocalDateTime getTimestamp() { return timestamp.get(); }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp.set(timestamp); }
    public String getWin() { return win.get(); }
    public void setWin(String win) { this.win.set(win); }
    public String getPokemon1() { return pokemon1.get(); }
    public void setPokemon1(String pokemon1) { this.pokemon1.set(pokemon1); }
    public String getPokemon2() { return pokemon2.get(); }
    public void setPokemon2(String pokemon2) { this.pokemon2.set(pokemon2); }
    public String getPokemon3() { return pokemon3.get(); }
    public void setPokemon3(String pokemon3) { this.pokemon3.set(pokemon3); }
    public String getPokemon4() { return pokemon4.get(); }
    public void setPokemon4(String pokemon4) { this.pokemon4.set(pokemon4); }
    public String getPokemon5() { return pokemon5.get(); }
    public void setPokemon5(String pokemon5) { this.pokemon5.set(pokemon5); }
    public String getPokemon6() { return pokemon6.get(); }
    public void setPokemon6(String pokemon6) { this.pokemon6.set(pokemon6); }
}
