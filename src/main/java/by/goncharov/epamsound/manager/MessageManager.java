package by.goncharov.epamsound.manager;

import java.util.ResourceBundle;

public class MessageManager {
    public static final String ADD_TRACK_DATA_ERROR =
            "message.add.track.data.error";
    public static final String NOT_UNIQUE_EMAIL = "message.signup.unique.email";
    public static final String NOT_UNIQUE_LOGIN = "message.signup.unique.login";
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
