package by.goncharov.epamsound.beans;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Comment extends Entity {
    private int userId;
    private int trackId;
    private String text;
    private String userLogin;
    private String dateTime;
    public Comment(final int userId, final int trackId, final String text) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(
                "yyyy-MM-dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        this.userId = userId;
        this.text = text;
        this.trackId = trackId;
        this.dateTime = now.format(formatter);
    }

    public Comment(final String userLogin, final String dateTime,
                   final String text) {
        this.userLogin = userLogin;
        this.dateTime = dateTime;
        this.text = text;
    }
    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(final String dateTime) {
        this.dateTime = dateTime;
    }

    public void setUserId(final int userId) {
        this.userId = userId;
    }

    public int getUserId() {
        return userId;
    }

    public String getUserLogin() {
        return userLogin;
    }

    public void setUserLogin(final String userLogin) {
        this.userLogin = userLogin;
    }

    public String getText() {
        return text;
    }

    public void setText(final String text) {
        this.text = text;
    }

    public int getAudioTrackId() {
        return trackId;
    }

    public void setAudioTrackId(final int trackId) {
        this.trackId = trackId;
    }
}
