package it.jpokebattle.model.area;

/**
 * Classe avente l'unico scopo di fare da tramite durante il caricamento dei dati delle aree.
 * Le istanze di questa classe vengono utilizzate per memorizzare i dati delle aree nella mappa,
 * successivamente i dati vengono caricati all'interno delle istanze della classe enum Area.
 *
 * @see Area
 */
public final class AreaData {
    public final String name;
    public final String description;
    public final String musicSrcName;
    public final String bgImageSrcName;
    public final String nextAreaName;            // nome dell'area successiva
    public final String nextAreaButtonName;      // nome del bottone che consente il passaggio all'area successiva
    public final String prevAreaName;            // nome dell'area precedente (se esiste)
    public final String prevAreaButtonName;      // nome del bottone che consente il passaggio all'area precedente (se esiste)
    public final String specialAreaName;         // nome dell'area speciale (se esiste)
    public final String specialAreaButtonName;   // nome del bottone che consente il passaggio all'area precedente (se esiste)
    public final boolean tallGrass;
    public final boolean pokeMart;
    public final boolean pokeCenter;

    public AreaData(String name, String narrationTxt, String musicSrcName, String bgImageSrcName, String next,
                    String nextBtn, String prev, String prevBtn, String spcl, String spclBtn, boolean grass,
                    boolean mart, boolean center) {
        this.name = name;
        this.description = narrationTxt;
        this.musicSrcName = musicSrcName;
        this.bgImageSrcName = bgImageSrcName;
        this.nextAreaName = next;
        this.nextAreaButtonName = nextBtn;
        this.prevAreaName = prev;
        this.prevAreaButtonName = prevBtn;
        this.specialAreaName = spcl;
        this.specialAreaButtonName = spclBtn;
        this.tallGrass = grass;
        this.pokeMart = mart;
        this.pokeCenter = center;
    }
}
