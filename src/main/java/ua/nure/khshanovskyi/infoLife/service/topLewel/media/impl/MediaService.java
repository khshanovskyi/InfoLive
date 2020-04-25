package ua.nure.khshanovskyi.infoLife.service.topLewel.media.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ua.nure.khshanovskyi.infoLife.dao.media.IMediaDao;
import ua.nure.khshanovskyi.infoLife.dao.media.impl.MediaDaoMySql;
import ua.nure.khshanovskyi.infoLife.entity.media.Media;
import ua.nure.khshanovskyi.infoLife.entity.media.builder.MediaBuilder;
import ua.nure.khshanovskyi.infoLife.service.topLewel.media.IMediaService;

import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Optional;

/**
 * This service is implementation of {@link IMediaService}.
 * This service work with DB and view interfaces and handle requests/response from DB and view interfaces.
 *
 * @author Khshanovskyi Pavlo
 */
public class MediaService implements IMediaService {

    /**
     * {@link Logger} log4j for logs.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(MediaService.class);

    /**
     * {@link IMediaDao} object for work with DB.
     */
    private IMediaDao mediaDao;

    /**
     * {@link List} with {@link Media} objects.
     */
    private List<Media> mediaList = new ArrayList<>();

    /**
     * {@link Set} with {@link Media} objects.
     */
    private Set<Media> mediaSet = new HashSet<>();

    /**
     * Constructor with Media DAO
     *
     * @param mediaDao - Media DAO object
     */
    public MediaService(IMediaDao mediaDao) {
        this.mediaDao = mediaDao;
    }

    /**
     * Firstly we are clearing the mediaList, after trying to take all {@link Media} from DB by
     * {@link MediaDaoMySql#allMedia()} method.
     *
     * @return List with {@link Media} objects
     */
    @Override
    public List productList() {
        clearList();
        if (mediaDao.allMedia() != null) {
            mediaList.addAll(mediaDao.allMedia());
        }
        return mediaList;
    }

    /**
     * Get {@link Media} object by {@link MediaDaoMySql#getMediaById(int)} method.
     *
     * @param mediaId - id of media
     * @return Optional with {@link Media} object
     */
    @Override
    public Optional<Media> getMediaById(int mediaId) {
        return (mediaDao.getMediaById(mediaId));
    }

    /**
     * This method firstly clearing mediaList, after call in {@link MediaDaoMySql#sortByPopular()} method.
     * Sort like 10 > 1.
     *
     * @return List with sorting {@link Media} objects by subscribers quantity
     */
    @Override
    public List<Media> sortPopularMedia() {
        clearList();
        if (mediaDao.sortByPopular() != null) {
            mediaList.addAll(mediaDao.sortByPopular());
        }
        return mediaList;
    }

    /**
     * This method firstly clearing mediaSet, after call in {@link MediaDaoMySql#sortByTopic(String)} method.
     *
     * @param language - language in topic parameters
     * @return List with sorting {@link Media} objects by language
     */
    @Override
    public Set<Media> getMediaWithLanguage(String language) {
        clearSet();
        if (mediaDao.sortByTopic(language) != null) {
            mediaSet.addAll(mediaDao.sortByTopic(language));
        }
        return mediaSet;
    }

    /**
     * This method firstly clearing mediaList, after call in {@link MediaDaoMySql#sortByName()} method.
     *
     * @return List with sorting {@link Media} objects by name
     */
    @Override
    public List<Media> sortByNameMedia() {
        clearList();
        if (mediaDao.sortByName() != null) {
            mediaList.addAll(mediaDao.sortByName());
        }
        return mediaList;
    }

    /**
     * This method firstly clearing mediaList, after call in {@link MediaDaoMySql#sortByPriceDesc()} method.
     *
     * @return List with sorting {@link Media} objects by price
     */
    @Override
    public List<Media> sortByPriceMediaDESK() {
        clearList();
        if (mediaDao.sortByPriceDesc() != null) {
            mediaList.addAll(mediaDao.sortByPriceDesc());
        }
        return mediaList;
    }

