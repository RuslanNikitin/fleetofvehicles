package com.nikitin.webproject.manager;

import java.util.ResourceBundle;

/**
 * Page Manager, to keep data of available pages.
 */
public class PageManager {
    private static PageManager instance;
    private ResourceBundle resourceBundle;

    private static final String BUNDLE_NAME = "pages";

    public static final String LOGIN_PAGE = "LOGIN_PAGE";
    public static final String ERROR_PAGE = "ERROR_PAGE";
    public static final String MAIN_PAGE = "MAIN_PAGE";
    public static final String BUSES_PAGE = "BUSES_PAGE";
    public static final String ROUTE_NUMBER = "ROUTE_NUMBER";
    public static final String DRIVERS_PAGE = "DRIVERS_PAGE";
    public static final String CLIENT_PAGE = "CLIENT_PAGE";
    public static final String ADD_BUS_PAGE = "ADD_BUS_PAGE";

    private PageManager(){}

    /**
     * Singleton implementation.
     * @return instance of current class.
     */
    public static PageManager getInstance(){
        if (instance == null){
            instance = new PageManager();
            instance.resourceBundle = ResourceBundle.getBundle(BUNDLE_NAME);
        }
        return instance;
    }

    /**
     * Method returns page address from ResourceBundle by specified key.
     * @param key String.
     * @return page String.
     */
    public String getProperty(String key){
        return (String) resourceBundle.getObject(key);
    }
}
