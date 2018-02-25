package com.nikitin.webproject.database.dao;

import com.nikitin.webproject.database.entity.Route;

import java.util.List;

/**
 * Route Data Access Object.
 */
public interface RouteDAO {

    /**
     * Method returns list of all routes from database.
     * @return list routes, List<Route>.
     */
    List<Route> AllRoutes();


    /**
     * Method returns route by ID from database.
     * @param id int.
     * @return route.
     */
    Route getRouteById(int id);
}
