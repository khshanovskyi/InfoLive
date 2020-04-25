package ua.nure.khshanovskyi.infoLife.service.topLewel.user.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ua.nure.khshanovskyi.infoLife.dao.user.IUserDao;
import ua.nure.khshanovskyi.infoLife.entity.user.User;
import ua.nure.khshanovskyi.infoLife.entity.constant.Constant;
import ua.nure.khshanovskyi.infoLife.service.topLewel.user.IUserService;

import java.util.Optional;

/**
 * Service for work with {@link User} object and DB.
 *
 * @author Khshanovskyi Pavlo
 */
public class UserServiceImpl implements IUserService {

    /**
     * {@link Logger} log4j for logs.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

    /**
     * {@link IUserDao} object for work with DB.
     */
    private IUserDao userDao;

    /**
     * Constructor with initialization {@link IUserDao}.
     *
     * @param IUserDao - {@link IUserDao}
     */
    public UserServiceImpl(IUserDao IUserDao) {
        this.userDao = IUserDao;
    }

    /**
     * Method do authorization.
     * Firstly we are trying to get {@link Optional} object by {@link User} email,
     * if this Optional is present - return Optional of User, else - empty Optional.
     *
     * @param email    - {@link User} email
     * @param password - {@link User} password
     * @return {@link Optional#empty()} or {@link Optional#of(Object)}
     */
    @Override
    public Optional<User> loginInAccount(String email, String password) {
        Optional<User> userOpt = userDao.getUserByEmail(email);
        if (userOpt.isPresent() && userOpt.get().getPassword().equals(password)) {
            LOGGER.info("Authorization success");
            return userOpt;
        }
        LOGGER.info("Authorization not performed!");
        return Optional.empty();
    }

    /**
     * Method do checking {@link User} state. If User state == UNBLOCKED - return "true".
     * If User state == BLOCKED - return "false".
     *
     * @param email - {@link User} email
     * @return true / false
     */
    @Override
    public boolean checkUserState(String email) {
        Optional<User> userOpt = userDao.getUserByEmail(email);
        return userOpt.get().getState().equals(String.valueOf(Constant.UNBLOCKED));
    }

    /**
     * Method is checking to contains DB transferable email or not.
     *
     * @param email - User email
     * @return {@link Optional#empty()} or {@link Optional#of(Object)}
     */
    @Override
    public Optional<User> checkEmailExisting(String email) {
        Optional<User> userOpt;
        if (email != null) {
            userOpt = userDao.getUserByEmail(email);
            return userOpt;
        }
        return Optional.empty();
    }

    /**
     * Method is calling {@link IUserDao#create(User)} method for create new {@link User} in DB.
     *
     * @param user {@link User}
     */
    @Override
    public void createNewUser(User user) {
        userDao.create(user);
    }

    /**
     * Method is calling {@link IUserDao#getUserById(int userId)} method for get {@link User} object by User id.
     *
     * @param id - User id
     * @return {@link Optional#empty()} or {@link Optional#of(Object)}
     */
    @Override
    public Optional<User> getUserById(int id) {
        return userDao.getUserById(id);
    }

    /**
     * Method is calling {@link IUserDao#getUserByEmail(String email)} method for get {@link User} object by User email.
     *
     * @param userEmail - User email
     * @return {@link Optional#empty()} or {@link Optional#of(Object)}
     */
    @Override
    public Optional<User> getUserByEmail(String userEmail) {
        return userDao.getUserByEmail(userEmail);
    }

    /**
     * Method for updating {@link User} email. Method is calling
     * {@link IUserDao#updateUserEmail(int userId, String newUserEmail)} for update User email in DB.
     *
     * @param userId   - User id
     * @param newEmail - new User email
     */
    @Override
    public void updateUserEmail(int userId, String newEmail) {
        userDao.updateUserEmail(userId, newEmail);
    }

    /**
     * Method for updating {@link User} phone number. Method is calling
     * {@link IUserDao#updateUserPhoneNumber(int userId, int newPhoneNumber)} for update User phone number in DB.
     *
     * @param userId      - User id
     * @param phoneNumber - new User phone number
     */
    @Override
    public void updateUserPhoneNumber(int userId, int phoneNumber) {
        userDao.updateUserPhoneNumber(userId, phoneNumber);
    }

    /**
     * Method for updating {@link User} surname. Method is calling
     * {@link IUserDao#updateUserSurname(int userId, String newUserSurname)} for update User surname in DB.
     *
     * @param userId     - User id
     * @param newSurname - new User surname
     */
    @Override
    public void updateUserSurname(int userId, String newSurname) {
        userDao.updateUserSurname(userId, newSurname);
    }

