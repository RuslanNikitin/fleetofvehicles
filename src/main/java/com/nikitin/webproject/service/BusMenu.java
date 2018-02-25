package com.nikitin.webproject.service;

import com.nikitin.webproject.database.entity.Bus;
import com.nikitin.webproject.database.entity.BusContent;
import com.nikitin.webproject.database.entity.Route;
import com.nikitin.webproject.database.entity.User;

/**
 * Bus Menu. Collect data from database to display it on "buses.jsp" page.
 */
public class BusMenu {
    private Bus bus;
    private BusContent busContent;
    private Route route;
    private User driver;


    /**
     * Getters and Setters.
     */
    public Bus getBus() {
        return bus;
    }

    public void setBus(Bus bus) {
        this.bus = bus;
    }

    public Route getRoute() {
        return route;
    }

    public void setRoute(Route route) {
        this.route = route;
    }

    public User getDriver() {
        return driver;
    }

    public void setDriver(User driver) {
        this.driver = driver;
    }

    public BusContent getBusContent() {
        return busContent;
    }

    public void setBusContent(BusContent busContent) {
        this.busContent = busContent;
    }
}
