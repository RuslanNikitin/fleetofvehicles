package com.nikitin.webproject.database.dao;

import com.nikitin.webproject.database.entity.Bus;
import com.nikitin.webproject.database.entity.BusContent;
import com.nikitin.webproject.database.entity.Language;

public interface BusContentDAO {
    BusContent getBusContent(Bus bus, Language language);
}