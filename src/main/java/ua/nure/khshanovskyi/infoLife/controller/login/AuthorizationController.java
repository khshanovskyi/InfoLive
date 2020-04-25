package ua.nure.khshanovskyi.infoLife.controller.login;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ua.nure.khshanovskyi.infoLife.entity.user.User;
import ua.nure.khshanovskyi.infoLife.entity.constant.Constant;
import ua.nure.khshanovskyi.infoLife.service.topLewel.user.IUserService;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.Optional;

@WebServlet("/authorization")
public class AuthorizationController extends HttpServlet {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthorizationController.class);

    private IUserService userService;

    @Override
    public void init() {
        userService = (IUserService) getServletContext().getAttribute("userService");
        LOGGER.trace("Init in AuthorizationController working");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String email = req.getParameter("EMAIL_LOGIN");
        String password = req.getParameter("PASSWORD_LOGIN");
        HttpSession session = req.getSession();

        Optional<User> userOptional = userService.loginInAccount(email, password);
        if (userOptional.isPresent()) {
            if (userService.checkUserState(email)) {
                session.setAttribute(String.valueOf(Constant.USER_IS_UNBLOCKED), userOptional.get());
                Cookie cookieUser = new Cookie(String.valueOf(Constant.IN_LOGIN), String.valueOf(userOptional.get().getUserId()));
                resp.addCookie(cookieUser);
                session.removeAttribute(String.valueOf(Constant.GUEST));
                if (session.getAttribute(String.valueOf(Constant.INVALID)) != null){
                    LOGGER.info("User input correct data. Remove INVALID session attribute");
                    session.removeAttribute(String.valueOf(Constant.INVALID));
                }
                if (session.getAttribute(String.valueOf(Constant.SUBSCRIPTION_DTO)) != null){
                    session.setAttribute("FromAuthorizationController", "FromAuthorizationController");
                    LOGGER.info("User have SUBSCRIPTION_DTO session attribute. User authorization is success. Redirect to subscribing");
                    resp.sendRedirect(req.getContextPath() + "/subscribing");
                    return;
                }
                LOGGER.trace("Have UNBLOCKED user in login");
                resp.sendRedirect(req.getContextPath() + "/media");
            } else {
                session.setAttribute(String.valueOf(Constant.USER_IS_BLOCKED), userOptional.get());
                LOGGER.info("Have BLOCKED user in login");
                resp.sendRedirect(req.getContextPath() + "/BLOCKED");
            }
        } else {
            LOGGER.info("User introduced not correct data");
            session.setAttribute(String.valueOf(Constant.INVALID), email);
            resp.sendRedirect(req.getContextPath() + "/login");
        }
    }
}

