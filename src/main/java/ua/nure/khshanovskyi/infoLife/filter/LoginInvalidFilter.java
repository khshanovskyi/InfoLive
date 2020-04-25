package ua.nure.khshanovskyi.infoLife.filter;

import ua.nure.khshanovskyi.infoLife.entity.constant.Constant;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.Filter;
import javax.servlet.FilterConfig;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * This filter skip {@link HttpSession} with attribute {@link Constant#INVALID} if user left login page and go to
 * pages listed bellow.
 *
 * @author Khshanovskyi Pavlo
 */
@WebFilter(urlPatterns = {"/media", "/user-interface", "/details", "/registration", "/reader"})
public class LoginInvalidFilter implements Filter {

    private static final Logger LOGGER = LoggerFactory.getLogger(SubscriptionDTOFilter.class);

    @Override
    public void init(FilterConfig filterConfig) {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpSession session = req.getSession();

        if (session.getAttribute(String.valueOf(Constant.INVALID)) != null) {
            session.removeAttribute(String.valueOf(Constant.INVALID));
            LOGGER.info("Remove attribute INVALID from session. User left login page");
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {

    }
}
