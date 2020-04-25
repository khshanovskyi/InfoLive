package ua.nure.khshanovskyi.infoLife.controller.menu.role.admin;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ua.nure.khshanovskyi.infoLife.entity.media.Media;
import ua.nure.khshanovskyi.infoLife.service.topLewel.media.IMediaService;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

@WebServlet("/delete-media")
public class DeleteMediaController extends HttpServlet {

    private static final Logger LOGGER = LoggerFactory.getLogger(DeleteMediaController.class);

    private static final String PATH_FOR_IMG = "E:/InfoLive/src/main/webapp/logo_imgs/";
    private static final String PATH_FOR_PDF = "E:/InfoLive/src/main/webapp/reader/";

    private IMediaService mediaService;

    @Override
    public void init() {
        mediaService = (IMediaService) getServletContext().getAttribute("mediaService");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        if (req.getParameter("deleteMediaBtn") != null){
            LOGGER.trace("req.getAttribute(deleteMediaBtn) != null");
            int mediaId = Integer.parseInt(req.getParameter("deleteMediaID"));
            Media media = mediaService.getMediaById(mediaId).get();

            mediaService.deleteMediaById(mediaId);

            Path pathToDeleteOldImg = Paths.get(PATH_FOR_IMG + media.getUriLogoImg());
            mediaService.deleteFile(pathToDeleteOldImg);
            Path pathToDeleteOldPdf = Paths.get(PATH_FOR_PDF + media.getPdfUri());
            mediaService.deleteFile(pathToDeleteOldPdf);

            LOGGER.info("Media deleted");
            resp.sendRedirect(req.getContextPath() + "/media");
        }else{
            LOGGER.trace("req.getAttribute(deleteMediaBtn) == null");
            resp.sendRedirect(req.getContextPath() + "/media");
        }
    }
}
