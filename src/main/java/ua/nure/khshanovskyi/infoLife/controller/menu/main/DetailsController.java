package ua.nure.khshanovskyi.infoLife.controller.menu.main;

import org.apache.log4j.Logger;
import ua.nure.khshanovskyi.infoLife.entity.constant.Constant;
import ua.nure.khshanovskyi.infoLife.entity.media.Media;
import ua.nure.khshanovskyi.infoLife.entity.user.User;
import ua.nure.khshanovskyi.infoLife.service.topLewel.media.IMediaService;
import ua.nure.khshanovskyi.infoLife.service.topLewel.subscribe.ISubscriptionService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/details")
public class DetailsController extends HttpServlet {

    private static final Logger LOGGER = Logger.getLogger(DetailsController.class);

    private IMediaService mediaService;
    private ISubscriptionService subscriptionService;

    @Override
    public void init() {
        mediaService = (IMediaService) getServletContext().getAttribute("mediaService");
        subscriptionService = (ISubscriptionService) getServletContext().getAttribute("subscriptionService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        User user;
        Media media;

        if (req.getParameter("info-about-media") != null) {
            media = mediaService.getMediaById(Integer.parseInt(req.getParameter("info-about-media"))).get();
            //check user authorization
            if (session.getAttribute(String.valueOf(Constant.USER_IS_UNBLOCKED)) != null) {
                user = (User) session.getAttribute(String.valueOf(Constant.USER_IS_UNBLOCKED));
                //check activity this subscription
                if (subscriptionService.checkActivityThisSubscription(user.getUserId(), media.getMediaId())) {
                    req.setAttribute(String.valueOf(Constant.SUBSCRIPTION),
                            subscriptionService.getActivityUserSubscription(user.getUserId(), media.getMediaId()).get());
                }
            }
            req.setAttribute("MediaObj", media);
            req.getRequestDispatcher("WEB-INF/jsp/details.jsp").forward(req, resp);
        } else {
            resp.sendRedirect(req.getContextPath() + "/media");
        }
    }
}
