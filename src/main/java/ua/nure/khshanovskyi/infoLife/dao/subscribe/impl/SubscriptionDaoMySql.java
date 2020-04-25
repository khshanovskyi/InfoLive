package ua.nure.khshanovskyi.infoLife.dao.subscribe.impl;

import ua.nure.khshanovskyi.infoLife.dao.ConnectionManager;
import ua.nure.khshanovskyi.infoLife.dao.ConstantMySqlRequest;
import ua.nure.khshanovskyi.infoLife.dao.subscribe.ISubscriptionDao;
import ua.nure.khshanovskyi.infoLife.entity.dto.ShortSubscriptionJoinDTO;
import ua.nure.khshanovskyi.infoLife.entity.dto.SubscriptionJoinUserAndMediaDTO;
import ua.nure.khshanovskyi.infoLife.entity.media.Media;
import ua.nure.khshanovskyi.infoLife.entity.subscription.Subscription;
import ua.nure.khshanovskyi.infoLife.entity.subscription.builder.SubscriptionBuilder;
import ua.nure.khshanovskyi.infoLife.entity.user.User;
import ua.nure.khshanovskyi.infoLife.exception.DaoException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Implementation of {@link ISubscriptionDao} interface for work with "subscription" table in MySQL DB.
 *
 * @author Khshanovskyi Pavlo
 */
public class SubscriptionDaoMySql extends ConnectionManager implements ISubscriptionDao {

    /**
     * {@link Logger} log4j for logs.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(SubscriptionDaoMySql.class);

    /**
     * {@link Connection}
     */
    private Connection connection = null;
    /**
     * {@link PreparedStatement}
     */
    private PreparedStatement statement = null;
    /**
     * {@link List<Subscription>}
     */
    private List<Subscription> subscriptionsList = new ArrayList<>();

    /**
     * Constructor for initialization this class and initialization {@link DataSource} for work with DB.
     *
     * @param dataSource {@link DataSource}
     */
    public SubscriptionDaoMySql(DataSource dataSource) {
        super(dataSource);
    }


    /**
     * Method create new {@link Subscription} object in DB.
     *
     * @param subscription - {@link Subscription} object
     */
    @Override
    public void create(Subscription subscription) {
        try {
            connection = getConnection();
            statement = connection.prepareStatement(ConstantMySqlRequest.CREATE_SUBSCRIBE);
            connection.setAutoCommit(false);
            statement.setInt(1, subscription.getUserId());
            statement.setInt(2, subscription.getMediaId());
            statement.setDate(3, subscription.getDateFrom());
            statement.setDate(4, subscription.getDateTo());
            statement.execute();
            connection.commit();
            LOGGER.trace("Creating a new subscription is success.");
        } catch (SQLException e) {
            rollback(connection);
            LOGGER.error("Problem with creating subscription " + subscription);
            throw new DaoException("problem with creating subscription ", e);
        } finally {
            close(connection);
            close(statement);
        }
    }

