package by.goncharov.epamsound.beans;

public class Track extends Entity {
    private int id;
    private String name;
    private String artist;
    private String genre;
    private double price;

    public Track(final int id, final String name, final String artist,
                 final String genre, final double price) {
        this.id = id;
        this.name = name;
        this.artist = artist;
        this.genre = genre;
        this.price = price;
    }
}
