package ua.nure.khshanovskyi.infoLife.controller.menu.role.admin;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ua.nure.khshanovskyi.infoLife.entity.media.Media;
import ua.nure.khshanovskyi.infoLife.service.topLewel.media.IMediaService;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

@WebServlet("/update-media")
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024, // 1 MB
        maxFileSize = 1024 * 1024 * 30, // 30 MB
        maxRequestSize = 1024 * 1024 * 40 // 40 MB
)
public class UpdateMediaController extends HttpServlet {

    private static final Logger LOGGER = LoggerFactory.getLogger(UpdateMediaController.class);

    private static final String PATH_FOR_IMG = "E:/InfoLive/src/main/webapp/logo_imgs/";
    private static final String PATH_FOR_PDF = "E:/InfoLive/src/main/webapp/reader/";
    private static final String MEDIA = "media";
    private static final String DISTRIBUTOR = "/distributor";
    private static final String UPDATE_JSP = "WEB-INF/jsp/update_media.jsp";
    private static final String IMG_LOGO_NAMES = "IMG_LOGO_NAMES";
    private static final String PDF_NAMES = "PDF_NAMES";

    private IMediaService mediaService;

    @Override
    public void init() {
        mediaService = (IMediaService) getServletContext().getAttribute("mediaService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getParameter("update_media_id") != null) {
            req.setAttribute(IMG_LOGO_NAMES, mediaService.getAllImgLogoNames());
            req.setAttribute(PDF_NAMES, mediaService.getAllPdfNames());
            req.setAttribute(MEDIA, mediaService.getMediaById(Integer.parseInt(req.getParameter("update_media_id"))).get());
            req.getRequestDispatcher(UPDATE_JSP).forward(req, resp);
        } else {
            resp.sendRedirect(req.getContextPath() + DISTRIBUTOR);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int mediaId;
        //update media name
        if (req.getParameter("updateNameBtn") != null) {
            mediaId = Integer.parseInt(req.getParameter("idOfMedia"));
            mediaService.updateMediaNameService(req.getParameter("newMediaName"), mediaId);
            LOGGER.info("Media name updated. New media name: " + req.getParameter("newMediaName") + ", mediaId: " + mediaId);
            req.setAttribute("mediaNameUpdated", "MediaNameUpdated");
            putMediaToReqScopeAndForward(req, resp, mediaId);
        }
        //update media description, logo image, PDF-file
        if (req.getParameter("update_main_info_media") != null) {
            mediaId = Integer.parseInt(req.getParameter("idOfMedia"));
            Media media = mediaService.getMediaById(mediaId).get();
            //update media description
            if (req.getParameter("newDescription") != null) {
                mediaService.updateMediaDescriptionService(req.getParameter("newDescription"), mediaId);
                LOGGER.info("Media description by mediaId: " + mediaId + " updated.");
                req.setAttribute("descriptionUpdated", "descriptionUpdated");
            }
            //update media logo image
            if (req.getParameter("imgName") != null && req.getPart("imgLogoFile") != null) {
                Part logPart = req.getPart("imgLogoFile");
                Path pathToDeleteOldImg = Paths.get(PATH_FOR_IMG + media.getUriLogoImg());
                mediaService.updateMediaImageLogoUriService(req.getParameter("imgName"), mediaId,
                        logPart, PATH_FOR_IMG + req.getParameter("imgName"), pathToDeleteOldImg);
                LOGGER.info("Media logo image by mediaId: " + mediaId + " updated.");
                req.setAttribute("imgLogoUpdated", "imgLogoUpdated");
            }
            //update media PDF-file
            if (req.getParameter("pdfName") != null && req.getPart("pdfFile") != null) {
                Part pdfPart = req.getPart("pdfFile");
                Path pathToDeleteOldPdf = Paths.get(PATH_FOR_PDF + media.getPdfUri());
                mediaService.updateMediaPdfUriService(req.getParameter("pdfName"), mediaId,
                        pdfPart, PATH_FOR_PDF + req.getParameter("pdfName"), pathToDeleteOldPdf);
                LOGGER.info("Media PDF file by mediaId: " + mediaId + " updated.");
                req.setAttribute("PDFUpdated", "PDFUpdated");
            }
            putMediaToReqScopeAndForward(req, resp, mediaId);
        }
        //update media topic
        if (req.getParameter("updateMediaTopics") != null) {
            mediaId = Integer.parseInt(req.getParameter("idOfMedia"));
            String topics = mediaService.handleTopicString(req.getParameterValues("topic"));
            mediaService.updateMediaTopicService(topics, mediaId);
            LOGGER.info("Media topic by mediaId: " + mediaId + " updated.");
            req.setAttribute("topicUpdated", "topicUpdated");
            putMediaToReqScopeAndForward(req, resp, mediaId);
        }
        //update media price
        if (req.getParameter("updateMediaPrice") != null) {
            mediaId = Integer.parseInt(req.getParameter("idOfMedia"));
            mediaService.updateMediaPriceService(Integer.parseInt(req.getParameter("newMediaPrice")), mediaId);
            LOGGER.info("Media price by mediaId: " + mediaId + " updated.");
            req.setAttribute("priceUpdated", "priceUpdated");
            putMediaToReqScopeAndForward(req, resp, mediaId);
        }
        //update publications in month quantity
        if (req.getParameter("updateMediaPublicationsQuantity") != null) {
            mediaId = Integer.parseInt(req.getParameter("idOfMedia"));
            mediaService.updateMediaPublicationsQuantityService(Integer.parseInt(req.getParameter("newMediaPublicationsQuantity")), mediaId);
            LOGGER.info("Media publications in month by mediaId: " + mediaId + " updated.");
            req.setAttribute("publicationsQuantityUpdated", "publicationsQuantityUpdated");
            putMediaToReqScopeAndForward(req, resp, mediaId);
        }
        //update media subscribers quantity
        if (req.getParameter("updateMediaSubscribers") != null) {
            mediaId = Integer.parseInt(req.getParameter("idOfMedia"));
            mediaService.updateMediaSubscribers(Integer.parseInt(req.getParameter("newMediaSubscribers")), mediaId);
            LOGGER.info("Media subscribers quantity by mediaId: " + mediaId + " updated.");
            req.setAttribute("subscribersQuantityUpdated", "subscribersQuantityUpdated");
            putMediaToReqScopeAndForward(req, resp, mediaId);
        }

        if (req.getParameter("update_main_info_media") == null && req.getParameter("updateNameBtn") == null &&
                req.getParameter("updateMediaTopics") == null && req.getParameter("updateMediaPrice") == null &&
                req.getParameter("updateMediaSubscribers") == null) {
            resp.sendRedirect(req.getContextPath() + DISTRIBUTOR);
        }
    }

    private void putMediaToReqScopeAndForward(HttpServletRequest req, HttpServletResponse resp, int mediaId) throws ServletException, IOException {
        req.setAttribute(IMG_LOGO_NAMES, mediaService.getAllImgLogoNames());
        req.setAttribute(PDF_NAMES, mediaService.getAllPdfNames());
        req.setAttribute(MEDIA, mediaService.getMediaById(mediaId).get());
        req.getRequestDispatcher(UPDATE_JSP).forward(req, resp);
    }
}
