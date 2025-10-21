package com.cab_customer_billing_system.user;

import com.cab_customer_billing_system.model.CabCustomer;
import com.cab_customer_billing_system.services.CabCustomerServicesImpl;
import java.util.Scanner;
public class Test {

    public static void main(String[] args) {
        CabCustomerServicesImpl services = new CabCustomerServicesImpl();
        CabCustomer cust = null;
        while (true) {
            Scanner sc = new Scanner(System.in);
            System.out.println("=== CAB CUSTOMER BILLING SYSTEM ===\n" +
                    "1. Add New BankCustomer\n" +
                    "2. Print Bill by Phone Number\n" +
                    "3. Exit\n" +
                    "====================================");
            int choice = sc.nextInt();
            sc.nextLine();
            switch (choice) {
                case 1 -> {
                    System.out.println("Enter a customer Name: ");
                    String name = sc.nextLine();
                    System.out.println("Enter a customer source: ");
                    String source = sc.next();
                    System.out.println("Enter a customer destination: ");
                    String destination = sc.nextLine();
                    sc.nextLine();
                    System.out.println("Enter a distance : ");
                    double distance = sc.nextDouble();
                    System.out.println("Enter a customer phone number: ");
                    long phoneNo = sc.nextLong();
                   cust = new CabCustomer(name, source, destination, distance, phoneNo);
                    services.addCustomer(cust);

                }
                case 2 -> {
                    if (cust != null) {
                        services.printBill(cust);

                    } else {
                        System.out.println("no data");
                    }
                }
                case 3 -> {
                    System.out.println("Thank you for using Cab Billing System. Goodbye!");
                    System.exit(0);
                }
            }
        }
    }
}
