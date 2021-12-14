package by.goncharov.epamsound.manager;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * The enum Resource bundle type.
 * @author Goncharov Daniil
 */
public enum ResourceBundleType {
    /**
     * En us resource bundle type.
     */
    EN_US("en", "Us"),
    /**
     * Ru ru resource bundle type.
     */
    RU_RU("ru", "Ru");

    private final ResourceBundle resourceBundle;

    ResourceBundleType(final String currentLocale, final String country) {
        resourceBundle = ResourceBundle.getBundle("content",
                new Locale(currentLocale, country));
    }

    /**
     * Gets resource bundle.
     *
     * @return the resource bundle
     */
    public ResourceBundle getResourceBundle() {
        return resourceBundle;
    }
}
