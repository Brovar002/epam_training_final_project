package by.goncharov.epamsound.manager;

import java.util.Locale;
import java.util.ResourceBundle;

public enum ResourceBundleType {
    EN_US("en", "Us"),
    RU_RU("ru", "Ru");

    private final ResourceBundle resourceBundle;

    ResourceBundleType(final String currentLocale, final String country) {
        resourceBundle = ResourceBundle.getBundle("content",
                new Locale(currentLocale, country));
    }

    public ResourceBundle getResourceBundle() {
        return resourceBundle;
    }
}
