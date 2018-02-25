package com.nikitin.webproject.database.dao.impl.mysql;

import com.nikitin.webproject.database.dao.UserDAO;
import com.nikitin.webproject.database.entity.Bus;
import com.nikitin.webproject.database.entity.User;
import com.nikitin.webproject.database.util.Status;
import com.nikitin.webproject.database.util.UserType;
import com.nikitin.webproject.database.util.mysql.MySqlConnectionSupplier;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * User Data Access Object implementation for MySQL database.
 */
public class UserMySqlDAOImpl implements UserDAO {
    private static final Logger LOGGER = Logger.getLogger(UserMySqlDAOImpl.class);

    /**
     * Sequence of columns in database table:
     */
    private static final int ID = 1;
    private static final int BUS_ID = 2;
    private static final int LOGIN = 3;
    private static final int PASSWORD = 4;
    private static final int TYPE = 5;
    private static final int STATUS = 6;


    /**
     * Bill Pugh Singleton Implementation.
     */
    private UserMySqlDAOImpl() {
    }

    private static class SingletonHelper {
        private static final UserMySqlDAOImpl INSTANCE = new UserMySqlDAOImpl();
    }

    public static UserMySqlDAOImpl getInstance() {
        return SingletonHelper.INSTANCE;
    }


    /**
     * Method returns user by login and pass from database.
     * @param login String.
     * @param pass String.
     * @return user from database, or null.
     */
    @Override
    public synchronized User getUserByLoginAndPass(String login, String pass) {
        if (login == null || login.isEmpty() || pass == null || pass.isEmpty()) {
            return null;
        }

        String sql = "SELECT * FROM USERS WHERE LOGIN = ? AND PASSWORD = ?";
        User user = null;

        try (Connection connection = MySqlConnectionSupplier.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, login);
            ps.setString(2, pass);

            try (ResultSet resultSet = ps.executeQuery()) {

                while (resultSet.next()) {
                    user = new User();

                    user.setId(resultSet.getInt(ID));
                    user.setBusId(resultSet.getInt(BUS_ID));
                    user.setLogin(resultSet.getString(LOGIN));
                    user.setPassword(resultSet.getString(PASSWORD));
                    user.setType(UserType.valueOf(resultSet.getString(TYPE)));
                    user.setStatus(Status.valueOf(resultSet.getString(STATUS)));
                }
            }

        } catch (SQLException e) {
            LOGGER.error("ERROR: trying to get user with login: {" + login + "} and pass: {" + pass + "} from DB", e);
        }

