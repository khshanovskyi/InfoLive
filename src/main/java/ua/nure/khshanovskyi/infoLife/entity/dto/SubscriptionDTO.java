package ua.nure.khshanovskyi.infoLife.entity.dto;

/**
 * DTO(data transfer object) for get JOIN from DB and after use it in java and view layer.
 *
 * @author Khshanovskyi Pavlo
 */
public class SubscriptionDTO {

    private int mediaId;
    private String period;
    private int pricePerPeriod;

    public SubscriptionDTO(int mediaId, String period, int pricePerPeriod) {
        this.mediaId = mediaId;
        this.period = period;
        this.pricePerPeriod = pricePerPeriod;
    }

    public int getMediaId() {
        return mediaId;
    }

    public void setMediaId(int mediaId) {
        this.mediaId = mediaId;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public int getPricePerPeriod() {
        return pricePerPeriod;
    }

    public void setPricePerPeriod(int pricePerPeriod) {
        this.pricePerPeriod = pricePerPeriod;
    }
}
