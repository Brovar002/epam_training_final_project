package by.goncharov.epamsound;

import by.goncharov.epamsound.dao.ConnectionPool;
import by.goncharov.epamsound.dao.Transaction;
import org.junit.*;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * The type Connection pool test.
 */
public class ConnectionPoolTest {
    private static ConnectionPool pool;
    private static ArrayList<Transaction> connections;

    /**
     * Init connection pool.
     */
    @BeforeClass
    public static void initConnectionPool() {
        pool = ConnectionPool.getInstance();
    }

    /**
     * Init connections.
     */
    @Before
    public void initConnections() {
        connections = new ArrayList<>();
    }

    /**
     * Destroy connections.
     */
    @After
    public void destroyConnections() {
        connections.clear();
    }

    /**
     * Close connection pool.
     */
    @AfterClass
    public static void closeConnectionPool() {
        pool.terminatePool();
    }

    /**
     * Check get connection.
     *
     * @throws SQLException the sql exception
     */
    @Test
    public void checkGetConnection() throws SQLException {
        Transaction connection = pool.getConnection();
        Assert.assertNotNull(connection);
        connection.close();
    }

    /**
     * Check return connection.
     *
     * @throws SQLException the sql exception
     */
    @Test
    public void checkReturnConnection() throws SQLException {
        Transaction connectionFirst = pool.getConnection();
        connectionFirst.close();
        Transaction connectionSecond;
        for (int i = 0; i < 9; i++) {
            connectionSecond = pool.getConnection();
            connectionSecond.close();
        }
        connectionSecond = pool.getConnection();
        connectionSecond.close();
        Assert.assertEquals(connectionFirst, connectionSecond);
    }
}
