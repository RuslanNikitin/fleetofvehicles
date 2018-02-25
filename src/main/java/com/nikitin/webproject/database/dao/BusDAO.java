package com.nikitin.webproject.database.dao;

import com.nikitin.webproject.database.entity.Bus;
import com.nikitin.webproject.database.entity.Route;
import com.nikitin.webproject.database.util.Status;

import java.util.List;

/**
 * Bus Data Access Object.
 */
public interface BusDAO {

    /**
     * Method return all buses from database.
     * @return list of all buses, List<Bus>.
     */
    List<Bus> AllBuses();


    /**
     * Method return only free (not busy) buses from database.
     * @return list of all free buses, List<Bus>.
     */
    List<Bus> getFreeBuses();


    /**
     * Method returns buses related to specific route.
     * @param route
     * @return list buses, List<Bus>.
     */
    List<Bus> getBusesByRouteId(Route route);


    /**
     * Method returns bus by ID from database.
     * @param id int.
     * @return bus.
     */
    Bus getBusById(int id);


    /**
     * Method update bus relation to the route in database.
     * @param bus
     * @param routeId int.
     * @return true, if operation successfully done.
     */
    boolean updateBusRouteId(Bus bus, int routeId);


    /**
     * Method update bus status in database.
     * @param bus
     * @param status
     * @return true, if operation successfully done.
     */
    boolean updateBusStatus(Bus bus, Status status);


    /**
     * Method add new bus to database.
     * @return ID of this bus.
     */
    int addBus();
}