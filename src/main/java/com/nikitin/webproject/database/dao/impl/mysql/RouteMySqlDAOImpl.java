package com.nikitin.webproject.database.dao.impl.mysql;

import com.nikitin.webproject.database.dao.RouteDAO;
import com.nikitin.webproject.database.entity.Route;
import com.nikitin.webproject.database.util.mysql.MySqlConnectionSupplier;

import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;

public class RouteMySqlDAOImpl implements RouteDAO {
    private static final Logger LOGGER = Logger.getLogger(RouteMySqlDAOImpl.class);

    // sequence of columns in DB:
    private static final int ID = 1;
    private static final int NUMBER = 2;

    // Bill Pugh Singleton Implementation ---start---
    private RouteMySqlDAOImpl() {}

    private static class SingletonHelper{
        private static final RouteMySqlDAOImpl INSTANCE = new RouteMySqlDAOImpl();
    }

    public static RouteMySqlDAOImpl getInstance(){
        return SingletonHelper.INSTANCE;
    }
    // Bill Pugh Singleton Implementation ---end---

    @Override
    public synchronized List<Route> AllRoutes() {
        List<Route> list = new ArrayList<>();

        String sql = "SELECT * FROM ROUTES";

        try(Connection connection = MySqlConnectionSupplier.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql)) {

            Route route;
            while (resultSet.next()) {
                route = new Route();

                route.setId(resultSet.getInt(ID));
                route.setNumber(resultSet.getString(NUMBER));

                list.add(route);
            }

        } catch (SQLException e) {
            LOGGER.error("ERROR: trying to get all routes from DB", e);
        }

        if(list.isEmpty()) {
            list = null;
        }

        return list;
    }

    @Override
    public synchronized Route getRouteById(int id) {
        String sql = "SELECT * FROM ROUTES WHERE ID = ?";
        Route route = null;

        try(Connection connection = MySqlConnectionSupplier.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, id);

            try(ResultSet resultSet = ps.executeQuery()) {
                route = getRouteFromResultSet(resultSet);
            }

        } catch (SQLException e) {
            LOGGER.error("ERROR: trying to get route from DB by id {" + id + "}", e);
        }

        return route;
    }

    private synchronized Route getRouteFromResultSet(ResultSet resultSet) {
        Route route = null;

        try {
            while (resultSet.next()) {
                route = new Route();

                route.setId(resultSet.getInt(ID));
                route.setNumber(resultSet.getString(NUMBER));
            }
        } catch (SQLException e) {
            LOGGER.error("ERROR: trying to take route from ResultSet", e);
        }

        return route;
    }
}
























