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
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * This filter redirect users which have the {@link Constant#BLOCKED} status in {@link HttpSession#getAttribute(String)}
 * from pages listed bellow to "/BLOCKED" page.
 *
 * @author Khshanovskyi Pavlo
 */
@WebFilter(urlPatterns = {"/media", "/login", "/registration", "/reader", "/subscription-success", "/user-interface",
        "/payment-service", "/my-subscriptions", "/admin-interface"})
public class FilterBlockUser implements Filter {

    private static final Logger LOGGER = LoggerFactory.getLogger(FilterBlockUser.class);

    @Override
    public void init(FilterConfig filterConfig) {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        HttpSession session = req.getSession();

        if (session.getAttribute(String.valueOf(Constant.USER_IS_BLOCKED)) != null) {
            LOGGER.info("Block user trying to use our resource");
            resp.sendRedirect(req.getContextPath() + "/BLOCKED");
            return;
        }
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
    }
}
