package by.goncharov.epamsound.beans;


import java.util.Objects;

/**
 * Class for describing the essence of a track.
 * @author Goncharov Daniil
 * @version 1.0
 * @see Entity
 */
public class Track extends Entity {
    private int id;
    private String name;
    private String artist;
    private String genre;
    private double price;
    private String path;

    @Override
    public String toString() {
        return "Track{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", artist='" + artist + '\'' +
                ", genre='" + genre + '\'' +
                ", price=" + price +
                ", path='" + path + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Track track = (Track) o;
        return id == track.id && Double.compare(track.price, price) == 0 && Objects.equals(name, track.name) && Objects.equals(artist, track.artist) && Objects.equals(genre, track.genre) && Objects.equals(path, track.path);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, artist, genre, price, path);
    }

    public Track() {

    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    /**
     * Instantiates a new Track.
     *
     * @param id     the id
     * @param name   the name
     * @param artist the artist
     * @param genre  the genre
     * @param price  the price
     */
    public Track(final int id, final String name, final String artist,
                 final String genre, final double price) {
        this.id = id;
        this.name = name;
        this.artist = artist;
        this.genre = genre;
        this.price = price;
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
}
