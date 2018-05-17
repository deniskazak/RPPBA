package command.impl.game.multiplayer;

import bean.User;
import command.Command;
import util.MultiplayerUtil;
import configuration.page.PageConfiguration;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class FindSecondPlayer implements Command {

    private boolean isSecondPlayerFound;

    /**
     * Find second player for multiplayer game.
     * @param request  HTTP request.
     * @param response HTTP response.
     * @return JSP page for redirection.
     * @see Command
     */
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = (User) request.getSession().getAttribute("user");
        if (user == null)
            return PageConfiguration.getInstance().getProperty(PageConfiguration.LOGIN_PAGE_PATH);
        setSecondPlayerFound(user);
        PrintWriter writer = response.getWriter();
        response.setContentType("text/plain");
        response.setCharacterEncoding("UTF-8");
        String output = initResponseString();
        writer.write(output);
        return null;
    }

    private void setSecondPlayerFound(User user){
        if (MultiplayerUtil.getInstance().getSecondPlayer(user)!=null){
            isSecondPlayerFound = true;
        }
        else {
            isSecondPlayerFound = false;
        }
    }

    private String initResponseString(){
        String output = String.format("{\"isGame\":\"%s\"}", isSecondPlayerFound);
        return output;
    }
}
