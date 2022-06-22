package by.goncharov.epamsound.manager;

import java.util.ResourceBundle;

/**
 * The type Message manager.
 * @author Goncharov Daniil
 */
public class MessageManager {

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
