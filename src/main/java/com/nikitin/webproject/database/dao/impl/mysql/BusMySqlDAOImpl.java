package com.nikitin.webproject.database.dao.impl.mysql;

import com.nikitin.webproject.database.dao.BusDAO;
import com.nikitin.webproject.database.entity.Bus;
import com.nikitin.webproject.database.entity.Route;
import com.nikitin.webproject.database.util.Status;
import com.nikitin.webproject.database.util.mysql.MySqlConnectionSupplier;

import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;

/**
 * Bus Data Access Object implementation for MySQL database.
 */
public class BusMySqlDAOImpl implements BusDAO {
    private static final Logger LOGGER = Logger.getLogger(BusMySqlDAOImpl.class);

    /**
     * Sequence of columns in database table:
     */
    private static final int ID = 1;
    private static final int ROUTE_ID = 2;
    private static final int STATUS = 3;


    /**
     * Bill Pugh Singleton Implementation.
     */
    private BusMySqlDAOImpl() {
    }

    private static class SingletonHelper {
        private static final BusMySqlDAOImpl INSTANCE = new BusMySqlDAOImpl();
    }

    public static BusMySqlDAOImpl getInstance() {
        return SingletonHelper.INSTANCE;
    }


    /**
     * Method returns all buses from database.
     * @return list of all buses, List<Bus>.
     */
    @Override
    public synchronized List<Bus> AllBuses() {
        String sql = "SELECT * FROM BUSES";
        return getBusesByQuery(sql);
    }


    /**
     * Method returns only free (not busy) buses from database.
     * @return list of all free buses, List<Bus>.
     */
    @Override
    public synchronized List<Bus> getFreeBuses() {
        String sql = "SELECT * FROM BUSES WHERE ROUTE_ID = 0";
        return getBusesByQuery(sql);
    }


    /**
     * Method returns buses related to specific route.
     * @param route
     * @return list buses, List<Bus>.
     */
    @Override
    public synchronized List<Bus> getBusesByRouteId(Route route) {
        if (route == null) {
            return null;
        }

        List<Bus> list = null;

        String sql = "SELECT * FROM BUSES WHERE ROUTE_ID = ?";

        try (Connection connection = MySqlConnectionSupplier.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, route.getId());

            try (ResultSet resultSet = ps.executeQuery()) {
                list = getBusesFromResultSet(resultSet);
            }

        } catch (SQLException e) {
            LOGGER.error("ERROR: trying to get bus from DB by route: {" + route + "}", e);
        }

        return list;
    }


    /**
     * Method update bus relation to the route in database.
     * @param bus
     * @param routeId int.
     * @return true, if operation successfully done.
     */
    @Override
    public synchronized boolean updateBusRouteId(Bus bus, int routeId) {
        if (bus == null) {
            return false;
        }

        String sql = "UPDATE BUSES SET ROUTE_ID = ? WHERE ID = ?";
        boolean rowUpdated = false;

        try (Connection connection = MySqlConnectionSupplier.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, routeId);
            ps.setInt(2, bus.getId());

            rowUpdated = ps.executeUpdate() > 0;

        } catch (SQLException e) {
            LOGGER.error("ERROR: trying to set bus: {" + bus + "} on the route with id: {" + routeId + "}", e);
        }

        return rowUpdated;
    }


    /**
     * Method update bus status in database.
     * @param bus
     * @param status
     * @return true, if operation successfully done.
     */
    @Override
    public synchronized boolean updateBusStatus(Bus bus, Status status) {
        if (bus == null || status == null) {
            return false;
        }

        String sql = "UPDATE BUSES SET STATUS = ? WHERE ID = ?";
        boolean rowUpdated = false;

        try (Connection connection = MySqlConnectionSupplier.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, status.toString());
            ps.setInt(2, bus.getId());

            rowUpdated = ps.executeUpdate() > 0;

        } catch (SQLException e) {
            LOGGER.error("ERROR: trying to set status {" + status + "} to the bus {" + bus + "}", e);
        }

        return rowUpdated;
    }


    /**
     * Method add new bus to database.
     * @return ID of this bus.
     */
    @Override
    public synchronized int addBus() {
        int id = 0;
        int routeID = 0;
        Status status = Status.AWAITING;

        String sql = "INSERT INTO BUSES (ROUTE_ID, STATUS) VALUES (?, ?)";

        try (Connection connection = MySqlConnectionSupplier.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, routeID);
            ps.setString(2, status.toString());

            ps.executeUpdate();

            ResultSet resultSet = ps.getGeneratedKeys();

            if(resultSet.next()){
                id = resultSet.getInt(1);
            }

        } catch (SQLException e) {
            LOGGER.error("ERROR: trying to add new bus to DB", e);
        }

        return id;
    }


    /**
     * Method to execute query and return list of buses from database.
     * @param sql String.
     * @return list buses, List<Bus>.
     */
    private synchronized List<Bus> getBusesByQuery(String sql) {
        List<Bus> list = null;

        try (Connection connection = MySqlConnectionSupplier.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            list = getBusesFromResultSet(resultSet);

        } catch (SQLException e) {
            LOGGER.error("ERROR: trying to get buses from DB by query: {" + sql + "}", e);
        }

        return list;
    }


    /**
     * Method returns bus by ID from database.
     * @param id int.
     * @return bus.
     */
    @Override
    public Bus getBusById(int id) {
        if (id == 0) {
            return null;
        }

        String sql = "SELECT * FROM BUSES WHERE ID = ?";
        Bus bus = null;

        try (Connection connection = MySqlConnectionSupplier.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, id);

            try(ResultSet resultSet = ps.executeQuery()) {

                while (resultSet.next()) {
                    bus = new Bus();

                    bus.setId(resultSet.getInt(ID));
                    bus.setRouteId(resultSet.getInt(ROUTE_ID));
                    bus.setStatus(Status.valueOf(resultSet.getString(STATUS)));
                }
            }

        } catch (SQLException e) {
            LOGGER.error("ERROR: trying to take bus by id = " + id, e);
        }

        return bus;
    }


    /**
     * Method to get list of buses from result set.
     * @param resultSet
     * @return list buses, List<Bus>.
     */
    private synchronized List<Bus> getBusesFromResultSet(ResultSet resultSet) {
        List<Bus> list = new ArrayList<>();

        try {
            Bus bus;
            while (resultSet.next()) {
                bus = new Bus();

                bus.setId(resultSet.getInt(ID));
                bus.setRouteId(resultSet.getInt(ROUTE_ID));
                bus.setStatus(Status.valueOf(resultSet.getString(STATUS)));

                list.add(bus);
            }
        } catch (SQLException e) {
            LOGGER.error("ERROR: trying to take buses from ResultSet", e);
        }

        if (list.isEmpty()) {
            list = null;
        }

        return list;
    }
}