package by.goncharov.epamsound.manager;

import java.util.ResourceBundle;

public class MessageManager {
    public static final String ADD_COMMENT_ERROR = "message.add.comment";
    public static final String ADD_TRACK_DATA_ERROR =
            "message.add.track.data.error";
    public static final String CHANGE_CASH_ERROR = "message.change.cash";
    public static final String CHANGE_EMAIL_ERROR =
            "message.change.email.error";
    public static final String CHANGE_LOGIN_ERROR =
            "message.change.login.error";
    public static final String CHANGE_PASS_EQUAL_ERROR =
            "message.change.pass.equal";
    public static final String CHANGE_PASS_EQUAL_NEW_ERROR =
            "message.change.pass.equal.new";
    public static final String CHANGE_PASS_NEW_ERROR =
            "message.change.pass.new";
    public static final String CHANGE_TRACK_ARTIST_ERROR =
            "message.change.track.artist.error";
    public static final String CHANGE_TRACK_GENRE_ERROR =
            "message.change.track.genre.error";
    public static final String CHANGE_TRACK_NAME_ERROR =
            "message.change.track.name.error";
    public static final String CHANGE_TRACK_PRICE_ERROR =
            "message.change.track.price.error";
    public static final String NOT_UNIQUE_EMAIL = "message.signup.unique.email";
    public static final String NOT_UNIQUE_LOGIN = "message.signup.unique.login";
    public static final String SET_BONUS_ERROR = "message.bonus.error";
    public static final String SIGNUP_ERROR = "message.signup.error";
    private  ResourceBundle resourceBundle;
    public String getProperty(final String key) {
        return resourceBundle.getString(key);
    }
    public MessageManager() {
        resourceBundle = ResourceBundleType.RU_RU.getResourceBundle();
    }
    public void setCurrentLocale(final String language) {
        if ("en_US".equals(language)) {
            resourceBundle = ResourceBundleType.EN_US.getResourceBundle();
        } else {
            resourceBundle = ResourceBundleType.RU_RU.getResourceBundle();
        }
    }
}
