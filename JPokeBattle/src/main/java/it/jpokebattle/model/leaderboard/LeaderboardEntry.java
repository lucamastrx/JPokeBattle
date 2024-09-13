package it.jpokebattle.model.leaderboard;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.ObjectProperty;

import java.time.LocalDateTime;
import java.util.List;


/**
 * Classe che rappresenta una singola voce nella classifica, contenente informazioni sul timestamp,
 * sullo stato della vittoria e sui Pokémon del team.
 */
public class LeaderboardEntry {

    @JsonProperty("timestamp")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime timestamp;

    @JsonProperty("winStatus")
    private String winStatus;

    @JsonProperty("pokemon1")
    private String pokemon1;

    @JsonProperty("pokemon2")
    private String pokemon2;

    @JsonProperty("pokemon3")
    private String pokemon3;

    @JsonProperty("pokemon4")
    private String pokemon4;

    @JsonProperty("pokemon5")
    private String pokemon5;

    @JsonProperty("pokemon6")
    private String pokemon6;


    /**
     * Costruttore di default, necessario per la deserializzazione da JSON.
     */
    public LeaderboardEntry() {
        // Default constructor needed for Jackson deserialization
    }

    /**
     * Costruttore che inizializza una nuova voce con il timestamp, lo stato della vittoria e il team di Pokémon.
     *
     * @param timestamp Il momento in cui è stata registrata la partita.
     * @param winStatus Lo stato di vittoria o sconfitta.
     * @param team La lista dei nomi dei Pokémon del team.
     */
    public LeaderboardEntry(LocalDateTime timestamp, String winStatus, List<String> team) {
        this.timestamp = timestamp;
        this.winStatus = winStatus;
        populatePokemons(team);
    }


    /**
     * Restituisce una proprietà di JavaFX per il timestamp.
     *
     * @return Oggetto Property di JavaFX contenente il timestamp.
     */
    @JsonIgnore
    public ObjectProperty<LocalDateTime> getTimestampProperty() {
        return new SimpleObjectProperty<>(timestamp);
    }


    /**
     * Restituisce una proprietà di JavaFX per il numero di vittorie.
     *
     * @return Oggetto Property di JavaFX contenente il numero di vittorie.
     */
    @JsonIgnore
    public SimpleStringProperty getWinStatusProperty() {
        return new SimpleStringProperty(winStatus);
    }

    // Metodi per restituire le proprietà di JavaFX per ciascun Pokémon.

    @JsonIgnore
    public SimpleStringProperty getPokemon1Property() {return new SimpleStringProperty(pokemon1);}

    @JsonIgnore
    public SimpleStringProperty getPokemon2Property() {return new SimpleStringProperty(pokemon2);}

    @JsonIgnore
    public SimpleStringProperty getPokemon3Property() {return new SimpleStringProperty(pokemon3);}

    @JsonIgnore
    public SimpleStringProperty getPokemon4Property() {return new SimpleStringProperty(pokemon4);}

    @JsonIgnore
    public SimpleStringProperty getPokemon5Property() {return new SimpleStringProperty(pokemon5);}

    @JsonIgnore
    public SimpleStringProperty getPokemon6Property() {return new SimpleStringProperty(pokemon6);}


    /**
     * Metodo privato per popolare i nomi dei Pokémon del team.
     *
     * @param team La lista dei nomi dei Pokémon.
     */
    @JsonIgnore
    private void populatePokemons(List<String> team) {
        if (team != null) {
            switch (team.size()) {
                case 6: pokemon6 = team.get(5);
                case 5: pokemon5 = team.get(4);
                case 4: pokemon4 = team.get(3);
                case 3: pokemon3 = team.get(2);
                case 2: pokemon2 = team.get(1);
                case 1: pokemon1 = team.get(0);
                default: break;
            }
        }
    }

    // Getter e setter per i campi

    @JsonIgnore
    public LocalDateTime getTimestamp() { return timestamp; }
    @JsonIgnore
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }

    @JsonIgnore
    public String getWinStatus() { return winStatus; }
    @JsonIgnore
    public void setWinStatus(String winStatus) { this.winStatus = winStatus; }

    @JsonIgnore
    public String getPokemon1() { return pokemon1; }
    @JsonIgnore
    public void setPokemon1(String pokemon1) { this.pokemon1 = pokemon1; }

    @JsonIgnore
    public String getPokemon2() { return pokemon2; }
    @JsonIgnore
    public void setPokemon2(String pokemon2) { this.pokemon2 = pokemon2; }

    @JsonIgnore
    public String getPokemon3() { return pokemon3; }
    @JsonIgnore
    public void setPokemon3(String pokemon3) { this.pokemon3 = pokemon3; }

    @JsonIgnore
    public String getPokemon4() { return pokemon4; }

    public void setPokemon4(String pokemon4) { this.pokemon4 = pokemon4; }

    @JsonIgnore
    public String getPokemon5() { return pokemon5; }
    @JsonIgnore
    public void setPokemon5(String pokemon5) { this.pokemon5 = pokemon5; }

    @JsonIgnore
    public String getPokemon6() { return pokemon6; }
    @JsonIgnore
    public void setPokemon6(String pokemon6) { this.pokemon6 = pokemon6; }
}