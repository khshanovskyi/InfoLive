package ua.nure.khshanovskyi.infoLife.entity.subscription;

import java.sql.Date;
import java.util.Objects;

/**
 * This is class the subscription entity.
 * Firstly we are creating this entity and after add to DB this object or we can get data from DB
 * and create new subscription object for using in server tern.
 */
public class Subscription {

    private int subscriptionId;
    private int userId;
    private int mediaId;
    private Date dateFrom;
    private Date dateTo;

    public int getSubscriptionId() {
        return subscriptionId;
    }

    public void setSubscriptionId(int subscriptionId) {
        this.subscriptionId = subscriptionId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getMediaId() {
        return mediaId;
    }

    public void setMediaId(int mediaId) {
        this.mediaId = mediaId;
    }

    public Date getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(Date dateFrom) {
        this.dateFrom = dateFrom;
    }

    public Date getDateTo() {
        return dateTo;
    }

    public void setDateTo(Date dateTo) {
        this.dateTo = dateTo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Subscription that = (Subscription) o;
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
        return "Subscription{" +
                "subscriptionId=" + subscriptionId +
                ", userId=" + userId +
                ", mediaId=" + mediaId +
                ", dateFrom=" + dateFrom +
                ", dateTo=" + dateTo +
                '}';
    }
}
