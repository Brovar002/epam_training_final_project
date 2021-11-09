package by.goncharov.epamsound.manager;

import java.util.ResourceBundle;

public class ConfigurationManager {
    public static final String ADD_TRACK_PATH = "path.page.add.track";
    public static final String CHANGE_PATH = "path.page.change";
    public static final String CHANGE_PASS_PATH = "path.page.pass";
    public static final String ERROR_PATH = "path.page.error";
    public static final String HOME_PATH = "path.page.home";
    public static final String LOGIN_PATH = "path.page.login";
    public static final String MAIN_PATH = "path.page.main";
    public static final String MONEY_PATH = "path.page.add.funds";
    public static final String MY_ORDERS_PATH = "path.page.my_orders";
    public static final String SHOW_MY_ORDERS_PATH = "path.page.orders";
    public static final String PROFILE_PATH = "path.page.profile";
    public static final String SET_BONUS_PATH = "path.page.bonus";
    public static final String SHOW_USERS_PATH = "path.page.show_users";
    public static final String SIGNUP_PATH = "path.page.signup";
    public static final String TRACK_INFO_PATH = "path.page.track_info";
    public static final String TRACK_DELETED_PATH = "path.page.deleted";
    public static final String TRACK_EDIT_PATH = "path.page.track_edit";
    public static final String TRACK_RECOVER_PATH = "path.page.recover";
    private static final ResourceBundle resourceBundle = ResourceBundle.
            getBundle("config");
    public static String getProperty(final String key) {
        return resourceBundle.getString(key);
    }
}