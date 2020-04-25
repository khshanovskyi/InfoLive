package ua.nure.khshanovskyi.infoLife.controller.menu.role.admin;

import ua.nure.khshanovskyi.infoLife.entity.constant.Constant;
import ua.nure.khshanovskyi.infoLife.service.topLewel.media.IMediaService;
import ua.nure.khshanovskyi.infoLife.service.topLewel.user.IUserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/admin-interface")
public class AdminInterfaceController extends HttpServlet {

    private IUserService userService;
    private IMediaService mediaService;

    @Override
    public void init(){
        mediaService = (IMediaService) getServletContext().getAttribute("mediaService");
        userService = (IUserService) getServletContext().getAttribute("userService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getSession().getAttribute(String.valueOf(Constant.USER_IS_UNBLOCKED)) != null ){
            req.setAttribute("IMG_LOGO_NAMES", mediaService.getAllImgLogoNames());
            req.setAttribute("PDF_NAMES", mediaService.getAllPdfNames());
            req.getRequestDispatcher("WEB-INF/jsp/admin_interface.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email;

        //for block user by email
        if (req.getParameter("blockUserBtn") != null) {
            if (req.getParameter("blockUserByEmail") != null) {
                email = req.getParameter("blockUserByEmail");
                if (userService.checkEmailExisting(email).isPresent()) {
                    if (userService.checkUserState(email)){
                        userService.updateUserState(email, String.valueOf(Constant.UNBLOCKED));
                        req.getRequestDispatcher("WEB-INF/jsp/admin_interface.jsp").forward(req, resp);
                    }else{
                        req.setAttribute("UserISBlocked", email);
                        req.getRequestDispatcher("WEB-INF/jsp/admin_interface.jsp").forward(req, resp);
                    }
                } else {
                    req.setAttribute("Email_NOT_existing_blocked", email);
                    req.getRequestDispatcher("WEB-INF/jsp/admin_interface.jsp").forward(req, resp);
                }
            }

        }
        //for unblock user by email
        if (req.getParameter("unblockUserBtn") != null) {
            if (req.getParameter("unblockUserByEmail") != null) {
                email = req.getParameter("unblockUserByEmail");
                if (userService.checkEmailExisting(email).isPresent()) {
                    if (!userService.checkUserState(email)){
                        userService.updateUserState(email, String.valueOf(Constant.UNBLOCKED));
                        req.getRequestDispatcher("WEB-INF/jsp/admin_interface.jsp").forward(req, resp);
                    }else{
                        req.setAttribute("UserISUnblocked", email);
                        req.getRequestDispatcher("WEB-INF/jsp/admin_interface.jsp").forward(req, resp);
                    }
                } else {
                    req.setAttribute("Email_NOT_existing_Unblocked", email);
                    req.getRequestDispatcher("WEB-INF/jsp/admin_interface.jsp").forward(req, resp);
                }
            }

        }
    }

}
