package configuration.pool;

import database.pool.ConnectionPool;

/**
 * Configuration for connection pool.
 * @see ConnectionPool
 */
public class PoolConfiguration {

    public String DB_USER_NAME;
    public String DB_PASSWORD;
    public String DB_URL;
    public String DB_DRIVER;
    public Integer DB_MAX_CONNECTIONS;


    private static PoolConfiguration ourInstance = new PoolConfiguration();
    /**
     * @return Instance of configuration.
     */
    public static PoolConfiguration getInstance() {
        return ourInstance;
    }

    private PoolConfiguration() {
        init();
    }

    private void init() {
        DB_USER_NAME = "root";
        DB_PASSWORD = "1234";
        DB_URL = "jdbc:mysql://localhost:3306/black_jack_db?useSSL=false";
        DB_DRIVER = "com.mysql.jdbc.Driver";
        DB_MAX_CONNECTIONS = 50;
    }

}
