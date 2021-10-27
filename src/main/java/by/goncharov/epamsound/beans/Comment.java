package by.goncharov.epamsound.beans;

public class Comment extends Entity {
    private int userId;
    private int trackId;
    private String text;
    private String userLogin;
    public Comment(final int userId, final int trackId, final String text) {
        this.userId = userId;
        this.text = text;
        this.trackId = trackId;
    }

    public Comment(final String userLogin, final String text) {
        this.userLogin = userLogin;
        this.text = text;
    }
}
