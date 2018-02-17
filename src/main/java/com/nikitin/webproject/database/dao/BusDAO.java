package com.nikitin.webproject.database.dao;

import com.nikitin.webproject.database.entity.Bus;
import com.nikitin.webproject.database.entity.Route;
import com.nikitin.webproject.database.util.Status;

import java.util.List;

public interface BusDAO {

    List<Bus> AllBuses();
    List<Bus> getFreeBuses();
    List<Bus> getBusesByRouteId(Route route);
    Bus getBusById(int id);
    boolean updateBusRouteId(Bus bus, int routeId);
    boolean updateBusStatus(Bus bus, Status status);
    int addBus();
}