    /**
     * TThis method firstly clearing mediaList, after call in {@link MediaDaoMySql#sortByPriceAsc()} method.
     *
     * @return List with sorting {@link Media} objects by price
     */
    @Override
    public List<Media> sortByPriceMediaASK() {
        clearList();
        if (mediaDao.sortByPriceAsc() != null) {
            mediaList.addAll(mediaDao.sortByPriceAsc());
        }
        return mediaList;
    }

    /**
     * This method firstly clearing mediaSet, after call in {@link MediaDaoMySql#sortByTopic(String)} method.
     *
     * @param topics - Searching topics
     * @return Set {@link Media} object sorting by topic
     */
    @Override
    public Set<Media> sortByTopic(List<String> topics) {
        clearSet();
        for (String topic : topics) {
            if (mediaDao.sortByTopic("%" + topic + "%") != null) {
                mediaSet.addAll(mediaDao.sortByTopic("%" + topic + "%"));
            }
        }
        return mediaSet;
    }

    /**
     * This method firstly clearing mediaList, after call in {@link MediaDaoMySql#searchFromSearchField(String)} method.
     *
     * @param request - Request from user in searching field
     * @return mediaList with {@link Media} objects from DB
     */
    @Override
    public List<Media> searchByRequestFromUser(String request) {
        clearList();
        if (mediaDao.searchFromSearchField(request) != null) {
            mediaList.addAll(mediaDao.searchFromSearchField(request));
        }
        return mediaList;
    }

    /**
     * This is method creating a new {@link Media} object and after add into DB by
     * {@link MediaDaoMySql#searchFromSearchField(String)} method.
     *
     * @param mediaName          - New media name
     * @param topic              - Topic for sorting media
     * @param description        - Short description for this media
     * @param uriImgLogo         - name of picture for media
     * @param pdfUri             - name of PDF file
     * @param price              - price per one month
     * @param publicationInMonth - quantity publications in month
     */
    @Override
    public void createNewMedia(String mediaName, String topic, String description, String uriImgLogo, String pdfUri, int price, int publicationInMonth) {
        MediaBuilder mediaBuilder = new MediaBuilder();
        int subscribers = 0;
        Date date = new Date(System.currentTimeMillis());
        Time time = new Time(System.currentTimeMillis());

        mediaBuilder.buildMediaName(mediaName)
                .buildMediaTopic(topic)
                .buildMediaDescription(description)
                .buildMediaUriLogImg(uriImgLogo)
                .buildMediaPdfUri(pdfUri)
                .buildMediaSubscribers(subscribers)
                .buildMediaPrice(price)
                .buildMediaQuantityPublicationInMonth(publicationInMonth)
                .buildMediaDate(date)
                .buildMediaTime(time);

        mediaDao.createNewMedia(mediaBuilder.build());
    }

    /**
     * Get all pictures names by {@link MediaDaoMySql#getAllNamesOfImgLogo()} method.
     *
     * @return All names of pictures from DB
     */
    @Override
    public List<String> getAllImgLogoNames() {
        return mediaDao.getAllNamesOfImgLogo();
    }

    /**
     * Get all PDF files names by {@link MediaDaoMySql#getAllNamesOfPdf()} method.
     *
     * @return All names of PDF files from DB
     */
    @Override
    public List<String> getAllPdfNames() {
        return mediaDao.getAllNamesOfPdf();
    }

    /**
     * Update {@link Media} name by media id.
     * Firstly call {@link MediaDaoMySql#updateMediaName(String, int)} method for update media name,
     * after call {@link MediaDaoMySql#updateMediaUpdateDateAndTime(Date, Time, int)} method for install new date and
     * time in "update_date" and "update_time" in DB.
     *
     * @param mediaName - New media name
     * @param mediaId   - id of media in DB
     */
    @Override
    public void updateMediaNameService(String mediaName, int mediaId) {
        mediaDao.updateMediaName(mediaName, mediaId);
        mediaDao.updateMediaUpdateDateAndTime(new Date(System.currentTimeMillis()), new Time(System.currentTimeMillis()), mediaId);
    }

