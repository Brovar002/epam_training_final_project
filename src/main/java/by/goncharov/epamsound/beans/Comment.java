package by.goncharov.epamsound.beans;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Class for describing the essence of a comment.
 * @author Goncharov Daniil
 * @version 1.0
 * @see DateTimeFormatter
 * @see Entity
 */
public class Comment extends Entity {
    private int userId;
    private int trackId;
    private String text;
    private String userLogin;
    private String dateTime;

    /**
     * Instantiates a new Comment.
     *
     * @param userId  the user id
     * @param trackId the track id
     * @param text    the text
     */
    public Comment(final int userId, final int trackId, final String text) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(
                "yyyy-MM-dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        this.userId = userId;
        this.text = text;
        this.trackId = trackId;
        this.dateTime = now.format(formatter);
    }

    /**
     * Instantiates a new Comment.
     *
     * @param userLogin the user login
     * @param dateTime  the date time
     * @param text      the text
     */
    public Comment(final String userLogin, final String dateTime,
                   final String text) {
        this.userLogin = userLogin;
        this.dateTime = dateTime;
        this.text = text;
    }

    /**
     * Gets date time.
     *
     * @return the date time
     */
    public String getDateTime() {
        return dateTime;
    }

    /**
     * Sets date time.
     *
     * @param dateTime the date time
     */
    public void setDateTime(final String dateTime) {
        this.dateTime = dateTime;
    }

    /**
     * Sets user id.
     *
     * @param userId the user id
     */
    public void setUserId(final int userId) {
        this.userId = userId;
    }

    /**
     * Gets user id.
     *
     * @return the user id
     */
    public int getUserId() {
        return userId;
    }

    /**
     * Gets user login.
     *
     * @return the user login
     */
    public String getUserLogin() {
        return userLogin;
    }

    /**
     * Sets user login.
     *
     * @param userLogin the user login
     */
    public void setUserLogin(final String userLogin) {
        this.userLogin = userLogin;
    }

    /**
     * Gets text.
     *
     * @return the text
     */
    public String getText() {
        return text;
    }

    /**
     * Sets text.
     *
     * @param text the text
     */
    public void setText(final String text) {
        this.text = text;
    }

    /**
     * Gets audio track id.
     *
     * @return the audio track id
     */
    public int getAudioTrackId() {
        return trackId;
    }

    /**
     * Sets audio track id.
     *
     * @param trackId the track id
     */
    public void setAudioTrackId(final int trackId) {
        this.trackId = trackId;
    }
}
