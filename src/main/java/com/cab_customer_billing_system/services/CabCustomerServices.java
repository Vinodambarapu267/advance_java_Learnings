package com.cab_customer_billing_system.services;

import com.cab_customer_billing_system.model.CabCustomer;

public interface CabCustomerServices {
    public void addCustomer(CabCustomer ref);
    public boolean isFirstCustomer(CabCustomer ref);
    public void printBill(CabCustomer ref);
}
