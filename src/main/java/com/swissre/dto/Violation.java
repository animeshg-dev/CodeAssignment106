package com.swissre.dto;

public class Violation {
    private final Type type;
    private final String employeeId;
    private final String employeeName;
    private final String message;
    public Violation(Type type, String employeeId, String employeeName, String message) {
        this.type = type;
        this.employeeId = employeeId;
        this.employeeName = employeeName;
        this.message = message;
    }

    public Type getType() {
        return type;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return String.format("[%s] %s (id: %s): %s", type, employeeName, employeeId, message);
    }

    public enum Type {SALARY, DEPTH}
}
