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
 * This filter redirect users which don't have authorization from "/BLOCKED" page to "/media" page.
 * "/BLOCKED" page is available only for users which have {@link Constant#BLOCKED} status.
 *
 * @author Khshanovskyi Pavlo
 */
@WebFilter("/BLOCKED")
public class FilterForGuestsToBLOCKEDPage implements Filter {

    private static final Logger LOGGER = LoggerFactory.getLogger(FilterForGuestsToBLOCKEDPage.class);

    @Override
    public void init(FilterConfig filterConfig) {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        HttpSession session = req.getSession();

        if (session.getAttribute(String.valueOf(Constant.USER_IS_BLOCKED)) == null &&
                session.getAttribute(String.valueOf(Constant.USER_IS_UNBLOCKED)) == null) {
            LOGGER.info("Guest trying to go on BLOCKED page, do redirect to media page");
            resp.sendRedirect(req.getContextPath() + "/media");
            return;
        }
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
    }
}
