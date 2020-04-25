package ua.nure.khshanovskyi.infoLife.entity.subscription.builder;

import ua.nure.khshanovskyi.infoLife.entity.subscription.Subscription;

import java.sql.Date;
import java.util.Objects;

/**
 * Builder for {@link Subscription} entity.
 *
 * @author Khshanovskyi Pavlo
 */
public class SubscriptionBuilder {

    private int subscriptionId;
    private int userId;
    private int mediaId;
    private Date dateFrom;
    private Date dateTo;

    public void buildSubscribeId(int subscribeId) {
        this.subscriptionId = subscribeId;
    }

    public SubscriptionBuilder buildUserId(int userId) {
        this.userId = userId;
        return this;
    }

    public SubscriptionBuilder buildMediaId(int mediaId) {
        this.mediaId = mediaId;
        return this;
    }

    public SubscriptionBuilder buildDateFrom(Date dateFrom) {
        this.dateFrom = dateFrom;
        return this;
    }

    public SubscriptionBuilder buildDateTo(Date dateTo) {
        this.dateTo = dateTo;
        return this;
    }

    /**
     * Method build {@link Subscription} object.
     *
     * @return new {@link Subscription} entity object
     */
    public Subscription build(){
        Subscription subscription = new Subscription();

        subscription.setSubscriptionId(subscriptionId);
        subscription.setUserId(userId);
        subscription.setMediaId(mediaId);
        subscription.setDateFrom(dateFrom);
        subscription.setDateTo(dateTo);

        return subscription;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SubscriptionBuilder that = (SubscriptionBuilder) o;
        return subscriptionId == that.subscriptionId &&
                userId == that.userId &&
                mediaId == that.mediaId &&
                Objects.equals(dateFrom, that.dateFrom) &&
                Objects.equals(dateTo, that.dateTo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(subscriptionId, userId, mediaId, dateFrom, dateTo);
    }

    @Override
    public String toString() {
        return "SubscriptionBuilder{" +
                "subscriptionId=" + subscriptionId +
                ", userId=" + userId +
                ", mediaId=" + mediaId +
                ", dateFrom=" + dateFrom +
                ", dateTo=" + dateTo +
                '}';
    }
}
