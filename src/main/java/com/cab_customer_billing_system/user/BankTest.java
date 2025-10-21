package com.cab_customer_billing_system.user;

import com.cab_customer_billing_system.model.BankCustomer;

import com.cab_customer_billing_system.services.BankService;
import com.cab_customer_billing_system.services.BankServiceImpl;
import com.connection.OracleCon;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.SQLException;
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
            case 2->
            {
                System.out.println("Enter the account number");
                long accNum = sc.nextLong();
                bankService.fetchCustomerDetails(accNum);
            }
            case 6->{
                System.out.println("Enter the account number");
                long accNum = sc.nextLong();
              double balance =   bankService.viewBalance(accNum);
              if (balance>=0){
                  System.out.println("current balance : "+balance);
              }
            }
            case 5->{
                System.out.println("Enter account Number");
                long accNum = sc.nextLong();
                sc.nextLine();
                System.out.println("Enter the photo path:");
                String path = sc.nextLine();
                String filePath = path.replace("\"","").trim();
                File file  = new File(filePath);

                try (FileInputStream fis = new FileInputStream(file)) {
                    // Read file content into byte array
                    byte[] photoBytes = fis.readAllBytes();

                    // Create a Blob from the connection
                    Connection con = OracleCon.getConnection();
                    Blob photoBlob = con.createBlob();
                    photoBlob.setBytes(1, photoBytes); // Write bytes starting at position 1

                    // Now pass the Blob (not FileInputStream) to the service
                    bankService.updatePhoto(photoBlob, accNum);

                } catch (FileNotFoundException e) {
                    System.err.println("Error: Photo file not found at path: " + filePath);
                } catch (SQLException e) {
                    System.err.println("Database error: " + e.getMessage());
                } catch (IOException e) {
                    System.err.println("Error reading file: " + e.getMessage());
                }
            }
        }
    }
}
