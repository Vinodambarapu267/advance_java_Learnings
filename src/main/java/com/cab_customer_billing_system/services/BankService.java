package com.cab_customer_billing_system.services;

import com.cab_customer_billing_system.model.BankCustomer;

import java.sql.Blob;

public interface BankService {
    public void addCustomer(BankCustomer cust);
    public void fetchCustomerDetails(long accountNumber);
    public void deleteCustomer(BankCustomer cust);
    public double depositAmount(long accNum,double amount);
    public double withdraw(long accNum,double amount);
    public double viewBalance(long accNumber);
    public void updatePhoto(Blob photo, long accountNumber);
}
