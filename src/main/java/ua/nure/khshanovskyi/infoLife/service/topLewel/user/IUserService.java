package ua.nure.khshanovskyi.infoLife.service.topLewel.user;

import ua.nure.khshanovskyi.infoLife.entity.user.User;

import java.util.Optional;

/**
 * @author Khshanovskyi Pavlo
 */
public interface IUserService {

    /**
     * Method do authorization.
     *
     * @param email    - {@link User} email
     * @param password - {@link User} password
     * @return {@link Optional#empty()} or {@link Optional#of(Object)}
     */
    Optional<User> loginInAccount(String email, String password);

    /**
     * Check {@link User} state if User state is  BLOCKED - return true, else- false.
     *
     * @param email - {@link User} email
     * @return true / false
     */
    boolean checkUserState(String email);

    /**
     * Check email existing or not.
     *
     * @param email - User email
     * @return {@link Optional#empty()} or {@link Optional#of(Object)}
     */
    Optional<User> checkEmailExisting(String email);

    /**
     * Create new {@link User}.
     *
     * @param user {@link User}
     */
    void createNewUser(User user);

    /**
     * Get {@link User} by User id.
     *
     * @param id - User id
     * @return {@link Optional#empty()} or {@link Optional#of(Object)}
     */
    Optional<User> getUserById(int id);

    /**
     * Get {@link User} by User email.
     *
     * @param userEmail - User email
     * @return {@link Optional#empty()} or {@link Optional#of(Object)}
     */
    Optional<User> getUserByEmail(String userEmail);

    /**
     * Update {@link User} email by User id.
     *
     * @param userId   - User id
     * @param newEmail - new User email
     */
    void updateUserEmail(int userId, String newEmail);

    /**
     * Update {@link User} phone number by User id.
     *
     * @param userId      - User id
     * @param phoneNumber - new User phone number
     */
    void updateUserPhoneNumber(int userId, int phoneNumber);

    /**
     * Update {@link User} surname by User id.
     *
     * @param userId     - User id
     * @param newSurname - new User surname
     */
    void updateUserSurname(int userId, String newSurname);

    /**
     * Update {@link User} name by User id.
     *
     * @param userId  - User id
     * @param newName - new User name
     */
    void updateUserName(int userId, String newName);

    /**
     * Update {@link User} patronymic by User id.
     *
     * @param userId        - User id
     * @param newPatronymic - new User patronymic
     */
    void updateUserPatronymic(int userId, String newPatronymic);

    /**
     * Update {@link User} region by User id.
     *
     * @param userId    - User id
     * @param newRegion - new User region
     */
    void updateUserRegion(int userId, String newRegion);

    /**
     * Update {@link User} district by User id.
     *
     * @param userId      - User id
     * @param newDistrict - new User district
     */
    void updateUserDistrict(int userId, String newDistrict);

    /**
     * Update {@link User} locality by User id.
     *
     * @param userId      - User id
     * @param newLocality - new User locality
     */
    void updateUserLocality(int userId, String newLocality);

    /**
     * Update {@link User} street by User id.
     *
     * @param userId    - User id
     * @param newStreet - new User street
     */
    void updateUserStreet(int userId, String newStreet);

    /**
     * Update {@link User} house number by User id.
     *
     * @param userId   - User id
     * @param newHouse - new User house number
     */
    void updateUserHouse(int userId, String newHouse);

    /**
     * Update {@link User} flat number by User id.
     *
     * @param userId  - User id
     * @param newFlat - new User flat number
     */
    void updateUserFlat(int userId, int newFlat);

    /**
     * Update {@link User} postcode by User id.
     *
     * @param userId      - User id
     * @param newPostcode - new User postcode
     */
    void updateUserPostcode(int userId, int newPostcode);

    /**
     * Update {@link User} money quantity by User id.
     *
     * @param userId   - User id
     * @param newMoney - new User money quantity
     */
    void updateUserMoney(int userId, int newMoney);

    /**
     * Update {@link User} password by User id.
     *
     * @param userId   - User id
     * @param password - new User password
     */
    void updateUserPassword(int userId, String password);

    /**
     * Update {@link User} state by User id.
     *
     * @param email - User id
     * @param state - change state parameter (BLOCK / UNBLOCK)
     */
    void updateUserState(String email, String state);
}
