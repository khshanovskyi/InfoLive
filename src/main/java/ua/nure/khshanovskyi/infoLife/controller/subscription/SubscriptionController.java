package ua.nure.khshanovskyi.infoLife.controller.subscription;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ua.nure.khshanovskyi.infoLife.entity.dto.SubscriptionDTO;
import ua.nure.khshanovskyi.infoLife.entity.constant.Constant;
import ua.nure.khshanovskyi.infoLife.entity.media.Media;
import ua.nure.khshanovskyi.infoLife.entity.user.User;
import ua.nure.khshanovskyi.infoLife.service.topLewel.media.IMediaService;
import ua.nure.khshanovskyi.infoLife.service.topLewel.subscribe.ISubscriptionService;
import ua.nure.khshanovskyi.infoLife.service.topLewel.user.IUserService;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/subscribing")
public class SubscriptionController extends HttpServlet {

    private static final Logger LOGGER = LoggerFactory.getLogger(SubscriptionController.class);

    private ISubscriptionService subscriptionService;
    private IMediaService mediaService;
    private IUserService userService;

    @Override
    public void init() {
        subscriptionService = (ISubscriptionService) getServletContext().getAttribute("subscriptionService");
        mediaService = (IMediaService) getServletContext().getAttribute("mediaService");
        userService = (IUserService) getServletContext().getAttribute("userService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HttpSession session = req.getSession();
        User user;
        Media media;
        SubscriptionDTO subscriptionDTO;
        int mediaId;
        String period;
        int pricePerPeriod;

        //check did user push on button "SUBSCRIBE" or not
        if (req.getParameter("subscription") != null) {
            LOGGER.trace("subscription btn not null");
            mediaId = Integer.parseInt(req.getParameter("media_id"));
            period = req.getParameter("subscription-period");
            media = mediaService.getMediaById(mediaId).get();
            pricePerPeriod = subscriptionService.getPricePerSubscription(media.getPrice(), period);

            //check user in login or not
            if (session.getAttribute(String.valueOf(Constant.USER_IS_UNBLOCKED)) != null) {
                //get user obj from session
                user = (User) session.getAttribute(String.valueOf(Constant.USER_IS_UNBLOCKED));

                //check activity this subscription
                if (!subscriptionService.checkActivityThisSubscription(user.getUserId(), mediaId)) {

                    //check quantity of of use money
                    if (subscriptionService.checkUserMoneyState(user.getMoney(), pricePerPeriod)) {
                        //create new subscription
                        subscriptionService.createSubscription(user.getUserId(), mediaId, period);
                        //subtract money from user money bill per subscription
                        userService.updateUserMoney(user.getUserId(), (user.getMoney() - pricePerPeriod));
                        //add +1 to media.subscribers DB
                        mediaService.updateMediaSubscribers(media.getSubscribers() + 1, mediaId);
                        //update user session
                        session.setAttribute(String.valueOf(Constant.USER_IS_UNBLOCKED), userService.getUserById(user.getUserId()).get());
                        LOGGER.info("Subscription creating success!");
                        //redirect to subscription-success page
                        resp.sendRedirect(req.getContextPath() + "/subscription-success");
                    } else {
                        //user don't have enough money
                        //create DTO obj for save info about this subscription
                        subscriptionDTO = new SubscriptionDTO(mediaId, period, pricePerPeriod);
                        //put this DTO to session with name "SubscriptionDTO"
                        session.setAttribute(String.valueOf(Constant.SUBSCRIPTION_DTO), subscriptionDTO);
                        LOGGER.info("User doesn't have enough money. Save parameters to DTO obj and redirect to payment-service page");
                        //redirect to payment-service page
                        resp.sendRedirect(req.getContextPath() + "/payment-service");
                    }
                } else {
                    LOGGER.warn("User have this subscription. Do redirect to media page");
                    resp.sendRedirect(req.getContextPath() + "/media");
                }
            } else {
                //user without authorization
                //create DTO obj for save info about this subscription
                subscriptionDTO = new SubscriptionDTO(mediaId, period, pricePerPeriod);
                //put this DTO to session with name "SubscriptionDTO"
                session.setAttribute(String.valueOf(Constant.SUBSCRIPTION_DTO), subscriptionDTO);
                LOGGER.info("User without authorization. Save parameters to DTO obj and redirect to login page");
                //redirect to login page
                resp.sendRedirect(req.getContextPath() + "/login");
            }
        }

        if (session.getAttribute("FromPaymentService") != null || session.getAttribute("FromAuthorizationController") != null
        || (session.getAttribute("FromPaymentService") != null && session.getAttribute("FromAuthorizationController") != null)){
            if (session.getAttribute(String.valueOf(Constant.SUBSCRIPTION_DTO)) != null){
                subscriptionDTO = (SubscriptionDTO) session.getAttribute(String.valueOf(Constant.SUBSCRIPTION_DTO));
                mediaId = subscriptionDTO.getMediaId();
                period = subscriptionDTO.getPeriod();
                media = mediaService.getMediaById(mediaId).get();
                pricePerPeriod  = subscriptionDTO.getPricePerPeriod();
                user = (User) session.getAttribute(String.valueOf(Constant.USER_IS_UNBLOCKED));
                if (subscriptionService.checkUserMoneyState(user.getMoney(), pricePerPeriod)) {
                    //create new subscription
                    subscriptionService.createSubscription(user.getUserId(),mediaId,period);
                    //subtract money from user money bill per subscription
                    userService.updateUserMoney(user.getUserId(), (user.getMoney() - pricePerPeriod));
                    //add +1 to media.subscribers DB
                    mediaService.updateMediaSubscribers(media.getSubscribers() + 1, mediaId);
                    //update user session
                    session.setAttribute(String.valueOf(Constant.USER_IS_UNBLOCKED), userService.getUserById(user.getUserId()).get());
                    //remove session attribute from payment service
                    if (session.getAttribute("FromPaymentService") != null){
                        session.removeAttribute("FromPaymentService");
                    }
                    //remove session attribute from authorization controller
                    if (session.getAttribute("FromAuthorizationController") != null){
                        session.removeAttribute("FromAuthorizationController");
                    }
                    LOGGER.info("Subscription creating success!");
                    //redirect to subscription-success page
                    resp.sendRedirect(req.getContextPath() + "/subscription-success");
                }else {
                    //redirect to payment-service page
                    resp.sendRedirect(req.getContextPath() + "/payment-service");
                }
            }
        }


        if (req.getParameter("subscription") == null &&
                session.getAttribute(String.valueOf(Constant.SUBSCRIPTION_DTO)) == null) {
            LOGGER.trace("Subscription btn and SUBSCRIPTION_DTO session is null. redirect to media");
            resp.sendRedirect(req.getContextPath() + "/media");
        }
    }
}
