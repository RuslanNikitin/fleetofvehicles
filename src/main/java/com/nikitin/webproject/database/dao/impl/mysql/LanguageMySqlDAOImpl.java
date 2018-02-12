package com.nikitin.webproject.database.dao.impl.mysql;

import com.nikitin.webproject.database.dao.LanguageDAO;
import com.nikitin.webproject.database.entity.Language;
import com.nikitin.webproject.database.util.mysql.MySqlConnectionSupplier;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LanguageMySqlDAOImpl implements LanguageDAO {
    private static final Logger LOGGER = Logger.getLogger(LanguageMySqlDAOImpl.class);

    // sequence of columns in DB:
    private static final int ID = 1;
    private static final int CODE = 2;

    // Bill Pugh Singleton Implementation ---start---
    private LanguageMySqlDAOImpl() {}

    private static class SingletonHelper{
        private static final LanguageMySqlDAOImpl INSTANCE = new LanguageMySqlDAOImpl();
    }

    public static LanguageMySqlDAOImpl getInstance(){
        return SingletonHelper.INSTANCE;
    }
    // Bill Pugh Singleton Implementation ---end---

    @Override
    public synchronized Language getLanguage(String code) {
        if(code == null || code.isEmpty()) {
            return null;
        }

        String sql = "SELECT * FROM LANGUAGES WHERE CODE = ?";
        Language language = null;

        try (Connection connection = MySqlConnectionSupplier.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, code);

            try(ResultSet resultSet = ps.executeQuery()) {

                while (resultSet.next()) {
                    language = new Language();

                    language.setId(resultSet.getInt(ID));
                    language.setCode(resultSet.getString(CODE));
                }
            }

        } catch (SQLException e) {
            LOGGER.error("ERROR: trying to get language from DB by code: {" + code + "}", e);
        }

        return language;
    }
}





























