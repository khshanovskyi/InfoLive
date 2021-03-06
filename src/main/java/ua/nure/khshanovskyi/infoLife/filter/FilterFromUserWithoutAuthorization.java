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
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * This filter redirect users which don't have authorization from pages listed bellow to "/media" page.
 *
 * @author Khshanovskyi Pavlo
 */
@WebFilter(urlPatterns = {"/subscription-success", "/user-interface", "/payment-service", "/my-subscriptions", "/admin-interface"})
public class FilterFromUserWithoutAuthorization implements Filter {

    private static final Logger LOGGER = LoggerFactory.getLogger(FilterFromUserWithoutAuthorization.class);

    @Override
    public void init(FilterConfig filterConfig) {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse) servletResponse;
        HttpSession session = req.getSession();

        if (session.getAttribute(String.valueOf(Constant.USER_IS_UNBLOCKED)) == null) {
            LOGGER.info("User without authorization want to go on page only with authorization. Redirect tp media page");
            resp.sendRedirect(req.getContextPath() + "/media");
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {

    }
}
