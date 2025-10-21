package com.cab_customer_billing_system.services;

import com.cab_customer_billing_system.model.BankCustomer;

import java.sql.Blob;

public interface BankService {
    public void addCustomer(BankCustomer cust);
    public void fetchCustomerDetails(BankCustomer cust);
    public void deleteCustomer(BankCustomer cust);
    public double depositAmount(BankCustomer cust);
    public double withdraw(BankCustomer cust);
    public double viewBalance(BankCustomer cust);
    public Blob updatePhoto(BankCustomer cust);
}
