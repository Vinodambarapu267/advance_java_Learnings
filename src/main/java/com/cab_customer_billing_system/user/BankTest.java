package com.cab_customer_billing_system.user;

import com.cab_customer_billing_system.model.BankCustomer;

import com.cab_customer_billing_system.services.BankServiceImpl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class BankTest {
    public static void main(String[] args) {
        BankServiceImpl bankService = new BankServiceImpl();
        Scanner sc = new Scanner(System.in);
        int choice = sc.nextInt();
        switch (choice) {
            case 1 -> {
                System.out.println("Enter the account Number:");
                long accNum = sc.nextLong();
                sc.nextLine();
                System.out.println("enter the account Name ");
                String name = sc.nextLine();
                System.out.println("enter the aadhaar number: ");
                long aadhaarNum = sc.nextLong();
                sc.nextLine();
                System.out.println("enter the Bank Name ");
                String baneName = sc.nextLine();
                System.out.println("enter the balance");
                double balance = sc.nextDouble();
                System.out.println("enter the customer Address ");
                String address = sc.nextLine();
                sc.nextLine();
                System.out.println("enter the path of photo");
                String path = sc.nextLine();
                String filePath = path.replace("\"", "");
                File file = new File(filePath);
                try {
                    FileInputStream photo = new FileInputStream(file);
                    BankCustomer customer = new BankCustomer(accNum, name, aadhaarNum, baneName, balance, address, photo);
                    bankService.addCustomer(customer);
                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}
