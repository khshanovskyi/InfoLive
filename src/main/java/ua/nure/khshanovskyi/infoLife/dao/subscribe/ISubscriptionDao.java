package ua.nure.khshanovskyi.infoLife.dao.subscribe;

import ua.nure.khshanovskyi.infoLife.entity.dto.ShortSubscriptionJoinDTO;
import ua.nure.khshanovskyi.infoLife.entity.dto.SubscriptionJoinUserAndMediaDTO;
import ua.nure.khshanovskyi.infoLife.entity.subscription.Subscription;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

public interface ISubscriptionDao {

    void create(Subscription subscription);

    List<Subscription> getUserSubscriptionsList(int userId);

    Optional<Subscription> getLastUserSubscription(int userId);

    Optional<SubscriptionJoinUserAndMediaDTO> getFullInfoAboutSubscription(int userId);

    List<ShortSubscriptionJoinDTO> getAllActivityUserSubscription(int userId, Date currentDate);
}
