package ua.nure.khshanovskyi.infoLife.controller.menu.reader;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/reader")
public class ReaderController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println(req.getParameter("read-online"));
        if (req.getParameter("read-online") != null){
            req.setAttribute("pathToFile", req.getParameter("read-online"));
            req.getRequestDispatcher("WEB-INF/jsp/reader.jsp").forward(req,resp);
        }else{
            resp.sendRedirect(req.getContextPath() + "/media");
        }
    }
}
