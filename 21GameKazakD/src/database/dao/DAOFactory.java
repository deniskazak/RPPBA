package database.dao;

import database.dao.impl.GameDAO;
import database.dao.impl.MessageDAO;
import database.dao.impl.UserDAO;


public class DAOFactory {

    private final GameDAO gameDAO = new GameDAO();
    private final MessageDAO messageDAO = new MessageDAO();
    private final UserDAO userDAO = new UserDAO();
    private static final DAOFactory ourInstance = new DAOFactory();
    /**
     * Get DAO factory. .
     * @return DAO factory.
     */
    public static DAOFactory getInstance() {
        return ourInstance;
    }

    private DAOFactory() {
    }
    /**
     * @return Game DAO.
     */
    public GameDAO getGameDAO() {
        return gameDAO;
    }
    /**
     * @return Message DAO.
     */
    public MessageDAO getMessageDAO() {
        return messageDAO;
    }
    /**
     * @return User DAO.
     */
    public UserDAO getUserDAO() {
        return userDAO;
    }
}
