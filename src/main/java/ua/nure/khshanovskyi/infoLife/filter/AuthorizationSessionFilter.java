package ua.nure.khshanovskyi.infoLife.filter;

import ua.nure.khshanovskyi.infoLife.entity.constant.Constant;
import ua.nure.khshanovskyi.infoLife.entity.user.User;
import ua.nure.khshanovskyi.infoLife.service.topLewel.user.IUserService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.Filter;
import javax.servlet.FilterConfig;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Optional;

/**
 * This filter for restore {@link HttpSession} for {@link User} from {@link Cookie} which we got from {@link User}.
 *
 * @author Khshanovskyi Pavlo
 */
@WebFilter(urlPatterns = {"/media", "/details", "/reader"})
public class AuthorizationSessionFilter implements Filter {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthorizationSessionFilter.class);

    private IUserService userService;

    //init for filter config
    @Override
    public void init(FilterConfig filterConfig){
        userService = (IUserService) filterConfig.getServletContext().getAttribute("userService");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse) servletResponse;
        HttpSession session = req.getSession();
        Cookie[] cookies = req.getCookies();

        if (session.getAttribute(String.valueOf(Constant.USER_IS_UNBLOCKED)) == null){
            if (cookies != null){
                for (Cookie cookie: cookies) {
                    if (cookie.getName().equals(String.valueOf(Constant.IN_LOGIN))){
                        Optional<User> userOptional = userService.getUserById(Integer.parseInt(cookie.getValue()));
                        if (userOptional.isPresent()){
                            session.setAttribute(String.valueOf(Constant.USER_IS_UNBLOCKED), userOptional.get());
                            session.removeAttribute(String.valueOf(Constant.GUEST));
                            LOGGER.info("Reinstall user session from cookie");
                        }
                    }else {
                        if (session.getAttribute(String.valueOf(Constant.GUEST)) == null){
                            session.setAttribute(String.valueOf(Constant.GUEST), Constant.GUEST);
                            LOGGER.info("Install GUEST session with attribute " + Constant.GUEST);
                            resp.sendRedirect(req.getContextPath() + "/media");
                            return;
                        }
                    }
                }
            }else {
                if (session.getAttribute(String.valueOf(Constant.GUEST)) == null){
                    session.setAttribute(String.valueOf(Constant.GUEST), Constant.GUEST);
                    LOGGER.info("Install GUEST session with attribute " + Constant.GUEST);
                    resp.sendRedirect(req.getContextPath() + "/media");
                    return;
                }
            }

        }
        filterChain.doFilter(servletRequest,servletResponse);
    }

    @Override
    public void destroy() {

    }
}
