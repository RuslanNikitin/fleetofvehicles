package com.nikitin.webproject.database.dao;

import com.nikitin.webproject.database.entity.Route;

import java.util.List;

public interface RouteDAO {
    List<Route> AllRoutes();
    Route getRouteById(int id);
}
