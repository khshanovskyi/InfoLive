package ua.nure.khshanovskyi.infoLife.service.lowerLevel.user.creator;

import org.apache.log4j.Logger;
import ua.nure.khshanovskyi.infoLife.entity.user.User;
import ua.nure.khshanovskyi.infoLife.entity.user.builder.UserBuilder;
import ua.nure.khshanovskyi.infoLife.entity.constant.Constant;
import ua.nure.khshanovskyi.infoLife.handler.DataEnteredHandler;

import java.util.Optional;

/**
 * This class have methods for create a new user entity.
 *
 * @author Khshanovskyi Pavlo
 */
public class UserCreator {

    /**
     * {@link Logger} log4j for logs.
     */
    private static final Logger LOGGER = Logger.getLogger(UserCreator.class);

    /**
     * {@link DataEnteredHandler} object for validation entered information from {@link User}.
     */
    private DataEnteredHandler dataHandler = new DataEnteredHandler();

    /**
     * Validation entered data and if validation success create after UserBuilder a new user entity.
     *
     * @param email           - User email
     * @param surname         - User surname
     * @param name            - User name
     * @param patronymic      - User patronymic
     * @param phoneNumber     - User phoneNumber
     * @param region          -  Region where user live
     * @param district        -  District where user live
     * @param locality        -  Locality where user live
     * @param street          -  Street where user live
     * @param house           -  House where user live
     * @param flat            - Flat where user live
     * @param postcode        - Postcode of user region
     * @param password        -  User password
     * @param passwordConfirm - Repeated user password
     * @return New {@link User} entity
     */
    public Optional<User> creatingNewUser(String email, String surname, String name, String patronymic, String phoneNumber,
                                          String region, String district, String locality, String street,
                                          String house, String flat, String postcode, String password, String passwordConfirm) {

        LOGGER.trace("User creator before validation");
        if (validation(email, surname, name, patronymic, phoneNumber,
                region, locality, street, house, String.valueOf(postcode),
                password, passwordConfirm)) {

            UserBuilder userBuilder = new UserBuilder();
            LOGGER.trace("Validation success. Start to creating new user");
            userBuilder.buildUserEmail(email)
                    .buildUserSurname(surname)
                    .buildUserName((name))
                    .buildUserPatronymic(patronymic)
                    .buildUserPhoneNumber(Integer.parseInt(phoneNumber))
                    .buildUserRegion(region)
                    .buildUserDistrict(district)
                    .buildUserLocality(locality)
                    .buildUserStreet(street)
                    .buildUserHouse(house)
                    .buildUserFlat(flatValidationMethod(flat))
                    .buildUserPostcode(Integer.parseInt(postcode))
                    .buildUserMoney(0)
                    .buildUserPassword(password)
                    .buildUserRole(String.valueOf(Constant.USER))
                    .buildUserState(String.valueOf(Constant.UNBLOCKED));
            LOGGER.trace("Creating a new user is success.");
            return Optional.of(userBuilder.build());
        }
        LOGGER.trace("Creating a new user is failed.");
        return Optional.empty();
    }

    /**
     * Validation with {@link DataEnteredHandler} methods.
     *
     * @param email           - User email
     * @param surname         - User surname
     * @param name            - User name
     * @param patronymic      - User patronymic
     * @param phoneNumber     - User phoneNumber
     * @param region          - Region where user live
     * @param locality        - Locality where user live
     * @param street          - Street where user live
     * @param house           - House where user live
     * @param postcode        - Postcode of user region
     * @param password        - User password
     * @param passwordConfirm - Repeated user password
     * @return if validation success "true" else "false"
     */
    private boolean validation(String email, String surname, String name, String patronymic, String phoneNumber,
                               String region, String locality, String street, String house, String postcode,
                               String password, String passwordConfirm) {
        return dataHandler.emailValidation(email) &&
                dataHandler.nameValidation(surname) &&
                dataHandler.nameValidation(name) &&
                dataHandler.nameValidation(patronymic) &&
                dataHandler.phoneNumberValidation(phoneNumber) &&
                dataHandler.addressValidation(region) &&
                dataHandler.addressValidation(locality) &&
                dataHandler.addressValidation(street) &&
                dataHandler.houseValidation(house) &&
                dataHandler.postcodeValidation(postcode) &&
                dataHandler.passwordValidation(password) &&
                dataHandler.passwordConfirmValidation(password, passwordConfirm);
    }

    /**
     * If flat number not null - return 0, if {@link DataEnteredHandler#flatValidation(String)} success return
     * flat number, else return 0.
     *
     * @param flat - Flat number
     * @return 0 or flat number
     */
    private int flatValidationMethod(String flat) {
        if (flat != null) {
            if (dataHandler.flatValidation(flat)) {
                return Integer.parseInt(flat);
            }
        }
        return 0;
    }
}
