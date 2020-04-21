package ua.nure.khshanovskyi.infoLife.dao.subscribe.impl;

import org.apache.log4j.Logger;
import ua.nure.khshanovskyi.infoLife.dao.ConnectionManager;
import ua.nure.khshanovskyi.infoLife.dao.ConstantMySqlRequest;
import ua.nure.khshanovskyi.infoLife.dao.subscribe.ISubscriptionDao;
import ua.nure.khshanovskyi.infoLife.entity.dto.ShortSubscriptionJoinDTO;
import ua.nure.khshanovskyi.infoLife.entity.dto.SubscriptionJoinUserAndMediaDTO;
import ua.nure.khshanovskyi.infoLife.entity.subscription.Subscription;
import ua.nure.khshanovskyi.infoLife.entity.subscription.builder.SubscriptionBuilder;
import ua.nure.khshanovskyi.infoLife.exception.DaoException;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SubscriptionDaoMySql extends ConnectionManager implements ISubscriptionDao {

    private static final Logger LOGGER = Logger.getLogger(SubscriptionDaoMySql.class);

    private Connection connection = null;
    private PreparedStatement statement = null;
    private List<Subscription> subscriptionsList = new ArrayList<>();

    public SubscriptionDaoMySql(DataSource dataSource) {
        super(dataSource);
    }

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

    private Subscription extractSubscription(ResultSet resultSet) throws SQLException {
        SubscriptionBuilder builder = new SubscriptionBuilder();

        builder.buildSubscribeId(resultSet.getInt("id_subscription"));
        builder.buildUserId(resultSet.getInt("user_id"));
        builder.buildMediaId(resultSet.getInt("id_media"));
        builder.buildDateFrom(resultSet.getDate("date_from"));
        builder.buildDateTo(resultSet.getDate("date_to"));

        return builder.build();
    }

    private void clearList() {
        if (!subscriptionsList.isEmpty()) {
            subscriptionsList.clear();
        }
    }
}
