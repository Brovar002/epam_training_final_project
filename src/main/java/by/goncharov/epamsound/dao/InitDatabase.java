package by.goncharov.epamsound.dao;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.util.MissingResourceException;
import java.util.Properties;


/**
 * The type Init database.
 * @author Goncharov Daniil
 *
 */
public class InitDatabase {
    private static final Logger LOGGER = LogManager.getLogger();
    /**
     * The Database login.
     */
    final String DATABASE_LOGIN;
    /**
     * The Database pass.
     */
    final String DATABASE_PASS;
    /**
     * The Database url.
     */
    final String DATABASE_URL;
    /**
     * The Pool size.
     */
    final int POOL_SIZE;

    /**
     * Instantiates a new Init database.
     */
    InitDatabase() {
        Properties properties = new Properties();
        String propFileName = "database.properties";
        try {
            InputStream inputStream = getClass().getClassLoader().
                    getResourceAsStream(propFileName);
            if (inputStream != null) {
                properties.load(inputStream);
            } else {
                LOGGER.fatal("Properties was not found");
                throw new RuntimeException("Properties was not found");
            }
        } catch (IOException | MissingResourceException e) {
            LOGGER.fatal("Exception during database initialization", e);
            throw new RuntimeException(
                    "Exception during database initialization", e);
        }
        DATABASE_URL = properties.getProperty(DatabaseManager.DB_URL);
        DATABASE_LOGIN = properties.getProperty(DatabaseManager.DB_USER);
        DATABASE_PASS = properties.getProperty(DatabaseManager.DB_PASSWORD);
        POOL_SIZE = Integer.valueOf(DatabaseManager.
                getProperty(DatabaseManager.DB_POOL_SIZE));
    }
}
