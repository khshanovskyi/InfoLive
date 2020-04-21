package ua.nure.khshanovskyi.infoLife.service.topLewel.media;

import ua.nure.khshanovskyi.infoLife.entity.media.Media;

import javax.servlet.http.Part;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * @author Khshanovskyi Pavlo
 */
public interface IMediaService {

    /**
     * Get all media from DB in list.
     *
     * @return List with {@link Media}
     */
    List productList();

    /**
     * Get {@link Media} from DB by media id.
     *
     * @param mediaId - id of media
     * @return {@link Media}
     */
    Optional<Media> getMediaById(int mediaId);

    /**
     * Do sorting by subscribers quantity.
     *
     * @return {@link List} {@link Media}
     */
    List<Media> sortPopularMedia();

    /**
     * Get {@link Set} with {@link Media} where topic has language param.
     *
     * @param language - name of language
     * @return {@link Set} {@link Media}
     */
    Set<Media> getMediaWithLanguage(String language);

    /**
     * Get {@link List} with {@link Media} sorting by name.
     *
     * @return {@link List} {@link Media}
     */
    List<Media> sortByNameMedia();

    /**
     * Get {@link List} with {@link Media} sorting by price where 10 > 9.
     *
     * @return {@link List} {@link Media}
     */
    List<Media> sortByPriceMediaDESK();

    /**
     * Get {@link List} with {@link Media} sorting by price where 9 > 10.
     *
     * @return {@link List} {@link Media}
     */
    List<Media> sortByPriceMediaASK();

    /**
     * Get media where topic field in DB contains topics parameter.
     *
     * @param topics - {@link List} with topics names
     * @return {@link Set} {@link Media}
     */
    Set<Media> sortByTopic(List<String> topics);

    /**
     * Locking for media in DB where media name contains request parameter.
     *
     * @param request - request from user in Search field
     * @return {@link List} {@link Media}
     */
    List<Media> searchByRequestFromUser(String request);

    /**
     * Update subscribers quantity by media id.
     *
     * @param newSubscribersQuantity - new subscribers quantity
     * @param mediaId                - id of media
     */
    void updateMediaSubscribers(int newSubscribersQuantity, int mediaId);

    /**
     * Create new {@link Media} object.
     *
     * @param mediaName          - Media name
     * @param topic              - topic of Media (like NEWS,CULINARY, SPORT)
     * @param description        - Media description
     * @param uriImgLogo         - name of image file which has a logotype of Media role
     * @param pdfUri             - name of PDF file. This PDF file contain scan of Media last edition
     * @param price              - price of Media
     * @param publicationInMonth - quantity publications in month
     */
    void createNewMedia(String mediaName, String topic, String description, String uriImgLogo, String pdfUri, int price, int publicationInMonth);

    /**
     * Get all image names from DB.
     *
     * @return {@link List} with {@link String} names of logo images.
     */
    List<String> getAllImgLogoNames();

    /**
     * Get all PDF names from DB.
     *
     * @return {@link List} with {@link String} names of PDF files.
     */
    List<String> getAllPdfNames();

    /**
     * Update {@link Media} name by Media id.
     *
     * @param mediaName - new Media name
     * @param mediaId   - id of Media
     */
    void updateMediaNameService(String mediaName, int mediaId);

    /**
     * Update {@link Media} description by Media id.
     *
     * @param description - new descriptions of {@link Media}
     * @param mediaId     - id of Media
     */
    void updateMediaDescriptionService(String description, int mediaId);

    /**
     * Update image (logo of {@link Media}). Update in DB and in project.
     * Firstly upload image-file after delete old logo image file after put a new name to DB.
     *
     * @param imgLogoUri   - new name of image
     * @param mediaId      - id of Media
     * @param part         - file uploaded from WEB-interface
     * @param pathToCreate - path where we put new image file
     * @param pathToDelete - path where we need to delete old image file
     */
    void updateMediaImageLogoUriService(String imgLogoUri, int mediaId, Part part, String pathToCreate, Path pathToDelete);

    /**
     * Update PDF-file of {@link Media}. Update in DB and in project.
     * Firstly upload PDF-file after delete old PDF-file after put a new name to DB.
     *
     * @param pdfUri       - new name of PDF file
     * @param mediaId      - id of Media
     * @param part         - file uploaded from WEB-interface
     * @param pathToCreate - path where we put new PDF file
     * @param pathToDelete - path where we need to delete old PDF file
     */
    void updateMediaPdfUriService(String pdfUri, int mediaId, Part part, String pathToCreate, Path pathToDelete);

    /**
     * Update topic of {@link Media}.
     *
     * @param topic   - new topic
     * @param mediaId - id of Media
     */
    void updateMediaTopicService(String topic, int mediaId);

    /**
     * Update price of {@link Media}.
     *
     * @param price   - new price of Media
     * @param mediaId - id of Media
     */
    void updateMediaPriceService(int price, int mediaId);

    /**
     * Update publicationsQuantity of {@link Media}.
     *
     * @param publicationsQuantity - new publications quantity in month
     * @param mediaId              - id of Media
     */
    void updateMediaPublicationsQuantityService(int publicationsQuantity, int mediaId);

    /**
     * Method for put new PDF or image file in application.
     *
     * @param part - file uploaded from WEB-interface
     * @param path - path where we put a new file
     */
    void putFile(Part part, String path);

    /**
     * Method for delete old PDF or image file.
     *
     * @param path - path where we need to delete old file
     */
    void deleteFile(Path path);

    /**
     * Handle array with topic names.
     *
     * @param topicSplit - array with topic names for  {@link Media}
     * @return {@link String} with new topic parameters
     */
    String handleTopicString(String[] topicSplit);

    /**
     * Delete media from DB.
     *
     * @param mediaId - id of Media
     */
    void deleteMediaById(int mediaId);
}
