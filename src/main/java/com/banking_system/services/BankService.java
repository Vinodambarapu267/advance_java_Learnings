package com.banking_system.services;

import com.banking_system.model.BankCustomer;

import java.sql.Blob;

public interface BankService {
    public void addCustomer(BankCustomer cust);
    public void fetchCustomerDetails(long accountNumber);
    public void deleteCustomer(long accNumber);
    public double depositAmount(long accNum,double amount);
    public double withdraw(long accNum,double amount);
    public double viewBalance(long accNumber);
    public void updatePhoto(Blob photo, long accountNumber);
    public void transferMoney(long senderAccountNum, long receiverAccount,double amount);
}
