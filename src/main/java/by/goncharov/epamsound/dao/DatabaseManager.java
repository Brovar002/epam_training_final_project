package by.goncharov.epamsound.dao;

import java.util.ResourceBundle;

/**
 * The type Database manager.
 * @author Goncharov Daniil
 */
public class DatabaseManager {

    /**
     * The constant DB_PASSWORD.
     */
    public static final String DB_PASSWORD = "db.password";

    /**
     * The constant DB_POOL_SIZE.
     */
    public static final String DB_POOL_SIZE = "db.poolsize";

    /**
     * The constant DB_URL.
     */
    public static final String DB_URL = "db.url";

    /**
     * The constant DB_USER.
     */
    public static final String DB_USER = "db.user";

    private static final ResourceBundle resourceBundle = ResourceBundle.
            getBundle("database");

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
