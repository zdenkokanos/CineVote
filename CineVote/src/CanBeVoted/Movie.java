package CanBeVoted;

import java.io.Serializable;

/**
 * Trieda Movie reprezentuje film, ktorý môže byť predmetom hlasovania.
 * Obsahuje informácie o názve filmu, režisérovi, hlavnom hercovi a roku vydania.
 * Táto trieda je serializovateľná, čo umožňuje jej ukladanie a načítanie v rámci prúdov objektov.
 */
public class Movie extends CanBeVoted implements Serializable, Votable {
    private String title;
    private Director director;
    private Actor mainActor;
    private int year;

    /**
     * Konštruktor pre vytvorenie objektu filmu s názvom, režisérom, hlavným hercom a rokom vydania.
     *
     * @param title názov filmu
     * @param director režisér filmu
     * @param mainActor hlavný herec filmu
     * @param year rok vydania filmu
     */
    public Movie(String title, Director director, Actor mainActor, int year) {
        this.title = title;
        this.director = director;
        this.mainActor = mainActor;
        this.year = year;
    }

    /**
     * Vracia názov filmu.
     *
     * @return názov filmu
     */
    public String getTitle() {
        return title;
    }

    /**
     * Vracia názov filmu pre observera.
     *
     * @return názov filmu
     */
    public String getName(){
        return title;
    }

    /**
     * Vracia meno režiséra filmu.
     *
     * @return meno režiséra
     */
    public String getDirectorName() {
        return director.getName();
    }

    /**
     * Vracia meno hlavného herca filmu.
     *
     * @return meno hlavného herca
     */
    public String getMainActorName() {
        return mainActor.getName();
    }

    /**
     * Vracia rok vydania filmu.
     *
     * @return rok vydania
     */
    public int getReleaseYear() {
        return year;
    }
}
