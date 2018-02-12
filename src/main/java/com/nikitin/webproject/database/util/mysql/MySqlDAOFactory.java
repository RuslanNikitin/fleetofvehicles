package com.nikitin.webproject.database.util.mysql;

import com.nikitin.webproject.database.dao.*;

import com.nikitin.webproject.database.dao.impl.mysql.*;
import com.nikitin.webproject.database.util.DAOFactory;

public class MySqlDAOFactory extends DAOFactory {

    // Bill Pugh Singleton Implementation ---start---
    private MySqlDAOFactory() {}

    private static class SingletonHelper{
        private static final MySqlDAOFactory INSTANCE = new MySqlDAOFactory();
    }

    public static MySqlDAOFactory getInstance(){
        return SingletonHelper.INSTANCE;
    }
    // Bill Pugh Singleton Implementation ---end---


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
