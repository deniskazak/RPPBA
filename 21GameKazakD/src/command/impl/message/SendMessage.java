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
import java.io.PrintWriter;
import java.util.Date;

public class SendMessage implements Command {

    /**
     * Send new message.
     * @param request  HTTP request.
     * @param response HTTP response.
     * @return JSP page for redirection.
     * @see Command
     */
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getSession().getAttribute("user") == null)
            return PageConfiguration.getInstance().getProperty(PageConfiguration.LOGIN_PAGE_PATH);
        String content =  request.getParameter("message");
        String toNickName = request.getParameter("to");
        String locale = (String) request.getSession().getAttribute("language");
        UserDAO userDAO = DAOFactory.getInstance().getUserDAO();
        if (!userDAO.isUserExistsWithNickname(toNickName)){
            response.setCharacterEncoding("UTF-8");
            PrintWriter writer = response.getWriter();
            String output = null;
            if (locale.equals("en") || locale.equals("en_EN")){
                output = String.format("{\"error\":\"%s\"}","Неверное имя пользователя!");
            } else if (locale.equals("ru")){
                output = String.format("{\"error\":\"%s\"}","Неверное имя пользователя!");
            }
            writer.write(output);
            userDAO.returnConnection();
            return null;
        }
        User from = (User)request.getSession().getAttribute("user");
        User to = userDAO.getEntityByNickname(toNickName);
        userDAO.returnConnection();
        Message message = new Message();
        message.setMessage(content);
        message.setSender_id(from.getId());
        message.setDest_id(to.getId());
        message.setDate(new Date());
        MessageDAO messagesDAO = DAOFactory.getInstance().getMessageDAO();
        messagesDAO.create(message);
        messagesDAO.returnConnection();
        return null;
    }
}
