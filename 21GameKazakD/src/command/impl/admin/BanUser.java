package command.impl.admin;

import bean.User;
import command.Command;
import database.dao.DAOFactory;
import database.dao.impl.UserDAO;
import configuration.page.PageConfiguration;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class BanUser implements Command {

    private boolean banned;
    private int id;

    /**
     * Set ban for the user.
     * @param request  HTTP request.
     * @param response HTTP response.
     * @return JSP page for redirection.
     * @see Command
     */
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getSession().getAttribute("user") == null || !((User)request.getSession().getAttribute("user")).isAdmin())
            return PageConfiguration.getInstance().getProperty(PageConfiguration.LOGIN_PAGE_PATH);
        initRequestParameters(request);
        updateUser();
        return null;
    }

    private void initRequestParameters(HttpServletRequest request){
        banned = Boolean.parseBoolean(request.getParameter("value"));
        id = Integer.parseInt(request.getParameter("id"));
    }

    private void updateUser(){
        UserDAO userDAO = DAOFactory.getInstance().getUserDAO();
        User user = userDAO.getEntityById(id);
        if (user.isAdmin()) return;
        user.setBanned(banned);
        userDAO.update(user);
        userDAO.returnConnection();
    }

}
