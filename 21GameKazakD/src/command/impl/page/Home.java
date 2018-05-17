package command.impl.page;

import bean.User;
import command.Command;
import configuration.page.PageConfiguration;
import util.MultiplayerUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class Home implements Command {

    /**
     * Return to the selection mode page.
     * @param request  HTTP request.
     * @param response HTTP response.
     * @return JSP page for redirection.
     * @see Command
     */
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = (User) request.getSession().getAttribute("user");
        MultiplayerUtil.getInstance().deleteUserGame(user);
        user.setCurrentGame(null);
        request.getSession().setAttribute("user",user);
        request.getSession().setAttribute("game", null);
        request.setAttribute("nickname", user.getNickname());
        return PageConfiguration.getInstance(). getProperty(PageConfiguration.MODE_PAGE_PATH);
    }
}
