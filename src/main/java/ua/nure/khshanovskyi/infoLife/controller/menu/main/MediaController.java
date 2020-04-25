package ua.nure.khshanovskyi.infoLife.controller.menu.main;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ua.nure.khshanovskyi.infoLife.service.topLewel.media.IMediaService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/media")
public class MediaController extends HttpServlet {

    private static final Logger LOGGER = LoggerFactory.getLogger(MediaController.class);
    private static final String HREF_TO_JSP = "WEB-INF/jsp/media.jsp";

    private IMediaService mediaService;
    private List<String> topics = new ArrayList<>();
    private String[] constantTopics = {"POLITICS","SCIENCE","TECHNOLOGIES","CULINARY","ASTROLOGY","NEWS","FASHION","BUSINESS","SPORT"};

    @Override
    public void init(){
        mediaService = (IMediaService)getServletContext().getAttribute("mediaService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //sorting by check box topic
        if (req.getParameter("search-by-topic") != null){
            getTopics(req);
           if (!topics.isEmpty()){
               LOGGER.trace("Sort by checkbox topic.");
               req.setAttribute("mediaSet", mediaService.sortByTopic(topics));
               req.getRequestDispatcher(HREF_TO_JSP).forward(req,resp);
           }else {
               req.setAttribute("mediaList", mediaService.productList());
               req.getRequestDispatcher(HREF_TO_JSP).forward(req,resp);
           }

            //sorting by popular (subscribers quantity)
        }else if (req.getParameter("sort-popular") != null){
            LOGGER.trace("Sort by popular.");
            req.setAttribute("mediaList", mediaService.sortPopularMedia());
            req.getRequestDispatcher(HREF_TO_JSP).forward(req,resp);

            //Only with UKRAINIAN topic
        }else if (req.getParameter("only-Ukrainian") != null){
            LOGGER.trace("Sorting! Only Ukrainian");
            req.setAttribute("mediaSet", mediaService.getMediaWithLanguage("%UKRAINIAN%"));
            req.getRequestDispatcher(HREF_TO_JSP).forward(req,resp);

            //Only with RUSSIAN topic
        }else if (req.getParameter("only-Russian") != null){
            LOGGER.trace("Sorting! Only Russian");
            req.setAttribute("mediaSet", mediaService.getMediaWithLanguage("%RUSSIAN%"));
            req.getRequestDispatcher(HREF_TO_JSP).forward(req,resp);

            //Only with ENGLISH topic
        }else if (req.getParameter("only-English") != null){
            LOGGER.trace("Sorting! Only English");
            req.setAttribute("mediaSet", mediaService.getMediaWithLanguage("%ENGLISH%"));
            req.getRequestDispatcher(HREF_TO_JSP).forward(req,resp);

            //sorting by name (A-Z)
        }else if (req.getParameter("sort-by-name") != null){
            LOGGER.trace("Sorting by name.");
            req.setAttribute("mediaList", mediaService.sortByNameMedia());
            req.getRequestDispatcher(HREF_TO_JSP).forward(req,resp);

            //sorting by price ASC
        }else if (req.getParameter("sort-cheap") != null){
            LOGGER.trace("Sorting by price. From cheap to expensive");
            req.setAttribute("mediaList", mediaService.sortByPriceMediaASK());
            req.getRequestDispatcher(HREF_TO_JSP).forward(req,resp);

            //sorting by price DESC
        }else if (req.getParameter("sort-expensive") != null){
            LOGGER.trace("Sorting by price. From expensive to cheap");
            req.setAttribute("mediaList", mediaService.sortByPriceMediaDESK());
            req.getRequestDispatcher(HREF_TO_JSP).forward(req,resp);

            //search media by request from user
        }else if (req.getParameter("search") != null){
            if (req.getParameter("request") != null){
                LOGGER.trace("Search by request parameter");
                req.setAttribute("mediaList", mediaService.searchByRequestFromUser("%" + req.getParameter("request") + "%"));
                req.getRequestDispatcher(HREF_TO_JSP).forward(req,resp);
            }else {
                req.setAttribute("mediaList", mediaService.productList());
                req.getRequestDispatcher(HREF_TO_JSP).forward(req,resp);
            }

            //default resp
        }else{
            LOGGER.trace("Default response with all media");
            req.setAttribute("mediaList", mediaService.productList());
            req.getRequestDispatcher(HREF_TO_JSP).forward(req,resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LOGGER.trace("In POST");
        super.doPost(req, resp);
    }

    private void getTopics(HttpServletRequest req){
        clearList();
        String[] requestTopics = req.getParameterValues("topic");
        if (requestTopics != null){
            for (String reqTopic: requestTopics) {
                for (String constTopic: constantTopics) {
                    if (reqTopic.equals(constTopic)){
                        topics.add(reqTopic);
                    }
                }
            }
        }
    }

    private void clearList(){
        if (!topics.isEmpty()){
            topics.clear();
        }
    }
}
