package ua.nure.khshanovskyi.infoLife.controller.subscription;

import ua.nure.khshanovskyi.infoLife.entity.constant.Constant;
import ua.nure.khshanovskyi.infoLife.entity.dto.ShortSubscriptionJoinDTO;
import ua.nure.khshanovskyi.infoLife.entity.user.User;
import ua.nure.khshanovskyi.infoLife.service.topLewel.subscribe.ISubscriptionService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/my-subscriptions")
public class MySubscriptionsController extends HttpServlet {

    private ISubscriptionService subscriptionService;

    @Override
    public void init(){
        subscriptionService = (ISubscriptionService) getServletContext().getAttribute("subscriptionService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getSession().getAttribute(String.valueOf(Constant.USER_IS_UNBLOCKED)) != null ){
            User user = (User) req.getSession().getAttribute(String.valueOf(Constant.USER_IS_UNBLOCKED));
            List<ShortSubscriptionJoinDTO> subscriptionsList = subscriptionService.getActivityUserSubscriptions(user.getUserId());
            req.setAttribute("subscriptionsList",subscriptionsList);
            req.getRequestDispatcher("WEB-INF/jsp/my_subscription.jsp").forward(req,resp);
        }
    }
}
