package command.impl.game.multiplayer;

import bean.Game;
import bean.User;
import command.Command;
import configuration.page.PageConfiguration;
import util.MultiplayerUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class WaitSecondPlayer implements Command {

    /**
     * Wait second player actions.
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
        PrintWriter writer = response.getWriter();
        boolean isReady;
        User secondPlayer = MultiplayerUtil.getInstance().getSecondPlayer(user);
        if (secondPlayer == null){
            isReady = true;
            String output = String.format("{\"isReady\":\"%s\"}", isReady);
            response.setContentType("text/plain");
            response.setCharacterEncoding("UTF-8");
            writer.write(output);
            return null;
        } else
        {
            Game gameSecondPlayer = secondPlayer.getCurrentGame();
            if (gameSecondPlayer == null || !gameSecondPlayer.isReady()){
                isReady = false;
            }
            else {
                isReady = true;
            }
            String output = String.format("{\"isReady\":\"%s\"}", isReady);
            response.setContentType("text/plain");
            response.setCharacterEncoding("UTF-8");
            writer.write(output);
            return null;
        }
    }
}
