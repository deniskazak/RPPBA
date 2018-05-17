package command.impl.game;

import bean.Card;
import bean.Game;
import bean.User;
import command.Command;
import database.dao.DAOFactory;
import database.dao.impl.GameDAO;
import database.dao.impl.UserDAO;
import database.pool.ConnectionPool;
import util.CardUtil;
import util.MultiplayerUtil;
import configuration.page.PageConfiguration;
//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.util.Calendar;

public class Deal implements Command {

    //   private static Logger logger = LogManager.getLogger(Deal.class);

    /**
     * Deal cards before the game.
     *
     * @param request  HTTP request.
     * @param response HTTP response.
     * @return JSP page for redirection.
     * @see Command
     */
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getSession().getAttribute("user") == null) {
            return PageConfiguration.getInstance().getProperty(PageConfiguration.LOGIN_PAGE_PATH);
        } else {
            User user = (User) request.getSession().getAttribute("user");
            Game game = initGame(request);
            user.setCurrentGame(game);
            request.getSession().setAttribute("game", game);
            response.setContentType("text/plain");
            response.setCharacterEncoding("UTF-8");
            PrintWriter writer = response.getWriter();
            boolean isMultiplayer = Boolean.parseBoolean(request.getParameter("isMultiplayer"));
            String output = null;
            if (isMultiplayer) {
                output = deal2(user);
            } else {
                output = deal(user, game);
            }
            writer.write(output);
            return null;
        }
    }

    private Game initGame(HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute("user");
        int bet = Integer.parseInt(request.getParameter("bet"));
        Game game = new Game();
        game.setUserId(user.getId());
        game.setDate(Calendar.getInstance().getTime());
        game.setBet(bet);
        game.setWin(0);
        game.setRate(0);
        GameDAO dao = DAOFactory.getInstance().getGameDAO();
        dao.create(game);
        dao.returnConnection();
        game.setId(getLastInsertGameId());
        return game;
    }

    private int getLastInsertGameId() {
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        Connection connection = connectionPool.getConnection();
        int lastId = 0;
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT MAX(id) FROM games");
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            lastId = resultSet.getInt(1);
        } catch (SQLException e) {
            //         logger.error(e.getMessage());
        }
        connectionPool.returnConnection(connection);
        return lastId;
    }

    private static void defineBet(User user, Game game) {
        user.setMoney(user.getMoney() - game.getBet());
        game.setRate(-3);
        GameDAO gameDAO = DAOFactory.getInstance().getGameDAO();
        gameDAO.update(game);
        gameDAO.returnConnection();
        UserDAO userDAO = DAOFactory.getInstance().getUserDAO();
        userDAO.update(user);
        userDAO.returnConnection();
    }

    private void defineBlackJack(User user, Game game, boolean isMyBlackJack, boolean isEnemyBlackJack) {
        UserDAO userDAO = DAOFactory.getInstance().getUserDAO();
        GameDAO gameDAO = DAOFactory.getInstance().getGameDAO();
        if (isMyBlackJack == true && isEnemyBlackJack == false) {
            game.setReady(true);
            int win = game.getBet() * 3;
            game.setWin(win);
            game.setRate(3);
            user.setMoney(user.getMoney() + win);
            userDAO.update(user);
            userDAO.returnConnection();
            gameDAO.update(game);
            gameDAO.returnConnection();
        } else if (isMyBlackJack == true && isEnemyBlackJack == true) {
            game.setReady(true);
            int win = game.getBet();
            game.setWin(win);
            game.setRate(1);
            user.setMoney(user.getMoney() + win);
            userDAO.update(user);
            userDAO.returnConnection();
            gameDAO.update(game);
            gameDAO.returnConnection();
        }
    }

    private String deal(User user, Game game) {
        defineBet(user, game);
        Card card1 = CardUtil.getRandomCard();
        Card card2 = CardUtil.getRandomCard();
        game.addMyCard(card1);
        game.addMyCard(card2);
        Card card3 = CardUtil.getRandomCard();
        Card card4 = CardUtil.getRandomCard();
        game.addEnemyCard(card3);
        game.addEnemyCard(card4);
        boolean isMyBlackJack = false;
        boolean isEnemyBlackJack = false;
        if (game.getPoints(game.getMyCards()) == 21) isMyBlackJack = true;
        if (game.getPoints(game.getEnemyCards()) == 21) isEnemyBlackJack = true;
        defineBlackJack(user, game, isMyBlackJack, isEnemyBlackJack);
        String output = String.format("{" +
                        "\"myCards\":[\"%s\",\"%s\"]," +
                        "\"myPoints\":\"%s\"," +
                        "\"isMyBlackJack\":\"%s\"," +
                        "\"enemyCards\":[\"%s\",\"%s\"]," +
                        "\"isEnemyBlackJack\":\"%s\"}",
                card1.getSrc(), card2.getSrc(), game.getPoints(game.getMyCards()), isMyBlackJack,
                card3.getSrc(), "/images/cards/cardobr.jpg", isEnemyBlackJack);
        return output;
    }

    private String deal2(User user) {
        defineBet(user, user.getCurrentGame());
        User secondPlayer = MultiplayerUtil.getInstance().getSecondPlayer(user);
        if (secondPlayer == null) return deal(user,user.getCurrentGame());
        Card card1 = CardUtil.getRandomCard();
        Card card2 = CardUtil.getRandomCard();
        user.getCurrentGame().addMyCard(card1);
        user.getCurrentGame().addMyCard(card2);
        Card card3 = CardUtil.getRandomCard();
        Card card4 = CardUtil.getRandomCard();
        if (secondPlayer.getCurrentGame() == null) {
            user.getCurrentGame().addEnemyCard(card3);
            user.getCurrentGame().addEnemyCard(card4);
            boolean isMyBlackJack = false;
            boolean isEnemyBlackJack = false;
            if (user.getCurrentGame().getPoints(user.getCurrentGame().getMyCards()) == 21) isMyBlackJack = true;
            if (user.getCurrentGame().getPoints(user.getCurrentGame().getEnemyCards()) == 21) isEnemyBlackJack = true;
            defineBlackJack(user, user.getCurrentGame(), isMyBlackJack, isEnemyBlackJack);
            String output = String.format("{" +
                            "\"myCards\":[\"%s\",\"%s\"]," +
                            "\"myPoints\":\"%s\"," +
                            "\"isMyBlackJack\":\"%s\"," +
                            "\"enemyCards\":[\"%s\",\"%s\"]," +
                            "\"isEnemyBlackJack\":\"%s\"}",
                    card1.getSrc(), card2.getSrc(), user.getCurrentGame().getPoints(user.getCurrentGame().getMyCards()), isMyBlackJack,
                    user.getCurrentGame().getEnemyCards().get(0).getSrc(), "/images/cards/cardobr.jpg", isEnemyBlackJack);
            return output;
        } else if (secondPlayer.getCurrentGame().getEnemyCards().size() != 0) {
            user.getCurrentGame().setEnemyCards(secondPlayer.getCurrentGame().getEnemyCards());
            boolean isMyBlackJack = false;
            boolean isEnemyBlackJack = false;
            if (user.getCurrentGame().getPoints(user.getCurrentGame().getMyCards()) == 21) isMyBlackJack = true;
            if (user.getCurrentGame().getPoints(user.getCurrentGame().getEnemyCards()) == 21) isEnemyBlackJack = true;
            defineBlackJack(user, user.getCurrentGame(), isMyBlackJack, isEnemyBlackJack);
            String output = String.format("{" +
                            "\"myCards\":[\"%s\",\"%s\"]," +
                            "\"myPoints\":\"%s\"," +
                            "\"isMyBlackJack\":\"%s\"," +
                            "\"enemyCards\":[\"%s\",\"%s\"]," +
                            "\"isEnemyBlackJack\":\"%s\"}",
                    card1.getSrc(), card2.getSrc(), user.getCurrentGame().getPoints(user.getCurrentGame().getMyCards()), isMyBlackJack,
                    user.getCurrentGame().getEnemyCards().get(0).getSrc(), "/images/cards/cardobr.jpg", isEnemyBlackJack);
            return output;
        }
        return null;
    }

}