    /**
     * Method for updating {@link User} a name. Method is calling
     * {@link IUserDao#updateUserName(int userId, String newUserName)} for update User name in DB.
     *
     * @param userId  - User id
     * @param newName - new User name
     */
    @Override
    public void updateUserName(int userId, String newName) {
        userDao.updateUserName(userId, newName);
    }

    /**
     * Method for updating {@link User} patronymic. Method is calling
     * {@link IUserDao#updateUserPatronymic(int userId, String newUserPatronymic)} for update User patronymic in DB.
     *
     * @param userId        - User id
     * @param newPatronymic - new User patronymic
     */
    @Override
    public void updateUserPatronymic(int userId, String newPatronymic) {
        userDao.updateUserPatronymic(userId, newPatronymic);
    }

    /**
     * Method for updating {@link User} region. Method is calling
     * {@link IUserDao#updateUserRegion(int userId, String newUserRegion)} for update User region in DB.
     *
     * @param userId    - User id
     * @param newRegion - new User region
     */
    @Override
    public void updateUserRegion(int userId, String newRegion) {
        userDao.updateUserRegion(userId, newRegion);
    }

    /**
     * Method for updating {@link User} district. Method is calling
     * {@link IUserDao#updateUserDistrict(int userId, String newUserDistrict)} for update User district in DB.
     *
     * @param userId      - User id
     * @param newDistrict - new User district
     */
    @Override
    public void updateUserDistrict(int userId, String newDistrict) {
        userDao.updateUserDistrict(userId, newDistrict);
    }

    /**
     * Method for updating {@link User} locality. Method is calling
     * {@link IUserDao#updateUserLocality(int userId, String newUserLocality)} for update User locality in DB.
     *
     * @param userId      - User id
     * @param newLocality - new User locality
     */
    @Override
    public void updateUserLocality(int userId, String newLocality) {
        userDao.updateUserLocality(userId, newLocality);
    }

    /**
     * Method for updating {@link User} street. Method is calling
     * {@link IUserDao#updateUserStreet(int userId, String newUserStreet)} for update User street in DB.
     *
     * @param userId    - User id
     * @param newStreet - new User street
     */
    @Override
    public void updateUserStreet(int userId, String newStreet) {
        userDao.updateUserStreet(userId, newStreet);
    }

    /**
     * Method for updating {@link User} house number. Method is calling
     * {@link IUserDao#updateUserHouse(int userId, String newUserHouse)} for update User house number in DB.
     *
     * @param userId   - User id
     * @param newHouse - new User house number
     */
    @Override
    public void updateUserHouse(int userId, String newHouse) {
        userDao.updateUserHouse(userId, newHouse);
    }

    /**
     * Method for updating {@link User} flat number. Method is calling
     * {@link IUserDao#updateUserFlat(int uderId, int newUserFlatNumber)} for update User flat number in DB.
     *
     * @param userId  - User id
     * @param newFlat - new User flat number
     */
    @Override
    public void updateUserFlat(int userId, int newFlat) {
        userDao.updateUserFlat(userId, newFlat);
    }

    /**
     * Method for updating {@link User} postcode. Method is calling
     * {@link IUserDao#updateUserPostcode(int userId, int newUserPostcode)} for update User postcode in DB.
     *
     * @param userId      - User id
     * @param newPostcode - new User postcode
     */
    @Override
    public void updateUserPostcode(int userId, int newPostcode) {
        userDao.updateUserPostcode(userId, newPostcode);
    }

    /**
     * Method for updating {@link User} money quantity. Method is calling
     * {@link IUserDao#updateUserMoney(int userId, int newMoneyQuantity)} for update User money quantity in DB.
     *
     * @param userId   - User id
     * @param newMoney - new User money quantity
     */
    @Override
    public void updateUserMoney(int userId, int newMoney) {
        userDao.updateUserMoney(userId, newMoney);
    }

    /**
     * Method for updating {@link User} password. Method is calling
     * {@link IUserDao#updateUserPassword(int userId, String newUserPassword)} for update User password in DB.
     *
     * @param userId   - User id
     * @param password - new User password
     */
    @Override
    public void updateUserPassword(int userId, String password) {
        userDao.updateUserPassword(userId, password);
    }

    /**
     * Method for updating {@link User} state. Method is calling
     * {@link IUserDao#updateUserState(String userEmail, String newUserState)} for update User state parameter
     * (BLOCK / UNBLOCK) in DB.
     *
     * @param email - User id
     * @param state - change state parameter (BLOCK / UNBLOCK)
     */
    @Override
    public void updateUserState(String email, String state) {
        userDao.updateUserState(email, state);
    }
}
