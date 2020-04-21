package ua.nure.khshanovskyi.infoLife.service.topLewel.subscribe.impl;

import ua.nure.khshanovskyi.infoLife.dao.subscribe.ISubscriptionDao;
import ua.nure.khshanovskyi.infoLife.dao.subscribe.impl.SubscriptionDaoMySql;
import ua.nure.khshanovskyi.infoLife.entity.dto.ShortSubscriptionJoinDTO;
import ua.nure.khshanovskyi.infoLife.entity.dto.SubscriptionJoinUserAndMediaDTO;
import ua.nure.khshanovskyi.infoLife.entity.subscription.Subscription;
import ua.nure.khshanovskyi.infoLife.entity.subscription.builder.SubscriptionBuilder;
import ua.nure.khshanovskyi.infoLife.entity.user.User;
import ua.nure.khshanovskyi.infoLife.service.topLewel.subscribe.ISubscriptionService;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

/**
 * Service for work with Subscription entity and DB.
 *
 * @author Khshanovskyi Pavlo
 */
public class SubscriptionService implements ISubscriptionService {

    /**
     * {@link ISubscriptionDao} object for work with DB.
     */
    private ISubscriptionDao subscribeDao;

    /**
     * Constructor with initialization {@link ISubscriptionDao}.
     *
     * @param subscribeDao - {@link ISubscriptionDao}
     */
    public SubscriptionService(ISubscriptionDao subscribeDao) {
        this.subscribeDao = subscribeDao;
    }

    /**
     * Get last user subscription by user id. This method call
     * {@link SubscriptionDaoMySql#getLastUserSubscription(int)} for get last user subscription.
     *
     * @param userId - User id
     * @return {@link Subscription} object.
     */
    @Override
    public Optional<Subscription> getLastUserSubscription(int userId) {
        return subscribeDao.getLastUserSubscription(userId);
    }

    /**
     * Call {@link SubscriptionDaoMySql#create(Subscription)} method for create a new {@link Subscription}.
     *
     * @param userId  - User id
     * @param mediaId - Media id
     * @param period  - Period activity this {@link Subscription}
     */
    @Override
    public void createSubscription(int userId, int mediaId, String period) {
        subscribeDao.create(createSubscriptionObj(userId, mediaId, period));
    }

    /**
     * Create a new {@link Subscription} object by {@link SubscriptionBuilder} methods.
     *
     * @param userId  - User id
     * @param mediaId - Media id
     * @param period  - Period activity this subscription.
     * @return New {@link Subscription} object.
     */
    @Override
    public Subscription createSubscriptionObj(int userId, int mediaId, String period) {
        SubscriptionBuilder subscriptionBuilder = new SubscriptionBuilder();
        Date currentDate = new Date(System.currentTimeMillis());
        subscriptionBuilder.buildUserId(userId)
                .buildMediaId(mediaId)
                .buildDateFrom(currentDate)
                .buildDateTo(dateInstall(period));
        return subscriptionBuilder.build();
    }

    /**
     * Method check state of activity {@link Subscription}. If {@link Subscription#getDateTo()} before current day -
     * return "true", else - "false".
     *
     * @param userId  - User id
     * @param mediaId - Media id
     * @return "true" or "false"
     */
    @Override
    public boolean checkActivityThisSubscription(int userId, int mediaId) {
        List<Subscription> listUserSubscriptions = subscribeDao.getUserSubscriptionsList(userId);
        Date currentDate = new Date(System.currentTimeMillis());
        if (listUserSubscriptions != null) {
            for (Subscription subscription : listUserSubscriptions) {
                if (subscription.getMediaId() == mediaId) {
                    return currentDate.before(subscription.getDateTo()) || currentDate.equals(subscription.getDateTo());
                }
            }
        }
        return false;
    }

    /**
     * Method get activity {@link Subscription}. If {@link Subscription#getDateTo()} before current day -
     * return {@link Optional#of(Object)} {@link Subscription}, else - {@link Optional#empty()}.
     *
     * @param userId  - User id
     * @param mediaId - Media id
     * @return {@link Optional#of(Object)} {@link Subscription}.
     */
    @Override
    public Optional<Subscription> getActivityUserSubscription(int userId, int mediaId) {
        List<Subscription> listUserSubscriptions = subscribeDao.getUserSubscriptionsList(userId);
        Date currentDate = new Date(System.currentTimeMillis());
        if (listUserSubscriptions != null) {
            for (Subscription subscription : listUserSubscriptions) {
                if (subscription.getMediaId() == mediaId) {
                    if (currentDate.before(subscription.getDateTo()) || currentDate.equals(subscription.getDateTo())) {
                        return Optional.of(subscription);
                    }
                }
            }
        }
        return Optional.empty();
    }

    /**
     * Method check {@link User} have enough money for {@link Subscription} or not.
     * If enough - return true, else - false.
     *
     * @param userMoney         - User money
     * @param subscriptionPrice - Price per subscription
     * @return true / false
     */
    @Override
    public boolean checkUserMoneyState(int userMoney, int subscriptionPrice) {
        return userMoney >= subscriptionPrice;
    }

    /**
     * Method handle date about period activity {@link Subscription} and in dependency from period change totalPrice.
     *
     * @param price  - Media price
     * @param period - Period activity subscription
     * @return int totalPrice
     */
    @Override
    public int getPricePerSubscription(int price, String period) {
        int totalPrice = 0;
        switch (period) {
            case ("one_month"):
                totalPrice = price;
                break;
            case ("three_months"):
                totalPrice = price * 3;
                break;
            case ("six_months"):
                totalPrice = price * 6;
                break;
            case ("one_year"):
                totalPrice = price * 12;
                break;
        }
        return totalPrice;
    }

    /**
     * Get from DB by {@link SubscriptionDaoMySql#getFullInfoAboutSubscription(int)} method
     * a new {@link SubscriptionJoinUserAndMediaDTO} object.
     *
     * @param userId - User id
     * @return {@link SubscriptionJoinUserAndMediaDTO}
     */
    @Override
    public Optional<SubscriptionJoinUserAndMediaDTO> getDTOOfSubscriptionUserAndMedia(int userId) {
        return subscribeDao.getFullInfoAboutSubscription(userId);
    }

    /**
     * Get from DB by {@link SubscriptionDaoMySql#getAllActivityUserSubscription(int, Date)} method a new
     * {@link SubscriptionJoinUserAndMediaDTO} object.
     *
     * @param userId - User id
     * @return {@link ShortSubscriptionJoinDTO}
     */
    @Override
    public List<ShortSubscriptionJoinDTO> getActivityUserSubscriptions(int userId) {
        Date currentDate = new Date(System.currentTimeMillis());
        return subscribeDao.getAllActivityUserSubscription(userId, currentDate);
    }

    /**
     * Method handle date about period activity {@link Subscription} and in dependency from period install in
     * {@link Date} object time activity {@link Subscription}.
     *
     * @param period - Subscription period
     * @return {@link Date}
     */
    private Date dateInstall(String period) {
        Date date = null;
        switch (period) {
            case ("one_month"):
                date = new Date(System.currentTimeMillis() + 2592000000L);
                break;
            case ("three_months"):
                date = new Date(System.currentTimeMillis() + 7776000000L);
                break;
            case ("six_months"):
                date = new Date(System.currentTimeMillis() + 15552000000L);
                break;
            case ("one_year"):
                date = new Date(System.currentTimeMillis() + 31104000000L);
                break;
        }
        return date;
    }
}
