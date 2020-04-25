package ua.nure.khshanovskyi.infoLife.dao.user;

import ua.nure.khshanovskyi.infoLife.entity.user.User;

import java.util.Optional;

/**
 * DAO interface for work with "user" table with different databases.
 *
 * @author Khshanovskyi Pavlo
 */
public interface IUserDao {

    /**
     * Method return {@link Optional#of(Object)} {@link User} if DB contain this "userId" or can return
     * {@link Optional#empty()} if DB does not contain this "userId"
     *
     * @param userId - id of {@link User}
     * @return {@link Optional#of(Object)} or {@link Optional#empty()}
     */
    Optional<User> getUserById(int userId);

    /**
     * Method return {@link Optional#of(Object)} {@link User} if DB contain this "email" or can return
     * {@link Optional#empty()} if DB does not contain this "email"
     *
     * @param email - {@link User} email
     * @return {@link Optional#of(Object)} or {@link Optional#empty()}
     */
    Optional<User> getUserByEmail(String email);

    /**
     * Method INSERT into DB (in "user" table) new {@link User} object which wos generated in java layer.
     *
     * @param user - {@link User} object
     */
    void create(User user);

    /**
     * Update {@link User} "email" field by "userId".
     *
     * @param userId   - id of {@link User}
     * @param newEmail - new {@link User} email
     */
    void updateUserEmail(int userId, String newEmail);

    /**
     * Update {@link User} "surname" field by "userId".
     *
     * @param userId     - id of {@link User}
     * @param newSurname - new {@link User} surname
     */
    void updateUserSurname(int userId, String newSurname);

    /**
     * Update {@link User} "name" field by "userId".
     *
     * @param userId  - id of {@link User}
     * @param newName - new {@link User} surname
     */
    void updateUserName(int userId, String newName);

    /**
     * Update {@link User} "patronymic" field by "userId".
     *
     * @param userId        - id of {@link User}
     * @param newPatronymic - new {@link User} patronymic
     */
    void updateUserPatronymic(int userId, String newPatronymic);

    /**
     * Update {@link User} "phoneNumber" field by "userId".
     *
     * @param userId         - id of {@link User}
     * @param newPhoneNumber - new {@link User} phoneNumber
     */
    void updateUserPhoneNumber(int userId, int newPhoneNumber);

    /**
     * Update {@link User} "region" field by "userId".
     *
     * @param userId    - id of {@link User}
     * @param newRegion - new {@link User} region
     */
    void updateUserRegion(int userId, String newRegion);

    /**
     * Update {@link User} "region" field by "userId".
     *
     * @param userId      - id of {@link User}
     * @param newDistrict - new {@link User} region
     */
    void updateUserDistrict(int userId, String newDistrict);

    /**
     * Update {@link User} "locality" field by "userId".
     *
     * @param userId      - id of {@link User}
     * @param newLocality - new {@link User} locality
     */
    void updateUserLocality(int userId, String newLocality);

    /**
     * Update {@link User} "street" field by "userId".
     *
     * @param userId    - id of {@link User}
     * @param newStreet - new {@link User} street
     */
    void updateUserStreet(int userId, String newStreet);

    /**
     * Update {@link User} "house" field by "userId".
     *
     * @param userId   - id of {@link User}
     * @param newHouse - new {@link User} house
     */
    void updateUserHouse(int userId, String newHouse);

    /**
     * Update {@link User} "flat" field by "userId".
     *
     * @param userId  - id of {@link User}
     * @param newFlat - new {@link User} flat
     */
    void updateUserFlat(int userId, int newFlat);

    /**
     * Update {@link User} "postcode" field by "userId".
     *
     * @param userId      - id of {@link User}
     * @param newPostcode - new {@link User} postcode
     */
    void updateUserPostcode(int userId, int newPostcode);

    /**
     * Update {@link User} "money" field by "userId".
     *
     * @param userId   - id of {@link User}
     * @param newMoney - new {@link User} money
     */
    void updateUserMoney(int userId, int newMoney);

    /**
     * Update {@link User} "password" field by "userId".
     *
     * @param userId   - id of {@link User}
     * @param password - new {@link User} password
     */
    void updateUserPassword(int userId, String password);

    /**
     * Update {@link User} "role" field by "userId".
     *
     * @param userId - id of {@link User}
     * @param role   - new {@link User} role
     */
    void updateUserRole(int userId, String role);

    /**
     * Update {@link User} "state" field by "userId".
     *
     * @param email - {@link User} email
     * @param state - new {@link User} state
     */
    void updateUserState(String email, String state);

    /**
     * For testing!
     * Method return {@link Optional#of(Object)}{@link User} object where "userId" more than other "userIds" or if
     * DB is empty then return {@link Optional#empty()}.
     *
     * @return {@link Optional#of(Object)} or {@link Optional#empty()}
     */
    Optional<User> getLastCreatedUser();

    /**
     * Delete {@link User} object from DB by "user email".
     *
     * @param userEmail - {@link User} email
     */
    void deleteUserByUserEmail(String userEmail);
}
