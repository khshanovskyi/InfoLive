package ua.nure.khshanovskyi.infoLife.dao.user.impl;

import ua.nure.khshanovskyi.infoLife.dao.ConnectionManager;
import ua.nure.khshanovskyi.infoLife.dao.ConstantMySqlRequest;
import ua.nure.khshanovskyi.infoLife.dao.user.IUserDao;
import ua.nure.khshanovskyi.infoLife.entity.user.User;
import ua.nure.khshanovskyi.infoLife.entity.user.builder.UserBuilder;
import ua.nure.khshanovskyi.infoLife.exception.DaoException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

/**
 * Implementation of {@link IUserDao} interface for work with "user" table in MySQL DB.
 *
 * @author Khshanovskyi Pavlo
 */
public class UserDaoMySql extends ConnectionManager implements IUserDao {

    /**
     * {@link Logger} log4j for logs.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(UserDaoMySql.class);

    /**
     * {@link Connection}
     */
    private Connection connection = null;
    /**
     * {@link PreparedStatement}
     */
    private PreparedStatement statement = null;

    /**
     * Constructor for initialization this class and initialization {@link DataSource} for work with DB.
     *
     * @param dataSource {@link DataSource}
     */
    public UserDaoMySql(DataSource dataSource) {
        super(dataSource);
    }

