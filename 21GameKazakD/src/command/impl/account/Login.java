package command.impl.account;

import bean.User;
import command.Command;
import configuration.page.PageConfiguration;
import database.dao.DAOFactory;
import database.dao.impl.UserDAO;
import database.pool.ConnectionPool;
//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Login implements Command {

//    private static Logger logger = LogManager.getLogger(Login.class);

    private static final String PARAM_NAME_EMAIL = "email";
    private static final String PARAM_NAME_PASSWORD  = "password";

    /**
     * Login into the application.
     * @param request  HTTP request.
     * @param response HTTP response.
     * @return JSP page for redirection.
     * @see Command
     */
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String page = null;
        String email = request.getParameter(PARAM_NAME_EMAIL);
        String pass = request.getParameter(PARAM_NAME_PASSWORD);
        String locale = (String) request.getSession().getAttribute("language");
        int id = checkLogin(email, pass);
        if (id != 0) {
            UserDAO userDAO = DAOFactory.getInstance().getUserDAO();
            page = PageConfiguration.getInstance().getProperty(PageConfiguration.MODE_PAGE_PATH);
            User user = userDAO.getEntityById(id);
            userDAO.returnConnection();
            if (user.isBanned()){
                if (locale.equals("en") || locale.equals("en_EN")){
                    request.setAttribute("errorMessage", "Вы были забанены!");
                } else if (locale.equals("ru")){
                    request.setAttribute("errorMessage", "Вы были забанены!");
                }
                page = PageConfiguration.getInstance() .getProperty(PageConfiguration.LOGIN_PAGE_PATH);
                return page;
            }
            HttpSession session = request.getSession();
            session.setAttribute("user",user);
            session.setAttribute("admin",user.isAdmin());
            request.setAttribute("nickname", user.getNickname());
        }
        else {
            if (locale.equals("en") || locale.equals("en_EN")){
                request.setAttribute("errorMessage", "Неверный email или пароль!");
            } else if (locale.equals("ru")){
                request.setAttribute("errorMessage", "Неверный email или пароль!");
            }
            page = PageConfiguration.getInstance() .getProperty(PageConfiguration.LOGIN_PAGE_PATH);
        }
        return page;
    }

    private int checkLogin(String email, String password) {
        try {
            Connection cn = null;
            ConnectionPool connectionPool = ConnectionPool.getInstance();
            try {
                cn = connectionPool.getConnection();
                PreparedStatement st = null;
                try {
                    st = cn.prepareStatement("SELECT id FROM users WHERE email = ? AND password = MD5(?)");
                    st.setString(1, email);
                    st.setString(2, password);
                    ResultSet rs = null;
                    try {
                        rs = st.executeQuery();
                        if (rs.next()){
                            int id = rs.getInt("id");
                            return id;
                        } else return 0;
                    } finally {
                        if (rs != null) rs.close();
                    }
                } finally {
                    if (st != null) st.close();
                }
            } finally {
                if (cn != null) connectionPool.returnConnection(cn);
            }
        } catch (SQLException e) {
            //        logger.error(e.getMessage());
            return 0;
        }
    }
}
