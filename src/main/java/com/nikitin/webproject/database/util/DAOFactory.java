package com.nikitin.webproject.database.util;

import com.nikitin.webproject.database.dao.BusContentDAO;
import com.nikitin.webproject.database.dao.BusDAO;
import com.nikitin.webproject.database.dao.LanguageDAO;
import com.nikitin.webproject.database.dao.RouteDAO;
import com.nikitin.webproject.database.dao.UserDAO;

import com.nikitin.webproject.database.util.mysql.MySqlDAOFactory;

/**
 * Abstract DAO Factory.
 */
public abstract class DAOFactory {

    /**
     * List of DAO types supported by the factory.
     */
    public static final int MY_SQL = 1;


    /**
     *  There will be a method for each DAO that can be created.
     *  The concrete factories will have to implement these methods.
     */
    public abstract BusContentDAO getBusContentDAO();
    public abstract BusDAO getBusDAO();
    public abstract LanguageDAO getLanguageDAO();
    public abstract RouteDAO getRouteDAO();
    public abstract UserDAO getUserDAO();

    /**
     * Method returns specified DAO Factory.
     * @param whichFactory int.
     * @return DAO Factory.
     */
    public static DAOFactory getDAOFactory(int whichFactory) {
        switch (whichFactory) {
            case MY_SQL:
                return MySqlDAOFactory.getInstance();
            default:
                return null;
        }
    }
}
