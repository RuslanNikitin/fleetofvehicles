package com.nikitin.webproject.service;

import com.nikitin.webproject.database.entity.Bus;
import com.nikitin.webproject.database.entity.BusContent;

/**
 * Bus content, to display it on page.
 */
public class BusWithContent {
    private Bus bus;
    private BusContent busContent;


    /**
     * Getters and Setters.
     */
    public Bus getBus() {
        return bus;
    }

    public void setBus(Bus bus) {
        this.bus = bus;
    }

    public BusContent getBusContent() {
        return busContent;
    }

    public void setBusContent(BusContent busContent) {
        this.busContent = busContent;
    }


    /**
     * toString.
     * @return bus content object as String.
     */
    @Override
    public String toString() {
        return "BusWithContent{" +
                "bus=" + bus.getId() +
                ", busContent=" + busContent.getNumber() +
                '}';
    }
}
