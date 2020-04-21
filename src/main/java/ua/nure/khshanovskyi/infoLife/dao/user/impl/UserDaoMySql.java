package ua.nure.khshanovskyi.infoLife.dao.user.impl;

import org.apache.log4j.Logger;
import ua.nure.khshanovskyi.infoLife.dao.ConnectionManager;
import ua.nure.khshanovskyi.infoLife.dao.ConstantMySqlRequest;
import ua.nure.khshanovskyi.infoLife.dao.user.IUserDao;
import ua.nure.khshanovskyi.infoLife.entity.user.User;
import ua.nure.khshanovskyi.infoLife.entity.user.builder.UserBuilder;
import ua.nure.khshanovskyi.infoLife.exception.DaoException;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserDaoMySql extends ConnectionManager implements IUserDao {

    private static final Logger LOGGER = Logger.getLogger(UserDaoMySql.class);

    private Connection connection = null;
    private PreparedStatement statement = null;

    public UserDaoMySql(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    public List<User> allUsers(){
        List<User> result = new ArrayList<>();
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(ConstantMySqlRequest.SELECT_ALL_USERS);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                result.add(extractUser(resultSet));
            }
        } catch (SQLException e) {
            LOGGER.error("cannot get users from DB");
            throw new DaoException("cannot get users from DB", e);
        }
        return result;
    }

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
