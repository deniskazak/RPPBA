package database.dao.impl;

import bean.Message;
import bean.User;
import database.dao.AbstractDAO;
import database.pool.ConnectionPool;
import util.RequestUtil;
//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;

import java.io.UnsupportedEncodingException;
import java.sql.*;
import java.util.*;
import java.util.Date;

/**
 * Class for work with bean Message and database.
 */
public class MessageDAO extends AbstractDAO<Message, Integer> {

//    private static Logger logger = LogManager.getLogger(MessageDAO.class);

    /**
     * @return Get all messages from table.
     */
    @Override
    public List<Message> getAll() {
        List<Message> messages = new LinkedList<>();
        try {
            Connection cn = null;
            ConnectionPool connectionPool = ConnectionPool.getInstance();
            try {
                cn = connectionPool.getConnection();
                PreparedStatement st = null;
                try {
                    st = cn.prepareStatement("SELECT * FROM black_jack_db.messages");
                    ResultSet rs = null;
                    try {
                        rs = st.executeQuery();
                        while (rs.next()) {
                            Message message = new Message();
                            message.setId(rs.getInt("id"));
                            message.setSender_id(rs.getInt("sender_id"));
                            message.setDest_id(rs.getInt("dest_id"));
                            message.setMessage(rs.getString("message"));
                            message.setDate(rs.getDate("date"));
                            messages.add(message);
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
            //           logger.error(e.getMessage());
        }
        return messages;
    }

    /**
     * @param id Message's id.
     * @return Message by it's id.
     */
    @Override
    public Message getEntityById(Integer id) {
        Message message = null;
        try {
            Connection cn = null;
            ConnectionPool connectionPool = ConnectionPool.getInstance();
            try {
                cn = connectionPool.getConnection();
                PreparedStatement st = null;
                try {
                    st = cn.prepareStatement("SELECT * FROM messages WHERE id = ?");
                    st.setInt(1, id);
                    ResultSet rs = null;
                    try {
                        rs = st.executeQuery();
                        while (rs.next()) {
                            message = new Message();
                            message.setId(rs.getInt("id"));
                            message.setSender_id(rs.getInt("sender_id"));
                            message.setDest_id(rs.getInt("dest_id"));
                            message.setMessage(rs.getString("message"));
                            message.setDate(rs.getDate("date"));
                            return message;
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
            //           logger.error(e.getMessage());
        }
        return message;
    }

    /**
     * @param entity Modified message.
     */
    @Override
    public void update(Message entity) {
        try {
            Connection cn = null;
            ConnectionPool connectionPool = ConnectionPool.getInstance();
            try {
                cn = connectionPool.getConnection();
                PreparedStatement st = null;
                try {
                    st = cn.prepareStatement(
                            "UPDATE black_jack_db.messages " +
                                    "SET " +
                                    "    sender_id = ?, " +
                                    "dest_id = ?, " +
                                    "message = ?, " +
                                    "date = ? " +
                                    "WHERE " +
                                    "    id = ?");
                    st.setInt(1, entity.getSender_id());
                    st.setInt(2, entity.getDest_id());
                    st.setString(3, entity.getMessage());
                    st.setTimestamp(4, new Timestamp(entity.getDate().getTime()));
                    st.setInt(5, entity.getId());
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
     * Delete message from table.
     *
     * @param id message's id.
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
                    st = cn.prepareStatement("DELETE FROM messages WHERE id = ?");
                    st.setInt(1, id);
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
     * Add new message in table.
     *
     * @param entity New message.
     */
    @Override
    public void create(Message entity) {
        String message = null;
        try {
            message = new String(entity.getMessage().getBytes("UTF-8"), "UTF-8");
            Connection cn = null;
            ConnectionPool connectionPool = ConnectionPool.getInstance();
            try {
                cn = connectionPool.getConnection();
                PreparedStatement st = null;
                try {
                    st = cn.prepareStatement("INSERT INTO messages (sender_id, dest_id, message, date) VALUES (?, ?, ?, ?)");
                    st.setInt(1, entity.getSender_id());
                    st.setInt(2, entity.getDest_id());
                    st.setString(3, message);
                    st.setTimestamp(4, new Timestamp(entity.getDate().getTime()));
                    st.executeUpdate();
                } finally {
                    if (st != null) st.close();
                }
            } finally {
                if (cn != null) connectionPool.returnConnection(cn);
            }
        } catch (SQLException e) {
            //           logger.error(e.getMessage());
        } catch (UnsupportedEncodingException e) {
            //           logger.error(e.getMessage());
        }
    }

    /**
     * @param user Current user.
     * @param page Page to preset.
     * @return Messages addressed to user.
     */
    public Map<Message, User> getMessages(User user, int page) {
        Map<Message, User> messages = new LinkedHashMap<>();
        try {
            Connection cn = null;
            ConnectionPool connectionPool = ConnectionPool.getInstance();
            try {
                cn = connectionPool.getConnection();
                PreparedStatement st = null;
                try {
                    st = cn.prepareStatement(
                            "SELECT messages.id, users.nickname, messages.message, messages.date " +
                                    "FROM messages " +
                                    "LEFT JOIN users ON messages.sender_id = users.id " +
                                    "WHERE messages.dest_id = ? " +
                                    "ORDER BY messages.date DESC LIMIT 10 OFFSET ?");
                    st.setInt(1, user.getId());
                    st.setInt(2, 10 * (page - 1));
                    ResultSet rs = null;
                    try {
                        rs = st.executeQuery();
                        while (rs.next()) {
                            User sender = new User();
                            Message message = new Message();
                            sender.setNickname(rs.getString("nickname"));
                            String content = RequestUtil.getInstance().htmlSpecialChar(rs.getString("message"));
                            message.setMessage(content);
                            message.setId(rs.getInt("id"));
                            message.setDate(new Date(rs.getTimestamp("date").getTime()));
                            messages.put(message, sender);
                        }
                        return messages;
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
        return messages;
    }

    public int getPagesCount(User user) {
        int pagesCount = 1;
        try {
            Connection cn = null;
            ConnectionPool connectionPool = ConnectionPool.getInstance();
            try {
                cn = connectionPool.getConnection();
                PreparedStatement st = null;
                try {
                    st = cn.prepareStatement("SELECT COUNT(id) FROM messages WHERE messages.dest_id = ?");
                    st.setInt(1, user.getId());
                    ResultSet rs = null;
                    try {
                        rs = st.executeQuery();
                        rs.next();
                        int count = rs.getInt(1);
                        if (count == 0) count = 1;
                        pagesCount = (int) Math.ceil(count / 10.0);
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
        return pagesCount;
    }

    /**
     * Delete all user's messages.
     *
     * @param userId User's id.
     */
    public void deleteUsersMessages(int userId) {
        try {
            Connection cn = null;
            ConnectionPool connectionPool = ConnectionPool.getInstance();
            try {
                cn = connectionPool.getConnection();
                PreparedStatement st = null;
                try {
                    st = cn.prepareStatement("DELETE FROM messages WHERE sender_id = ? OR dest_id = ?");
                    st.setInt(1, userId);
                    st.setInt(2, userId);
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
