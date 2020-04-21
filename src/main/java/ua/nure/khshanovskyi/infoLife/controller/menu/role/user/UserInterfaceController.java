package ua.nure.khshanovskyi.infoLife.controller.menu.role.user;

import org.apache.log4j.Logger;
import ua.nure.khshanovskyi.infoLife.entity.constant.Constant;
import ua.nure.khshanovskyi.infoLife.entity.user.User;
import ua.nure.khshanovskyi.infoLife.handler.DataEnteredHandler;
import ua.nure.khshanovskyi.infoLife.service.topLewel.user.IUserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/user-interface")
public class UserInterfaceController extends HttpServlet {

    private static final Logger LOGGER = Logger.getLogger(UserInterfaceController.class);
    private static final String DISTRIBUTOR = "/distributor";

    private IUserService userService;
    private DataEnteredHandler dataEnteredHandler = new DataEnteredHandler();

    @Override
    public void init() {
        userService = (IUserService) getServletContext().getAttribute("userService");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Cookie[] cookies = req.getCookies();
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute(String.valueOf(Constant.USER_IS_UNBLOCKED));
        String oldPassword = req.getParameter(String.valueOf(Constant.OLD_PASSWORD));
        int actualUserId = 0;
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals(String.valueOf(Constant.IN_LOGIN))) {
                actualUserId = Integer.parseInt(cookie.getValue());
            }
        }

        //for update user email
        if (req.getParameter("SUBMIT_CHANGE_EMAIL") != null) {
            if (!userService.checkEmailExisting(req.getParameter(String.valueOf(Constant.E_MAIL))).isPresent()) {
                LOGGER.trace("Check password confirm");
                if (dataEnteredHandler.passwordConfirmValidation(user.getPassword(),
                        req.getParameter(String.valueOf(Constant.PASSWORD_CONFIRM)))) {
                    LOGGER.trace("Updating user email");
                    userService.updateUserEmail(actualUserId, req.getParameter(String.valueOf(Constant.E_MAIL)));
                    LOGGER.trace("Updating session");
                    session.setAttribute(String.valueOf(Constant.USER_IS_UNBLOCKED), userService.getUserById(actualUserId).get());
                    resp.sendRedirect(req.getContextPath() + DISTRIBUTOR);
                } else {
                    LOGGER.trace("Password not confirmed");
                    resp.sendRedirect(req.getContextPath() + DISTRIBUTOR);
                }
            } else {
                resp.sendRedirect(req.getContextPath() + DISTRIBUTOR);
            }
        }

        //for update user phone number
        if (req.getParameter("SUBMIT_CHANGE_PHONE_NUM") != null) {
            if (dataEnteredHandler.phoneNumberValidation(req.getParameter(String.valueOf(Constant.CHANGE_PHONE_NUM)))) {
                LOGGER.trace("Updating user phone number");
                userService.updateUserPhoneNumber(actualUserId,
                        Integer.parseInt(req.getParameter(String.valueOf(Constant.CHANGE_PHONE_NUM))));
                LOGGER.trace("Updating session");
                session.setAttribute(String.valueOf(Constant.USER_IS_UNBLOCKED), userService.getUserById(actualUserId).get());
                resp.sendRedirect(req.getContextPath() + DISTRIBUTOR);
            } else {
                LOGGER.trace("Updating user phone number failed");
                resp.sendRedirect(req.getContextPath() + DISTRIBUTOR);
            }
        }

        //for update user password
        if (req.getParameter("SUBMIT_CHANGE_PASSWORD") != null) {
            String newPassword = req.getParameter(String.valueOf(Constant.NEW_PASSWORD));
            String newPasswordConfirm = req.getParameter(String.valueOf(Constant.NEW_PASSWORD_CONFIRM));
            if (oldPassword != null && newPassword != null && newPasswordConfirm != null) {
                if (dataEnteredHandler.passwordConfirmValidation(user.getPassword(), oldPassword)) {
                    if (dataEnteredHandler.passwordValidation(newPassword)) {
                        if (dataEnteredHandler.passwordConfirmValidation(newPassword, newPasswordConfirm)) {
                            userService.updateUserPassword(actualUserId, newPassword);
                            LOGGER.trace("Updating user password success");
                            session.setAttribute(String.valueOf(Constant.USER_IS_UNBLOCKED), userService.getUserById(actualUserId).get());
                            resp.sendRedirect(req.getContextPath() + DISTRIBUTOR);
                        } else {
                            LOGGER.trace("Updating user failed! New password not confirmed with new password confirm");
                            resp.sendRedirect(req.getContextPath() + DISTRIBUTOR);
                        }
                    } else {
                        LOGGER.trace("Updating user failed! New password is not valid");
                        resp.sendRedirect(req.getContextPath() + DISTRIBUTOR);
                    }
                } else {
                    LOGGER.trace("Updating user failed! Not valid old password");
                    resp.sendRedirect(req.getContextPath() + DISTRIBUTOR);
                }
            } else {
                LOGGER.trace("Updating user failed! Some field is null!");
                resp.sendRedirect(req.getContextPath() + DISTRIBUTOR);
            }
        }

        //for update user surname, name, patronymic
        if (req.getParameter("SUBMIT_CHANGE_NAME_SURNAME") != null) {
            if (req.getParameter(String.valueOf(Constant.CHANGE_SURNAME)) != null) {
                if (dataEnteredHandler.nameValidation(req.getParameter(String.valueOf(Constant.CHANGE_SURNAME)))) {
                    userService.updateUserSurname(actualUserId, req.getParameter(String.valueOf(Constant.CHANGE_SURNAME)));
                }
            }
            if (req.getParameter(String.valueOf(Constant.CHANGE_NAME)) != null) {
                if (dataEnteredHandler.nameValidation(req.getParameter(String.valueOf(Constant.CHANGE_NAME)))) {
                    userService.updateUserName(actualUserId, req.getParameter(String.valueOf(Constant.CHANGE_NAME)));
                }
            }
            if (req.getParameter(String.valueOf(Constant.CHANGE_PATRONYMIC)) != null) {
                if (dataEnteredHandler.nameValidation(req.getParameter(String.valueOf(Constant.CHANGE_PATRONYMIC)))) {
                    userService.updateUserPatronymic(actualUserId, req.getParameter(String.valueOf(Constant.CHANGE_PATRONYMIC)));
                }
            }

            LOGGER.trace("Updating session");
            session.setAttribute(String.valueOf(Constant.USER_IS_UNBLOCKED), userService.getUserById(actualUserId).get());
            resp.sendRedirect(req.getContextPath() + DISTRIBUTOR);
        }

        //for update user address
        if (req.getParameter("SUBMIT_CHANGE_ADDRESS") != null) {
            if (!req.getParameter("CHANGE_REGION").isEmpty()) {
                if (dataEnteredHandler.addressValidation(req.getParameter("CHANGE_REGION"))) {
                    userService.updateUserRegion(actualUserId, req.getParameter("CHANGE_REGION"));
                    LOGGER.trace("Updating region success");
                }
            }
            if (!req.getParameter("CHANGE_DISTRICT").isEmpty()) {
                if (dataEnteredHandler.addressValidation(req.getParameter("CHANGE_DISTRICT"))) {
                    userService.updateUserDistrict(actualUserId, req.getParameter("CHANGE_DISTRICT"));
                    LOGGER.trace("Updating district success");
                }
            }
            if (!req.getParameter("CHANGE_LOCALITY").isEmpty()) {
                if (dataEnteredHandler.addressValidation(req.getParameter("CHANGE_LOCALITY"))) {
                    userService.updateUserLocality(actualUserId, req.getParameter("CHANGE_LOCALITY"));
                    LOGGER.trace("Updating locality success");
                }
            }
            if (!req.getParameter("CHANGE_STREET").isEmpty()) {
                if (dataEnteredHandler.addressValidation(req.getParameter("CHANGE_STREET"))) {
                    userService.updateUserStreet(actualUserId, req.getParameter("CHANGE_STREET"));
                    LOGGER.trace("Updating street success");
                }
            }
            if (!req.getParameter("CHANGE_HOUSE").isEmpty()) {
                if (dataEnteredHandler.houseValidation(req.getParameter("CHANGE_HOUSE"))) {
                    userService.updateUserHouse(actualUserId, req.getParameter("CHANGE_HOUSE"));
                    LOGGER.trace("Updating house success");
                }
            }
            if (!req.getParameter("CHANGE_FLAT").isEmpty()) {
                if (dataEnteredHandler.flatValidation(req.getParameter("CHANGE_FLAT"))) {
                    userService.updateUserFlat(actualUserId, Integer.parseInt(req.getParameter("CHANGE_FLAT")));
                    LOGGER.trace("Updating flat success");
                }
            }
            if (!req.getParameter("CHANGE_POSTCODE").isEmpty()) {
                if (dataEnteredHandler.postcodeValidation(req.getParameter("CHANGE_POSTCODE"))) {
                    userService.updateUserPostcode(actualUserId, Integer.parseInt(req.getParameter("CHANGE_POSTCODE")));
                    LOGGER.trace("Updating postcode success");
                }
            }
            LOGGER.trace("Updating session");
            session.setAttribute(String.valueOf(Constant.USER_IS_UNBLOCKED), userService.getUserById(actualUserId).get());
            resp.sendRedirect(req.getContextPath() + DISTRIBUTOR);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("WEB-INF/jsp/user_interface.jsp").forward(req, resp);
    }

}
