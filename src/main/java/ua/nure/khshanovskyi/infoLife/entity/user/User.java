package ua.nure.khshanovskyi.infoLife.entity.user;

import java.util.Objects;

public class User {

    private int userId;
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
    private String password;
    private String role;
    private String state;

    public int getUserId() {
        return userId;
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

    public String getDistrict() {
        return district;
    }

    public String getRegion() {
        return region;
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

    public String getPassword() {
        return password;
    }

    public String getRole() {
        return role;
    }

    public String getState() {
        return state;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public void setPhoneNumber(int phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public void setLocality(String locality) {
        this.locality = locality;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public void setHouse(String house) {
        this.house = house;
    }

    public void setFlat(int flat) {
        this.flat = flat;
    }

    public void setPostcode(int postcode) {
        this.postcode = postcode;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setState(String state) {
        this.state = state;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return userId == user.userId &&
                phoneNumber == user.phoneNumber &&
                flat == user.flat &&
                postcode == user.postcode &&
                money == user.money &&
                Objects.equals(email, user.email) &&
                Objects.equals(surname, user.surname) &&
                Objects.equals(name, user.name) &&
                Objects.equals(patronymic, user.patronymic) &&
                Objects.equals(region, user.region) &&
                Objects.equals(district, user.district) &&
                Objects.equals(locality, user.locality) &&
                Objects.equals(street, user.street) &&
                Objects.equals(house, user.house) &&
                Objects.equals(password, user.password) &&
                Objects.equals(role, user.role) &&
                Objects.equals(state, user.state);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, email, surname, name, patronymic, phoneNumber, region, district, locality, street, house, flat, postcode, money, password, role, state);
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", email='" + email + '\'' +
                ", surname='" + surname + '\'' +
                ", name='" + name + '\'' +
                ", patronymic='" + patronymic + '\'' +
                ", phoneNumber=" + phoneNumber +
                ", region='" + region + '\'' +
                ", district='" + district + '\'' +
                ", locality='" + locality + '\'' +
                ", street='" + street + '\'' +
                ", house='" + house + '\'' +
                ", flat=" + flat +
                ", postcode=" + postcode +
                ", money=" + money +
                ", password='" + password + '\'' +
                ", role='" + role + '\'' +
                ", state='" + state + '\'' +
                '}';
    }
}
