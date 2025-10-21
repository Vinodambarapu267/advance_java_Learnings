package com.cab_customer_billing_system.model;

public class CabCustomer {
    private String customerName;
    private String source;
    private String destination;
    private double distance;
    private long phoneNo;

    public CabCustomer(String customerName, String source, String destination, double distance, long phoneNo) {

        this.customerName = customerName;
        this.source = source;
        this.destination = destination;
        this.distance = distance;
        this.phoneNo = phoneNo;
    }

    public String getCustomerName() {
        return this.customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getSource() {
        return this.source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getDestination() {
        return this.destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public double getDistance() {
        return this.distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public long getPhoneNo() {
        return this.phoneNo;
    }

    public void setPhoneNo(long phoneNo) {
        this.phoneNo = phoneNo;
    }
}