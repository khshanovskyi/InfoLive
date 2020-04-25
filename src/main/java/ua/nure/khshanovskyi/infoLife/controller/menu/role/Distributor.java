package ua.nure.khshanovskyi.infoLife.controller.menu.role;

import ua.nure.khshanovskyi.infoLife.entity.constant.Constant;
import ua.nure.khshanovskyi.infoLife.entity.user.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/distributor")
public class Distributor extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        if (req.getSession().getAttribute(String.valueOf(Constant.USER_IS_UNBLOCKED)) != null ){
            User user = (User) req.getSession().getAttribute(String.valueOf(Constant.USER_IS_UNBLOCKED));

            if (user.getRole().equals(String.valueOf(Constant.USER))){
                resp.sendRedirect(req.getContextPath() + "/user-interface");
            }else if (user.getRole().equals(String.valueOf(Constant.ADMIN))){
                resp.sendRedirect(req.getContextPath() + "/admin-interface");
            }else {
                resp.sendRedirect(req.getContextPath() + "/media");
            }
        }else {
            resp.sendRedirect(req.getContextPath() + "/media");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
