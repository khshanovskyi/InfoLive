package ua.nure.khshanovskyi.infoLife.controller.subscription;

import org.apache.log4j.Logger;
import ua.nure.khshanovskyi.infoLife.entity.constant.Constant;
import ua.nure.khshanovskyi.infoLife.entity.user.User;
import ua.nure.khshanovskyi.infoLife.service.topLewel.subscribe.ISubscriptionService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/subscription-success")
public class SubscriptionSuccessController extends HttpServlet {

    private static final Logger LOGGER = Logger.getLogger(SubscriptionSuccessController.class);

    private ISubscriptionService subscriptionService;

    @Override
    public void init(){
        subscriptionService = (ISubscriptionService) getServletContext().getAttribute("subscriptionService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute(String.valueOf(Constant.USER_IS_UNBLOCKED));
        req.setAttribute(String.valueOf(Constant.SUBSCRIPTION_USER_AND_MEDIA_JOIN_DTO),
                subscriptionService.getDTOOfSubscriptionUserAndMedia(user.getUserId()).get());
        req.getRequestDispatcher("WEB-INF/jsp/subscription_success.jsp").forward(req,resp);
    }
}
