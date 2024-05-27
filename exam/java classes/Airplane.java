package com.example.exam;

public class Airplane {
    private String name;
    private String model;

    public Airplane(String name, String model) {
        this.name = name;
        this.model = model;
    }

    public Airplane() {

    }

    public String getName() {
        return name;
    }

    public String getModel() {
        return model;
    }

    @Override
    public String toString() {
        return name + ", " + model;
    }

    public void setName(String newName) {
        name = newName;
    }
}
