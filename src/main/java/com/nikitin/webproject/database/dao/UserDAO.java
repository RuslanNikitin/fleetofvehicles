package com.nikitin.webproject.database.dao;

import com.nikitin.webproject.database.entity.User;
import com.nikitin.webproject.database.entity.Bus;
import com.nikitin.webproject.database.util.Status;

import java.util.List;

/**
 * User Data Access Object.
 */
public interface UserDAO {

    /**
     * Method returns user by login and pass from database.
     * @param login String.
     * @param pass String.
     * @return user from database, or null.
     */
    User getUserByLoginAndPass(String login, String pass);


    /**
     * Method returns all drivers from database.
     * @return list drivers, List<User>.
     */
    List<User> getAllDrivers();


    /**
     * Method returns list of all free (not busy) drivers from database.
     * @return list drivers, List<User>.
     */
    List<User> getFreeDrivers();


    /**
     * Method returns driver related to bus from database.
     * @param bus
     * @return driver.
     */
    User getDriverByBusId(Bus bus);


    /**
     * Method returns user by ID from database.
     * @param id int.
     * @return user.
     */
    User getUserById(int id);


    /**
     * Method update driver relation to the bus in database.
     * @param userId int.
     * @param busId int.
     * @return true, if operation successfully done.
     */
    boolean updateDriverBusId(int userId, int busId);


    /**
     * Method update driver status in database.
     * @param user
     * @param status
     * @return true, if operation successfully done.
     */
    boolean updateDriverStatus(User user, Status status);
}
