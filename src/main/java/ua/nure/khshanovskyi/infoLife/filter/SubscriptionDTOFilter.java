package ua.nure.khshanovskyi.infoLife.filter;

import org.apache.log4j.Logger;
import ua.nure.khshanovskyi.infoLife.entity.constant.Constant;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter(urlPatterns = {"/media", "/user-interface", "/details", "/registration","/reader"})
public class SubscriptionDTOFilter  implements Filter {

    private static final Logger LOGGER = Logger.getLogger(SubscriptionDTOFilter.class);

    @Override
    public void init(FilterConfig filterConfig){

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpSession session = req.getSession();

        if (session.getAttribute(String.valueOf(Constant.SUBSCRIPTION_DTO)) != null){
            session.removeAttribute(String.valueOf(Constant.SUBSCRIPTION_DTO));
            LOGGER.info("Remove attribute SUBSCRIPTION_DTO from session. User left payment-service or login page");
        }

        filterChain.doFilter(servletRequest,servletResponse);
    }

    @Override
    public void destroy() {

    }
}
