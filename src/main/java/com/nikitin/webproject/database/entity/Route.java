package com.nikitin.webproject.database.entity;

import java.io.Serializable;
import java.util.Objects;

/**
 * Route Entity. This class completely describes table in database.
 */
public class Route implements Serializable {
    private int id;
    private String number;

    public Route() {
    }


    /**
     * Builder design pattern implementation.
     */
    private Route(Builder builder) {
        this.id = builder.id;
        this.number = builder.number;
    }

    public static class Builder {
        private int id;
        private String number;

        public Builder setId(int id) {
            this.id = id;
            return this;
        }

        public Builder setNumber(String number) {
            this.number = number;
            return this;
        }

        public Route build() {
            return new Route(this);
        }
    }


    /**
     * Getters and Setters.
     */
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }


    /**
     * equals, hashCode and toString.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Route route = (Route) o;
        return id == route.id &&
                Objects.equals(number, route.number);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, number);
    }

    @Override
    public String toString() {
        return "# " + number;
    }
}
