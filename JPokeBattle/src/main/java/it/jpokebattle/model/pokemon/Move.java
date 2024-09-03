package it.jpokebattle.model.pokemon;

import it.jpokebattle.model.type.Type;

public class Move {
    private String name;
    private Type type;
    private String description;
    private int power;
    private int precision;
    private int ppDefault;          // numero di utilizzi massimo di una mossa
    private boolean isSpecial;  // la mossa è speciale o fisica
    private String opponentModificatorType;     // la mossa modifica un qualche valore all'avversario
    private int opponentModificatorValue;       // di quanto la modifica
    private String personalModificatorType;     // la mossa modifica un qualche valore personale
    private int personalModificatorValue;       // di quanto la modifica

    /**
     * Metodo Costruttore.
     *
     * @param name
     * @param type          il tipo della mossa (es: FIRE)
     * @param description   descrizione della mossa
     * @param power
     * @param precision     precisione della mossa (compreso tra 0.0 e 1.0)
     * @param ppDefault
     * @param isSpecial     la mossa è Speciale o Fisica
     */
    public Move (String name, Type type, String description, int power, int precision, int ppDefault, boolean isSpecial, String opponentModificatorType, int opponentModificatorValue, String personalModificatorType, int personalModificatorValue) {
        this.name = name;
        this.type = type;
        this.description = description;
        this.power = power;                     // se 0 è una mossa che non fa danno agli hp avversari
        this.precision = precision;
        this.ppDefault = ppDefault;
        this.isSpecial = isSpecial;
        this.opponentModificatorType = opponentModificatorType;
        this.opponentModificatorValue = opponentModificatorValue;
        this.personalModificatorType = personalModificatorType;
        this.personalModificatorValue = personalModificatorValue;
    }


    public String getName() {return name;}

    public Type getType() {return type;}

    public String getDescription() {return description;}

    public int getDamage() {return power;}

    public int getPrecision() {return precision;}

    /**
     *
     * @return numero di utilizzi massimi di una mossa
     */
    public int getPPDefault() {return ppDefault;}


    /**
     *
     * @return valore booleano true se la mossa è speciale, altrimenti è fisica
     */
    public boolean isSpecial() {return isSpecial;}

    public int getPower() {return power;}

    public int getPpDefault() {return ppDefault;}

    public String getOpponentModificatorType() {return opponentModificatorType;}

    public int getOpponentModificatorValue() {return opponentModificatorValue;}

    public String getPersonalModificatorType() {return personalModificatorType;}

    public int getPersonalModificatorValue() {return personalModificatorValue;}


    public void setName(String name) {this.name = name;}

    public void setType(Type type) {this.type = type;}

    public void setDescription(String description) {this.description = description;}

    public void setPower(int power) {this.power = power;}

    public void setPrecision(int precision) {this.precision = precision;}

    public void setPpMAX(int ppDefault) {this.ppDefault = ppDefault;}

    public void setIsSpecial(boolean special) {isSpecial = special;}

}
