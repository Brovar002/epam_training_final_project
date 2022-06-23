package by.goncharov.epamsound.beans;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import javax.persistence.*;
import javax.persistence.Entity;

/**
 * Class for describing the essence of a comment.
 * @author Goncharov Daniil
 * @version 1.0
 * @see DateTimeFormatter
 * @see Entity
 */
@Entity
@Table(name = "comment")
public class Comment extends by.goncharov.epamsound.beans.Entity {
    @Id
    @Column(name = "id")
    private int id;
    @Column(name = "user_id")
    private int userId;
    @JoinColumn(name = "audio_track_id")
    @ManyToOne
    private Track track;
    @Column(name = "text")
    private String text;
    @Column(name = "user_login")
    private String userLogin;
    @Column(name = "date")
    private String dateTime;


    /**
     * Instantiates a new Comment.
     *
     * @param userId  the user id
     * @param track the track id
     * @param text    the text
     */
    public Comment(final int userId, final Track track, final String text) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(
                "yyyy-MM-dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        this.userId = userId;
        this.text = text;
        this.track = track;
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

    public Comment() {
    }

    public Comment(String text, User user, Track track) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(
                "yyyy-MM-dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        this.userLogin = user.getLogin();
        this.text = text;
        this.userId = user.getId();
        this.track = track;
        this.dateTime = now.format(formatter);

    }


    @Override
    public String toString() {
        return "Comment{" +
                "userId=" + userId +
                ", trackId=" + track +
                ", text='" + text + '\'' +
                ", userLogin='" + userLogin + '\'' +
                ", dateTime='" + dateTime + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Comment comment = (Comment) o;
        return userId == comment.userId && track == comment.track && Objects.equals(text, comment.text) && Objects.equals(userLogin, comment.userLogin) && Objects.equals(dateTime, comment.dateTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, track, text, userLogin, dateTime);
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
    public Track getAudioTrackId() {
        return track;
    }

    /**
     * Sets audio track id.
     *
     * @param track the track id
     */
    public void setAudioTrackId(final Track track) {
        this.track = track;
    }
}
