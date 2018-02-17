package com.nikitin.webproject.service;

import com.nikitin.webproject.database.dao.*;
import com.nikitin.webproject.database.entity.*;
import com.nikitin.webproject.database.util.DAOFactory;
import com.nikitin.webproject.database.util.Status;

import java.util.ArrayList;
import java.util.List;

public class Service {
    private DAOFactory daoFactory = DAOFactory.getDAOFactory(DAOFactory.MY_SQL);
    private UserDAO userDAO = daoFactory.getUserDAO();
    private RouteDAO routeDAO = daoFactory.getRouteDAO();
    private BusDAO busDAO = daoFactory.getBusDAO();
    private BusContentDAO busContentDAO = daoFactory.getBusContentDAO();
    private LanguageDAO languageDAO = daoFactory.getLanguageDAO();

    // Bill Pugh Singleton Implementation ---start---
    private Service() {
    }

    private static class SingletonHelper {
        private static final Service INSTANCE = new Service();
    }

    public static Service getInstance() {
        return SingletonHelper.INSTANCE;
    }
    // Bill Pugh Singleton Implementation ---end---

    public User signIn(String login, String pass) {
        return userDAO.getUserByLoginAndPass(login, pass);
    }

    public int freeDriversQuantity() {
        int result = 0;
        List<User> freeDrivers = userDAO.getFreeDrivers();

        if (freeDrivers != null) {
            result = freeDrivers.size();
        }

        return result;
    }

    public List<User> freeDrivers() {
        return userDAO.getFreeDrivers();
    }

    public int freeBusesQuantity() {
        int result = 0;

        List<Bus> freeBuses = busDAO.getFreeBuses();

        if (freeBuses != null) {
            result = freeBuses.size();
        }

        return result;
    }

    public List<BusWithContent> freeBuses(Language language) {
        List<BusWithContent> list = new ArrayList<>();

        BusWithContent busWithContent = null;

        List<Bus> buses = busDAO.getFreeBuses();

        if (buses != null) {
            for (Bus bus : buses) {
                if (bus != null) {
                    BusContent busContent = busContentDAO.getBusContent(bus, language);

                    busWithContent = new BusWithContent();

                    busWithContent.setBus(bus);
                    busWithContent.setBusContent(busContent);

                    list.add(busWithContent);
                }
            }
        }

        return list;
    }

    public List<MainMenu> refreshRoutes() {
        List<MainMenu> list = new ArrayList<>();
        MainMenu menu;

        for (Route route : routeDAO.AllRoutes()) {
            menu = new MainMenu();

            int inWork = 0;
            int awaiting = 0;

            List<Bus> buses = busDAO.getBusesByRouteId(route);

            if (buses != null) {
                for (Bus bus : buses) {
                    if (bus.getStatus().equals(Status.APPROVED)) {
                        inWork++;
                    } else if (bus.getStatus().equals(Status.AWAITING)) {
                        awaiting++;
                    }
                }
            }

            menu.setRoute(route);
            menu.setInWork(inWork);
            menu.setAwaiting(awaiting);

            list.add(menu);
        }

        return list;
    }

    public List<BusMenu> refreshBuses(Language language) {
        List<BusMenu> list = new ArrayList<>();

        BusMenu busMenu;

        for (Bus bus : busDAO.AllBuses()) {
            busMenu = new BusMenu();

            Route route = routeDAO.getRouteById(bus.getRouteId());
            User driver = userDAO.getDriverByBusId(bus);
            BusContent busContent = busContentDAO.getBusContent(bus, language);

            busMenu.setBus(bus);
            busMenu.setBusContent(busContent);
            busMenu.setRoute(route);
            busMenu.setDriver(driver);

            list.add(busMenu);
        }

        return list;
    }

    public List<DriverMenu> refreshDrivers(Language language) {
        List<DriverMenu> list = new ArrayList<>();

        DriverMenu driverMenu;

        for (User driver : userDAO.getAllDrivers()) {
            driverMenu = new DriverMenu();

            Bus bus = busDAO.getBusById(driver.getBusId());

            BusContent busContent = null;
            Route route = null;

            if (bus != null) {
                busContent = busContentDAO.getBusContent(bus, language);
                route = routeDAO.getRouteById(bus.getRouteId());
            }

            driverMenu.setDriver(driver);
            driverMenu.setBus(bus);
            driverMenu.setBusContent(busContent);
            driverMenu.setRoute(route);

            list.add(driverMenu);
        }

        return list;
    }

    public List<RouteNumberMenu> refreshRouteNumberMenu(Route route, Language language) {
        List<RouteNumberMenu> list = new ArrayList<>();

        RouteNumberMenu routeNumberMenu;

        List<Bus> buses = busDAO.getBusesByRouteId(route);

        if (buses != null) {
            for (Bus bus : buses) {
                routeNumberMenu = new RouteNumberMenu();

                BusContent busContent = null;
                User driver = null;

                if (bus != null) {
                    busContent = busContentDAO.getBusContent(bus, language);
                    driver = userDAO.getDriverByBusId(bus);

                    routeNumberMenu.setBus(bus);
                    routeNumberMenu.setBusContent(busContent);
                    routeNumberMenu.setDriver(driver);
                }

                list.add(routeNumberMenu);
            }
        }


        return list;
    }

    public Route getRouteById(int id) {
        return routeDAO.getRouteById(id);
    }

    public Language getLang(String code) {
        return languageDAO.getLanguage(code);
    }

    public Bus getBusById(int id) {
        return busDAO.getBusById(id);
    }

    public boolean updateBusRouteId(Bus bus, int routeId) {
        return busDAO.updateBusRouteId(bus, routeId);
    }

    public boolean updateDriverByBusId(int userId, int busId) {
        return userDAO.updateDriverBusId(userId, busId);
    }

    public BusContent getBusContent(Bus bus, Language language) {
        return busContentDAO.getBusContent(bus, language);
    }

    public boolean updateBusStatus(Bus bus, Status status) {
        return busDAO.updateBusStatus(bus, status);
    }

    public boolean updateUserStatus(User user, Status status) {
        return userDAO.updateDriverStatus(user, status);
    }

    public User getUserByBusId(Bus bus) {
        return userDAO.getDriverByBusId(bus);
    }

    public User getUserByUserId(int id) {
        return userDAO.getUserById(id);
    }

    public int addNewBus(Bus bus) {
        return busDAO.addBus();
    }

    public boolean addBusContent(BusContent busContent, int busID, String lang) {
        return busContentDAO.addBusContent(busContent, busID, lang);
    }

}

































