package database.dao.impl;

import bean.Game;
import bean.Message;
import bean.User;
import database.dao.AbstractDAO;
import database.pool.ConnectionPool;
//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

/**
 * Class for work with bean 'game' and database.
 */
public class GameDAO extends AbstractDAO<Game,Integer> {

//    private static Logger logger = LogManager.getLogger(GameDAO.class);

    /**
     * @return List of all games.
     */
    @Override
    public List<Game> getAll() {
        List<Game> games = new LinkedList<>();
        try {
            Connection cn = null;
            ConnectionPool connectionPool = ConnectionPool.getInstance();
            try {
                cn = connectionPool.getConnection();
                PreparedStatement st = null;
                try {
                    st = cn.prepareStatement("SELECT * FROM games");
                    ResultSet rs = null;
                    try {
                        rs = st.executeQuery();
                        while (rs.next()) {
                            Game game = new Game();
                            game.setId(rs.getInt("id"));
                            game.setUserId(rs.getInt("users_id"));
                            game.setDate(rs.getDate("date"));
                            game.setBet(rs.getInt("bet"));
                            game.setWin(rs.getInt("win"));
                            game.setRate(rs.getInt("rate"));
                            games.add(game);
                        }
                    } finally {
                        if (rs != null) rs.close();
                    }
                } finally {
                    if (st != null) st.close();
                }
            } finally {
                if (cn != null) connectionPool.returnConnection(cn);
            }
        } catch (SQLException e) {
            //          logger.error(e.getMessage());
        }
        return games;
    }
    /**
     * @param id Game's id.
     * @return Game by its id.
     */
    @Override
    public Game getEntityById(Integer id) {
        Game game = new Game();
        try {
            Connection cn = null;
            ConnectionPool connectionPool = ConnectionPool.getInstance();
            try {
                cn = connectionPool.getConnection();
                PreparedStatement st = null;
                try {
                    st = cn.prepareStatement("SELECT * FROM games WHERE id = ?");
                    st.setInt(1, id);
                    ResultSet rs = null;
                    try {
                        rs = st.executeQuery();
                        rs.next();
                        game.setId(rs.getInt("id"));
                        game.setUserId(rs.getInt("users_id"));
                        game.setDate(rs.getDate("date"));
                        game.setBet(rs.getInt("bet"));
                        game.setWin(rs.getInt("win"));
                        game.setRate(rs.getInt("rate"));
                    } finally {
                        if (rs != null) rs.close();
                    }
                } finally {
                    if (st != null) st.close();
                }
            } finally {
                if (cn != null) connectionPool.returnConnection(cn);
            }
        } catch (SQLException e) {
            //           logger.error(e.getMessage());
        }
        return game;
    }
    /**
     * @param game Modified game in the table.
     */
    @Override
    public void update(Game game) {
        try {
            Connection cn = null;
            ConnectionPool connectionPool = ConnectionPool.getInstance();
            try {
                cn = connectionPool.getConnection();
                PreparedStatement st = null;
                try {
                    st = cn.prepareStatement(
                            "UPDATE black_jack_db.games " +
                                    "SET " +
                                    "   users_id = ?, " +
                                    "   date = ?, " +
                                    "   bet = ?, " +
                                    "   win = ?, " +
                                    "   rate = ? " +
                                    "WHERE " +
                                    "   id = ?");
                    st.setInt(1, game.getUserId());
                    st.setTimestamp(2, new Timestamp(game.getDate().getTime()));
                    st.setInt(3, game.getBet());
                    st.setInt(4, game.getWin());
                    st.setInt(5, game.getRate());
                    st.setInt(6, game.getId());
                    st.executeUpdate();
                } finally {
                    if (st != null) st.close();
                }
            } finally {
                if (cn != null) connectionPool.returnConnection(cn);
            }
        } catch (SQLException e) {
            //         logger.error(e.getMessage());
        }
    }
    /**
     * Delete the game from the table.
     * @param id Game's id.
     */
    @Override
    public void delete(Integer id) {
        try {
            Connection cn = null;
            ConnectionPool connectionPool = ConnectionPool.getInstance();
            try {
                cn = connectionPool.getConnection();
                PreparedStatement st = null;
                try {
                    st = cn.prepareStatement("DELETE FROM games WHERE id = ?");
                    st.setInt(1, id);
                    st.executeUpdate();
                } finally {
                    if (st != null) st.close();
                }
            } finally {
                if (cn != null) connectionPool.returnConnection(cn);
            }
        } catch (SQLException e) {
            //         logger.error(e.getMessage());
        }
    }
    /**
     * Add new game in the table.
     * @param game Inserted game.
     */
    @Override
    public void create(Game game) {
        try {
            Connection cn = null;
            ConnectionPool connectionPool = ConnectionPool.getInstance();
            try {
                cn = connectionPool.getConnection();
                PreparedStatement st = null;
                try {
                    st = cn.prepareStatement("INSERT INTO games (users_id, date, bet, win, rate) VALUES (?, ?, ?, ?, ?)");
                    st.setInt(1, game.getUserId());
                    st.setTimestamp(2, new Timestamp(game.getDate().getTime()));
                    st.setInt(3, game.getBet());
                    st.setInt(4, game.getWin());
                    st.setInt(5, game.getRate());
                    st.executeUpdate();
                } finally {
                    if (st != null) st.close();
                }
            } finally {
                if (cn != null) connectionPool.returnConnection(cn);
            }
        } catch (SQLException e) {
//            logger.error(e.getMessage());
        }
    }
    /**
     * Delete all user's games.
     * @param userId User's id.
     */
    public void deleteUsersGames(int userId){
        try {
            Connection cn = null;
            ConnectionPool connectionPool = ConnectionPool.getInstance();
            try {
                cn = connectionPool.getConnection();
                PreparedStatement st = null;
                try {
                    st = cn.prepareStatement("DELETE FROM games WHERE users_id = ?");
                    st.setInt(1,userId);
                    st.executeUpdate();
                } finally {
                    if (st != null) st.close();
                }
            } finally {
                if (cn != null) connectionPool.returnConnection(cn);
            }
        } catch (SQLException e) {
            //           logger.error(e.getMessage());
        }
    }
}
