package com.nikitin.webproject.manager;

import java.util.ResourceBundle;

/**
 * Session Manager, to keep data of available keys, used to manage data in session.
 */
public class SessionManager {
    private static SessionManager instance;
    private ResourceBundle resourceBundle;

    private static final String BUNDLE_NAME = "session";

    public static final String LOGIN = "LOGIN";
    public static final String PASSWORD = "PASSWORD";
    public static final String LANG = "LANG";
    public static final String CURRENT_LANG = "CURRENT_LANG";
    public static final String EN_LANG = "EN_LANG";
    public static final String RU_LANG = "RU_LANG";
    public static final String MENU = "MENU";
    public static final String BUS_MENU = "BUS_MENU";
    public static final String ROUTE_NUMBER_MENU = "ROUTE_NUMBER_MENU";
    public static final String DRIVER_MENU = "DRIVER_MENU";
    public static final String BUS = "BUS";
    public static final String USER = "USER";
    public static final String ROUTE = "ROUTE";
    public static final String BUS_CONTENT = "BUS_CONTENT";
    public static final String CURRENT_ROUTE = "CURRENT_ROUTE";
    public static final String SELECTED_BUS = "SELECTED_BUS";
    public static final String SELECTED_DRIVER = "SELECTED_DRIVER";
    public static final String BUS_ID = "BUS_ID";
    public static final String DRIVER_ID = "DRIVER_ID";
    public static final String FREE_BUSES = "FREE_BUSES";
    public static final String FREE_DRIVERS = "FREE_DRIVERS";
    public static final String FREE_DRIVER_LIST = "FREE_DRIVER_LIST";
    public static final String FREE_BUSES_LIST = "FREE_BUSES_LIST";
    public static final String BRAND_EN = "BRAND_EN";
    public static final String MODEL_EN = "MODEL_EN";
    public static final String COLOR_EN = "COLOR_EN";
    public static final String BRAND_RU = "BRAND_RU";
    public static final String MODEL_RU = "MODEL_RU";
    public static final String COLOR_RU = "COLOR_RU";
    public static final String BUS_NUMBER = "BUS_NUMBER";
    public static final String ERROR_MESSAGE = "ERROR_MESSAGE";
    public static final String CURRENT_PAGE = "CURRENT_PAGE";
    public static final String ROUTE_ID = "ROUTE_ID";

    private SessionManager() {}

    /**
     * Singleton implementation.
     * @return instance of current class.
     */
    public static SessionManager getInstance(){
        if (instance == null){
            instance = new SessionManager();
            instance.resourceBundle = ResourceBundle.getBundle(BUNDLE_NAME);
        }
        return instance;
    }

    /**
     * Method returns data from ResourceBundle by specified key.
     * @param key String.
     * @return data String.
     */
    public String getProperty(String key){
        return (String) resourceBundle.getObject(key);
    }
}


























