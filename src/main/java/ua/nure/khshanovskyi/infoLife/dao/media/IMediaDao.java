package ua.nure.khshanovskyi.infoLife.dao.media;

import ua.nure.khshanovskyi.infoLife.entity.media.Media;

import java.sql.Date;
import java.sql.Time;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * @author Khshanovskyi Pavlo
 */
public interface IMediaDao {

    /**
     * Method return {@link List} with {@link Media} from DB.
     * @return {@link List} {@link Media}
     */
    List<Media> allMedia();

    /**
     * Method return {@link Media} from DB by this media id.
     * @param mediaId - id of Media
     * @return {@link Media}
     */
    Optional<Media> getMediaById (int mediaId);

    /**
     * Method return {@link List} with {@link Media} from DB sorted by subscribers quantity.
     * @return {@link List} {@link Media}
     */
    List<Media> sortByPopular();

    /**
     * Method return {@link Set} with {@link Media} from DB sorted by topic names transferable from parameter.
     * @param topicName - topic names
     * @return {@link Set} {@link Media}
     */
    Set<Media> sortByTopic(String topicName);

    /**
     * Method return {@link List} with {@link Media} from DB sorted by name.
     * @return {@link List} {@link Media}
     */
    List<Media> sortByName();

    /**
     * Method return {@link List} with {@link Media} from DB sorted by price where 9 > 10 (like firstly cheap).
     * @return {@link List} {@link Media}
     */
    List<Media> sortByPriceAsc();

    /**
     * Method return {@link List} with {@link Media} from DB sorted by price where 10 > 9 (like firstly expensive).
     * @return {@link List} {@link Media}
     */
    List<Media> sortByPriceDesc();

    /**
     * @param request - request from User after search field
     * @return {@link List} {@link Media}
     */
    List<Media> searchFromSearchField(String request);

    /**
     * @param subscribers
     * @param mediaId
     */
    void updateSubscribersQuantity(int subscribers,int mediaId);

    /**
     * @param media
     */
    void createNewMedia (Media media);

    /**
     * @return
     */
    List<String> getAllNamesOfImgLogo();

    /**
     * @return
     */
    List<String> getAllNamesOfPdf();

    /**
     * @param mediaName
     * @param mediaId
     */
    void updateMediaName(String mediaName,int mediaId);

    /**
     * @param description
     * @param mediaId
     */
    void updateMediaDescription(String description,int mediaId);

    /**
     * @param imgLogoUri
     * @param mediaId
     */
    void updateMediaImageLogoUri(String imgLogoUri,int mediaId);

    /**
     * @param pdfUri
     * @param mediaId
     */
    void updateMediaPdfUri(String pdfUri,int mediaId);

    /**
     * @param topic
     * @param mediaId
     */
    void updateMediaTopic(String topic,int mediaId);

    /**
     * @param price
     * @param mediaId
     */
    void updateMediaPrice(int price,int mediaId);

    /**
     * @param publicationsQuantity
     * @param mediaId
     */
    void updateMediaPublicationsQuantity(int publicationsQuantity,int mediaId);

    /**
     * @param date
     * @param time
     * @param mediaId
     */
    void updateMediaUpdateDateAndTime(Date date, Time time, int mediaId);

    /**
     * @param mediaId
     */
    void deleteMediaById(int mediaId);

}
