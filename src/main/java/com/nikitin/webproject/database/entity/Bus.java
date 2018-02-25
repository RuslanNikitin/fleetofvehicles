package com.nikitin.webproject.database.entity;

import com.nikitin.webproject.database.util.Status;

import java.io.Serializable;
import java.util.Objects;

/**
 * Bus Entity. This class completely describes table in database.
 */
public class Bus implements Serializable {
    private int id;
    private int routeId;
    private Status status;

    public Bus() {
    }


    /**
     * Builder design pattern implementation.
     */
    private Bus(Builder builder) {
        this.id = builder.id;
        this.routeId = builder.routeId;
        this.status = builder.status;
    }

    public static class Builder {
        private int id;
        private int routeId;
        private Status status;

        public Builder setId(int id) {
            this.id = id;
            return this;
        }

        public Builder setRouteId(int routeId) {
            this.routeId = routeId;
            return this;
        }

        public Builder setStatus(Status status) {
            this.status = status;
            return this;
        }

        public Bus build() {
            return new Bus(this);
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

    public int getRouteId() {
        return routeId;
    }

    public void setRouteId(int routeId) {
        this.routeId = routeId;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }


    /**
     * equals, hashCode and toString.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Bus bus = (Bus) o;
        return id == bus.id &&
                routeId == bus.routeId &&
                status == bus.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, routeId, status);
    }

    @Override
    public String toString() {
        return "Bus{" +
                "id=" + id +
                '}';
    }
}
