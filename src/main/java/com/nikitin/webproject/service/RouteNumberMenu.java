package com.nikitin.webproject.service;

import com.nikitin.webproject.database.entity.Bus;
import com.nikitin.webproject.database.entity.BusContent;
import com.nikitin.webproject.database.entity.User;

/**
 * Route Number Menu. Collect data from database to display it on "routeNumber.jsp" page.
 */
public class RouteNumberMenu {
    private Bus bus;
    private BusContent busContent;
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
