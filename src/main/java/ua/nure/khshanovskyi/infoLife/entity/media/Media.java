package ua.nure.khshanovskyi.infoLife.entity.media;

import java.sql.Date;
import java.sql.Time;
import java.util.Objects;

/**
 * Entity of media.
 */
public class Media {

    private int mediaId;
    private String mediaName;
    private String topic;
    private String description;
    private String uriLogoImg;
    private String pdfUri;
    private int subscribers;
    private int price;
    private int quantityPublicationInMonth;
    private Date date;
    private Time time;

    public int getMediaId() {
        return mediaId;
    }

    public void setMediaId(int mediaId) {
        this.mediaId = mediaId;
    }

    public String getMediaName() {
        return mediaName;
    }

    public void setMediaName(String mediaName) {
        this.mediaName = mediaName;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUriLogoImg() {
        return uriLogoImg;
    }

    public void setUriLogoImg(String uriLogoImg) {
        this.uriLogoImg = uriLogoImg;
    }

    public String getPdfUri() {
        return pdfUri;
    }

    public void setPdfUri(String pdfUri) {
        this.pdfUri = pdfUri;
    }

    public int getSubscribers() {
        return subscribers;
    }

    public void setSubscribers(int subscribers) {
        this.subscribers = subscribers;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getQuantityPublicationInMonth() {
        return quantityPublicationInMonth;
    }

    public void setQuantityPublicationInMonth(int quantityPublicationInMonth) {
        this.quantityPublicationInMonth = quantityPublicationInMonth;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Time getTime() {
        return time;
    }

    public void setTime(Time time) {
        this.time = time;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Media media = (Media) o;
        return mediaId == media.mediaId &&
                subscribers == media.subscribers &&
                price == media.price &&
                quantityPublicationInMonth == media.quantityPublicationInMonth &&
                Objects.equals(mediaName, media.mediaName) &&
                Objects.equals(topic, media.topic) &&
                Objects.equals(description, media.description) &&
                Objects.equals(uriLogoImg, media.uriLogoImg) &&
                Objects.equals(pdfUri, media.pdfUri) &&
                Objects.equals(date, media.date) &&
                Objects.equals(time, media.time);
    }

    @Override
    public int hashCode() {
        return Objects.hash(mediaId, mediaName, topic, description, uriLogoImg, pdfUri, subscribers, price, quantityPublicationInMonth, date, time);
    }

    @Override
    public String toString() {
        return "Media{" +
                "mediaId=" + mediaId +
                ", mediaName='" + mediaName + '\'' +
                ", topic='" + topic + '\'' +
                ", description='" + description + '\'' +
                ", uriLogoImg='" + uriLogoImg + '\'' +
                ", pdfUri='" + pdfUri + '\'' +
                ", subscribers=" + subscribers +
                ", price=" + price +
                ", quantityPublicationInMonth=" + quantityPublicationInMonth +
                ", date=" + date +
                ", time=" + time +
                '}';
    }
}
