package com.mycompany.mavenproject3;

public class Customer {
    private final int id;
    private final String code;
    private final String name;
    private final String phone;
    private final String birth;
    private final String status;
    private double totalSpent = 0;

    public Customer(int id, String code, String name, String phone, String birth, String status, double totalSpent) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.phone = phone;
        this.birth = birth;
        this.status = status;
        this.totalSpent = totalSpent;
    }

    public int getId() { return id; }
    public String getCode() { return code; }
    public String getName() { return name; }
    public String getPhone() { return phone; }
    public String getBirth() { return birth; }
    public String getStatus() { return status; }
    public double getTotalSpent() { return totalSpent; }
    public void addTotalSpent(double totalSpent) { this.totalSpent += totalSpent; }
}
