package command.impl.common;

import command.Command;
import configuration.page.PageConfiguration;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class NoCommand implements Command {

    /**
     * Return to the login page if the command doesn't exist.
     * @param request  HTTP request.
     * @param response HTTP response.
     * @return JSP page for redirection.
     * @see Command
     */
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String page = PageConfiguration.getInstance() .getProperty(PageConfiguration.LOGIN_PAGE_PATH);
        return page;
    }
}
