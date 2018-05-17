package command.impl.money;

import bean.User;
import command.Command;
import configuration.page.PageConfiguration;
import database.dao.DAOFactory;
import database.dao.impl.UserDAO;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class Deposit implements Command {

    /**
     * Deposit money into the game.
     * @param request  HTTP request.
     * @param response HTTP response.
     * @return JSP page for redirection.
     * @see Command
     */
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getSession().getAttribute("user") == null)
            return PageConfiguration.getInstance().getProperty(PageConfiguration.LOGIN_PAGE_PATH);
        User user = (User) request.getSession().getAttribute("user");
        deposit(user);
        response.setContentType("text/plain");
        response.setCharacterEncoding("UTF-8");
        PrintWriter writer = response.getWriter();
        String output = initResponseString(user);
        writer.write(output);
        return null;
    }

    private void deposit(User user){
        user.setMoney(user.getMoney() + 100);
        UserDAO dao = DAOFactory.getInstance().getUserDAO();
        dao.update(user);
        dao.returnConnection();
    }

    private String initResponseString(User user){
        String output = String.format("{\"money\":\"%s\"}", user.getMoney());
        return output;
    }
}
