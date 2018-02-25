package com.nikitin.webproject.database.dao;

import com.nikitin.webproject.database.entity.Bus;
import com.nikitin.webproject.database.entity.BusContent;
import com.nikitin.webproject.database.entity.Language;

/**
 * Bus Content Data Access Object.
 */
public interface BusContentDAO {

    /**
     * Method to get bus content by bus and language from database.
     * @param bus
     * @param language
     * @return bus content.
     */
    BusContent getBusContent(Bus bus, Language language);

    /**
     * Method to add new bus content to database.
     * @param busContent
     * @param busID
     * @param lang
     * @return true, if operation successfully done.
     */
    boolean addBusContent(BusContent busContent, int busID, String lang);
}