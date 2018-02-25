package com.nikitin.webproject.database.entity;

import com.nikitin.webproject.database.util.Status;
import com.nikitin.webproject.database.util.UserType;

import java.io.Serializable;
import java.util.Objects;

/**
 * User Entity. This class completely describes table in database.
 */
public class User implements Serializable {
    private int id;
    private int busId;
    private String login;
    private String password;
    private UserType type;
    private Status status;


    public User() {
    }


    /**
     * Builder design pattern implementation.
     */
    private User(Builder builder) {
        this.id = builder.id;
        this.busId = builder.busId;
        this.login = builder.login;
        this.password = builder.password;
        this.type = builder.type;
        this.status = builder.status;

    }

    public static class Builder {
        private int id;
        private int busId;
        private String login;
        private String password;
        private UserType type;
        private Status status;

        public Builder setId(int id) {
            this.id = id;
            return this;
        }

        public Builder setBusId(int busId) {
            this.busId = busId;
            return this;
        }

        public Builder setLogin(String login) {
            this.login = login;
            return this;
        }

        public Builder setPassword(String password) {
            this.password = password;
            return this;
        }

        public Builder setType(UserType type) {
            this.type = type;
            return this;
        }

        public Builder setStatus(Status status) {
            this.status = status;
            return this;
        }

        public User build() {
            return new User(this);
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

    public int getBusId() {
        return busId;
    }

    public void setBusId(int busId) {
        this.busId = busId;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserType getType() {
        return type;
    }

    public void setType(UserType type) {
        this.type = type;
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
        User user = (User) o;
        return id == user.id &&
                busId == user.busId &&
                Objects.equals(login, user.login) &&
                Objects.equals(password, user.password) &&
                Objects.equals(type, user.type) &&
                status == user.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, busId, login, password, type, status);
    }

    @Override
    public String toString() {
        return login;
    }
}
