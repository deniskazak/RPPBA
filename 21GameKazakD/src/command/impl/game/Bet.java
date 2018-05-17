package command.impl.game;

import bean.User;
import command.Command;
import configuration.page.PageConfiguration;
import util.MultiplayerUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class Bet implements Command {

    private int cash;
    private int bet;
    private int value;

    /**
     * Set user bet for the current game.
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
        initRequestParameters(request);
        bet();
        response.setContentType("text/plain");
        response.setCharacterEncoding("UTF-8");
        PrintWriter writer = response.getWriter();
        String output = initResponseString(user);
        writer.write(output);
        return null;
    }

    private void initRequestParameters(HttpServletRequest request) {
        cash = Integer.parseInt(request.getParameter("cash"));
        bet = Integer.parseInt(request.getParameter("bet"));
        value = Integer.parseInt(request.getParameter("value"));
    }

    private void bet() {
        if (cash >= value) {
            cash -= value;
            bet += value;
        } else return;
    }

    private String initResponseString(User user){
        String firstPlayer = user.getNickname();
        User user2 = MultiplayerUtil.getSecondPlayer(user);
        String secondPlayer = "Disconnected";
        if (user2 != null) {
            secondPlayer = user2.getNickname();
        }
        String output = String.format("{" +
                        "\"cash\":\"%s\"," +
                        "\"bet\":\"%s\"," +
                        "\"firstPlayer\":\"%s\"," +
                        "\"secondPlayer\":\"%s\"}"
                , cash, bet, firstPlayer, secondPlayer);
        return output;
    }
}
