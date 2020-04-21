package ua.nure.khshanovskyi.infoLife.dao.user;

import ua.nure.khshanovskyi.infoLife.entity.user.User;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface IUserDao {
    List<User> allUsers() throws SQLException;

    Optional<User> getUserById(int userId);

    Optional<User> getUserByEmail(String email);

    void create(User user);

    void updateUserEmail (int userId, String newEmail);

    void updateUserSurname (int userId, String newSurname);

    void updateUserName (int userId, String newName);

    void updateUserPatronymic (int userId, String newPatronymic);

    void updateUserPhoneNumber (int userId, int newPhoneNumber);

    void updateUserRegion (int userId, String newRegion);

    void updateUserDistrict (int userId, String newDistrict);

    void updateUserLocality (int userId, String newLocality);

    void updateUserStreet (int userId, String newStreet);

    void updateUserHouse (int userId, String newHouse);

    void updateUserFlat (int userId, int newFlat);

    void updateUserPostcode (int userId, int newPostcode);

    void updateUserMoney (int userId, int newMoney);

    void updateUserPassword (int userId, String password);

    void updateUserRole (int userId, String role);

    void updateUserState (String email, String state);
}
