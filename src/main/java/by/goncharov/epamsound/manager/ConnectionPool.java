package by.goncharov.epamsound.manager;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.ReentrantLock;

public final class ConnectionPool {
    private static final Logger LOGGER = LogManager.getLogger();
    private ArrayBlockingQueue<ProxyConnection> connectionQueue;
    private static AtomicBoolean instanceCreated = new AtomicBoolean(false);
    private static ReentrantLock lock = new ReentrantLock();
    private static ConnectionPool instance;
    private static InitDatabase db;
    private ConnectionPool() {
        db = new InitDatabase();
        this.connectionQueue = new ArrayBlockingQueue<>(db.POOL_SIZE);
        try {
            DriverManager.registerDriver(new com.mysql.jdbc.Driver());
        } catch (SQLException e) {
            LOGGER.fatal(e);
            throw new RuntimeException(e);
        }
        for (int i = 0; i < db.POOL_SIZE; i++) {
            createConnection();
        }

        if (size() != db.POOL_SIZE) {
            int number = db.POOL_SIZE - connectionQueue.size();
            LOGGER.warn(number + " connections should be recreated");
            for (int i = 0; i < number; i++) {
                createConnection();
            }
        }
        if (size() == 0) {
            LOGGER.fatal("ERROR");
            throw new RuntimeException("ERROR");
        }
    }
    private void createConnection() {
        try {
            Connection connection = DriverManager.getConnection(db.DATABASE_URL,
                    db.DATABASE_LOGIN, db.DATABASE_PASS);
            ProxyConnection proxyConnection = new ProxyConnection(connection);
            this.connectionQueue.put(proxyConnection);
        } catch (SQLException | InterruptedException e) {
            LOGGER.error(e);
        }
    }
    public static ConnectionPool getInstance() {
        if (!instanceCreated.get()) {
            lock.lock();
            try {
                if (instance == null) {
                    instance = new ConnectionPool();
                    instanceCreated.set(true);
                }
            } finally {
                lock.unlock();
            }
        }
        return instance;
    }
    public ProxyConnection getConnection() {
        ProxyConnection connection = null;
        try {
            connection = connectionQueue.take();
        } catch (InterruptedException e) {
            LOGGER.error(e);
        }
        return connection;
    }
    public void terminatePool() {
        try {
            for (int i = 0; i < db.POOL_SIZE; i++) {
                connectionQueue.take().terminateConnection();
            }
        } catch (SQLException | InterruptedException e) {
            LOGGER.error(e);
        }
    }
    void returnConnection(final ProxyConnection connection) {
        try {
            connectionQueue.put(connection);
        } catch (InterruptedException e) {
            LOGGER.error(e);
        }
    }
    private int size() {
        return connectionQueue.size();
    }
}
