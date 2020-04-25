package ua.nure.khshanovskyi.infoLife.controller.locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/locale")
public class LocaleController extends HttpServlet {

    private static final Logger LOGGER = LoggerFactory.getLogger(LocaleController.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String en = "default_bundle";
        String ru = "default__en_US_ru_RU";
        String ua = "default__en_US_uk_UA";
        HttpSession session = req.getSession();

        if (req.getParameter("localeBtnEN") != null) {
            session.setAttribute("bundle", en);
            LOGGER.trace("Install English bundle");
        }
        if (req.getParameter("localeBtnRU") != null) {
            session.setAttribute("bundle", ru);
            LOGGER.trace("Install russian bundle");
        }
        if (req.getParameter("localeBtnUK") != null) {
            session.setAttribute("bundle", ua);
            LOGGER.trace("Install Ukrainian bundle");
        }

        resp.sendRedirect(req.getContextPath() + req.getParameter("URLFromRequest"));
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }

}
