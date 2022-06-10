package by.goncharov.epamsound.beans;

import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Class for describing the essence of a track.
 * @author Goncharov Daniil
 * @version 1.0
 * @see Entity
 */
@Entity
@Table(name = "audio_track")
public class Track extends by.goncharov.epamsound.beans.Entity {
    @Id
    @Column(name = "id")
    private int id;
    @Column(name = "name")
    private String name;
    @Column(name = "artist_name")
    private String artist;
    @Column(name = "genre_id")
    private String genre;
    @Column(name = "price")
    private double price;
    @Column(name = "path")
    private String path;
    @Column(name = "deleted")
    private boolean deleted;

    @Override
    public String toString() {
        return "Track{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", artist='" + artist + '\'' +
                ", genre='" + genre + '\'' +
                ", price=" + price +
                ", path='" + path + '\'' +
                ", deleted=" + deleted +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Track track = (Track) o;
        return id == track.id && Double.compare(track.price, price) == 0 && deleted == track.deleted && Objects.equals(name, track.name) && Objects.equals(artist, track.artist) && Objects.equals(genre, track.genre) && Objects.equals(path, track.path);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, artist, genre, price, path, deleted);
    }

    public Track() {

    }

    /**
     * Instantiates a new Track.
     *
     * @param name   the name
     * @param artist the artist
     * @param genre  the genre
     * @param price  the price
     */
    public Track(final String name, final String artist,
                 final String genre, final double price) {
        this.name = name;
        this.artist = artist;
        this.genre = genre;
        this.price = price;
    }

    public Track(String name, String artist, String genre, double price, String path, boolean deleted) {
        this.name = name;
        this.artist = artist;
        this.genre = genre;
        this.price = price;
        this.path = path;
        this.deleted = deleted;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
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
     * Gets name.
     *
     * @return the name
     */
    public String getName() {
        return name;
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
     * Gets artist.
     *
     * @return the artist
     */
    public String getArtist() {
        return artist;
    }

    /**
     * Sets artist.
     *
     * @param artist the artist
     */
    public void setArtist(final String artist) {
        this.artist = artist;
    }

    /**
     * Gets price.
     *
     * @return the price
     */
    public double getPrice() {
        return price;
    }

    /**
     * Sets price.
     *
     * @param price the price
     */
    public void setPrice(final double price) {
        this.price = price;
    }

    /**
     * Gets genre.
     *
     * @return the genre
     */
    public String getGenre() {
        return genre;
    }

    /**
     * Sets genre.
     *
     * @param genre the genre
     */
    public void setGenre(final String genre) {
        this.genre = genre;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }
}
