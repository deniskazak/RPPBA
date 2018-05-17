package command.impl.rating;

import bean.User;
import command.Command;
import configuration.page.PageConfiguration;
import database.dao.DAOFactory;
import database.dao.impl.UserDAO;
import database.pool.ConnectionPool;
//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class ShowRating implements Command {

    private int page = 1;
    private int pagesCount = 1;

    // private static Logger logger = LogManager.getLogger(ShowRating.class);

    /**
     * Present rating of users.
     *
     * @param request  HTTP request.
     * @param response HTTP response.
     * @return JSP page for redirection.
     * @see Command
     */
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getSession().getAttribute("user") == null)
            return PageConfiguration.getInstance().getProperty(PageConfiguration.LOGIN_PAGE_PATH);
        initHTTPParameters(request);
        if (page < 1) {
            initRating(request, 1);
            return PageConfiguration.getInstance().getProperty(PageConfiguration.RATING_PAGE_PATH);
        } else if (page > pagesCount) {
            initRating(request, pagesCount);
            return PageConfiguration.getInstance().getProperty(PageConfiguration.RATING_PAGE_PATH);
        } else {
            initRating(request, page);
            return PageConfiguration.getInstance().getProperty(PageConfiguration.RATING_PAGE_PATH);
        }
    }

    private void initHTTPParameters(HttpServletRequest request) {
        UserDAO userDAO = DAOFactory.getInstance().getUserDAO();
        String pageParam = request.getParameter("page");
        if (pageParam != null) {
            page = Integer.parseInt(pageParam);
        }
        pagesCount = userDAO.getPagesCount();
        userDAO.returnConnection();
    }

    private void initRating(HttpServletRequest request, int page) {
        User user = (User) request.getSession().getAttribute("user");
        UserDAO userDAO = DAOFactory.getInstance().getUserDAO();
        List<User> users = userDAO.getRating(page);
        request.setAttribute("users", users);
        request.setAttribute("nickname", user.getNickname());
        request.setAttribute("admin", user.isAdmin());
        request.setAttribute("pagesCount", pagesCount);
        request.setAttribute("currentPage", page);
        userDAO.returnConnection();
    }


}
