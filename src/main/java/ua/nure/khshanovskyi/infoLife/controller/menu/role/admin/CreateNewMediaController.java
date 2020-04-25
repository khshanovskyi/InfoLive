package ua.nure.khshanovskyi.infoLife.controller.menu.role.admin;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ua.nure.khshanovskyi.infoLife.service.topLewel.media.IMediaService;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;

@WebServlet("/create-media")
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024, // 1 MB
        maxFileSize = 1024 * 1024 * 30, // 30 MB
        maxRequestSize = 1024 * 1024 * 40 // 40 MB
)
public class CreateNewMediaController extends HttpServlet {

    private static final Logger LOGGER = LoggerFactory.getLogger(CreateNewMediaController.class);

    private static final String pathForImg = "E:/InfoLive/src/main/webapp/logo_imgs/";
    private static final String pathForPdf = "E:/InfoLive/src/main/webapp/reader/";

    private IMediaService mediaService;

    @Override
    public void init() {
        mediaService = (IMediaService) getServletContext().getAttribute("mediaService");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getParameter("btn_create_new_media") != null) {
            String topic = mediaService.handleTopicString(req.getParameterValues("topic"));

            mediaService.createNewMedia(req.getParameter("mediaName"), topic, req.getParameter("description"),
                    req.getParameter("imgName"), req.getParameter("pdfName"), Integer.parseInt(req.getParameter("price")),
                    Integer.parseInt(req.getParameter("publicationsInMonth")));
            Part logPart = req.getPart("imgLogo");
            mediaService.putFile(logPart, pathForImg + req.getParameter("imgName"));
            Part partPdf = req.getPart("pdfFile");
            mediaService.putFile(partPdf, pathForPdf + req.getParameter("pdfName"));
            LOGGER.trace("new media created");
            resp.sendRedirect(req.getContextPath() + "/media");
        }
        else{
            resp.sendRedirect(req.getContextPath() + "/admin-interface");
        }
    }
}
