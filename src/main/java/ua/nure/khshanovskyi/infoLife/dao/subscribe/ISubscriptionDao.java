package ua.nure.khshanovskyi.infoLife.dao.subscribe;

import ua.nure.khshanovskyi.infoLife.entity.dto.ShortSubscriptionJoinDTO;
import ua.nure.khshanovskyi.infoLife.entity.dto.SubscriptionJoinUserAndMediaDTO;
import ua.nure.khshanovskyi.infoLife.entity.media.Media;
import ua.nure.khshanovskyi.infoLife.entity.subscription.Subscription;
import ua.nure.khshanovskyi.infoLife.entity.user.User;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

/**
 * DAO interface for work with "subscription" table with different databases.
 *
 * @author Khshanovskyi Pavlo
 */
public interface ISubscriptionDao {

    /**
     * Method create new {@link Subscription} object in DB.
     *
     * @param subscription - {@link Subscription} object
     */
    void create(Subscription subscription);

    /**
     * Method return all activity {@link Subscription} of {@link User}.
     *
     * @param userId - id of {@link User}
     * @return {@link List<Subscription>}
     */
    List<Subscription> getUserSubscriptionsList(int userId);

    /**
     * Method get last {@link User} {@link Subscription} object from DB.
     *
     * @param userId - id of {@link User}
     * @return {@link Optional#of(Object)} {@link Subscription} or {@link Optional#empty()}
     */
    Optional<Subscription> getLastUserSubscription(int userId);

    /**
     * Method get {@link SubscriptionJoinUserAndMediaDTO} object from DB.
     *
     * @param userId - id of {@link User}
     * @return {@link Optional#of(Object)}{@link SubscriptionJoinUserAndMediaDTO} or {@link Optional#empty()}
     */
    Optional<SubscriptionJoinUserAndMediaDTO> getFullInfoAboutSubscription(int userId);

    /**
     * Method get JOIN of {@link Subscription}, {@link Media} and {@link User} where field "date_to" more than cuurrent
     * day.
     *
     * @param userId      - id of {@link User}
     * @param currentDate - {@link Date}
     * @return {@link List<ShortSubscriptionJoinDTO>}
     */
    List<ShortSubscriptionJoinDTO> getAllActivityUserSubscription(int userId, Date currentDate);

    /**
     * For testing.
     *
     * @return {@link Optional<Subscription>} or {@link Optional#empty()}
     */
    Optional<Subscription> getLastCreatedSubscription();
}
