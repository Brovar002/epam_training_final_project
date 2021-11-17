package by.goncharov.epamsound;

import by.goncharov.epamsound.manager.ConnectionPool;
import by.goncharov.epamsound.manager.Transaction;
import org.junit.*;

import java.sql.SQLException;
import java.util.ArrayList;

public class ConnectionPoolTest {
    private static ConnectionPool pool;
    private static ArrayList<Transaction> connections;

    @BeforeClass
    public static void initConnectionPool() {
        pool = ConnectionPool.getInstance();
    }

    @Before
    public void initConnections() {
        connections = new ArrayList<>();
    }

    @After
    public void destroyConnections() {
        connections.clear();
    }

    @AfterClass
    public static void closeConnectionPool() {
        pool.terminatePool();
    }

    @Test
    public void checkGetConnection() throws SQLException {
        Transaction connection = pool.getConnection();
        Assert.assertNotNull(connection);
    }

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
