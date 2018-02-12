package com.nikitin.webproject.database.entity;

import java.io.Serializable;
import java.util.Objects;

public class Language implements Serializable {
    private int id;
    private String code;

    public Language() {
    }

    private Language(Builder builder) {
        this.id = builder.id;
        this.code = builder.code;
    }

    public static class Builder {
        private int id;
        private String code;

        public Builder setId(int id) {
            this.id = id;
            return this;
        }

        public Builder setCode(String code) {
            this.code = code;
            return this;
        }

        public Language build() {
            return new Language(this);
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Language language = (Language) o;
        return id == language.id &&
                Objects.equals(code, language.code);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, code);
    }

    @Override
    public String toString() {
        return code.toLowerCase();
    }
}
