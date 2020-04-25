package ua.nure.khshanovskyi.infoLife.dao.media;

import ua.nure.khshanovskyi.infoLife.entity.media.Media;
import ua.nure.khshanovskyi.infoLife.entity.user.User;

import java.sql.Date;
import java.sql.Time;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * DAO interface for work with "media" table with different databases.
 *
 * @author Khshanovskyi Pavlo
 */
public interface IMediaDao {

    /**
     * Method return {@link List} with {@link Media} from DB.
     *
     * @return {@link List} {@link Media}
     */
    List<Media> allMedia();

    /**
     * Method return {@link Media} from DB by this {@link Media} id.
     *
     * @param mediaId - id of {@link Media}
     * @return {@link Media}
     */
    Optional<Media> getMediaById(int mediaId);

    /**
     * Method return {@link List} with {@link Media} from DB sorted by subscribers quantity.
     *
     * @return {@link List} {@link Media}
     */
    List<Media> sortByPopular();

    /**
     * Method return {@link Set} with {@link Media} from DB sorted by topic names transferable from parameter.
     *
     * @param topicName - topic names
     * @return {@link Set} {@link Media}
     */
    Set<Media> sortByTopic(String topicName);

    /**
     * Method return {@link List} with {@link Media} from DB sorted by name.
     *
     * @return {@link List} {@link Media}
     */
    List<Media> sortByName();

    /**
     * Method return {@link List} with {@link Media} from DB sorted by price where 9 > 10 (like firstly cheap).
     *
     * @return {@link List} {@link Media}
     */
    List<Media> sortByPriceAsc();

    /**
     * Method return {@link List} with {@link Media} from DB sorted by price where 10 > 9 (like firstly expensive).
     *
     * @return {@link List} {@link Media}
     */
    List<Media> sortByPriceDesc();

    /**
     * Method for searching {@link Media} by Media name.
     *
     * @param request - request from {@link User} after search field
     * @return {@link List} {@link Media}
     */
    List<Media> searchFromSearchField(String request);

    /**
     * Method for update {@link Media} field "subscribers".
     *
     * @param subscribers - new subscribers quantity
     * @param mediaId     - id of {@link Media}
     */
    void updateSubscribersQuantity(int subscribers, int mediaId);

    /**
     * Method for INSERT new {@link Media} in DB.
     *
     * @param media - {@link Media} object generated in java layer
     */
    void createNewMedia(Media media);

    /**
     * Get all "uriLogoImg" form DB in {@link List} {@link String}.
     *
     * @return {@link List} {@link String} with names of {@link Media} "uriLogoImg" field.
     */
    List<String> getAllNamesOfImgLogo();

    /**
     * Get all "pdfUri" form DB in {@link List} {@link String}
     *
     * @return {@link List} {@link String} with names of {@link Media} "pdfUri" field.
     */
    List<String> getAllNamesOfPdf();

    /**
     * Method for update {@link Media} field "mediaName".
     *
     * @param mediaName - new {@link Media} name
     * @param mediaId   - id of {@link Media}
     */
    void updateMediaName(String mediaName, int mediaId);

    /**
     * Method for update {@link Media} field "description".
     *
     * @param description - new {@link Media} description
     * @param mediaId     - id of {@link Media}
     */
    void updateMediaDescription(String description, int mediaId);

    /**
     * Method for update {@link Media} field "imgLogoUri".
     *
     * @param imgLogoUri - new imgLogoUri (name of {@link Media} logotype picture)
     * @param mediaId    - id of {@link Media}
     */
    void updateMediaImageLogoUri(String imgLogoUri, int mediaId);

    /**
     * Method for update {@link Media} field "pdfUri".
     *
     * @param pdfUri  - new pdfUri (name of PDF file)
     * @param mediaId - id of {@link Media}
     */
    void updateMediaPdfUri(String pdfUri, int mediaId);

    /**
     * Method for update {@link Media} field "topic".
     *
     * @param topic   -  new topic of {@link Media}
     * @param mediaId - id of {@link Media}
     */
    void updateMediaTopic(String topic, int mediaId);

    /**
     * Method for update {@link Media} field "price".
     *
     * @param price   - new {@link Media} price
     * @param mediaId - id of {@link Media}
     */
    void updateMediaPrice(int price, int mediaId);

    /**
     * Method for update {@link Media} field "publicationsQuantity".
     *
     * @param publicationsQuantity - new quantity publications in month of {@link Media}
     * @param mediaId              - id of {@link Media}
     */
    void updateMediaPublicationsQuantity(int publicationsQuantity, int mediaId);

    /**
     * Method for update {@link Media} field "date" and "time".
     *
     * @param date    - {@link Media} update {@link Date}
     * @param time    - {@link Media} update {@link Time}
     * @param mediaId - id of {@link Media}
     */
    void updateMediaUpdateDateAndTime(Date date, Time time, int mediaId);

    /**
     * Delete from DB {@link Media} object by mediaId.
     *
     * @param mediaId - id of {@link Media}
     */
    void deleteMediaById(int mediaId);

    /**
     * Method for testing MediaDAO
     *
     * @return {@link Media}
     */
    Optional<Media> getLastCreatedMedia();
}