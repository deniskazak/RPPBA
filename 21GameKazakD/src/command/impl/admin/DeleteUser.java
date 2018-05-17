package command.impl.admin;

import bean.User;
import command.Command;
import configuration.page.PageConfiguration;
import database.dao.DAOFactory;
import database.dao.impl.GameDAO;
import database.dao.impl.MessageDAO;
import database.dao.impl.UserDAO;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class DeleteUser implements Command {

    private int userId;

    /**
     * Delete user from database.
     * @param request  HTTP request.
     * @param response HTTP response.
     * @return JSP page for redirection.
     * @see Command
     */
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getSession().getAttribute("user") == null || !((User) request.getSession().getAttribute("user")).isAdmin())
            return PageConfiguration.getInstance().getProperty(PageConfiguration.LOGIN_PAGE_PATH);
        initRequestParameters(request);
        deleteUsersGames();
        deleteUsersMessages();
        deleteUser();
        return null;
    }

    private void initRequestParameters(HttpServletRequest request){
        userId = Integer.parseInt(request.getParameter("id"));
    }

    private void deleteUsersGames() {
        GameDAO gameDAO = DAOFactory.getInstance().getGameDAO();
        gameDAO.deleteUsersGames(userId);
        gameDAO.returnConnection();
    }

    private void deleteUsersMessages(){
        MessageDAO messageDAO = DAOFactory.getInstance().getMessageDAO();
        messageDAO.deleteUsersMessages(userId);
        messageDAO.returnConnection();
    }

    private void deleteUser(){
        UserDAO userDAO = DAOFactory.getInstance().getUserDAO();
        userDAO.delete(userId);
        userDAO.returnConnection();
    }
}
