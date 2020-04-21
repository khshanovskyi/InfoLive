package ua.nure.khshanovskyi.infoLife.dao.media.impl;

import org.apache.log4j.Logger;
import ua.nure.khshanovskyi.infoLife.dao.ConnectionManager;
import ua.nure.khshanovskyi.infoLife.dao.ConstantMySqlRequest;
import ua.nure.khshanovskyi.infoLife.dao.media.IMediaDao;
import ua.nure.khshanovskyi.infoLife.entity.media.Media;
import ua.nure.khshanovskyi.infoLife.entity.media.builder.MediaBuilder;
import ua.nure.khshanovskyi.infoLife.exception.DaoException;

import javax.sql.DataSource;
import java.sql.*;
import java.sql.Date;
import java.util.*;

public class MediaDaoMySql extends ConnectionManager implements IMediaDao {

    private static final Logger LOGGER = Logger.getLogger(MediaDaoMySql.class);

    private List<Media> mediaList = new ArrayList<>();
    private Set<Media> mediaSet = new HashSet<>();
    private Connection connection;
    private PreparedStatement statement;

    public MediaDaoMySql(DataSource dataSource) {
        super(dataSource);
    }


    @Override
    public List<Media> allMedia() {
        clearList();
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(ConstantMySqlRequest.SELECT_ALL_MEDIA);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                mediaList.add(extractMedia(resultSet));
            }
        } catch (SQLException e) {
            LOGGER.error("Could not get media object" + e);
        }
        return mediaList;
    }

    @Override
    public Optional<Media> getMediaById(int mediaId) {
        ResultSet resultSet = null;
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(ConstantMySqlRequest.GET_MEDIA_BY_ID)) {
            statement.setInt(1, mediaId);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(extractMedia(resultSet));
            }
        } catch (SQLException e) {
            LOGGER.error("Could not get media object" + e);
        } finally {
            close(resultSet);
        }
        return Optional.empty();
    }

    @Override
    public List<Media> sortByPopular() {
        clearList();
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(ConstantMySqlRequest.SELECT_ALL_ORDER_BY_POPULAR);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                mediaList.add(extractMedia(resultSet));
            }
        } catch (SQLException e) {
            LOGGER.error("Could not get media object" + e);
        }
        return mediaList;
    }

    @Override
    public Set<Media> sortByTopic(String topicName) {
        clearList();
        clearSet();
        ResultSet resultSet = null;
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(ConstantMySqlRequest.SEARCH_BY_TOPIC)) {
            statement.setString(1, topicName);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                mediaSet.add(extractMedia(resultSet));
            }
        } catch (SQLException e) {
            LOGGER.error("Could not get media object" + e);
        } finally {
            close(resultSet);
        }
        return mediaSet;
    }

    @Override
    public List<Media> sortByName() {
        clearList();
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(ConstantMySqlRequest.SELECT_ALL_ORDER_BY_NAME);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                mediaList.add(extractMedia(resultSet));
            }
        } catch (SQLException e) {
            LOGGER.error("Could not get media object" + e);
        }
        return mediaList;
    }

    @Override
    public List<Media> sortByPriceAsc() {
        clearList();
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(ConstantMySqlRequest.SELECT_ALL_ORDER_BY_PRICE_CHEAP);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                mediaList.add(extractMedia(resultSet));
            }
        } catch (SQLException e) {
            LOGGER.error("Could not get media object" + e);
        }
        return mediaList;
    }

    @Override
    public List<Media> sortByPriceDesc() {
        clearList();
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(ConstantMySqlRequest.SELECT_ALL_ORDER_BY_PRICE_EXPENSIVE);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                mediaList.add(extractMedia(resultSet));
            }
        } catch (SQLException e) {
            LOGGER.error("Could not get media object" + e);
        }
        return mediaList;
    }

    @Override
    public List<Media> searchFromSearchField(String request) {
        clearList();
        ResultSet resultSet = null;
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(ConstantMySqlRequest.SEARCH_BY_REQUEST)) {
            statement.setString(1, request);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                mediaList.add(extractMedia(resultSet));
            }
        } catch (SQLException e) {
            LOGGER.error("Could not get media object" + e);
        } finally {
            close(resultSet);
        }
        return mediaList;
    }

    @Override
    public void updateSubscribersQuantity(int subscribers, int mediaId) {
        try {
            connection = getConnection();
            connection.setAutoCommit(false);
            statement = connection.prepareStatement(ConstantMySqlRequest.UPDATE_SUBSCRIBERS_BY_ID);
            statement.setInt(1, subscribers);
            statement.setInt(2, mediaId);
            statement.execute();
            connection.commit();
            LOGGER.trace("Update media.subscribers success.");
        } catch (SQLException e) {
            rollback(connection);
            LOGGER.error("Problem with updating media.subscribers: " + subscribers + " media.subscribers are " + mediaId);
            throw new DaoException("Cannot update media.subscribers", e);
        } finally {
            close(connection);
            close(statement);
        }
    }

    @Override
    public void createNewMedia(Media media) {
        try {
            connection = getConnection();
            statement = connection.prepareStatement(ConstantMySqlRequest.CREATE_NEW_MEDIA);
            connection.setAutoCommit(false);
            statement.setString(1, media.getMediaName());
            statement.setString(2, media.getTopic());
            statement.setString(3, media.getDescription());
            statement.setString(4, media.getUriLogoImg());
            statement.setString(5, media.getPdfUri());
            statement.setInt(6, media.getSubscribers());
            statement.setInt(7, media.getPrice());
            statement.setInt(8, media.getQuantityPublicationInMonth());
            statement.setDate(9, media.getDate());
            statement.setTime(10, media.getTime());
            statement.execute();
            connection.commit();
            LOGGER.trace("Creating a new media is success.");
        } catch (SQLException e) {
            rollback(connection);
            LOGGER.error("Problem with creating media " + media);
            throw new DaoException("problem with creating media ", e);
        } finally {
            close(connection);
            close(statement);
        }
    }

    @Override
    public List<String> getAllNamesOfImgLogo() {
        List<String> listImgLogo = new ArrayList<>();
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(ConstantMySqlRequest.GET_ALL_IMG_LOGO_NAMES);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                listImgLogo.add(resultSet.getString(1));
            }
        } catch (SQLException e) {
            LOGGER.error("Could not get uri_logo_img string" + e);
        }
        return listImgLogo;
    }

    @Override
    public List<String> getAllNamesOfPdf() {
        List<String> listPdfUri = new ArrayList<>();
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(ConstantMySqlRequest.GET_ALL_PDF_NAMES);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                listPdfUri.add(resultSet.getString(1));
            }
        } catch (SQLException e) {
            LOGGER.error("Could not get pdf_uri string" + e);
        }
        return listPdfUri;
    }

    @Override
    public void updateMediaName(String mediaName, int mediaId) {
        try {
            connection = getConnection();
            connection.setAutoCommit(false);
            statement = connection.prepareStatement(ConstantMySqlRequest.UPDATE_MEDIA_NAME_BY_ID);
            statement.setString(1, mediaName);
            statement.setInt(2, mediaId);
            statement.execute();
            connection.commit();
            LOGGER.trace("Update media.mediaName success.");
        } catch (SQLException e) {
            rollback(connection);
            LOGGER.error("Problem with updating media.mediaName: " + mediaName + " media.mediaName are " + mediaId);
            throw new DaoException("Cannot update media.subscribers", e);
        } finally {
            close(connection);
            close(statement);
        }
    }

    @Override
    public void updateMediaDescription(String description, int mediaId) {
        try {
            connection = getConnection();
            connection.setAutoCommit(false);
            statement = connection.prepareStatement(ConstantMySqlRequest.UPDATE_DESCRIPTION_BY_ID);
            statement.setString(1, description);
            statement.setInt(2, mediaId);
            statement.execute();
            connection.commit();
            LOGGER.trace("Update media.description success.");
        } catch (SQLException e) {
            rollback(connection);
            LOGGER.error("Problem with updating media.description: " + description + " media.description are " + mediaId);
            throw new DaoException("Cannot update media.description", e);
        } finally {
            close(connection);
            close(statement);
        }
    }

    @Override
    public void updateMediaImageLogoUri(String imgLogoUri, int mediaId) {
        try {
            connection = getConnection();
            connection.setAutoCommit(false);
            statement = connection.prepareStatement(ConstantMySqlRequest.UPDATE_lOGO_IMG_URI_BY_ID);
            statement.setString(1, imgLogoUri);
            statement.setInt(2, mediaId);
            statement.execute();
            connection.commit();
            LOGGER.trace("Update media.imgLogoUri success.");
        } catch (SQLException e) {
            rollback(connection);
            LOGGER.error("Problem with updating media.imgLogoUri: " + imgLogoUri + " media.imgLogoUri are " + mediaId);
            throw new DaoException("Cannot update media.imgLogoUri", e);
        } finally {
            close(connection);
            close(statement);
        }
    }

    @Override
    public void updateMediaPdfUri(String pdfUri, int mediaId) {
        try {
            connection = getConnection();
            connection.setAutoCommit(false);
            statement = connection.prepareStatement(ConstantMySqlRequest.UPDATE_PDF_URI_BY_ID);
            statement.setString(1, pdfUri);
            statement.setInt(2, mediaId);
            statement.execute();
            connection.commit();
            LOGGER.trace("Update media.pdfUri success.");
        } catch (SQLException e) {
            rollback(connection);
            LOGGER.error("Problem with updating media.pdfUri: " + pdfUri + " media.pdfUri are " + mediaId);
            throw new DaoException("Cannot update media.pdfUri", e);
        } finally {
            close(connection);
            close(statement);
        }
    }

    @Override
    public void updateMediaTopic(String topic, int mediaId) {
        try {
            connection = getConnection();
            connection.setAutoCommit(false);
            statement = connection.prepareStatement(ConstantMySqlRequest.UPDATE_TOPIC_BY_ID);
            statement.setString(1, topic);
            statement.setInt(2, mediaId);
            statement.execute();
            connection.commit();
            LOGGER.trace("Update media.topic success.");
        } catch (SQLException e) {
            rollback(connection);
            LOGGER.error("Problem with updating media.topic: " + topic + " media.topic are " + mediaId);
            throw new DaoException("Cannot update media.topic", e);
        } finally {
            close(connection);
            close(statement);
        }
    }

    @Override
    public void updateMediaPrice(int price, int mediaId) {
        try {
            connection = getConnection();
            connection.setAutoCommit(false);
            statement = connection.prepareStatement(ConstantMySqlRequest.UPDATE_PRICE_BY_ID);
            statement.setInt(1, price);
            statement.setInt(2, mediaId);
            statement.execute();
            connection.commit();
            LOGGER.trace("Update media.price success.");
        } catch (SQLException e) {
            rollback(connection);
            LOGGER.error("Problem with updating media.price: " + price + " media.price are " + mediaId);
            throw new DaoException("Cannot update media.price", e);
        } finally {
            close(connection);
            close(statement);
        }
    }

    @Override
    public void updateMediaPublicationsQuantity(int publicationsQuantity, int mediaId) {
        try {
            connection = getConnection();
            connection.setAutoCommit(false);
            statement = connection.prepareStatement(ConstantMySqlRequest.UPDATE_PUBLICATIONS_IN_MONTH_QUANTITY_BY_ID);
            statement.setInt(1, publicationsQuantity);
            statement.setInt(2, mediaId);
            statement.execute();
            connection.commit();
            LOGGER.trace("Update media.publicationsQuantity success.");
        } catch (SQLException e) {
            rollback(connection);
            LOGGER.error("Problem with updating media.publicationsQuantity: " + publicationsQuantity
                    + " media.publicationsQuantity are " + mediaId);
            throw new DaoException("Cannot update media.publicationsQuantity", e);
        } finally {
            close(connection);
            close(statement);
        }
    }

    @Override
    public void updateMediaUpdateDateAndTime(Date date, Time time, int mediaId) {
        try {
            connection = getConnection();
            connection.setAutoCommit(false);
            statement = connection.prepareStatement(ConstantMySqlRequest.UPDATE_DATE_AND_TIME_BY_ID);
            statement.setDate(1, date);
            statement.setTime(2, time);
            statement.setInt(3, mediaId);
            statement.execute();
            connection.commit();
            LOGGER.trace("Update media.date success.");
        } catch (SQLException e) {
            rollback(connection);
            LOGGER.error("Problem with updating media.date: " + date + " media.date are " + mediaId);
            throw new DaoException("Cannot update media.date", e);
        } finally {
            close(connection);
            close(statement);
        }
    }

    @Override
    public void deleteMediaById(int mediaId) {
        try {
            connection = getConnection();
            connection.setAutoCommit(false);
            statement = connection.prepareStatement(ConstantMySqlRequest.DELETE_MEDIA_BY_ID);
            statement.setInt(1, mediaId);
            statement.execute();
            connection.commit();
            LOGGER.trace("DELETE media " + mediaId +" success.");
        } catch (SQLException e) {
            rollback(connection);
            LOGGER.error("Problem with DELETING media: " + mediaId);
            throw new DaoException("Cannot DELETE media" + mediaId, e);
        } finally {
            close(connection);
            close(statement);
        }
    }

    private Media extractMedia(ResultSet resultSet) throws SQLException {
        MediaBuilder mediaBuilder = new MediaBuilder();

        mediaBuilder.buildMediaId(resultSet.getInt("id_media"));
        mediaBuilder.buildMediaName(resultSet.getString("media_name"));
        mediaBuilder.buildMediaTopic(resultSet.getString("topic"));
        mediaBuilder.buildMediaDescription(resultSet.getString("description"));
        mediaBuilder.buildMediaUriLogImg(resultSet.getString("uri_logo_img"));
        mediaBuilder.buildMediaPdfUri(resultSet.getString("pdf_uri"));
        mediaBuilder.buildMediaSubscribers(resultSet.getInt("subscribers"));
        mediaBuilder.buildMediaPrice(resultSet.getInt("price"));
        mediaBuilder.buildMediaQuantityPublicationInMonth(resultSet.getInt("publication_in_month"));
        mediaBuilder.buildMediaDate(resultSet.getDate("update_date"));
        mediaBuilder.buildMediaTime(resultSet.getTime("update_time"));

        return mediaBuilder.build();
    }

    private void clearList() {
        if (!mediaList.isEmpty()) {
            mediaList.clear();
        }
    }

    private void clearSet() {
        if (!mediaSet.isEmpty()) {
            mediaSet.clear();
        }
    }
}
