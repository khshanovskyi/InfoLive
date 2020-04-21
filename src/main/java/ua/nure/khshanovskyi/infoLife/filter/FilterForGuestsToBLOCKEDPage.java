package ua.nure.khshanovskyi.infoLife.filter;

import org.apache.log4j.Logger;
import ua.nure.khshanovskyi.infoLife.entity.constant.Constant;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter("/BLOCKED")
public class FilterForGuestsToBLOCKEDPage implements Filter {

    private static final Logger LOGGER = Logger.getLogger(FilterForGuestsToBLOCKEDPage.class);

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
