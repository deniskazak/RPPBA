package util;

import command.*;
import command.impl.account.ChangeLanguage;
import command.impl.account.LogOut;
import command.impl.account.Login;
import command.impl.account.SignUp;
import command.impl.admin.BanUser;
import command.impl.admin.DeleteUser;
import command.impl.admin.ShowUsers;
import command.impl.common.NoCommand;
import command.impl.game.Bet;
import command.impl.game.Deal;
import command.impl.game.Hit;
import command.impl.game.Stand;
import command.impl.game.multiplayer.FindSecondPlayer;
import command.impl.game.multiplayer.ShowSecondPlayerCards;
import command.impl.game.multiplayer.WaitSecondPlayer;
import command.impl.message.DeleteMessage;
import command.impl.message.SendMessage;
import command.impl.message.ShowMessages;
import command.impl.money.Deposit;
import command.impl.page.Home;
import command.impl.page.Mode;
import command.impl.page.Registration;
import command.impl.rating.ShowRating;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

/**
 * Registration commands for http request.
 */
public class RequestUtil {

    private static RequestUtil instance = null;
    private HashMap<String, Command> commands =  new HashMap<>();

    private RequestUtil() {
        commands.put("login", new Login());
        commands.put("registration", new Registration());
        commands.put("signUp", new SignUp());
        commands.put("mode", new Mode());
        commands.put("bet", new Bet());
        commands.put("deal", new Deal());
        commands.put("hit", new Hit());
        commands.put("stand", new Stand());
        commands.put("deposit", new Deposit());
        commands.put("showRating", new ShowRating());
        commands.put("logOut", new LogOut());
        commands.put("showUsers", new ShowUsers());
        commands.put("banUser", new BanUser());
        commands.put("deleteUser", new DeleteUser());
        commands.put("showMessages", new ShowMessages());
        commands.put("sendMessage", new SendMessage());
        commands.put("deleteMessage", new DeleteMessage());
        commands.put("changeLanguage", new ChangeLanguage());
        commands.put("checkGame", new FindSecondPlayer());
        commands.put("waitSecondPlayer", new WaitSecondPlayer());
        commands.put("home", new Home());
        commands.put("showSecondPlayerCards", new ShowSecondPlayerCards());

    }
    /**
     * @param request Http request.
     * @return Command from http request.
     */
    public Command getCommand(HttpServletRequest request) {
        String action = request.getParameter("command");
        Command command = commands.get(action);
        if (command == null) {
            command = new NoCommand();
        }
        return command;
    }

    /**
     * Get instance.
     * @return RequestUtil instance.
     */
    public static RequestUtil getInstance() {
        if (instance == null) {
            instance = new RequestUtil();
        }
        return instance;
    }
    /**
     * Replace all special chars in http request string.
     * @param string Request string.
     * @return String with replaced special chars.
     */
    public String htmlSpecialChar(String string){
        String res = string.replaceAll("&","&amp;");
        res = res.replace("'","&#039;");
        res = res.replace("\"","&qout;");
        res = res.replace("$","&#036;");
        res = res.replace("%","&#037;");
        res = res.replace("<","&lt;");
        res = res.replace(">","&gt;");
        res = res.replace("\n","\\n");
        return res;
    }

}
