package by.goncharov.epamsound.beans;

public class Genre extends Entity{
    private String name;
    private Long id;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Genre(){

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Genre(Long id, String name){
        this.name = name;
        this.id = id;
    }
}
