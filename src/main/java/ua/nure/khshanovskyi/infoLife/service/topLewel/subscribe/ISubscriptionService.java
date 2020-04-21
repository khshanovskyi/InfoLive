package ua.nure.khshanovskyi.infoLife.service.topLewel.subscribe;

import ua.nure.khshanovskyi.infoLife.entity.dto.ShortSubscriptionJoinDTO;
import ua.nure.khshanovskyi.infoLife.entity.dto.SubscriptionJoinUserAndMediaDTO;
import ua.nure.khshanovskyi.infoLife.entity.media.Media;
import ua.nure.khshanovskyi.infoLife.entity.subscription.Subscription;
import ua.nure.khshanovskyi.infoLife.entity.user.User;

import java.util.List;
import java.util.Optional;

/**
 * @author Khshanovskyi Pavlo
 */
public interface ISubscriptionService {

    /**
     * Get info about last User subscription.
     *
     * @param userId - User id
     * @return info about last {@link User} {@link Subscription}
     */
    Optional<Subscription> getLastUserSubscription(int userId);

    /**
     * Create new {@link Subscription} in DB.
     *
     * @param userId  - User id
     * @param mediaId - Media id
     * @param period  - time activity of subscription
     */
    void createSubscription(int userId, int mediaId, String period);

    /**
     * Create new {@link Subscription} in DB.
     *
     * @param userId  - User id
     * @param mediaId - Media id
     * @param period  - time activity of subscription
     * @return {@link Subscription}
     */
    Subscription createSubscriptionObj(int userId, int mediaId, String period);

    /**
     * Check enough money has {@link User} or not.
     *
     * @param userMoney         - quantity money on {@link User} bill
     * @param subscriptionPrice - cost of {@link Subscription} per some period
     * @return true / false
     */
    boolean checkUserMoneyState(int userMoney, int subscriptionPrice);

    /**
     * Check has {@link User} {@link Subscription} in activity period or not.
     *
     * @param userId  - User id
     * @param mediaId - Media id
     * @return true / false
     */
    boolean checkActivityThisSubscription(int userId, int mediaId);

    /**
     * Get activity {@link User} {@link Subscription}.
     *
     * @param userId  - User id
     * @param mediaId - Media id
     * @return {@link Optional} with activity {@link Subscription}
     */
    Optional<Subscription> getActivityUserSubscription(int userId, int mediaId);

    /**
     * Method counting total price of {@link Subscription}.
     *
     * @param price  - Media price
     * @param period - activity time of Subscription
     * @return total sum
     */
    int getPricePerSubscription(int price, String period);

    /**
     * Method return all info about {@link Subscription} and {@link User} and {@link Media}.
     *
     * @param userId - User id
     * @return {@link SubscriptionJoinUserAndMediaDTO}
     */
    Optional<SubscriptionJoinUserAndMediaDTO> getDTOOfSubscriptionUserAndMedia(int userId);

    /**
     * Method return short info about {@link Subscription} details here: {@link ShortSubscriptionJoinDTO}.
     *
     * @param userId - User id
     * @return {@link ShortSubscriptionJoinDTO}
     */
    List<ShortSubscriptionJoinDTO> getActivityUserSubscriptions(int userId);
}
