package com.nikitin.webproject.service;

import com.nikitin.webproject.database.entity.Route;

public class MainMenu {
    private Route route;
    private int inWork;
    private int awaiting;

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