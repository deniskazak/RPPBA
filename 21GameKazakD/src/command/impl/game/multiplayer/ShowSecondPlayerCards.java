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

public class ShowSecondPlayerCards implements Command {

    /**
     * Show cards of the second player in the end of the game.
     * @param request  HTTP request.
     * @param response HTTP response.
     * @return JSP page for redirection.
     * @see Command
     */
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getSession().getAttribute("user") == null)
            return PageConfiguration.getInstance().getProperty(PageConfiguration.LOGIN_PAGE_PATH);
        User secondPlayer = MultiplayerUtil.getInstance().getSecondPlayer((User) request.getSession().getAttribute("user"));
        if (secondPlayer == null) return null;
        Game game = secondPlayer.getCurrentGame();
        StringBuilder builder = new StringBuilder();
        builder.append("{");
        builder.append(String.format("\"secondPlayerCardSize\":\"%s\",", game.getMyCards().size()));
        builder.append("\"secondPlayerCards\":[");
        for (int i = 0; i < game.getMyCards().size(); i++) {
            if (i == game.getMyCards().size() - 1) {
                builder.append(String.format("\"%s\"]", game.getMyCards().get(i).getSrc()));
            } else {
                builder.append(String.format("\"%s\", ", game.getMyCards().get(i).getSrc()));
            }
        }
        builder.append("}");
        String output = builder.toString();
        PrintWriter writer = response.getWriter();
        writer.write(output);
        return null;
    }
}
