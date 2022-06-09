package by.goncharov.epamsound.beans;


import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Class for describing the essence of a genre.
 *
 * @author Goncharov Daniil
 * @version 1.0
 * @see Entity
 */
@Entity
@Table(name = "genre")
public class Genre extends by.goncharov.epamsound.beans.Entity {
    @Column(name = "genre")
    private String name;
    @Id
    @Column(name = "id")
    private int id;

    /**
     * Instantiates a new Genre.
     */
    public Genre() {

    }

    public Genre(final int id, final String name) {
        this.name = name;
        this.id = id;
    }

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
     * Gets id.
     *
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * Sets id.
     *
     * @param id the id
     */
    public void setId(final int id) {
        this.id = id;
    }

    /**
     * Instantiates a new Genre.
     *
     * @param id   the id
     * @param name the name
     */
}
