package ua.nure.khshanovskyi.infoLife.controller.registration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ua.nure.khshanovskyi.infoLife.entity.constant.Constant;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/registration")
public class RegistrationPageController extends HttpServlet {

    private static final Logger LOGGER = LoggerFactory.getLogger(RegistrationPageController.class);

    @Override
    public void init(){
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp){
        try{
            if (req.getSession().getAttribute(String.valueOf(Constant.USER_IS_UNBLOCKED)) != null){
                resp.sendRedirect(req.getContextPath() + "/electronics");
            }else{
                req.getRequestDispatcher("WEB-INF/jsp/registration.jsp").forward(req,resp);
            }

        }catch (ServletException | IOException e){
            LOGGER.error(String.valueOf(e));
        }
    }

}

