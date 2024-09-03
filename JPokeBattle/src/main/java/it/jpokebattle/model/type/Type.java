package it.jpokebattle.model.type;


public enum Type {
    NORMAL("NORMAL"),
    FIGHTING("FIGHTING"),
    FLYING("FLYING"),
    POISON("POISON"),
    GROUND("GROUND"),
    ROCK("ROCK"),
    BUG("BUG"),
    GHOST("GHOST"),
    FIRE("FIRE"),
    WATER("WATER"),
    GRASS("GRASS"),
    ELECTRIC("ELECTRIC"),
    PSYCHIC("PSYCHIC"),
    ICE("ICE"),
    DRAGON("DRAGON"),
    STEEL("STEEL"),
    DARK("DARK");

    private String name;

    private Type(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static Type fromName(String name) {
        for (Type t : Type.values()) {
            if (t.getName().equalsIgnoreCase(name))
                return t;
        }

        return null;
    }
}
