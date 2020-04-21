package ua.nure.khshanovskyi.infoLife.controller.login;


import org.apache.log4j.Logger;
import ua.nure.khshanovskyi.infoLife.entity.constant.Constant;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/login")
public class LoginController extends HttpServlet {

    private static final Logger LOGGER = Logger.getLogger(LoginController.class);

    @Override
    public void init(){ }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        HttpSession session = req.getSession();
        try{
            if (req.getSession().getAttribute(String.valueOf(Constant.USER_IS_UNBLOCKED)) != null){
                resp.sendRedirect(req.getContextPath() + "/media");
            }else if (session.getAttribute(String.valueOf(Constant.INVALID)) != null ||
                    session.getAttribute(String.valueOf(Constant.SUBSCRIPTION_DTO)) != null){
                req.getRequestDispatcher("WEB-INF/jsp/login.jsp").forward(req,resp);
            }else{
                req.getRequestDispatcher("WEB-INF/jsp/login.jsp").forward(req,resp);
            }

        }catch (ServletException | IOException e){
            LOGGER.error(e);
        }
    }
}
