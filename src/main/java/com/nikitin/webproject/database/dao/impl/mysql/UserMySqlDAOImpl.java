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

public class UserMySqlDAOImpl implements UserDAO {
    private static final Logger LOGGER = Logger.getLogger(UserMySqlDAOImpl.class);

    // sequence of columns in DB:
    private static final int ID = 1;
    private static final int BUS_ID = 2;
    private static final int LOGIN = 3;
    private static final int PASSWORD = 4;
    private static final int TYPE = 5;
    private static final int STATUS = 6;

    // Bill Pugh Singleton Implementation ---start---
    private UserMySqlDAOImpl() {
    }

    private static class SingletonHelper {
        private static final UserMySqlDAOImpl INSTANCE = new UserMySqlDAOImpl();
    }

    public static UserMySqlDAOImpl getInstance() {
        return SingletonHelper.INSTANCE;
    }
    // Bill Pugh Singleton Implementation ---end---

    @Override
    public synchronized boolean addNewDriver(String login, String password) {
        if (login == null || login.isEmpty() || password == null || password.isEmpty()) {
            return false;
        }

        int initialBusId = 0;  // new user does not bonded with buses

        String sql = "INSERT INTO USERS (?, ?, ?, ?, ?) VALUES (BUS_ID, LOGIN, PASSWORD, TYPE, STATUS)";
        boolean rowUpdated = false;

        try (Connection connection = MySqlConnectionSupplier.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, initialBusId);
            ps.setString(2, login);
            ps.setString(3, password);
            ps.setString(4, UserType.DRIVER.toString());
            ps.setString(5, Status.AWAITING.toString());

            rowUpdated = ps.executeUpdate() > 0;

        } catch (SQLException e) {
            LOGGER.error("ERROR: trying to add new user with login: {" + login + "} and pass: {" + password + "}", e);
        }

        return rowUpdated;
    }

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

    @Override
    public synchronized List<User> getAllDrivers() {
        String sql = "SELECT * FROM USERS WHERE TYPE = \'DRIVER\'";
        return getUsersByQuery(sql);
    }

    @Override
    public synchronized List<User> getFreeDrivers() {
        ;
        String sql = "SELECT * FROM USERS WHERE BUS_ID = 0 AND TYPE = \'DRIVER\'";
        return getUsersByQuery(sql);
    }

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

    @Override
    public List<User> getUsersByStatus(Status status) {
        if (status == null) {
            return null;
        }

        List<User> list = null;

        String sql = "SELECT * FROM USERS WHERE STATUS = ?";

        try (Connection connection = MySqlConnectionSupplier.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, status.toString());

            try (ResultSet resultSet = ps.executeQuery()) {
                list = getUsersFromResultSet(resultSet);
            }

        } catch (SQLException e) {
            LOGGER.error("ERROR: trying to get users from DB by status: {" + status + "}", e);
        }

        return list;
    }

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
            LOGGER.error("ERROR: trying to take users from ResultSet", e);
        }

        if (list.isEmpty()) {
            list = null;
        }

        return list;
    }
}

















