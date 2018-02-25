package com.nikitin.webproject.service;

import com.nikitin.webproject.database.dao.*;
import com.nikitin.webproject.database.entity.*;
import com.nikitin.webproject.database.util.DAOFactory;
import com.nikitin.webproject.database.util.Status;

import java.util.ArrayList;
import java.util.List;

/**
 * Service Layer. Methods to bundle business logic and database.
 */
public class Service {
    private DAOFactory daoFactory = DAOFactory.getDAOFactory(DAOFactory.MY_SQL);
    private UserDAO userDAO = daoFactory.getUserDAO();
    private RouteDAO routeDAO = daoFactory.getRouteDAO();
    private BusDAO busDAO = daoFactory.getBusDAO();
    private BusContentDAO busContentDAO = daoFactory.getBusContentDAO();
    private LanguageDAO languageDAO = daoFactory.getLanguageDAO();


    /**
     * Bill Pugh Singleton Implementation.
     */
    private Service() {}

    private static class SingletonHelper {
        private static final Service INSTANCE = new Service();
    }

    public static Service getInstance() {
        return SingletonHelper.INSTANCE;
    }


    /**
     * Method to login to application.
     * @param login String.
     * @param pass String.
     * @return user from database, or null.
     */
    public User signIn(String login, String pass) {
        return userDAO.getUserByLoginAndPass(login, pass);
    }


    /**
     * Method to count quantity of free drivers.
     * @return quantity free drivers, int.
     */
    public int freeDriversQuantity() {
        int result = 0;
        List<User> freeDrivers = userDAO.getFreeDrivers();

        if (freeDrivers != null) {
            result = freeDrivers.size();
        }

        return result;
    }


    /**
     * Method get all available (not busy) drivers from database.
     * @return list of free drivers, List<User>.
     */
    public List<User> freeDrivers() {
        return userDAO.getFreeDrivers();
    }


    /**
     * Method to count quantity of free buses.
     * @return quantity free buses, int.
     */
    public int freeBusesQuantity() {
        int result = 0;

        List<Bus> freeBuses = busDAO.getFreeBuses();

        if (freeBuses != null) {
            result = freeBuses.size();
        }

        return result;
    }


    /**
     * Method to refresh bus content by language.
     * @param language
     * @return bus content, related to current language, List<BusWithContent>.
     */
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


    /**
     * Method to refresh Main Menu.
     * @return main menu, List<MainMenu>.
     */
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


    /**
     * Method to refresh Bus Menu by language.
     * @param language
     * @return bus menu, related to current language, List<BusMenu>.
     */
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


    /**
     * Method to refresh Driver Menu by language.
     * @param language
     * @return driver menu, related to current language, List<DriverMenu>.
     */
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


    /**
     * Method to refresh Route Number Menu by route and language.
     * @param route
     * @param language
     * @return route number menu, List<RouteNumberMenu>.
     */
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


    /**
     * Methor return route by ID from database.
     * @param id int.
     * @return route.
     */
    public Route getRouteById(int id) {
        return routeDAO.getRouteById(id);
    }


    /**
     * Method return language by code from database.
     * @param code String.
     * @return language.
     */
    public Language getLang(String code) {
        return languageDAO.getLanguage(code);
    }


    /**
     * Method return bus by ID from database.
     * @param id int.
     * @return bus.
     */
    public Bus getBusById(int id) {
        return busDAO.getBusById(id);
    }


    /**
     * Method update bus relation to the route in database.
     * @param bus
     * @param routeId int.
     * @return true, if operation successfully done.
     */
    public boolean updateBusRouteId(Bus bus, int routeId) {
        return busDAO.updateBusRouteId(bus, routeId);
    }


    /**
     * Method update driver relation to the bus in database.
     * @param userId int.
     * @param busId int.
     * @return true, if operation successfully done.
     */
    public boolean updateDriverByBusId(int userId, int busId) {
        return userDAO.updateDriverBusId(userId, busId);
    }


    /**
     * Method return bus content from database.
     * @param bus
     * @param language
     * @return bus content.
     */
    public BusContent getBusContent(Bus bus, Language language) {
        return busContentDAO.getBusContent(bus, language);
    }


    /**
     * Method update bus status in database.
     * @param bus
     * @param status
     * @return true, if operation successfully done.
     */
    public boolean updateBusStatus(Bus bus, Status status) {
        return busDAO.updateBusStatus(bus, status);
    }


    /**
     * Method update driver status in database.
     * @param user
     * @param status
     * @return true, if operation successfully done.
     */
    public boolean updateDriverStatus(User user, Status status) {
        return userDAO.updateDriverStatus(user, status);
    }


    /**
     * Method return driver by bus ID from database.
     * @param bus
     * @return driver.
     */
    public User getDriverByBusId(Bus bus) {
        return userDAO.getDriverByBusId(bus);
    }


    /**
     * Method return user by ID from database.
     * @param id int.
     * @return user.
     */
    public User getUserByUserId(int id) {
        return userDAO.getUserById(id);
    }


    /**
     * Method add new bus to database.
     * @return ID of this bus.
     */
    public int addNewBus() {
        return busDAO.addBus();
    }


    /**
     * Method add new bus content to database.
     * @param busContent
     * @param busID
     * @param lang
     * @return true, if operation successfully done.
     */
    public boolean addBusContent(BusContent busContent, int busID, String lang) {
        return busContentDAO.addBusContent(busContent, busID, lang);
    }

}

































