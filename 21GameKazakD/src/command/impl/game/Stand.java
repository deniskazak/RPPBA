package command.impl.game;

import bean.Card;
import bean.Game;
import bean.User;
import command.Command;
import configuration.page.PageConfiguration;
import database.dao.DAOFactory;
import database.dao.impl.GameDAO;
import database.dao.impl.UserDAO;
import util.CardUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class Stand implements Command {

    /**
     *  Finish the game if player has enough cards.
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
        user.getCurrentGame().setReady(true);
        Game game = (Game) request.getSession().getAttribute("game");
        String output = stand(user,game);
        PrintWriter writer = response.getWriter();
        response.setContentType("text/plain");
        response.setCharacterEncoding("UTF-8");
        writer.write(output);
        return null;
    }

    private String stand(User user, Game game) {
        boolean isEnough = false;
        boolean isEnemyBlackJack = false;
        if (game.getPoints(game.getEnemyCards()) >= 17) {
            if (game.getEnemyCards().size() == 2 && game.getPoints(game.getEnemyCards()) == 21) isEnemyBlackJack = true;
            isEnough = true;
            defineWinner(user, game);
            StringBuilder builder = new StringBuilder();
            builder.append("{");
            builder.append(String.format("\"enemyPoints\":\"%s\",", game.getPoints(game.getEnemyCards())));
            builder.append(String.format("\"myPoints\":\"%s\",", game.getPoints(game.getMyCards())));
            builder.append(String.format("\"isEnemyBlackJack\":\"%s\",", isEnemyBlackJack));
            builder.append(String.format("\"isEnough\":\"%s\",", isEnough));
            builder.append(String.format("\"enemyCardSize\":\"%s\",", game.getEnemyCards().size()));
            builder.append("\"enemyCards\":[");
            for (int i = 1; i < game.getEnemyCards().size(); i++) {
                if (i == game.getEnemyCards().size() - 1) {
                    builder.append(String.format("\"%s\"]", game.getEnemyCards().get(i).getSrc()));
                } else {
                    builder.append(String.format("\"%s\", ", game.getEnemyCards().get(i).getSrc()));
                }
            }
            builder.append("}");
            return builder.toString();
        } else {
            Card card = CardUtil.getRandomCard();
            game.getEnemyCards().add(card);
            String output = String.format("{" +
                            "\"enemyCard\":\"%s\"," +
                            "\"enemyPoints\":\"%s\"," +
                            "\"myPoints\":\"%s\"," +
                            "\"isEnemyBlackJack\":\"%s\"," +
                            "\"isEnough\":\"%s\"}",
                    card.getSrc(), game.getPoints(game.getEnemyCards()), game.getPoints(game.getMyCards()),
                    isEnemyBlackJack, isEnough);
            return output;
        }
    }

    private void defineWinner(User user, Game game) {
        int myPoints = game.getPoints(game.getMyCards());
        int enemyPoints = game.getPoints(game.getEnemyCards());
        UserDAO userDAO = DAOFactory.getInstance().getUserDAO();
        GameDAO gameDAO = DAOFactory.getInstance().getGameDAO();
        if (enemyPoints > 21){
            int win = game.getBet() * 2;
            game.setWin(win);
            game.setRate(3);
            gameDAO.update(game);
            user.setMoney(user.getMoney() + win);
            userDAO.update(user);
        } else if (enemyPoints == myPoints) {
            boolean isEnemyBlackJack = game.getEnemyCards().size() == 2 && game.getPoints(game.getEnemyCards()) == 21;
            if (isEnemyBlackJack == false) {
                int win = game.getBet();
                game.setWin(win);
                game.setRate(1);
                gameDAO.update(game);
                user.setMoney(user.getMoney() + win);
                userDAO.update(user);
            }
        } else if (enemyPoints < myPoints) {
            int win = game.getBet() * 2;
            game.setWin(win);
            game.setRate(3);
            gameDAO.update(game);
            user.setMoney(user.getMoney() + win);
            userDAO.update(user);
        }
        userDAO.returnConnection();
        gameDAO.returnConnection();
    }
}
