package ua.nure.khshanovskyi.infoLife.dao;

/**
 * This class contains requests for MEDIA, USER and SUBSCRIPTION tables in DB.
 * This requests only for MySQL database.
 *
 * @author Khshanovskyi Pavlo
 */
public class ConstantMySqlRequest {

    // !!!FOR MEDIA!!!
    public static final String SELECT_ALL_MEDIA = "SELECT * FROM media ORDER BY update_date desc;";
    public static final String GET_MEDIA_BY_ID = "SELECT * FROM media WHERE id_media = ?;";
    public static final String SEARCH_BY_TOPIC = "SELECT * FROM media WHERE topic like ? ORDER BY update_date DESC;";
    public static final String SELECT_ALL_ORDER_BY_POPULAR = "SELECT * FROM media ORDER BY subscribers DESC;";
    public static final String SELECT_ALL_ORDER_BY_NAME = "SELECT * FROM media ORDER BY media_name;";
    public static final String SELECT_ALL_ORDER_BY_PRICE_CHEAP = "SELECT * FROM media ORDER BY price;";
    public static final String SELECT_ALL_ORDER_BY_PRICE_EXPENSIVE = "SELECT * FROM media ORDER BY price DESC;";
    public static final String SEARCH_BY_REQUEST = "SELECT * FROM media where media_name like ?;";
    public static final String UPDATE_SUBSCRIBERS_BY_ID = "UPDATE media SET subscribers = ? WHERE id_media = ?;";
    public static final String CREATE_NEW_MEDIA = "INSERT INTO media " +
            "(media_name, topic, description, uri_logo_img, pdf_uri, subscribers, price, publication_in_month, update_date, update_time) " +
            "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
    public static final String GET_ALL_IMG_LOGO_NAMES = "SELECT uri_logo_img FROM media;";
    public static final String GET_ALL_PDF_NAMES = "SELECT pdf_uri FROM media;";
    public static final String UPDATE_MEDIA_NAME_BY_ID = "UPDATE media SET media_name = ? WHERE id_media = ?;";
    public static final String UPDATE_DESCRIPTION_BY_ID = "UPDATE media SET description = ? WHERE id_media = ?;";
    public static final String UPDATE_lOGO_IMG_URI_BY_ID = "UPDATE media SET uri_logo_img = ? WHERE id_media = ?;";
    public static final String UPDATE_PDF_URI_BY_ID = "UPDATE media SET pdf_uri = ? WHERE id_media = ?;";
    public static final String UPDATE_PRICE_BY_ID = "UPDATE media SET price = ? WHERE id_media = ?;";
    public static final String UPDATE_PUBLICATIONS_IN_MONTH_QUANTITY_BY_ID = "UPDATE media SET publication_in_month = ? WHERE id_media = ?;";
    public static final String UPDATE_DATE_AND_TIME_BY_ID = "UPDATE media SET update_date = ?, update_time = ?  WHERE id_media = ?;";
    public static final String UPDATE_TOPIC_BY_ID = "UPDATE media SET topic = ? WHERE id_media = ?;";
    public static final String DELETE_MEDIA_BY_ID = "DELETE FROM media WHERE id_media = ?;";
    public static final String GET_LAST_MEDIA_OBJECT = "SELECT * FROM media WHERE id_media = (SELECT MAX(id_media) FROM media);";

    // !!!FOR USER!!!
    public static final String GET_USER_BY_ID = "SELECT * FROM user WHERE user_id = ?;";
    public static final String GET_USER_BY_EMAIL = "SELECT * FROM user WHERE email = ?;";
    public static final String CREATE_NEW_USER = "INSERT INTO user " +
            "(email, surname, name, patronymic, phone_number, region, district, locality, street, house, flat, " +
            "postcode, money, password, role, state) " +
            "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
    public static final String UPDATE_USER_EMAIL_BY_ID = "UPDATE user SET email = ? WHERE user_id = ?;";
    public static final String UPDATE_USER_SURNAME_BY_ID = "UPDATE user SET surname = ? WHERE user_id = ?;";
    public static final String UPDATE_USER_NAME_BY_ID = "UPDATE user SET name = ? WHERE user_id = ?;";
    public static final String UPDATE_USER_PATRONYMIC_BY_ID = "UPDATE user SET patronymic = ? WHERE user_id = ?;";
    public static final String UPDATE_USER_PHONE_NUMBER_BY_ID = "UPDATE user SET phone_number = ? WHERE user_id = ?;";
    public static final String UPDATE_USER_REGION_BY_ID = "UPDATE user SET region = ? WHERE user_id = ?;";
    public static final String UPDATE_USER_DISTRICT_BY_ID = "UPDATE user SET district = ? WHERE user_id = ?;";
    public static final String UPDATE_USER_LOCALITY_BY_ID = "UPDATE user SET locality = ? WHERE user_id = ?;";
    public static final String UPDATE_USER_STREET_BY_ID = "UPDATE user SET street = ? WHERE user_id = ?;";
    public static final String UPDATE_USER_HOUSE_BY_ID = "UPDATE user SET house = ? WHERE user_id = ?;";
    public static final String UPDATE_USER_FLAT_BY_ID = "UPDATE user SET flat = ? WHERE user_id = ?;";
    public static final String UPDATE_USER_POSTCODE_BY_ID = "UPDATE user SET postcode = ? WHERE user_id = ?;";
    public static final String UPDATE_USER_MONEY_BY_ID = "UPDATE user SET money = ? WHERE user_id = ?;";
    public static final String UPDATE_USER_PASSWORD_BY_ID = "UPDATE user SET password = ? WHERE user_id = ?;";
    public static final String UPDATE_USER_ROLE_BY_ID = "UPDATE user SET role = ? WHERE user_id = ?;";
    public static final String UPDATE_USER_STATE_BY_ID = "UPDATE user SET state = ? WHERE email = ?;";
    public static final String GET_LAST_USER_OBJECT = "SELECT * FROM user WHERE user_id = (SELECT MAX(user_id) FROM user);";
    public static final String DELETE_USER_BY_EMAIL = "DELETE FROM user WHERE email = ?;";

    // !!!FOR SUBSCRIPTION!!!
    public static final String CREATE_SUBSCRIBE = "INSERT INTO subscription (user_id, id_media, date_from, date_to) " +
            "VALUES (?, ?, ?, ?)";
    public static final String GET_ALL_USER_SUBSCRIPTIONS = "SELECT * FROM subscription where user_id = ?";
    public static final String GET_LAST_USER_SUBSCRIPTION = "SELECT * FROM subscription where user_id = ? " +
            "and id_subscription=(SELECT MAX(id_subscription) from subscription where user_id = ?);";
    public static final String GET_SUBSCRIPTION_JOIN_USER_JOIN_MEDIA = "SELECT * FROM subscription " +
            "inner join user on subscription.user_id = user.user_id " +
            "inner join media on subscription.id_media = media.id_media  where user.user_id = ? " +
            "and id_subscription=(SELECT MAX(id_subscription) from subscription where user_id = ?);";
    public static final String GET_ACTIVITY_USER_SUBSCRIPTIONS = "SELECT * FROM infolive.subscription " +
            "inner join infolive.media on subscription.id_media = media.id_media  where user_id = ? and date_to > ?;";
    public static final String GET_LAST_CREATED_SUBSCRIPTION = "SELECT * FROM subscription WHERE id_subscription = " +
            "(SELECT MAX(id_subscription) FROM subscription);";
}
