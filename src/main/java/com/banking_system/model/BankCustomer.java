package com.banking_system.model;

import java.io.FileInputStream;

public class BankCustomer {
    private long accountNumber;
    private String accountName;
    private long aadhaarNumber;
    private String bankName;
    private double balance;
    private String address;
    private FileInputStream photo;

    public BankCustomer(long accountNumber, String accountName, long aadhaarNumber, String bankName, double balance, String address, FileInputStream photo) {
        this.accountNumber = accountNumber;
        this.accountName = accountName;
        this.aadhaarNumber = aadhaarNumber;
        this.bankName = bankName;
        this.balance = balance;
        this.address = address;
        this.photo = photo;
    }

    public long getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(long accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public long getAadhaarNumber() {
        return aadhaarNumber;
    }

    public void setAadhaarNumber(long aadhaarNumber) {
        this.aadhaarNumber = aadhaarNumber;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public FileInputStream getPhoto() {
        return photo;
    }

    public void setPhoto(FileInputStream photo) {
        this.photo = photo;
    }
}