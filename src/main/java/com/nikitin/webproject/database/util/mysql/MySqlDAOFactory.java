package com.nikitin.webproject.database.util.mysql;

import com.nikitin.webproject.database.dao.*;

import com.nikitin.webproject.database.dao.impl.mysql.*;
import com.nikitin.webproject.database.util.DAOFactory;

/**
 * DAO Factory for MySQL database.
 */
public class MySqlDAOFactory extends DAOFactory {

    /**
     * Bill Pugh Singleton Implementation.
     */
    private MySqlDAOFactory() {}

    private static class SingletonHelper{
        private static final MySqlDAOFactory INSTANCE = new MySqlDAOFactory();
    }

    public static MySqlDAOFactory getInstance(){
        return SingletonHelper.INSTANCE;
    }


    /**
     * Methods to return determined Data Access Object for MySQL database.
     * @return determined DAO.
     */
    @Override
    public BusContentDAO getBusContentDAO() {
        return BusContentMySqlDAOImpl.getInstance();
    }

    @Override
    public BusDAO getBusDAO() {
        return BusMySqlDAOImpl.getInstance();
    }

    @Override
    public LanguageDAO getLanguageDAO() {
        return LanguageMySqlDAOImpl.getInstance();
    }

    @Override
    public RouteDAO getRouteDAO() {
        return RouteMySqlDAOImpl.getInstance();
    }

    @Override
    public UserDAO getUserDAO() {
        return UserMySqlDAOImpl.getInstance();
    }
}
