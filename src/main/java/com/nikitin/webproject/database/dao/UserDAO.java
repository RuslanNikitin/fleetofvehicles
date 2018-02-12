package com.nikitin.webproject.database.dao;

import com.nikitin.webproject.database.entity.User;
import com.nikitin.webproject.database.entity.Bus;
import com.nikitin.webproject.database.util.Status;

import java.util.List;

public interface UserDAO {

    boolean addNewDriver(String login, String password);

    User getUserByLoginAndPass(String login, String pass);

    List<User> getAllDrivers();

    // all drivers, which is not appointed to the buses
    List<User> getFreeDrivers();

    User getDriverByBusId(Bus bus);

    User getUserById(int id);

    List<User> getUsersByStatus(Status status);

    boolean updateDriverBusId(int userId, int busId);

    boolean updateDriverStatus(User user, Status status);

}
