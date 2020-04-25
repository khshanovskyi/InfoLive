package ua.nure.khshanovskyi.infoLife.controller.payment;

import ua.nure.khshanovskyi.infoLife.entity.constant.Constant;
import ua.nure.khshanovskyi.infoLife.entity.user.User;
import ua.nure.khshanovskyi.infoLife.service.topLewel.user.IUserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/payment-service")
public class PaymentServiceController extends HttpServlet {

    private IUserService userService;
    private HttpSession session;
    private User user;

    @Override
    public void init(){
        userService = (IUserService) getServletContext().getAttribute("userService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getSession().getAttribute(String.valueOf(Constant.USER_IS_UNBLOCKED)) != null ){
            session = req.getSession();
            user = (User) session.getAttribute(String.valueOf(Constant.USER_IS_UNBLOCKED));
            req.getRequestDispatcher("WEB-INF/jsp/payment_service.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        session = req.getSession();
        user = (User) session.getAttribute(String.valueOf(Constant.USER_IS_UNBLOCKED));
        int moneyAmount;
        long cardNumber;
        int year;
        int month;
        int cvvCode;

        if (req.getParameter("pay-button") != null) {
            moneyAmount = Integer.parseInt(req.getParameter("money_amount"));
            cardNumber = Long.parseLong(req.getParameter("card_number"));
            year = Integer.parseInt(req.getParameter("year"));
            month = Integer.parseInt(req.getParameter("month"));
            cvvCode = Integer.parseInt(req.getParameter("CVV-code"));
            if (moneyAmount > 0){
                userService.updateUserMoney(user.getUserId(), moneyAmount + user.getMoney());
                session.setAttribute(String.valueOf(Constant.USER_IS_UNBLOCKED), userService.getUserById(user.getUserId()).get());
                session.setAttribute("FromPaymentService", "FromPaymentService");
                resp.sendRedirect(req.getContextPath() + "/subscribing");
            }else{
                if (session.getAttribute(String.valueOf(Constant.SUBSCRIPTION_DTO)) != null){
                    session.removeAttribute(String.valueOf(Constant.SUBSCRIPTION_DTO));
                    resp.sendRedirect(req.getContextPath() + "/media");
                }
            }
        }
    }
}
