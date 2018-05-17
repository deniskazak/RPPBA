package command.impl.admin;

import bean.User;
import command.Command;
import configuration.page.PageConfiguration;
import database.dao.DAOFactory;
import database.dao.impl.UserDAO;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;


public class ShowUsers implements Command {

    private int page = 1;
    private int pagesCount = 1;

    /**
     * Show user list.
     *
     * @param request  HTTP request.
     * @param response HTTP response.
     * @return JSP page for redirection.
     * @see Command
     */
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getSession().getAttribute("user") == null || !((User) request.getSession().getAttribute("user")).isAdmin())
            return PageConfiguration.getInstance().getProperty(PageConfiguration.LOGIN_PAGE_PATH);
        initHTTPParameters(request);
        if (page < 1) {
            initUsers(request, 1);
            return PageConfiguration.getInstance().getProperty(PageConfiguration.USERS_PAGE_PATH);
        } else if (page > pagesCount) {
            initUsers(request, pagesCount);
            return PageConfiguration.getInstance().getProperty(PageConfiguration.USERS_PAGE_PATH);
        } else {
            initUsers(request, page);
            return PageConfiguration.getInstance().getProperty(PageConfiguration.USERS_PAGE_PATH);
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

    private void initUsers(HttpServletRequest request, int page) {
        User user = (User) request.getSession().getAttribute("user");
        UserDAO userDAO = DAOFactory.getInstance().getUserDAO();
        List<User> users = userDAO.getUsers(page);
        userDAO.returnConnection();
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).isAdmin()) users.remove(i);
        }
        request.setAttribute("users", users);
        request.setAttribute("nickname", user.getNickname());
        request.setAttribute("admin", user.isAdmin());
        request.setAttribute("pagesCount", pagesCount);
        request.setAttribute("currentPage", page);
    }

}
