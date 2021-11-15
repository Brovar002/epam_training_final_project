package by.goncharov.epamsound.dao;

import by.goncharov.epamsound.beans.Entity;
import by.goncharov.epamsound.manager.Transaction;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.sql.SQLException;
import java.sql.Statement;

public abstract class AbstractDAO<T extends Entity> {
    private static final Logger LOGGER = LogManager.getLogger();
    protected Transaction connection;
    protected AbstractDAO(final Transaction connection) {
        this.connection = connection;
    }

    public void closeStatement(final Statement statement) {
        try {
            if (statement != null) {
                statement.close();
            } else {
                LOGGER.warn("Can't close statement. Statement was'n created");
            }
        } catch (SQLException e) {
            LOGGER.error("Error during statement closing", e);
        }
    }

    public void closeConnection(final Transaction connection) {
        try {
            connection.close();
        } catch (SQLException e) {
            LOGGER.error("Error during connection to pull return", e);
        }
    }
}
