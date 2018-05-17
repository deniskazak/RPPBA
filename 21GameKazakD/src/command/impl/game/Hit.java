package command.impl.game;

import bean.Card;
import bean.Game;
import bean.User;
import command.Command;
import configuration.page.PageConfiguration;
import database.dao.DAOFactory;
import database.dao.impl.GameDAO;
import util.CardUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class Hit implements Command {

    /**
     * Take one more card during the game.
     * @param request  HTTP request.
     * @param response HTTP response.
     * @return JSP page for redirection.
     * @see Command
     */
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = (User) request.getSession().getAttribute("user");
        if (user==null)
        {
            return PageConfiguration.getInstance().getProperty(PageConfiguration.LOGIN_PAGE_PATH);
        }
        else {
            Game game = (Game) request.getSession().getAttribute("game");
            if(game.getPoints(game.getMyCards()) > 21){
                game.setReady(true);
                return null;
            }
            Card card = CardUtil.getRandomCard();
            game.addMyCard(card);
            if(game.getPoints(game.getMyCards()) > 21){
                game.setReady(true);
                game.setRate(-3);
                GameDAO gameDAO = DAOFactory.getInstance().getGameDAO();
                gameDAO.update(game);
                gameDAO.returnConnection();
            }
            request.getSession().setAttribute("user",user);
            response.setContentType("text/plain");
            response.setCharacterEncoding("UTF-8");
            PrintWriter writer = response.getWriter();
            String output = initRequestString(game,card);
            writer.write(output);
            return null;
        }

    }

    private String initRequestString(Game game, Card card){
        int cardId = game.getMyCards().size() + 1;
        String output = String.format("{" +
                        "\"myCard\":\"%s\"," +
                        "\"myPoints\":\"%s\"," +
                        "\"enemyCard\":\"%s\"," +
                        "\"cardId\":\"%s\"}",
                card.getSrc(), game.getPoints(game.getMyCards()), "/images/cards/cardobr.jpg", cardId);
        return output;
    }
}
