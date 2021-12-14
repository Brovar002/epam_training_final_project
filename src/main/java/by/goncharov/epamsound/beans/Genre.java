package by.goncharov.epamsound.beans;


import java.util.Objects;

/**
 * Class for describing the essence of a genre.
 * @author Goncharov Daniil
 * @version 1.0
 * @see Entity
 */
public class Genre extends Entity {
    private String name;
    private Long id;

    /**
     * Gets name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Genre{" +
                "name='" + name + '\'' +
                ", id=" + id +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Genre genre = (Genre) o;
        return Objects.equals(name, genre.name) && Objects.equals(id, genre.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, id);
    }

    /**
     * Sets name.
     *
     * @param name the name
     */
    public void setName(final String name) {
        this.name = name;
    }

    /**
     * Instantiates a new Genre.
     */
    public Genre() {

    }

    /**
     * Gets id.
     *
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets id.
     *
     * @param id the id
     */
    public void setId(final Long id) {
        this.id = id;
    }

    /**
     * Instantiates a new Genre.
     *
     * @param id   the id
     * @param name the name
     */
    public Genre(final Long id, final String name) {
        this.name = name;
        this.id = id;
    }
}
