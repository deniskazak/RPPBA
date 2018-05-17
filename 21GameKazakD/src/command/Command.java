package command;

import configuration.page.PageConfiguration;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Interface for servlet management
 */
public interface Command {
    /**
     * Execute command for servlet
     * @param request HTTP request.
     * @param response HTTP response.
     * @throws ServletException
     * @throws IOException
     * @return JSP page for redirection.
     * @see PageConfiguration
     */
    String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException;
}
