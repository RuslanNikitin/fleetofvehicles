package com.nikitin.webproject.database.dao;

import com.nikitin.webproject.database.entity.Language;

public interface LanguageDAO {
    Language getLanguage(String code);
}
