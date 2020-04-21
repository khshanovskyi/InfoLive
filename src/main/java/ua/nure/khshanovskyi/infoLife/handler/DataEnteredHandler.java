package ua.nure.khshanovskyi.infoLife.handler;

import org.apache.log4j.Logger;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DataEnteredHandler {

    private static final Logger LOGGER = Logger.getLogger(DataEnteredHandler.class);

    private static Pattern checkEmail = Pattern.compile("^[\\w!#$%&’*+/=?`{|}~^-]+(?:\\.[\\w!#$%&’*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$");
    private static Pattern checkName = Pattern.compile("[A-Za-zА-Яа-яёЁЇїІіЄєҐґ]{2,15}");
    private static Pattern checkPhoneNumber = Pattern.compile("[0-9]{9,12}");
    private static Pattern checkAddress = Pattern.compile("[A-Za-zА-Яа-яёЁЇїІіЄєҐґ0-9 .-]{2,30}");
    private static Pattern checkHouse = Pattern.compile("[0-9A-ZА-Я/]{1,5}");
    private static Pattern checkFlat = Pattern.compile("[0-9]{1,3}");
    private static Pattern checkPostCode = Pattern.compile("[0-9]{4,6}");
    private static Pattern checkPassword = Pattern.compile("[A-Za-z0-9]{8,21}");

    public boolean emailValidation(String email) {
        if (email != null) {
            Matcher matcher = checkEmail.matcher(email);
            if (matcher.matches()) {
                LOGGER.trace("Email validation success");
                return true;
            } else {
                LOGGER.trace("Email validation failed! Email is not valid");
            }
        }
        LOGGER.trace("Email validation failed! Email is NULL");
        return false;
    }

    public boolean nameValidation(String name) {
        if (name != null) {
            Matcher matcher = checkName.matcher(name);
            if (matcher.matches()) {
                LOGGER.trace("Name validation success");
                return true;
            } else {
                LOGGER.trace("Name validation failed! Name is not valid");
            }
        }
        LOGGER.trace("Name validation failed! Name is NULL");
        return false;
    }

    public boolean phoneNumberValidation(String phoneNumber) {
        if (phoneNumber != null) {
            Matcher matcher = checkPhoneNumber.matcher(phoneNumber);
            if (matcher.matches()) {
                LOGGER.trace("Phone number validation success");
                return true;
            } else {
                LOGGER.trace("Phone number validation failed! Phone number is not valid");
                return false;
            }
        }
        LOGGER.trace("Phone number validation failed! Phone number is NULL");
        return false;
    }

    public boolean addressValidation(String address) {
        if (address != null) {
            Matcher matcher = checkAddress.matcher(address);
            if (matcher.matches()) {
                LOGGER.trace("Address validation success");
                return true;
            } else {
                LOGGER.trace("Address validation failed! Address is not valid");
                return false;
            }
        }
        LOGGER.trace("Address validation failed! Address is NULL");
        return false;
    }

    public boolean houseValidation(String house) {
        if (house != null) {
            Matcher matcher = checkHouse.matcher(house);
            if (matcher.matches()) {
                LOGGER.trace("House validation success");
                return true;
            } else {
                LOGGER.trace("House validation failed! House is not valid");
                return false;
            }
        }
        LOGGER.trace("House validation failed! House is NULL");
        return false;
    }

    public boolean flatValidation(String flat) {
        Matcher matcher = checkFlat.matcher(flat);
        if (matcher.matches()) {
            LOGGER.trace("Flat validation success");
            return true;
        } else {
            LOGGER.trace("flat validation failed! flat is not valid");
            return false;
        }
    }

    public boolean postcodeValidation(String postcode) {
        if (postcode != null) {
            Matcher matcher = checkPostCode.matcher(postcode);
            if (matcher.matches()) {
                LOGGER.trace("Postcode validation success");
                return true;
            } else {
                LOGGER.trace("Postcode validation failed! Postcode is not valid");
                return false;
            }
        }
        LOGGER.trace("Postcode validation failed! Postcode is NULL");
        return false;
    }

    public boolean passwordValidation(String password) {
        if (password != null) {
            Matcher matcher = checkPassword.matcher(password);
            if (matcher.matches()) {
                LOGGER.trace("House validation success");
                return true;
            } else {
                LOGGER.trace("Password validation failed! Password is not valid");
                return false;
            }
        }
        LOGGER.trace("Password validation failed! Password is NULL");
        return false;
    }

    public boolean passwordConfirmValidation(String password, String passwordConfirm) {
        if (password != null && passwordConfirm != null) {
            return password.equals(passwordConfirm);
        }
        LOGGER.trace("Passwords NOT confirmed");
        return false;
    }
}