    /**
     * Method return {@link Optional#of(Object)} {@link User} if DB contain this "userId" or can return
     * {@link Optional#empty()} if DB does not contain this "userId"
     *
     * @param userId - id of {@link User}
     * @return {@link Optional#of(Object)} or {@link Optional#empty()}
     */
    @Override
    public Optional<User> getUserById(int userId) {
        ResultSet resultSet = null;
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(ConstantMySqlRequest.GET_USER_BY_ID)){
            statement.setInt(1, userId);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(extractUser(resultSet));
            }
        } catch (SQLException e) {
            LOGGER.error("Cannot get user from DB, user id = " + userId);
            e.printStackTrace();
        } finally {
            close(resultSet);
        }
        return Optional.empty();
    }

    /**
     * Method return {@link Optional#of(Object)} {@link User} if DB contain this "email" or can return
     * {@link Optional#empty()} if DB does not contain this "email"
     *
     * @param email - {@link User} email
     * @return {@link Optional#of(Object)} or {@link Optional#empty()}
     */
    @Override
    public Optional<User> getUserByEmail(String email) {
        ResultSet resultSet = null;
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(ConstantMySqlRequest.GET_USER_BY_EMAIL)){
            statement.setString(1, email);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(extractUser(resultSet));
            }
        } catch (SQLException e) {
            LOGGER.error("Cannot get user from DB, user email = " + email);
        } finally {
            close(resultSet);
        }
        return Optional.empty();
    }

    /**
     * Method INSERT into DB (in "user" table) new {@link User} object which wos generated in java layer.
     *
     * @param user - {@link User} object
     */
    @Override
    public void create(User user) {
        try {
            connection = getConnection();
            statement = connection.prepareStatement(ConstantMySqlRequest.CREATE_NEW_USER);
            connection.setAutoCommit(false);
            statement.setString(1, user.getEmail());
            statement.setString(2, user.getSurname());
            statement.setString(3, user.getName());
            statement.setString(4, user.getPatronymic());
            statement.setInt(5, user.getPhoneNumber());
            statement.setString(6, user.getRegion());
            statement.setString(7, user.getDistrict());
            statement.setString(8, user.getLocality());
            statement.setString(9, user.getStreet());
            statement.setString(10, user.getHouse());
            statement.setInt(11, user.getFlat());
            statement.setInt(12, user.getPostcode());
            statement.setInt(13, user.getMoney());
            statement.setString(14, user.getPassword());
            statement.setString(15, user.getRole());
            statement.setString(16, user.getState());
            statement.execute();
            connection.commit();
            LOGGER.trace("Creating a new user is success.");
        } catch (SQLException e) {
            rollback(connection);
            LOGGER.error("Problem with creating user " + user);
            throw new DaoException("problem with creating user ", e);
        } finally {
            close(connection);
            close(statement);
        }
    }

    /**
     * Update {@link User} "email" field by "userId".
     *
     * @param userId   - id of {@link User}
     * @param newEmail - new {@link User} email
     */
    @Override
    public void updateUserEmail(int userId, String newEmail) {
        try {
            connection = getConnection();
            connection.setAutoCommit(false);
            statement = connection.prepareStatement(ConstantMySqlRequest.UPDATE_USER_EMAIL_BY_ID);
            statement.setString(1, newEmail);
            statement.setInt(2, userId);
            statement.execute();
            connection.commit();
            LOGGER.trace("Update user email success.");
        } catch (SQLException e) {
            rollback(connection);
            LOGGER.error("Problem with updating user email: " + userId + " new email is " + newEmail);
            throw new DaoException("Cannot update user", e);
        }finally {
            close(connection);
            close(statement);
        }
    }

    /**
     * Update {@link User} "surname" field by "userId".
     *
     * @param userId     - id of {@link User}
     * @param newSurname - new {@link User} surname
     */
    @Override
    public void updateUserSurname(int userId, String newSurname) {
        try {
            connection = getConnection();
            connection.setAutoCommit(false);
            statement = connection.prepareStatement(ConstantMySqlRequest.UPDATE_USER_SURNAME_BY_ID);
            statement.setString(1, String.valueOf(newSurname));
            statement.setInt(2, userId);
            statement.execute();
            connection.commit();
            LOGGER.trace("Update user surname success.");
        } catch (SQLException e) {
            rollback(connection);
            LOGGER.error("Problem with updating user surname: " + userId + " new surname is " + newSurname);
            throw new DaoException("Cannot update user", e);
        }
        finally {
            close(connection);
            close(statement);
        }
    }

    /**
     * Update {@link User} "name" field by "userId".
     *
     * @param userId  - id of {@link User}
     * @param newName - new {@link User} surname
     */
    @Override
    public void updateUserName(int userId, String newName) {
        try {
            connection = getConnection();
            connection.setAutoCommit(false);
            statement = connection.prepareStatement(ConstantMySqlRequest.UPDATE_USER_NAME_BY_ID);
            statement.setString(1, String.valueOf(newName));
            statement.setInt(2, userId);
            statement.execute();
            connection.commit();
            LOGGER.trace("Update user name success.");
        } catch (SQLException e) {
            rollback(connection);
            LOGGER.error("Problem with updating user name: " + userId + " new name is " + newName);
            throw new DaoException("Cannot update user", e);
        }
        finally {
            close(connection);
            close(statement);
        }
    }

    /**
     * Update {@link User} "patronymic" field by "userId".
     *
     * @param userId        - id of {@link User}
     * @param newPatronymic - new {@link User} patronymic
     */
    @Override
    public void updateUserPatronymic(int userId, String newPatronymic) {
        try {
            connection = getConnection();
            connection.setAutoCommit(false);
            statement = connection.prepareStatement(ConstantMySqlRequest.UPDATE_USER_PATRONYMIC_BY_ID);
            statement.setString(1, String.valueOf(newPatronymic));
            statement.setInt(2, userId);
            statement.execute();
            connection.commit();
            LOGGER.trace("Update user patronymic success.");
        } catch (SQLException e) {
            rollback(connection);
            LOGGER.error("Problem with updating user patronymic: " + userId + " new patronymic is " + newPatronymic);
            throw new DaoException("Cannot update user", e);
        }
        finally {
            close(connection);
            close(statement);
        }
    }

    /**
     * Update {@link User} "phoneNumber" field by "userId".
     *
     * @param userId         - id of {@link User}
     * @param newPhoneNumber - new {@link User} phoneNumber
     */
    @Override
    public void updateUserPhoneNumber(int userId, int newPhoneNumber) {
        try {
            connection = getConnection();
            connection.setAutoCommit(false);
            statement = connection.prepareStatement(ConstantMySqlRequest.UPDATE_USER_PHONE_NUMBER_BY_ID);
            statement.setString(1, String.valueOf(newPhoneNumber));
            statement.setInt(2, userId);
            statement.execute();
            connection.commit();
            LOGGER.trace("Update user phoneNumber success.");
        } catch (SQLException e) {
            rollback(connection);
            LOGGER.error("Problem with updating user phoneNumber: " + userId + " new phoneNumber is " + newPhoneNumber);
            throw new DaoException("Cannot update user", e);
        }
        finally {
            close(connection);
            close(statement);
        }
    }

    /**
     * Update {@link User} "region" field by "userId".
     *
     * @param userId    - id of {@link User}
     * @param newRegion - new {@link User} region
     */
    @Override
    public void updateUserRegion(int userId, String newRegion) {
        try {
            connection = getConnection();
            connection.setAutoCommit(false);
            statement = connection.prepareStatement(ConstantMySqlRequest.UPDATE_USER_REGION_BY_ID);
            statement.setString(1, String.valueOf(newRegion));
            statement.setInt(2, userId);
            statement.execute();
            connection.commit();
            LOGGER.trace("Update user region success.");
        } catch (SQLException e) {
            rollback(connection);
            LOGGER.error("Problem with updating user region: " + userId + " new region is " + newRegion);
            throw new DaoException("Cannot update user", e);
        }
        finally {
            close(connection);
            close(statement);
        }
    }

    /**
     * Update {@link User} "region" field by "userId".
     *
     * @param userId      - id of {@link User}
     * @param newDistrict - new {@link User} region
     */
    @Override
    public void updateUserDistrict(int userId, String newDistrict) {
        try {
            connection = getConnection();
            connection.setAutoCommit(false);
            statement = connection.prepareStatement(ConstantMySqlRequest.UPDATE_USER_DISTRICT_BY_ID);
            statement.setString(1, String.valueOf(newDistrict));
            statement.setInt(2, userId);
            statement.execute();
            connection.commit();
            LOGGER.trace("Update user district success.");
        } catch (SQLException e) {
            rollback(connection);
            LOGGER.error("Problem with updating user district: " + userId + " new district is " + newDistrict);
            throw new DaoException("Cannot update user", e);
        }
        finally {
            close(connection);
            close(statement);
        }
    }

    /**
     * Update {@link User} "locality" field by "userId".
     *
     * @param userId      - id of {@link User}
     * @param newLocality - new {@link User} locality
     */
    @Override
    public void updateUserLocality(int userId, String newLocality) {
        try {
            connection = getConnection();
            connection.setAutoCommit(false);
            statement = connection.prepareStatement(ConstantMySqlRequest.UPDATE_USER_LOCALITY_BY_ID);
            statement.setString(1, String.valueOf(newLocality));
            statement.setInt(2, userId);
            statement.execute();
            connection.commit();
            LOGGER.trace("Update user locality success.");
        } catch (SQLException e) {
            rollback(connection);
            LOGGER.error("Problem with updating user locality: " + userId + " new locality is " + newLocality);
            throw new DaoException("Cannot update user", e);
        }
        finally {
            close(connection);
            close(statement);
        }
    }

    /**
     * Update {@link User} "street" field by "userId".
     *
     * @param userId    - id of {@link User}
     * @param newStreet - new {@link User} street
     */
    @Override
    public void updateUserStreet(int userId, String newStreet) {
        try {
            connection = getConnection();
            connection.setAutoCommit(false);
            statement = connection.prepareStatement(ConstantMySqlRequest.UPDATE_USER_STREET_BY_ID);
            statement.setString(1, String.valueOf(newStreet));
            statement.setInt(2, userId);
            statement.execute();
            connection.commit();
            LOGGER.trace("Update user street success.");
        } catch (SQLException e) {
            rollback(connection);
            LOGGER.error("Problem with updating user street: " + userId + " new street is " + newStreet);
            throw new DaoException("Cannot update user", e);
        }
        finally {
            close(connection);
            close(statement);
        }
    }

    /**
     * Update {@link User} "house" field by "userId".
     *
     * @param userId   - id of {@link User}
     * @param newHouse - new {@link User} house
     */
    @Override
    public void updateUserHouse(int userId, String newHouse) {
        try {
            connection = getConnection();
            connection.setAutoCommit(false);
            statement = connection.prepareStatement(ConstantMySqlRequest.UPDATE_USER_HOUSE_BY_ID);
            statement.setString(1, newHouse);
            statement.setInt(2, userId);
            statement.execute();
            connection.commit();
            LOGGER.trace("Update user house success.");
        } catch (SQLException e) {
            rollback(connection);
            LOGGER.error("Problem with updating user house: " + userId + " new house is " + newHouse);
            throw new DaoException("Cannot update user", e);
        }
        finally {
            close(connection);
            close(statement);
        }
    }

    /**
     * Update {@link User} "flat" field by "userId".
     *
     * @param userId  - id of {@link User}
     * @param newFlat - new {@link User} flat
     */
    @Override
    public void updateUserFlat(int userId, int newFlat) {
        try {
            connection = getConnection();
            connection.setAutoCommit(false);
            statement = connection.prepareStatement(ConstantMySqlRequest.UPDATE_USER_FLAT_BY_ID);
            statement.setInt(1, newFlat);
            statement.setInt(2, userId);
            statement.execute();
            connection.commit();
            LOGGER.trace("Update user flat success.");
        } catch (SQLException e) {
            rollback(connection);
            LOGGER.error("Problem with updating user flat: " + userId + " new flat is " + newFlat);
            throw new DaoException("Cannot update user", e);
        }
        finally {
            close(connection);
            close(statement);
        }
    }

    /**
     * Update {@link User} "postcode" field by "userId".
     *
     * @param userId      - id of {@link User}
     * @param newPostcode - new {@link User} postcode
     */
    @Override
    public void updateUserPostcode(int userId, int newPostcode) {
        try {
            connection = getConnection();
            connection.setAutoCommit(false);
            statement = connection.prepareStatement(ConstantMySqlRequest.UPDATE_USER_POSTCODE_BY_ID);
            statement.setInt(1, newPostcode);
            statement.setInt(2, userId);
            statement.execute();
            connection.commit();
            LOGGER.trace("Update user postcode success.");
        } catch (SQLException e) {
            rollback(connection);
            LOGGER.error("Problem with updating user postcode: " + userId + " new postcode is " + newPostcode);
            throw new DaoException("Cannot update user", e);
        }
        finally {
            close(connection);
            close(statement);
        }
    }

    /**
     * Update {@link User} "money" field by "userId".
     *
     * @param userId   - id of {@link User}
     * @param newMoney - new {@link User} money
     */
    @Override
    public void updateUserMoney(int userId, int newMoney) {
        try {
            connection = getConnection();
            connection.setAutoCommit(false);
            statement = connection.prepareStatement(ConstantMySqlRequest.UPDATE_USER_MONEY_BY_ID);
            statement.setInt(1, newMoney);
            statement.setInt(2, userId);
            statement.execute();
            connection.commit();
            LOGGER.info("Update user money success.");
        } catch (SQLException e) {
            rollback(connection);
            LOGGER.error("Problem with updating user money: " + userId + " new money is " + newMoney);
            throw new DaoException("Cannot update user", e);
        }
        finally {
            close(connection);
            close(statement);
        }
    }

    /**
     * Update {@link User} "password" field by "userId".
     *
     * @param userId   - id of {@link User}
     * @param password - new {@link User} password
     */
    @Override
    public void updateUserPassword(int userId, String password) {
        try {
            connection = getConnection();
            connection.setAutoCommit(false);
            statement = connection.prepareStatement(ConstantMySqlRequest.UPDATE_USER_PASSWORD_BY_ID);
            statement.setString(1, password);
            statement.setInt(2, userId);
            statement.execute();
            connection.commit();
            LOGGER.trace("Update user password success.");
        } catch (SQLException e) {
            rollback(connection);
            LOGGER.error("Problem with updating user password: " + userId + " new password is " + password);
            throw new DaoException("Cannot update user", e);
        }
        finally {
            close(connection);
            close(statement);
        }
    }

    /**
     * Update {@link User} "role" field by "userId".
     *
     * @param userId - id of {@link User}
     * @param role   - new {@link User} role
     */
    @Override
    public void updateUserRole(int userId, String role) {
        try {
            connection = getConnection();
            connection.setAutoCommit(false);
            statement = connection.prepareStatement(ConstantMySqlRequest.UPDATE_USER_ROLE_BY_ID);
            statement.setString(1, role);
            statement.setInt(2, userId);
            statement.execute();
            connection.commit();
            LOGGER.trace("Update user role success.");
        } catch (SQLException e) {
            rollback(connection);
            LOGGER.error("Problem with updating user role: " + userId + " new role is " + role);
            throw new DaoException("Cannot update user", e);
        }
        finally {
            close(connection);
            close(statement);
        }
    }

    /**
     * Update {@link User} "state" field by "userId".
     *
     * @param email - {@link User} email
     * @param state - new {@link User} state
     */
    @Override
    public void updateUserState(String email, String state) {
        try {
            connection = getConnection();
            connection.setAutoCommit(false);
            statement = connection.prepareStatement(ConstantMySqlRequest.UPDATE_USER_STATE_BY_ID);
            statement.setString(1, state);
            statement.setString(2, email);
            statement.execute();
            connection.commit();
            LOGGER.trace("Update user state success.");
        } catch (SQLException e) {
            rollback(connection);
            LOGGER.error("Problem with updating user state: " + email + " new state is " + state);
            throw new DaoException("Cannot update user", e);
        }
        finally {
            close(connection);
            close(statement);
        }
    }

    /**
     * Method return {@link Optional#of(Object)}{@link User} object where "userId" more than other "userIds" or if
     * DB is empty then return {@link Optional#empty()}.
     *
     * @return {@link Optional#of(Object)} or {@link Optional#empty()}
     */
    @Override
    public Optional<User> getLastCreatedUser() {
        ResultSet resultSet = null;
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(ConstantMySqlRequest.GET_LAST_USER_OBJECT)) {
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(extractUser(resultSet));
            }
        } catch (SQLException e) {
            LOGGER.error("Could not get last media object" + e);
        } finally {
            close(resultSet);
        }
        return Optional.empty();
    }

    /**
     * Delete {@link User} object from DB by "user email".
     *
     * @param userEmail - {@link User} email
     */
    @Override
    public void deleteUserByUserEmail(String userEmail) {
        try {
            connection = getConnection();
            connection.setAutoCommit(false);
            statement = connection.prepareStatement(ConstantMySqlRequest.DELETE_USER_BY_EMAIL);
            statement.setString(1, userEmail);
            statement.execute();
            connection.commit();
            LOGGER.trace("DELETE user " + userEmail + " is success.");
        } catch (SQLException e) {
            rollback(connection);
            LOGGER.error("Problem with DELETING user: " + userEmail);
            throw new DaoException("Cannot DELETE user" + userEmail, e);
        } finally {
            close(connection);
            close(statement);
        }
    }

    /**
     * Method extract Media parameters from DB and generated new {@link User} object for work with this object in
     * java and view layers.
     * Important for project.
     *
     * @param resultSet - {@link ResultSet}
     * @return generated {@link User} object. This object is generation by {@link UserBuilder}
     * @throws SQLException - {@link SQLException}
     */
    private User extractUser(ResultSet resultSet) throws SQLException {
        UserBuilder userBuilder = new UserBuilder();

        userBuilder.buildUserId(resultSet.getInt("user_id"));
        userBuilder.buildUserEmail(resultSet.getString("email"));
        userBuilder.buildUserSurname(resultSet.getString("surname"));
        userBuilder.buildUserName(resultSet.getString("name"));
        userBuilder.buildUserPatronymic(resultSet.getString("patronymic"));
        userBuilder.buildUserPhoneNumber(resultSet.getInt("phone_number"));
        userBuilder.buildUserRegion(resultSet.getString("region"));
        userBuilder.buildUserDistrict(resultSet.getString("district"));
        userBuilder.buildUserLocality(resultSet.getString("locality"));
        userBuilder.buildUserStreet(resultSet.getString("street"));
        userBuilder.buildUserHouse(resultSet.getString("house"));
        userBuilder.buildUserFlat(resultSet.getInt("flat"));
        userBuilder.buildUserPostcode(resultSet.getInt("postcode"));
        userBuilder.buildUserMoney(resultSet.getInt("money"));
        userBuilder.buildUserPassword(resultSet.getString("password"));
        userBuilder.buildUserRole(resultSet.getString("role"));
        userBuilder.buildUserState(resultSet.getString("state"));

        return userBuilder.build();
    }
}