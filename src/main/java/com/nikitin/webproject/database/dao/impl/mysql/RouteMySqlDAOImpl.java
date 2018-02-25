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

/**
 * Route Data Access Object implementation for MySQL database.
 */
public class RouteMySqlDAOImpl implements RouteDAO {
    private static final Logger LOGGER = Logger.getLogger(RouteMySqlDAOImpl.class);

    /**
     * Sequence of columns in database table:
     */
    private static final int ID = 1;
    private static final int NUMBER = 2;


    /**
     * Bill Pugh Singleton Implementation.
     */
    private RouteMySqlDAOImpl() {}

    private static class SingletonHelper{
        private static final RouteMySqlDAOImpl INSTANCE = new RouteMySqlDAOImpl();
    }

    public static RouteMySqlDAOImpl getInstance(){
        return SingletonHelper.INSTANCE;
    }


    /**
     * Method returns list of all routes from database.
     * @return list routes, List<Route>.
     */
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


    /**
     * Method returns route by ID from database.
     * @param id int.
     * @return route.
     */
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


    /**
     * Method to get route from result set.
     * @param resultSet
     * @return route.
     */
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
























