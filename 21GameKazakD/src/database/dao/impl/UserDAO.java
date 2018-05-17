package database.dao.impl;

import bean.User;
import database.dao.AbstractDAO;
import database.pool.ConnectionPool;
//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

/**
 * Class for work with bean User and database.
 */
public class UserDAO extends AbstractDAO<User, Integer> {

//    private static Logger logger = LogManager.getLogger(UserDAO.class);
    /**
     * Get all users.
     * @return List of users.
     */
    @Override
    public List<User> getAll() {
        List<User> users = new LinkedList<>();
        PreparedStatement ps = getPrepareStatement("SELECT * FROM black_jack_db.users");
        try {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setNickname(rs.getString("nickname"));
                user.setEmail(rs.getString("email"));
                user.setPassword(rs.getString("password"));
                user.setMoney(rs.getInt("money"));
                user.setBanned(rs.getBoolean("banned"));
                user.setAdmin(rs.getBoolean("admin"));
                users.add(user);
            }
        } catch (SQLException e) {
            //           logger.error(e.getMessage());
        } finally {
            closePrepareStatement(ps);
        }
        return users;
    }

    public List<User> getUsers(int page){
        List<User> users = new LinkedList<>();
        PreparedStatement ps = getPrepareStatement("SELECT * FROM black_jack_db.users LIMIT 10 OFFSET ?");
        try {
            ps.setInt(1,10*(page-1));
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setNickname(rs.getString("nickname"));
                user.setEmail(rs.getString("email"));
                user.setPassword(rs.getString("password"));
                user.setMoney(rs.getInt("money"));
                user.setBanned(rs.getBoolean("banned"));
                user.setAdmin(rs.getBoolean("admin"));
                users.add(user);
            }
        } catch (SQLException e) {
            //           logger.error(e.getMessage());
        } finally {
            closePrepareStatement(ps);
        }
        return users;
    }

    public List<User> getRating(int page){
        List<User> users = new LinkedList<>();
        try {
            Connection cn = null;
            ConnectionPool connectionPool = ConnectionPool.getInstance();
            try {
                cn = connectionPool.getConnection();
                PreparedStatement st = null;
                try {
                    st = cn.prepareStatement("SELECT users.nickname, SUM(games.rate) AS 'rate' " +
                            "FROM games INNER JOIN users ON games.users_id = users.id " +
                            "GROUP BY games.users_id ORDER BY rate DESC LIMIT 10 OFFSET ?");
                    st.setInt(1,10*(page-1));
                    ResultSet rs = null;
                    try {
                        rs = st.executeQuery();
                        while (rs.next()){
                            User user = new User();
                            user.setNickname(rs.getString("nickname"));
                            user.setRate(rs.getInt("rate"));
                            users.add(user);
                        }
                        return users;
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
        return users;
    }

    public int getPagesCount(){
        int pagesCount = 1;
        try {
            Connection cn = null;
            ConnectionPool connectionPool = ConnectionPool.getInstance();
            try {
                cn = connectionPool.getConnection();
                PreparedStatement st = null;
                try {
                    st = cn.prepareStatement("SELECT COUNT(id) FROM users");
                    ResultSet rs = null;
                    try {
                        rs = st.executeQuery();
                        rs.next();
                        int count = rs.getInt(1);
                        if (count == 0) count = 1;
                        pagesCount = (int) Math.ceil(count/10.0);
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
        return pagesCount;
    }
    /**
     * @return User by it's id.
     * @param id User's id.
     */
    @Override
    public User getEntityById(Integer id) {
        User user = new User();
        try {
            Connection cn = null;
            ConnectionPool connectionPool = ConnectionPool.getInstance();
            try {
                cn = connectionPool.getConnection();
                PreparedStatement st = null;
                try {
                    st = cn.prepareStatement("SELECT * FROM users WHERE id = ?");
                    st.setInt(1, id);
                    ResultSet rs = null;
                    try {
                        rs = st.executeQuery();
                        rs.next();
                        user.setId(rs.getInt("id"));
                        user.setNickname(rs.getString("nickname"));
                        user.setEmail(rs.getString("email"));
                        user.setPassword(rs.getString("password"));
                        user.setMoney(rs.getInt("money"));
                        user.setBanned(rs.getBoolean("banned"));
                        user.setAdmin(rs.getBoolean("admin"));
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
//            logger.error(e.getMessage());
        }
        return user;
    }
    /**
     * Update user.
     * @param entity Modified user.
     */
    @Override
    public void update(User entity) {
        try {
            Connection cn = null;
            ConnectionPool connectionPool = ConnectionPool.getInstance();
            try {
                cn = connectionPool.getConnection();
                PreparedStatement st = null;
                try {
                    st = cn.prepareStatement(
                            "UPDATE black_jack_db.users " +
                                    "SET " +
                                    "    nickname = ?, " +
                                    "email = ?, " +
                                    "password = ?, " +
                                    "money = ?, " +
                                    "banned = ?, " +
                                    "admin = ? " +
                                    "WHERE " +
                                    "    id = ?");
                    st.setString(1, entity.getNickname());
                    st.setString(2, entity.getEmail());
                    st.setString(3, entity.getPassword());
                    st.setInt(4, entity.getMoney());
                    st.setBoolean(5, entity.isBanned());
                    st.setBoolean(6, entity.isAdmin());
                    st.setInt(7, entity.getId());
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
     * Delete user by it's id.
     * @param id User's id.
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
                    st = cn.prepareStatement("DELETE FROM users WHERE id = ?");
                    st.setInt(1, id);
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

    /**
     * Add new user in table.
     * @param entity New user.
     */
    @Override
    public void create(User entity) {
        try {
            Connection cn = null;
            ConnectionPool connectionPool = ConnectionPool.getInstance();
            try {
                cn = connectionPool.getConnection();
                PreparedStatement st = null;
                try {
                    st = cn.prepareStatement("INSERT INTO users (nickname, email, password, money, banned, admin) " +
                            "VALUES (?, ?, MD5(?), ?, ?, ?)");

                    st.setString(1, entity.getNickname());
                    st.setString(2, entity.getEmail());
                    st.setString(3, entity.getPassword());
                    st.setInt(4, entity.getMoney());
                    st.setBoolean(5, entity.isBanned());
                    st.setBoolean(6, entity.isAdmin());
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

    /**
     * Get user by it's nickname.
     * @param nickname User's nickname.
     * @return User by its nickname;
     */
    public User getEntityByNickname(String nickname) {
        User user = new User();
        try {
            Connection cn = null;
            ConnectionPool connectionPool = ConnectionPool.getInstance();
            try {
                cn = connectionPool.getConnection();
                PreparedStatement st = null;
                try {
                    st = cn.prepareStatement("SELECT * FROM users WHERE nickname = ?");
                    st.setString(1, nickname);
                    ResultSet rs = null;
                    try {
                        rs = st.executeQuery();
                        rs.next();
                        user.setId(rs.getInt("id"));
                        user.setNickname(rs.getString("nickname"));
                        user.setEmail(rs.getString("email"));
                        user.setPassword(rs.getString("password"));
                        user.setMoney(rs.getInt("money"));
                        user.setBanned(rs.getBoolean("banned"));
                        user.setAdmin(rs.getBoolean("admin"));
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
        return user;
    }

    /**
     * Check if user exists with the following email.
     * @param email User's email.
     * @return True if user exists with the following email.
     */
    public boolean isUserExistsWithEmail(String email) {
        try {
            Connection cn = null;
            ConnectionPool connectionPool = ConnectionPool.getInstance();
            try {
                cn = connectionPool.getConnection();
                PreparedStatement st = null;
                try {
                    st = cn.prepareStatement("SELECT * FROM users WHERE email = ?");
                    st.setString(1, email);
                    ResultSet rs = st.executeQuery();
                    return rs.next();
                } finally {
                    if (st != null) st.close();
                }
            } finally {
                if (cn != null) connectionPool.returnConnection(cn);
            }
        } catch (SQLException e) {
            //          logger.error(e.getMessage());
        }
        return false;
    }

    /**
     * Check if user exists with the following nickname.
     * @param nickname User's nickname.
     * @return True if user exists with the following nickname.
     */
    public boolean isUserExistsWithNickname(String nickname) {
        try {
            Connection cn = null;
            ConnectionPool connectionPool = ConnectionPool.getInstance();
            try {
                cn = connectionPool.getConnection();
                PreparedStatement st = null;
                try {
                    st = cn.prepareStatement("SELECT * FROM users WHERE nickname = ?");
                    st.setString(1, nickname);
                    ResultSet rs = st.executeQuery();
                    return rs.next();
                } finally {
                    if (st != null) st.close();
                }
            } finally {
                if (cn != null) connectionPool.returnConnection(cn);
            }
        } catch (SQLException e) {
            //          logger.error(e.getMessage());
        }
        return false;
    }
}

