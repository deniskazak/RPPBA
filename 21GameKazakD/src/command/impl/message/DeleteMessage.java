package command.impl.message;

import command.Command;
import configuration.page.PageConfiguration;
import database.dao.DAOFactory;
import database.dao.impl.MessageDAO;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class DeleteMessage implements Command {

    private int userId;

    /**
     * Delete message between users.
     * @param request  HTTP request.
     * @param response HTTP response.
     * @return JSP page for redirection.
     * @see Command
     */
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getSession().getAttribute("user") == null)
            return PageConfiguration.getInstance().getProperty(PageConfiguration.LOGIN_PAGE_PATH);
        initRequestParameters(request);
        deleteMessage();
        return null;
    }

    private void initRequestParameters(HttpServletRequest request) {
        userId = Integer.parseInt(request.getParameter("id"));
    }

    private void deleteMessage() {
        MessageDAO messageDAO = DAOFactory.getInstance().getMessageDAO();
        messageDAO.delete(userId);
        messageDAO.returnConnection();
    }
}
