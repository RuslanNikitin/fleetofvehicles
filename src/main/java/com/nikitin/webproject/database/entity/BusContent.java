package com.nikitin.webproject.database.entity;

import java.io.Serializable;
import java.util.Objects;

public class BusContent implements Serializable {
    private int id;
    private int busId;
    private int langId;
    private String number;
    private String brand;
    private String model;
    private String color;

    public BusContent() {
    }

    private BusContent(Builder builder) {
        this.id = builder.id;
        this.busId = builder.busId;
        this.langId = builder.langId;
        this.number = builder.number;
        this.brand = builder.brand;
        this.model = builder.model;
        this.color = builder.color;
    }

    public static class Builder {
        private int id;
        private int busId;
        private int langId;
        private String number;
        private String brand;
        private String model;
        private String color;

        public Builder setId(int id) {
            this.id = id;
            return this;
        }

        public Builder setBusId(int busId) {
            this.busId = busId;
            return this;
        }

        public Builder getLangId(int langId) {
            this.langId = langId;
            return this;
        }

        public Builder setNumber(String number) {
            this.number = number;
            return this;
        }

        public Builder setBrand(String brand) {
            this.brand = brand;
            return this;
        }

        public Builder setModel(String model) {
            this.model = model;
            return this;
        }

        public Builder setColor(String color) {
            this.color = color;
            return this;
        }

        public BusContent build() {
            return new BusContent(this);
        }
    }

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

    public int getLangId() {
        return langId;
    }

    public void setLangId(int langId) {
        this.langId = langId;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BusContent that = (BusContent) o;
        return id == that.id &&
                busId == that.busId &&
                langId == that.langId &&
                Objects.equals(number, that.number) &&
                Objects.equals(brand, that.brand) &&
                Objects.equals(model, that.model) &&
                Objects.equals(color, that.color);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, busId, langId, number, brand, model, color);
    }

    @Override
    public String toString() {
        return number + ", " + brand + " - " + model + ", " + color;
    }
}