    /**
     * Update {@link Media} description by media id.
     * Firstly call {@link MediaDaoMySql#updateMediaDescription(String, int)} method for update media description,
     * after call {@link MediaDaoMySql#updateMediaUpdateDateAndTime(Date, Time, int)} method for install new date and
     * time in "update_date" and "update_time" in DB.
     *
     * @param description - new media description
     * @param mediaId     - id of media in DB
     */
    @Override
    public void updateMediaDescriptionService(String description, int mediaId) {
        mediaDao.updateMediaDescription(description, mediaId);
        mediaDao.updateMediaUpdateDateAndTime(new Date(System.currentTimeMillis()), new Time(System.currentTimeMillis()), mediaId);
    }

    /**
     * This method updating {@link Media} logo picture.
     * Firstly call {@link MediaDaoMySql#updateMediaImageLogoUri(String, int)} method for update media logo picture,
     * after call {@link MediaDaoMySql#updateMediaUpdateDateAndTime(Date, Time, int)} method for install new date and
     * time in "update_date" and "update_time" in DB.
     *
     * @param imgLogoUri   - new name of logo picture
     * @param mediaId      - id of media in DB
     * @param part         - a picture downloaded from view interface
     * @param pathToCreate - path where we need to put a new picture
     * @param pathToDelete - path where we need to delete old picture
     */
    @Override
    public void updateMediaImageLogoUriService(String imgLogoUri, int mediaId, Part part, String pathToCreate, Path pathToDelete) {
        putFile(part, pathToCreate);
        deleteFile(pathToDelete);
        mediaDao.updateMediaImageLogoUri(imgLogoUri, mediaId);
        mediaDao.updateMediaUpdateDateAndTime(new Date(System.currentTimeMillis()), new Time(System.currentTimeMillis()), mediaId);
    }

    /**
     * This method updating PDF file.
     * Firstly call {@link MediaDaoMySql#updateMediaPdfUri(String, int)} method for update media PDF URI,
     * after call {@link MediaDaoMySql#updateMediaUpdateDateAndTime(Date, Time, int)} method for install new date and
     * time in "update_date" and "update_time" in DB.
     *
     * @param pdfUri       - new name of PDF file
     * @param mediaId      - id of media in DB
     * @param part         - a PDF file downloaded from view interface
     * @param pathToCreate - path where we need to put a new PDF file
     * @param pathToDelete - path where we need to delete old PDF file
     */
    @Override
    public void updateMediaPdfUriService(String pdfUri, int mediaId, Part part, String pathToCreate, Path pathToDelete) {
        putFile(part, pathToCreate);
        deleteFile(pathToDelete);
        mediaDao.updateMediaPdfUri(pdfUri, mediaId);
        mediaDao.updateMediaUpdateDateAndTime(new Date(System.currentTimeMillis()), new Time(System.currentTimeMillis()), mediaId);
    }

    /**
     * Update {@link Media} topic by media id.
     * Firstly call {@link MediaDaoMySql#updateMediaTopic(String, int)} method for update media topic,
     * after call {@link MediaDaoMySql#updateMediaUpdateDateAndTime(Date, Time, int)} method for install new date and
     * time in "update_date" and "update_time" in DB.
     *
     * @param topic   - new topic of media
     * @param mediaId - id of media in DB
     */
    @Override
    public void updateMediaTopicService(String topic, int mediaId) {
        mediaDao.updateMediaTopic(topic, mediaId);
        mediaDao.updateMediaUpdateDateAndTime(new Date(System.currentTimeMillis()), new Time(System.currentTimeMillis()), mediaId);
    }

