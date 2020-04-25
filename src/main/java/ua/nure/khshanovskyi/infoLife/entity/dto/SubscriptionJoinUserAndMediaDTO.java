package ua.nure.khshanovskyi.infoLife.entity.dto;

import java.sql.Date;

/**
 * DTO(data transfer object) for get JOIN from DB and after use it in java and view layer.
 *
 * @author Khshanovskyi Pavlo
 */
public class SubscriptionJoinUserAndMediaDTO {

    private int subscriptionId;
    private int userId;
    private int mediaId;
    private Date dateFrom;
    private Date dateTo;

    private String email;
    private String surname;
    private String name;
    private String patronymic;
    private int phoneNumber;
    private String region;
    private String district;
    private String locality;
    private String street;
    private String house;
    private int flat;
    private int postcode;
    private int money;

    private String mediaName;
    private int price;
    private int quantityPublicationInMonth;
    private Date date;

    public SubscriptionJoinUserAndMediaDTO(int subscriptionId, int userId, int mediaId, Date dateFrom, Date dateTo,
                                           String email, String surname, String name, String patronymic, int phoneNumber,
                                           String region, String district, String locality, String street, String house,
                                           int flat, int postcode, int money, String mediaName, int price,
                                           int quantityPublicationInMonth, Date date) {
        this.subscriptionId = subscriptionId;
        this.userId = userId;
        this.mediaId = mediaId;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
        this.email = email;
        this.surname = surname;
        this.name = name;
        this.patronymic = patronymic;
        this.phoneNumber = phoneNumber;
        this.region = region;
        this.district = district;
        this.locality = locality;
        this.street = street;
        this.house = house;
        this.flat = flat;
        this.postcode = postcode;
        this.money = money;
        this.mediaName = mediaName;
        this.price = price;
        this.quantityPublicationInMonth = quantityPublicationInMonth;
        this.date = date;
    }

    public int getSubscriptionId() {
        return subscriptionId;
    }

    public int getUserId() {
        return userId;
    }

    public int getMediaId() {
        return mediaId;
    }

    public Date getDateFrom() {
        return dateFrom;
    }

    public Date getDateTo() {
        return dateTo;
    }

    public String getEmail() {
        return email;
    }

    public String getSurname() {
        return surname;
    }

    public String getName() {
        return name;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public int getPhoneNumber() {
        return phoneNumber;
    }

    public String getRegion() {
        return region;
    }

    public String getDistrict() {
        return district;
    }

    public String getLocality() {
        return locality;
    }

    public String getStreet() {
        return street;
    }

    public String getHouse() {
        return house;
    }

    public int getFlat() {
        return flat;
    }

    public int getPostcode() {
        return postcode;
    }

    public int getMoney() {
        return money;
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

    public Date getDate() {
        return date;
    }
}
