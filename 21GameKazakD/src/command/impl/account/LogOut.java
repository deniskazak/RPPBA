package command.impl.account;

import command.Command;
import configuration.page.PageConfiguration;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LogOut implements Command {

    /**
     * Log out from the application.
     * @param request  HTTP request.
     * @param response HTTP response.
     * @return JSP page for redirection.
     * @see Command
     */
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getSession().invalidate();
        return PageConfiguration.getInstance().getProperty(PageConfiguration.LOGIN_PAGE_PATH);
    }
}
