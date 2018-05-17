package command.impl.account;

import bean.User;
import command.Command;
import configuration.page.PageConfiguration;
import database.dao.DAOFactory;
import database.dao.impl.UserDAO;
//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.regex.Pattern;

public class SignUp implements Command {

    private static final String PARAM_NAME_EMAIL = "email";
    private static final String PARAM_NAME_PASSWORD  = "password";
    private static final String PARAM_NAME_NICK  = "nickName";


    /**
     * Sign up new player.
     * @param request  HTTP request.
     * @param response HTTP response.
     * @return JSP page for redirection.
     * @see Command
     */
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String page = null;
        String email = request.getParameter(PARAM_NAME_EMAIL);
        if (!validateEmail(email)){
            initError(request,"Недопустимый email!", "Недопустимый email!");
            return PageConfiguration.getInstance() .getProperty(PageConfiguration.REGISTRATION_PAGE_PATH);
        }
        String pass = request.getParameter(PARAM_NAME_PASSWORD);
        if (!validatePassword(pass)){
            initError(request,"Недопустимый пароль!", "Недопустимый пароль!");
            return PageConfiguration.getInstance() .getProperty(PageConfiguration.REGISTRATION_PAGE_PATH);
        }
        String nick = request.getParameter(PARAM_NAME_NICK);
        if (!validateNickname(nick)){
            initError(request,"Недопустимое имя пользователя!", "Недопустимое имя пользователя!");
            return PageConfiguration.getInstance() .getProperty(PageConfiguration.REGISTRATION_PAGE_PATH);
        }
        UserDAO userDAO = DAOFactory.getInstance().getUserDAO();
        if (!userDAO.isUserExistsWithEmail(email)) {
            if (!userDAO.isUserExistsWithNickname(nick)){
                signUp(email,nick,pass);
                page = PageConfiguration.getInstance() .getProperty(PageConfiguration.LOGIN_PAGE_PATH);
            }
            else {
                initError(request,"Пользователь с таким именем уже существует!","Пользователь с таким именем уже существует!");
                page = PageConfiguration.getInstance() .getProperty(PageConfiguration.REGISTRATION_PAGE_PATH);
            }
        }
        else {
            initError(request,"Пользователь с таким email уже существует!","Пользователь с таким email уже существует!");
            page = PageConfiguration.getInstance() .getProperty(PageConfiguration.REGISTRATION_PAGE_PATH);
        }
        userDAO.returnConnection();
        return page;
    }

    private void initError(HttpServletRequest request, String en_Error, String ru_Error){
        String locale = (String) request.getSession().getAttribute("language");
        if (locale.equals("en") || locale.equals("en_EN")){
            request.setAttribute("errorMessage", en_Error);
        } else if (locale.equals("ru")){
            request.setAttribute("errorMessage", ru_Error);
        }
    }

    private boolean validateEmail(String email){
        String pattern = new String("(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])");
        boolean isMatch = Pattern.matches(pattern, email);
        if (isMatch) return true;
        else return false;
    }

    private boolean validateNickname(String nickName){
        String pattern = "^[A-Za-z][A-Za-z0-9]+";
        boolean isMatch = Pattern.matches(pattern, nickName);
        if (isMatch) return true;
        else return false;
    }

    private boolean validatePassword(String password){
        String pattern = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{6,20}";
        boolean isMatch = Pattern.matches(pattern, password);
        if (isMatch) return true;
        else return false;
    }

    private void signUp(String email, String nick, String pass) {
        User user = new User();
        user.setEmail(email);
        user.setNickname(nick);
        user.setPassword(pass);
        user.setMoney(1000);
        user.setBanned(false);
        user.setAdmin(false);
        UserDAO userDAO = new UserDAO();
        userDAO.create(user);
        userDAO.returnConnection();
    }
}
