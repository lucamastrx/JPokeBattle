package it.jpokebattle.model.area;

import it.jpokebattle.util.FileManager;

import java.io.Serializable;

/**
 * Un'area è uno degli stati che può assumere il gioco durante la sua esecuzione e attraverso
 * cui il giocatore passa. Ogni area è seguita da un'altra area.
 */
public enum Area implements Serializable {
    START("start"),
    HOME_BEDROOM_1("home_bedroom_1"),
    HOME_LIVING_ROOM_1("home_living_room_1"),
    BARK_TOWN_1("bark_town_1"),
    ROUTE1_1("route1_1"),
    LABORATORY_1("laboratory_1"),
    STARTER_SELECTION("starter_selection"),
    LABORATORY_2("laboratory_2"),
    BARK_TOWN("bark_town"),
    LABORATORY("laboratory"),
    HOME_LIVING_ROOM("home_living_room"),
    HOME_BEDROOM("home_bedroom"),
    ROUTE1_START("route1_start"),
    R1_TALL_GRASS("r1_tall_grass"),
    ROUTE1_END("route1_end"),
    CHERRYGROVE_EST("cherrygrove_est"),
    CHERRYGROVE_OVEST("cherrygrove_ovest"),
    CHERRYGROVE_POKEMART("cherrygrove_pokemart"),
    CHERRYGROVE_POKECENTER("cherrygrove_pokecenter"),
    ROUTE2_START("route2_start"),
    R2_TALL_GRASS("r2_tall_grass"),
    ROUTE2_END("route2_end"),
    VIOLET_SUD("violet_sud"),
    VIOLET_NORD("violet_nord"),
    VIOLET_POKEMART("violet_pokemart"),
    VIOLET_POKECENTER("violet_pokecenter"),
    END("end"),
    ;

    private String name;
    private String description;
    private String audioSrcName;
    private String imageSrcName;
    private String nextAreaName;            // nome dell'area successiva
    private String nextAreaButtonName;      // nome del bottone che consente il passaggio all'area successiva
    private String prevAreaName;            // nome dell'area precedente (se esiste)
    private String prevAreaButtonName;      // nome del bottone che consente il passaggio all'area precedente (se esiste)
    private String specialAreaName;         // nome dell'area speciale (se esiste)
    private String specialAreaButtonName;   // nome del bottone che consente il passaggio all'area precedente (se esiste)
    private boolean tallGrass;
    private boolean pokeMart;
    private boolean pokeCenter;

    private Area(String name) {
        this.name = name;
        initializeArea();
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getAudioSrcName() {
        return audioSrcName;
    }

    public String getImageSrcName() {
        return imageSrcName;
    }

    public Area getNextArea() {
        return Area.fromName(this.nextAreaName);
    }

    public Area getPrevArea() {
        return Area.fromName(this.prevAreaName);
    }

    public Area getSpecialArea() {
        return Area.fromName(this.specialAreaName);
    }

    public String getNextAreaButtonName() {
        return this.nextAreaButtonName;
    }

    public String getPrevAreaButtonName() {
        return this.prevAreaButtonName;
    }

    public String getSpecialAreaButtonName() {
        return this.specialAreaButtonName;
    }

    /**
     * A partire dal nome di un'area ritorna (se esiste) l'istanza dell'area con quel nome.
     *
     * @param areaName Nome dell'area di cui si vuole l'istanza
     * @return Istanza della classe Area avente per nome areaName
     */
    public static Area fromName(String areaName) {
        for (Area area : Area.values()) {
            if (area.getName().equalsIgnoreCase(areaName))
                return area;
        }
        return null;
    }

    private void initializeArea() {
        AreaData area = FileManager.getAreaDataMap().get(this.name);
        this.description = area.description;
        this.audioSrcName =  area.musicSrcName;
        this.imageSrcName = area.bgImageSrcName;
        this.nextAreaName = area.nextAreaName;
        this.nextAreaButtonName = area.nextAreaButtonName;
        this.prevAreaName = area.prevAreaName;
        this.prevAreaButtonName = area.prevAreaButtonName;
        this.specialAreaName = area.specialAreaName;
        this.specialAreaButtonName = area.specialAreaButtonName;
        this.tallGrass = area.tallGrass;
        this.pokeMart = area.pokeMart;
        this.pokeCenter = area.pokeCenter;
    }

}
