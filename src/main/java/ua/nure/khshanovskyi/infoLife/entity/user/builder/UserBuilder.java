package ua.nure.khshanovskyi.infoLife.entity.user.builder;

import ua.nure.khshanovskyi.infoLife.entity.user.User;

public class UserBuilder {

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

    public void buildUserId(int userId) {
        this.userId = userId;
    }

    public UserBuilder buildUserEmail(String email) {
        this.email = email;
        return this;
    }

    public UserBuilder buildUserName(String name) {
        this.name = name;
        return this;
    }

    public UserBuilder buildUserSurname(String surname) {
        this.surname = surname;
        return this;
    }

    public UserBuilder buildUserPatronymic(String patronymic) {
        this.patronymic = patronymic;
        return this;
    }

    public UserBuilder buildUserPhoneNumber(int phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    public UserBuilder buildUserRegion(String region) {
        this.region = region;
        return this;
    }

    public UserBuilder buildUserDistrict(String district) {
        this.district = district;
        return this;
    }

    public UserBuilder buildUserLocality(String locality) {
        this.locality = locality;
        return this;
    }

    public UserBuilder buildUserStreet(String street) {
        this.street = street;
        return this;
    }

    public UserBuilder buildUserHouse(String house) {
        this.house = house;
        return this;
    }

    public UserBuilder buildUserFlat(int flat) {
        this.flat = flat;
        return this;
    }

    public UserBuilder buildUserPostcode(int postcode) {
        this.postcode = postcode;
        return this;
    }

    public UserBuilder buildUserMoney(int money) {
        this.money = money;
        return this;
    }

    public UserBuilder buildUserPassword(String password) {
        this.password = password;
        return this;
    }

    public UserBuilder buildUserRole(String role) {
        this.role = role;
        return this;
    }

    public UserBuilder buildUserState(String state) {
        this.state = state;
        return this;
    }

    public User build(){
        User user = new User();

        user.setUserId(userId);
        user.setEmail(email);
        user.setSurname(surname);
        user.setName(name);
        user.setPatronymic(patronymic);
        user.setPhoneNumber(phoneNumber);
        user.setRegion(region);
        user.setDistrict(district);
        user.setLocality(locality);
        user.setStreet(street);
        user.setHouse(house);
        user.setFlat(flat);
        user.setPostcode(postcode);
        user.setMoney(money);
        user.setPassword(password);
        user.setRole(role);
        user.setState(state);

        return user;
    }
}
