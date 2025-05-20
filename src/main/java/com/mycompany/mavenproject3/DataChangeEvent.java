package com.mycompany.mavenproject3;

public class DataChangeEvent {
    private final String operation;

    public DataChangeEvent(String operation) {
        this.operation = operation;
    }

    public String getOperation() {
        return operation;
    }
}
