package by.goncharov.epamsound.manager;

import java.util.ResourceBundle;

/**
 * The type Message manager.
 * @author Goncharov Daniil
 */
public class MessageManager {
    /**
     * The constant ADD_COMMENT_ERROR.
     */
    public static final String ADD_COMMENT_ERROR = "message.add.comment";
    /**
     * The constant ADD_TRACK_DATA_ERROR.
     */
    public static final String ADD_TRACK_DATA_ERROR =
            "message.add.track.data.error";
    /**
     * The constant CHANGE_CASH_ERROR.
     */
    public static final String CHANGE_CASH_ERROR = "message.change.cash";
    /**
     * The constant CHANGE_EMAIL_ERROR.
     */
    public static final String CHANGE_EMAIL_ERROR =
            "message.change.email.error";
    /**
     * The constant CHANGE_LOGIN_ERROR.
     */
    public static final String CHANGE_LOGIN_ERROR =
            "message.change.login.error";
    /**
     * The constant CHANGE_PASS_EQUAL_ERROR.
     */
    public static final String CHANGE_PASS_EQUAL_ERROR =
            "message.change.pass.equal";
    /**
     * The constant CHANGE_PASS_EQUAL_NEW_ERROR.
     */
    public static final String CHANGE_PASS_EQUAL_NEW_ERROR =
            "message.change.pass.equal.new";
    /**
     * The constant CHANGE_PASS_NEW_ERROR.
     */
    public static final String CHANGE_PASS_NEW_ERROR =
            "message.change.pass.new";
    /**
     * The constant CHANGE_TRACK_ARTIST_ERROR.
     */
    public static final String CHANGE_TRACK_ARTIST_ERROR =
            "message.change.track.artist.error";
    /**
     * The constant CHANGE_TRACK_GENRE_ERROR.
     */
    public static final String CHANGE_TRACK_GENRE_ERROR =
            "message.change.track.genre.error";
    /**
     * The constant CHANGE_TRACK_NAME_ERROR.
     */
    public static final String CHANGE_TRACK_NAME_ERROR =
            "message.change.track.name.error";
    /**
     * The constant CHANGE_TRACK_PRICE_ERROR.
     */
    public static final String CHANGE_TRACK_PRICE_ERROR =
            "message.change.track.price.error";
    /**
     * The constant NOT_UNIQUE_EMAIL.
     */
    public static final String NOT_UNIQUE_EMAIL = "message.signup.unique.email";
    /**
     * The constant NOT_UNIQUE_LOGIN.
     */
    public static final String NOT_UNIQUE_LOGIN = "message.signup.unique.login";
    /**
     * The constant SET_BONUS_ERROR.
     */
    public static final String SET_BONUS_ERROR = "message.bonus.error";
    /**
     * The constant SIGNUP_ERROR.
     */
    public static final String SIGNUP_ERROR = "message.signup.error";
    /**
     * The constant ADD_COMMENT_EMPTY.
     */
    public static final String ADD_COMMENT_EMPTY = "message.add.comment.empty";
    /**
     * The constant ADD_TRACK_ERROR.
     */
    public static final String ADD_TRACK_ERROR = "message.add.track.error";
    /**
     * The constant ADD_TRACK_SUCCESS.
     */
    public static final String ADD_TRACK_SUCCESS = "message.add.track.success";
    /**
     * The constant CHANGE_SUCCESS.
     */
    public static final String CHANGE_SUCCESS = "message.change.success";
    /**
     * The constant DELETE_TRACK_SUCCESS.
     */
    public static final String DELETE_TRACK_SUCCESS =
            "message.delete.track.success";
    /**
     * The constant DOWNLOAD_ERROR.
     */
    public static final String DOWNLOAD_ERROR = "message.download.error";
    /**
     * The constant LOGIN_ERROR.
     */
    public static final String LOGIN_ERROR = "message.login.error";
    /**
     * The constant ODER_DOWNLOAD.
     */
    public static final String ODER_DOWNLOAD = "message.order.download";
    /**
     * The constant ORDER_ERROR.
     */
    public static final String ORDER_ERROR = "message.order.money";
    /**
     * The constant ORDER_SUCCESS.
     */
    public static final String ORDER_SUCCESS = "message.order.success";
    /**
     * The constant SET_BONUS_SUCCESS.
     */
    public static final String SET_BONUS_SUCCESS = "message.bonus.success";
    /**
     * The constant TRACK_RECOVER_SUCCESS.
     */
    public static final String TRACK_RECOVER_SUCCESS =
            "message.track.recover.success";
    private  ResourceBundle resourceBundle;

    /**
     * Gets property.
     *
     * @param key the key
     * @return the property
     */
    public String getProperty(final String key) {
        return resourceBundle.getString(key);
    }

    /**
     * Instantiates a new Message manager.
     */
    public MessageManager() {
        resourceBundle = ResourceBundleType.RU_RU.getResourceBundle();
    }

    /**
     * Sets current locale.
     *
     * @param language the language
     */
    public void setCurrentLocale(final String language) {
        if ("en_US".equals(language)) {
            resourceBundle = ResourceBundleType.EN_US.getResourceBundle();
        } else {
            resourceBundle = ResourceBundleType.RU_RU.getResourceBundle();
        }
    }
}