        return user;
    }


    /**
     * Method returns all drivers from database.
     * @return list drivers, List<User>.
     */
    @Override
    public synchronized List<User> getAllDrivers() {
        String sql = "SELECT * FROM USERS WHERE TYPE = \'DRIVER\'";
        return getUsersByQuery(sql);
    }


    /**
     * Method returns list of all free (not busy) drivers from database.
     * @return list drivers, List<User>.
     */
    @Override
    public synchronized List<User> getFreeDrivers() {
        String sql = "SELECT * FROM USERS WHERE BUS_ID = 0 AND TYPE = \'DRIVER\'";
        return getUsersByQuery(sql);
    }


    /**
     * Method returns driver related to bus from database.
     * @param bus
     * @return driver.
     */
    @Override
    public synchronized User getDriverByBusId(Bus bus) {
        if (bus == null) {
            return null;
        }

        String sql = "SELECT * FROM USERS WHERE BUS_ID = ?";
        User user = null;

        try (Connection connection = MySqlConnectionSupplier.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, bus.getId());

            try (ResultSet resultSet = ps.executeQuery()) {

                while (resultSet.next()) {
                    user = new User();

                    user.setId(resultSet.getInt(ID));
                    user.setBusId(resultSet.getInt(BUS_ID));
                    user.setLogin(resultSet.getString(LOGIN));
                    user.setPassword(resultSet.getString(PASSWORD));
                    user.setType(UserType.valueOf(resultSet.getString(TYPE)));
                    user.setStatus(Status.valueOf(resultSet.getString(STATUS)));
                }
            }

        } catch (SQLException e) {
            LOGGER.error("ERROR: trying to get user from DB by bus id: {" + bus.getId() + "}", e);
        }

        return user;
    }


    /**
     * Method returns user by ID from database.
     * @param id int.
     * @return user.
     */
    @Override
    public User getUserById(int id) {
        if (id == 0) {
            return null;
        }

        String sql = "SELECT * FROM USERS WHERE ID = ?";
        User user = null;

        try (Connection connection = MySqlConnectionSupplier.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, id);

            try (ResultSet resultSet = ps.executeQuery()) {

                while (resultSet.next()) {
                    user = new User();

                    user.setId(resultSet.getInt(ID));
                    user.setBusId(resultSet.getInt(BUS_ID));
                    user.setLogin(resultSet.getString(LOGIN));
                    user.setPassword(resultSet.getString(PASSWORD));
                    user.setType(UserType.valueOf(resultSet.getString(TYPE)));
                    user.setStatus(Status.valueOf(resultSet.getString(STATUS)));
                }
            }

        } catch (SQLException e) {
            LOGGER.error("ERROR: trying to get user from DB by id: {" + id + "}", e);
        }

        return user;
    }


    /**
     * Method update driver relation to the bus in database.
     * @param userId int.
     * @param busId int.
     * @return true, if operation successfully done.
     */
    @Override
    public synchronized boolean updateDriverBusId(int userId, int busId) {
        if (userId == 0) {
            return false;
        }

        String sql = "UPDATE USERS SET BUS_ID = ? WHERE ID = ?";
        boolean rowUpdated = false;

        try (Connection connection = MySqlConnectionSupplier.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, busId);
            ps.setInt(2, userId);

            rowUpdated = ps.executeUpdate() > 0;

        } catch (SQLException e) {
            LOGGER.error("ERROR: trying to appoint user: {" + userId + "} on the bus with bus_id: {" + busId + "}", e);
        }

        return rowUpdated;
    }


    /**
     * Method update driver status in database.
     * @param user
     * @param status
     * @return true, if operation successfully done.
     */
    @Override
    public synchronized boolean updateDriverStatus(User user, Status status) {
        if (user == null || status == null) {
            return false;
        }

        String sql = "UPDATE USERS SET STATUS = ? WHERE ID = ?";
        boolean rowUpdated = false;

        try (Connection connection = MySqlConnectionSupplier.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, status.toString());
            ps.setInt(2, user.getId());

            rowUpdated = ps.executeUpdate() > 0;

        } catch (SQLException e) {
            LOGGER.error("ERROR: trying to set status {" + status + "} to the user {" + user + "}", e);
        }

        return rowUpdated;
    }


    /**
     * Method to execute query and return list of users from database.
     * @param sql String.
     * @return list users, List<User>.
     */
    private synchronized List<User> getUsersByQuery(String sql) {
        List<User> list = null;

        try (Connection connection = MySqlConnectionSupplier.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            list = getUsersFromResultSet(resultSet);

        } catch (SQLException e) {
            LOGGER.error("ERROR: trying to get users from DB by query: {" + sql + "}", e);
        }

        return list;
    }


    /**
     * Method to get list of users from result set.
     * @param resultSet
     * @return list users, List<User>.
     */
    private synchronized List<User> getUsersFromResultSet(ResultSet resultSet) {
        List<User> list = new ArrayList<>();

        try {
            User user;
            while (resultSet.next()) {
                user = new User();

                user.setId(resultSet.getInt(ID));
                user.setBusId(resultSet.getInt(BUS_ID));
                user.setLogin(resultSet.getString(LOGIN));
                user.setPassword(resultSet.getString(PASSWORD));
                user.setType(UserType.valueOf(resultSet.getString(TYPE)));
                user.setStatus(Status.valueOf(resultSet.getString(STATUS)));

                list.add(user);
            }
        } catch (SQLException e) {
            LOGGER.error("ERROR: trying to take list of users from ResultSet", e);
        }

        if (list.isEmpty()) {
            list = null;
        }

        return list;
    }
}