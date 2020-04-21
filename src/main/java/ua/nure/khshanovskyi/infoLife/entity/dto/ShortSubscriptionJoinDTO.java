package ua.nure.khshanovskyi.infoLife.entity.dto;

import java.sql.Date;

public class ShortSubscriptionJoinDTO {

    private int subscriptionId;
    private int userId;
    private Date dateFrom;
    private Date dateTo;
    private int mediaId;
    private String mediaName;
    private int price;
    private int quantityPublicationInMonth;

    public int getSubscriptionId() {
        return subscriptionId;
    }

    public int getUserId() {
        return userId;
    }

    public Date getDateFrom() {
        return dateFrom;
    }

    public Date getDateTo() {
        return dateTo;
    }

    public int getMediaId() {
        return mediaId;
    }

    public String getMediaName() {
        return mediaName;
    }

    public int getPrice() {
        return price;
    }

    public int getQuantityPublicationInMonth() {
        return quantityPublicationInMonth;
    }

    public ShortSubscriptionJoinDTO(int subscriptionId, int userId, Date dateFrom, Date dateTo, int mediaId,
                                    String mediaName, int price, int quantityPublicationInMonth) {
        this.subscriptionId = subscriptionId;
        this.userId = userId;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
        this.mediaId = mediaId;
        this.mediaName = mediaName;
        this.price = price;
        this.quantityPublicationInMonth = quantityPublicationInMonth;
    }

    @Override
    public String toString() {
        return "ShortSubscriptionJoinDTO{" +
                "subscriptionId=" + subscriptionId +
                ", userId=" + userId +
                ", dateFrom=" + dateFrom +
                ", dateTo=" + dateTo +
                ", mediaId=" + mediaId +
                ", mediaName='" + mediaName + '\'' +
                ", price=" + price +
                ", quantityPublicationInMonth=" + quantityPublicationInMonth +
                '}';
    }
}
