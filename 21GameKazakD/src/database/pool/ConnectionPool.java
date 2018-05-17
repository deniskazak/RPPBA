package database.pool;

import configuration.pool.PoolConfiguration;
//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * Pool with connections to database.
 */
public class ConnectionPool {

    //   private static Logger logger = LogManager.getLogger(ConnectionPool.class);
    private final int MAX_POOL_SIZE = PoolConfiguration.getInstance().DB_MAX_CONNECTIONS;
    private BlockingQueue<Connection> availableConnections = new ArrayBlockingQueue<Connection>(MAX_POOL_SIZE);

    private final static ConnectionPool ourInstance = new ConnectionPool();

    /**
     * @return Instance of connection pool.
     */
    public static ConnectionPool getInstance() {
        return ourInstance;
    }

    private ConnectionPool() {
        initializeConnectionPool();
    }

    private void initializeConnectionPool() {
        while (!isPoolFull()) {
            availableConnections.offer(createConnection());
        }
    }

    private boolean isPoolFull() {
        if (availableConnections.size() < MAX_POOL_SIZE) {
            return false;
        }
        return true;
    }

    private Connection createConnection() {
        PoolConfiguration config = PoolConfiguration.getInstance();
        Connection connection = null;
        try {
            Class.forName(config.DB_DRIVER);
            connection = DriverManager.getConnection(
                    config.DB_URL, config.DB_USER_NAME, config.DB_PASSWORD);
        } catch (ClassNotFoundException e) {
            //          logger.error(e.getMessage());
        } catch (SQLException e) {
            //         logger.error(e.getMessage());
        }
        return connection;
    }

    /**
     * @return Connection to database.
     */
    public synchronized Connection getConnection() {
        Connection connection = null;
        try {
            connection = availableConnections.take();
        } catch (InterruptedException e) {
            //           logger.error(e.getMessage());
        }
        return connection;
    }

    /**
     * Return connection to the pool after work.
     * @param connection Used connection.
     */
    public void returnConnection(Connection connection) {
        availableConnections.offer(connection);
    }


}
