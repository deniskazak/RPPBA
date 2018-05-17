package command.impl.page;

import bean.User;
import command.Command;
import configuration.page.PageConfiguration;
import util.MultiplayerUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class Mode implements Command {

    /**
     * Select game mode.
     * @param request  HTTP request.
     * @param response HTTP response.
     * @return JSP page for redirection.
     * @see Command
     */
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getSession().getAttribute("user")==null)
            return PageConfiguration.getInstance().getProperty(PageConfiguration.LOGIN_PAGE_PATH);
        else {
            String mode = request.getParameter("mode");
            User user = (User) request.getSession().getAttribute("user");
            if (mode.startsWith("1")){
                request.setAttribute("money", user.getMoney());
                request.setAttribute("nickname", user.getNickname());
                request.setAttribute("admin", user.isAdmin());
                return PageConfiguration.getInstance().getProperty(PageConfiguration.TABLE_PAGE_PATH);
            } else if (mode.startsWith("2")){
                MultiplayerUtil.getInstance().join(user);
                request.setAttribute("money", user.getMoney());
                request.setAttribute("nickname", user.getNickname());
                request.setAttribute("admin", user.isAdmin());
                return PageConfiguration.getInstance().getProperty(PageConfiguration.TABLE2_PAGE_PATH);
            }

        }
        return null;
    }

}