    /**
     * Update {@link Media} price by media id.
     * Firstly call {@link MediaDaoMySql#updateMediaPrice(int, int)} method for update media price,
     * after call {@link MediaDaoMySql#updateMediaUpdateDateAndTime(Date, Time, int)} method for install new date and
     * time in "update_date" and "update_time" in DB.
     *
     * @param price   - new price
     * @param mediaId - id of media in DB
     */
    @Override
    public void updateMediaPriceService(int price, int mediaId) {
        mediaDao.updateMediaPrice(price, mediaId);
        mediaDao.updateMediaUpdateDateAndTime(new Date(System.currentTimeMillis()), new Time(System.currentTimeMillis()), mediaId);
    }

    /**
     * Update publications quantity in month by {@link Media} id.
     * Firstly call {@link MediaDaoMySql#updateMediaPublicationsQuantity(int, int)} method for update media publications
     * quantity in month, after call {@link MediaDaoMySql#updateMediaUpdateDateAndTime(Date, Time, int)}
     * method for install new date and time in "update_date" and "update_time" in DB.
     *
     * @param publicationsQuantity - new publications quantity in month
     * @param mediaId              - id of media in DB
     */
    @Override
    public void updateMediaPublicationsQuantityService(int publicationsQuantity, int mediaId) {
        mediaDao.updateMediaPublicationsQuantity(publicationsQuantity, mediaId);
        mediaDao.updateMediaUpdateDateAndTime(new Date(System.currentTimeMillis()), new Time(System.currentTimeMillis()), mediaId);
    }

    /**
     * Update subscribers quantity by {@link Media} id.
     * Firstly call {@link MediaDaoMySql#updateSubscribersQuantity(int, int)} method for update subscribers on media,
     * after call {@link MediaDaoMySql#updateMediaUpdateDateAndTime(Date, Time, int)} method for install new date and
     * time in "update_date" and "update_time" in DB.
     *
     * @param newSubscribersQuantity - new subscribers quantity
     * @param mediaId                - id of media in DB
     */
    @Override
    public void updateMediaSubscribers(int newSubscribersQuantity, int mediaId) {
        mediaDao.updateSubscribersQuantity(newSubscribersQuantity, mediaId);
    }

    /**
     * This method create a new file by path.
     *
     * @param part - file which we got from view interface
     * @param path - path where we need to put a new file
     */
    @Override
    public void putFile(Part part, String path) {
        File file = new File(path);
        try (InputStream input = part.getInputStream()) {
            Files.copy(input, file.toPath());
        } catch (IOException e) {
            LOGGER.error("Problem with creating a new file in " + path);
        }
    }

    /**
     * This method delete a file by path.
     *
     * @param path - path where we need to delete a new file
     */
    @Override
    public void deleteFile(Path path) {
        try {
            Files.delete(path);
        } catch (IOException e) {
            LOGGER.error("Problem with deleting the old file in " + path.toString());
        }
    }

    /**
     * This method split topics array and append topic with each other between coma.
     *
     * @param topicSplit - array of topics
     * @return string of topics
     */
    @Override
    public String handleTopicString(String[] topicSplit) {
        StringBuilder stringBuilder = new StringBuilder();
        for (String spl : topicSplit) {
            stringBuilder.append(spl).append(",");
        }
        return stringBuilder.toString();
    }

    /**
     * Delete media from DB by {@link Media} id.
     * Call {@link MediaDaoMySql#deleteMediaById(int)} method for delete media from DB.
     *
     * @param mediaId - id of media in DB
     */
    @Override
    public void deleteMediaById(int mediaId) {
        mediaDao.deleteMediaById(mediaId);
    }

    /**
     * If mediaSet not empty clear mediaSet.
     */
    private void clearSet() {
        if (!mediaSet.isEmpty()) {
            mediaSet.clear();
        }
    }

    /**
     * If mediaList not empty clear mediaList.
     */
    private void clearList() {
        if (!mediaList.isEmpty()) {
            mediaList.clear();
        }
    }
}
