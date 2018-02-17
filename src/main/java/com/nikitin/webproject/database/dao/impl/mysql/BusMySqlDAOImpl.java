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

public class BusMySqlDAOImpl implements BusDAO {
    private static final Logger LOGGER = Logger.getLogger(BusMySqlDAOImpl.class);

    // sequence of columns in DB:
    private static final int ID = 1;
    private static final int ROUTE_ID = 2;
    private static final int STATUS = 3;

    // Bill Pugh Singleton Implementation ---start---
    private BusMySqlDAOImpl() {
    }

    private static class SingletonHelper {
        private static final BusMySqlDAOImpl INSTANCE = new BusMySqlDAOImpl();
    }

    public static BusMySqlDAOImpl getInstance() {
        return SingletonHelper.INSTANCE;
    }
    // Bill Pugh Singleton Implementation ---end---

    @Override
    public synchronized List<Bus> AllBuses() {
        String sql = "SELECT * FROM BUSES";
        return getBusesByQuery(sql);
    }

    @Override
    public synchronized List<Bus> getFreeBuses() {
        String sql = "SELECT * FROM BUSES WHERE ROUTE_ID = 0";
        return getBusesByQuery(sql);
    }

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

























