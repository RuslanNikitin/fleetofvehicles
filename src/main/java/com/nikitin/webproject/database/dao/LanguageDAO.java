package com.nikitin.webproject.database.dao;

import com.nikitin.webproject.database.entity.Language;

/**
 * Language Data Access Object.
 */
public interface LanguageDAO {

    /**
     * Method returns language by code from database.
     * @param code String.
     * @return language.
     */
    Language getLanguage(String code);
}
