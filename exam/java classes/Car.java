package com.example.exam2;

public class Car {
    private String id;
    private String status;
    private String clientName;

    public Car(String id, String status, String clientName) {
        this.id = id;
        this.status = status;
        this.clientName = clientName;
    }

    public Car() {

    }

    public String getClientName() {
        return clientName;
    }

    public String getId() {
        return id;
    }

    public String getStatus() {
        return status;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    // Getters and setters
}