    /**
     * Method return all activity {@link Subscription} of {@link User}.
     *
     * @param userId - id of {@link User}
     * @return {@link List<Subscription>}
     */
    @Override
    public List<Subscription> getUserSubscriptionsList(int userId) {
        clearList();
        ResultSet resultSet = null;
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(ConstantMySqlRequest.GET_ALL_USER_SUBSCRIPTIONS)) {
            statement.setInt(1, userId);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                subscriptionsList.add(extractSubscription(resultSet));
            }
        } catch (SQLException e) {
            LOGGER.error("Could not get subscription object" + e);
        } finally {
            close(resultSet);
        }
        return subscriptionsList;
    }

    /**
     * Method get last {@link User} {@link Subscription} object from DB.
     *
     * @param userId - id of {@link User}
     * @return {@link Optional#of(Object)} {@link Subscription} or {@link Optional#empty()}
     */
    @Override
    public Optional<Subscription> getLastUserSubscription(int userId) {
        ResultSet resultSet = null;
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(ConstantMySqlRequest.GET_LAST_USER_SUBSCRIPTION)) {
            statement.setInt(1, userId);
            statement.setInt(2, userId);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(extractSubscription(resultSet));
            }
        } catch (SQLException e) {
            LOGGER.error("Cannot get a subscription from DB " + e);
            throw new DaoException("Cannot get a subscription from DB ", e);
        } finally {
            close(resultSet);
        }
        return Optional.empty();
    }

    /**
     * Method get {@link SubscriptionJoinUserAndMediaDTO} object from DB.
     *
     * @param userId - id of {@link User}
     * @return {@link Optional#of(Object)}{@link SubscriptionJoinUserAndMediaDTO} or {@link Optional#empty()}
     */
    @Override
    public Optional<SubscriptionJoinUserAndMediaDTO> getFullInfoAboutSubscription(int userId) {
        ResultSet resultSet = null;
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(ConstantMySqlRequest.GET_SUBSCRIPTION_JOIN_USER_JOIN_MEDIA)) {
            statement.setInt(1, userId);
            statement.setInt(2, userId);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(new SubscriptionJoinUserAndMediaDTO(resultSet.getInt("id_subscription"),
                        userId, resultSet.getInt("id_media"), resultSet.getDate("date_from"),
                        resultSet.getDate("date_to"), resultSet.getString("email"),
                        resultSet.getString("surname"), resultSet.getString("name"),
                        resultSet.getString("patronymic"), resultSet.getInt("phone_number"),
                        resultSet.getString("region"), resultSet.getString("district"),
                        resultSet.getString("locality"), resultSet.getString("street"),
                        resultSet.getString("house"), resultSet.getInt("flat"),
                        resultSet.getInt("postcode"), resultSet.getInt("money"),
                        resultSet.getString("media_name"), resultSet.getInt("price"),
                        resultSet.getInt("publication_in_month"), resultSet.getDate("update_date")));
            }
        } catch (SQLException e) {
            LOGGER.error("Cannot get a join of subscription and use and media from DB " + e);
            throw new DaoException("Cannot get a join of subscription and use and media from DB ", e);
        } finally {
            close(resultSet);
        }
        return Optional.empty();
    }

    /**
     * Method get JOIN of {@link Subscription}, {@link Media} and {@link User} where field "date_to" more than cuurrent
     * day.
     *
     * @param userId      - id of {@link User}
     * @param currentDate - {@link Date}
     * @return {@link List<ShortSubscriptionJoinDTO>}
     */
    @Override
    public List<ShortSubscriptionJoinDTO> getAllActivityUserSubscription(int userId, Date currentDate) {
        List<ShortSubscriptionJoinDTO> dtoList = new ArrayList<>();
        ResultSet resultSet = null;
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(ConstantMySqlRequest.GET_ACTIVITY_USER_SUBSCRIPTIONS)) {
            statement.setInt(1, userId);
            statement.setDate(2, currentDate);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                dtoList.add(new ShortSubscriptionJoinDTO(resultSet.getInt("id_subscription"),
                        userId, resultSet.getDate("date_from"), resultSet.getDate("date_to"),
                        resultSet.getInt("id_media"), resultSet.getString("media_name"),
                        resultSet.getInt("price"), resultSet.getInt("publication_in_month")));
            }
        } catch (SQLException e) {
            LOGGER.error("Could not get subscription object" + e);
        } finally {
            close(resultSet);
        }
        return dtoList;
    }

    /**
     * For testing.
     *
     * @return {@link Optional<Subscription>} or {@link Optional#empty()}
     */
    @Override
    public Optional<Subscription> getLastCreatedSubscription() {
        ResultSet resultSet = null;
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(ConstantMySqlRequest.GET_LAST_CREATED_SUBSCRIPTION)) {
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(extractSubscription(resultSet));
            }
        } catch (SQLException e) {
            LOGGER.error("Could not get last subscription object" + e);
        } finally {
            close(resultSet);
        }
        return Optional.empty();
    }

    /**
     * Method extract Media parameters from DB and generated new {@link Subscription} object for work with this is
     * object in java and view layers.
     *
     * @param resultSet - {@link ResultSet}
     * @return generated {@link Subscription} object. This object is generation by {@link SubscriptionBuilder}
     * @throws SQLException - {@link SQLException}
     */
    private Subscription extractSubscription(ResultSet resultSet) throws SQLException {
        SubscriptionBuilder builder = new SubscriptionBuilder();

        builder.buildSubscribeId(resultSet.getInt("id_subscription"));
        builder.buildUserId(resultSet.getInt("user_id"));
        builder.buildMediaId(resultSet.getInt("id_media"));
        builder.buildDateFrom(resultSet.getDate("date_from"));
        builder.buildDateTo(resultSet.getDate("date_to"));

        return builder.build();
    }

    /**
     * Method for clear subscriptionsList if that not empty.
     */
    private void clearList() {
        if (!subscriptionsList.isEmpty()) {
            subscriptionsList.clear();
        }
    }
}
