package com.nikitin.webproject.database.dao.impl.mysql;

import com.nikitin.webproject.database.dao.BusContentDAO;
import com.nikitin.webproject.database.entity.Bus;
import com.nikitin.webproject.database.entity.BusContent;
import com.nikitin.webproject.database.entity.Language;
import com.nikitin.webproject.database.util.mysql.MySqlConnectionSupplier;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BusContentMySqlDAOImpl implements BusContentDAO {
    private static final Logger LOGGER = Logger.getLogger(BusContentMySqlDAOImpl.class);

    // sequence of columns in DB:
    private static final int ID = 1;
    private static final int BUS_ID = 2;
    private static final int LANG_ID = 3;
    private static final int NUMBER = 4;
    private static final int BRAND = 5;
    private static final int MODEL = 6;
    private static final int COLOR = 7;

    // Bill Pugh Singleton Implementation ---start---
    private BusContentMySqlDAOImpl() {}

    private static class SingletonHelper{
        private static final BusContentMySqlDAOImpl INSTANCE = new BusContentMySqlDAOImpl();
    }

    public static BusContentMySqlDAOImpl getInstance(){
        return SingletonHelper.INSTANCE;
    }
    // Bill Pugh Singleton Implementation ---end---

    @Override
    public synchronized BusContent getBusContent(Bus bus, Language language) {
        if(bus == null || language == null) {
            return null;
        }

        String sql = "SELECT * FROM BUS_CONTENTS WHERE BUS_ID = ? AND LANG_ID = ?";
        BusContent busContent = null;

        try(Connection connection = MySqlConnectionSupplier.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, bus.getId());
            ps.setInt(2, language.getId());

            try(ResultSet resultSet = ps.executeQuery()) {

                while (resultSet.next()) {
                    busContent = new BusContent();

                    busContent.setId(resultSet.getInt(ID));
                    busContent.setBusId(resultSet.getInt(BUS_ID));
                    busContent.setLangId(resultSet.getInt(LANG_ID));
                    busContent.setNumber(resultSet.getString(NUMBER));
                    busContent.setBrand(resultSet.getString(BRAND));
                    busContent.setModel(resultSet.getString(MODEL));
                    busContent.setColor(resultSet.getString(COLOR));
                }
            }
        } catch (SQLException e) {
            LOGGER.error("ERROR: trying to get bus content from DB by bus: {" + bus + "} and language {" + language + "}", e);
        }

        return busContent;
    }
}





























