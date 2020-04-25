package ua.nure.khshanovskyi.infoLife.entity.media.builder;

import ua.nure.khshanovskyi.infoLife.entity.media.Media;

import java.sql.Date;
import java.sql.Time;

/**
 * Builder for {@link Media} entity.
 *
 * @author Khshanovskyi Pavlo
 */
public class MediaBuilder {

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

    public MediaBuilder buildMediaId(int mediaId) {
        this.mediaId = mediaId;
        return this;
    }

    public MediaBuilder buildMediaName(String mediaName) {
        this.mediaName = mediaName;
        return this;
    }

    public MediaBuilder buildMediaTopic(String topic) {
        this.topic = topic;
        return this;
    }

    public MediaBuilder buildMediaDescription(String description) {
        this.description = description;
        return this;
    }

    public MediaBuilder buildMediaUriLogImg(String uriLogoImg) {
        this.uriLogoImg = uriLogoImg;
        return this;
    }

    public MediaBuilder buildMediaPdfUri(String pdfUri) {
        this.pdfUri = pdfUri;
        return this;
    }

    public MediaBuilder buildMediaSubscribers(int subscribers) {
        this.subscribers = subscribers;
        return this;
    }

    public MediaBuilder buildMediaPrice(int price) {
        this.price = price;
        return this;
    }

    public MediaBuilder buildMediaQuantityPublicationInMonth(int quantityPublicationInMonth) {
        this.quantityPublicationInMonth = quantityPublicationInMonth;
        return this;
    }

    public MediaBuilder buildMediaDate(Date date) {
        this.date = date;
        return this;
    }

    public MediaBuilder buildMediaTime(Time time) {
        this.time = time;
        return this;
    }

    /**
     * Method build {@link Media} object.
     *
     * @return new {@link Media} entity object
     */
    public Media build(){
        Media media = new Media();

        media.setMediaId(mediaId);
        media.setMediaName(mediaName);
        media.setTopic(topic);
        media.setDescription(description);
        media.setUriLogoImg(uriLogoImg);
        media.setPdfUri(pdfUri);
        media.setSubscribers(subscribers);
        media.setPrice(price);
        media.setQuantityPublicationInMonth(quantityPublicationInMonth);
        media.setDate(date);
        media.setTime(time);

        return media;
    }
}
