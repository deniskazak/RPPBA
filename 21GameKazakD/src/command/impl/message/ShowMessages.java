package command.impl.message;

import bean.Message;
import bean.User;
import command.Command;
import configuration.page.PageConfiguration;
import database.dao.DAOFactory;
import database.dao.impl.MessageDAO;
import database.dao.impl.UserDAO;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ShowMessages implements Command {

    private int page = 1;
    private int pagesCount = 1;
    private Map<Message, User> messageMap = new LinkedHashMap<>();

    /**
     * Present all messages of the current user.
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
        if (page < 1){
            initMessages(request,1);
            return PageConfiguration.getInstance().getProperty(PageConfiguration.MESSAGES_PAGE_PATH);
        } else if (page > pagesCount){
            initMessages(request,pagesCount);
            return PageConfiguration.getInstance().getProperty(PageConfiguration.MESSAGES_PAGE_PATH);
        }
        else {
            initMessages(request,page);
            return PageConfiguration.getInstance().getProperty(PageConfiguration.MESSAGES_PAGE_PATH);
        }
    }

    private void initHTTPParameters(HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute("user");
        String pageParam = request.getParameter("page");
        if (pageParam != null){
            page = Integer.parseInt(pageParam);
        }
        MessageDAO messagesDAO = DAOFactory.getInstance().getMessageDAO();
        pagesCount = messagesDAO.getPagesCount(user);
        messagesDAO.returnConnection();
    }

    private void initMessages(HttpServletRequest request, int page) {
        User user = (User) request.getSession().getAttribute("user");
        MessageDAO messagesDAO = DAOFactory.getInstance().getMessageDAO();
        messageMap = messagesDAO.getMessages(user,page);
        request.setAttribute("nickname", user.getNickname());
        request.setAttribute("admin", user.isAdmin());
        request.setAttribute("pagesCount", pagesCount);
        request.setAttribute("messages", messageMap);
        request.setAttribute("currentPage", page);
        messagesDAO.returnConnection();
    }
}
