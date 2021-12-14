package by.goncharov.epamsound.controller;

import java.util.ResourceBundle;

/**
 * The type Configuration manager.
 * @author Goncharov Daniil
 * @version 1.0
 */
public class ConfigurationManager {
    /**
     * The constant ADD_TRACK_PATH.
     */
    public static final String ADD_TRACK_PATH = "path.page.add.track";
    /**
     * The constant CHANGE_PATH.
     */
    public static final String CHANGE_PATH = "path.page.change";
    /**
     * The constant CHANGE_PASS_PATH.
     */
    public static final String CHANGE_PASS_PATH = "path.page.pass";
    /**
     * The constant ERROR_PATH.
     */
    public static final String ERROR_PATH = "path.page.error";
    /**
     * The constant HOME_PATH.
     */
    public static final String HOME_PATH = "path.page.home";
    /**
     * The constant LOGIN_PATH.
     */
    public static final String LOGIN_PATH = "path.page.login";
    /**
     * The constant MAIN_PATH.
     */
    public static final String MAIN_PATH = "path.page.main";
    /**
     * The constant MONEY_PATH.
     */
    public static final String MONEY_PATH = "path.page.add.funds";
    /**
     * The constant MY_ORDERS_PATH.
     */
    public static final String MY_ORDERS_PATH = "path.page.my_orders";
    /**
     * The constant SHOW_MY_ORDERS_PATH.
     */
    public static final String SHOW_MY_ORDERS_PATH = "path.page.orders";
    /**
     * The constant PROFILE_PATH.
     */
    public static final String PROFILE_PATH = "path.page.profile";
    /**
     * The constant SET_BONUS_PATH.
     */
    public static final String SET_BONUS_PATH = "path.page.bonus";
    /**
     * The constant SHOW_USERS_PATH.
     */
    public static final String SHOW_USERS_PATH = "path.page.show_users";
    /**
     * The constant SIGNUP_PATH.
     */
    public static final String SIGNUP_PATH = "path.page.signup";
    /**
     * The constant TRACK_INFO_PATH.
     */
    public static final String TRACK_INFO_PATH = "path.page.track_info";
    /**
     * The constant TRACK_DELETED_PATH.
     */
    public static final String TRACK_DELETED_PATH = "path.page.deleted";
    /**
     * The constant TRACK_EDIT_PATH.
     */
    public static final String TRACK_EDIT_PATH = "path.page.track_edit";
    /**
     * The constant TRACK_RECOVER_PATH.
     */
    public static final String TRACK_RECOVER_PATH = "path.page.recover";
    private static final ResourceBundle resourceBundle = ResourceBundle.
            getBundle("config");

    /**
     * Gets property.
     *
     * @param key the key
     * @return the property
     */
    public static String getProperty(final String key) {
        return resourceBundle.getString(key);
    }
}
