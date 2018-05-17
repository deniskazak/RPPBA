package database.dao;

import database.pool.ConnectionPool;
//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

/**
 * Abstract class for work with database and beans.
 */
public abstract class AbstractDAO<E, K> {
    //   private static Logger logger = LogManager.getLogger(AbstractDAO.class);
    private Connection connection;
    private ConnectionPool connectionPool;

    public AbstractDAO() {
        connectionPool = ConnectionPool.getInstance();
        connection = connectionPool.getConnection();
    }
    /**
     * @return  All entities from the table.
     */
    public abstract List<E> getAll();
    /**
     * @param id Id of the entity in the table.
     * @return  Entity by its id.
     */
    public abstract E getEntityById(K id);
    /**
     * @param entity Modified entity in the table.
     */
    public abstract void update(E entity);
    /**
     * @param id Entity's id in the table.
     */
    public abstract void delete(K id);
    /**
     * @param entity Add new entity in the table.
     */
    public abstract void create(E entity);
    /**
     * Return used connection into the connection pool.
     */
    public void returnConnection() {
        connectionPool.returnConnection(connection);
    }
    /**
     * Prepare the SQL statement for the query.
     * @param sql SQL query.
     * @return SQL statement.
     */
    protected PreparedStatement getPrepareStatement(String sql) {
        PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement(sql);
        } catch (SQLException e) {
            //           logger.error(e.getMessage());
        }
        return ps;
    }
    /**
     * Close the SQL statement after the query.
     * @param ps Used SQL statement.
     */
    protected void closePrepareStatement(PreparedStatement ps) {
        if (ps != null) {
            try {
                ps.close();
            } catch (SQLException e) {
                //              logger.error(e.getMessage());
            }
        }
    }

}
