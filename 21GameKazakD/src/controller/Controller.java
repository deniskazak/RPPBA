package controller; /**
 * Created by Деня on 06.02.2017.
 */
import command.Command;
import util.RequestUtil;
import configuration.page.PageConfiguration;
//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;

/**
 * Controller for management HTTP requests.
 */
public class Controller extends HttpServlet{

    //  private static Logger logger = LogManager.getLogger(Controller.class);

    private RequestUtil requestUtil =  RequestUtil.getInstance();

    /**
     * Manage HTTP "GET" action
     * @param request HTTP request.
     * @param response HTTP response.
     * @see Command
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }
    /**
     * Manage HTTP "POST" action
     * @param request HTTP request.
     * @param response HTTP response.
     * @see Command
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String page = null;
        try {
            Command command =  requestUtil.getCommand(request);
            page = command.execute(request, response);
        } catch (ServletException e) {
            // logger.error(e.getMessage());
            page = PageConfiguration.getInstance() .getProperty(PageConfiguration.LOGIN_PAGE_PATH);
        } catch (IOException e) {
            //   logger.error(e.getMessage());
            page = PageConfiguration.getInstance() .getProperty(PageConfiguration.LOGIN_PAGE_PATH);
        }
        if (page!=null){
            RequestDispatcher dispatcher =  getServletContext().getRequestDispatcher(page);
            dispatcher.forward(request, response);
        }
    }
}
