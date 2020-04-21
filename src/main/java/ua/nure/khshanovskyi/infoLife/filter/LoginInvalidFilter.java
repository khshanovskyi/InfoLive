package ua.nure.khshanovskyi.infoLife.filter;

import org.apache.log4j.Logger;
import ua.nure.khshanovskyi.infoLife.entity.constant.Constant;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter(urlPatterns = {"/media", "/user-interface", "/details", "/registration","/reader"})
public class LoginInvalidFilter  implements Filter {

    private static final Logger LOGGER = Logger.getLogger(SubscriptionDTOFilter.class);

    @Override
    public void init(FilterConfig filterConfig){

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpSession session = req.getSession();

        if (session.getAttribute(String.valueOf(Constant.INVALID)) != null){
            session.removeAttribute(String.valueOf(Constant.INVALID));
            LOGGER.info("Remove attribute INVALID from session. User left login page");
        }

        filterChain.doFilter(servletRequest,servletResponse);
    }

    @Override
    public void destroy() {

    }
}
