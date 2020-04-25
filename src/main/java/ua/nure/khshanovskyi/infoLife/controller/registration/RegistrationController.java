package ua.nure.khshanovskyi.infoLife.controller.registration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ua.nure.khshanovskyi.infoLife.entity.constant.Constant;
import ua.nure.khshanovskyi.infoLife.entity.user.User;
import ua.nure.khshanovskyi.infoLife.service.lowerLevel.user.creator.UserCreator;
import ua.nure.khshanovskyi.infoLife.service.topLewel.user.IUserService;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.Optional;

@WebServlet("/registration-controller")
public class RegistrationController extends HttpServlet {

    private static final Logger LOGGER = LoggerFactory.getLogger(RegistrationController.class);

    private IUserService userService;

    @Override
    public void init() {
        userService = (IUserService) getServletContext().getAttribute("userService");
        LOGGER.trace("Init in RegistrationController worked");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String email = req.getParameter("REGISTRATION_EMAIL");
        String surname = req.getParameter("REGISTRATION_SURNAME");
        String name = req.getParameter("REGISTRATION_NAME");
        String patronymic = req.getParameter("REGISTRATION_PATRONYMIC");
        String phoneNumber = req.getParameter("REGISTRATION_PHONE_NUMBER");
        String region = req.getParameter("REGISTRATION_REGION");
        String district = req.getParameter("REGISTRATION_DISTRICT");
        String locality = req.getParameter("REGISTRATION_LOCALITY");
        String street = req.getParameter("REGISTRATION_STREET");
        String house = req.getParameter("REGISTRATION_HOUSE");
        String flat = req.getParameter("REGISTRATION_FLAT");
        String postcode = req.getParameter("REGISTRATION_POSTCODE");
        String password = req.getParameter("REGISTRATION_PASSWORD");
        String confirmPassword = req.getParameter("REGISTRATION_PASSWORD_CONFIRM");

        UserCreator userCreator = new UserCreator();
        Optional<User> userOpt = userCreator.creatingNewUser(email, surname, name, patronymic, phoneNumber,
                                                            region, district, locality, street, house, flat, postcode,
                                                            password, confirmPassword);
        HttpSession session = req.getSession();
        User user;

        if (userService.checkEmailExisting(req.getParameter(String.valueOf(Constant.REGISTRATION_EMAIL))).isPresent()) {
            LOGGER.info("User with this email already exist");
            resp.sendRedirect(req.getContextPath() + "/registration");
        } else {
            try {
                user = userOpt.get();
                userService.createNewUser(user);
                LOGGER.info("User created and added to DB");
                User newUserFromDB = userService.getUserByEmail(user.getEmail()).get();
                Cookie cookieUser = new Cookie(String.valueOf(Constant.IN_LOGIN), String.valueOf(newUserFromDB.getUserId()));
                resp.addCookie(cookieUser);
                session.setAttribute(String.valueOf(Constant.USER_IS_UNBLOCKED), newUserFromDB);
                resp.sendRedirect(req.getContextPath() + "/login");
            } catch (NoSuchElementException e) {
                LOGGER.error("NoSuchElementException in registration");
                resp.sendRedirect(req.getContextPath() + "/registration");
            }
        }
    }
}
