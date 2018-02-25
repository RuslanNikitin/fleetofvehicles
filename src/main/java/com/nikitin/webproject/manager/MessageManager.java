package com.nikitin.webproject.manager;

import java.util.ResourceBundle;

/**
 * Message Manager, to keep data of available error messages.
 */
public class MessageManager {
    private static MessageManager instance;
    private ResourceBundle resourceBundle;

    private static final String BUNDLE_NAME = "messages";

    public static final String LOGIN_ERROR_MESSAGE = "LOGIN_ERROR_MESSAGE";
    public static final String SERVLET_EXCEPTION = "SERVLET_EXCEPTION";
    public static final String IO_EXCEPTION = "IO_EXCEPTION";
    public static final String SESSION_INTERRUPTED = "SESSION_INTERRUPTED";

    private MessageManager() {}

    /**
     * Singleton implementation.
     * @return instance of current class.
     */
    public static MessageManager getInstance(){
        if (instance == null){
            instance = new MessageManager();
            instance.resourceBundle = ResourceBundle.getBundle(BUNDLE_NAME);
        }
        return instance;
    }

    /**
     * Method returns message from ResourceBundle by specified key.
     * @param key String.
     * @return message String.
     */
    public String getProperty(String key){
        return (String) resourceBundle.getObject(key);
    }
}
