package by.goncharov.epamsound.dao;

import java.util.ResourceBundle;

public class DatabaseManager {

    public static final String DB_PASSWORD = "db.password";

    public static final String DB_POOL_SIZE = "db.poolsize";

    public static final String DB_URL = "db.url";

    public static final String DB_USER = "db.user";

    private static final ResourceBundle resourceBundle = ResourceBundle.
            getBundle("database");

    public static String getProperty(final String key) {
        return resourceBundle.getString(key);
    }
}