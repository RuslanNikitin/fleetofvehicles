package com.nikitin.webproject.service;

import com.nikitin.webproject.database.entity.Route;

/**
 * Main Menu. Collect data from database to display it on "main.jsp" page.
 */
public class MainMenu {
    private Route route;
    private int inWork;
    private int awaiting;


    /**
     * Getters and Setters.
     */
    public Route getRoute() {
        return route;
    }

    public void setRoute(Route route) {
        this.route = route;
    }

    public int getInWork() {
        return inWork;
    }

    public void setInWork(int inWork) {
        this.inWork = inWork;
    }

    public int getAwaiting() {
        return awaiting;
    }

    public void setAwaiting(int awaiting) {
        this.awaiting = awaiting;
    }
}