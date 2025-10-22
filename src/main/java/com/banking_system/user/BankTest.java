package com.banking_system.user;

import com.banking_system.model.BankCustomer;

import com.banking_system.services.BankServiceImpl;
import com.connection.OracleCon;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;
//add customer
//fetch details
//delete customer
//deposit
//withdraw
//check balance
//update photo
//exit
public class BankTest {
    public static void main(String[] args) {
        BankServiceImpl bankService = new BankServiceImpl();
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("=== BANK ACCOUNT MENU ===\n" +
                    "1. Add Customer\n" +
                    "2. Fetch Customer Details\n" +
                    "3. Delete Customer\n" +
                    "4. Deposit Amount\n" +
                    "5. Withdraw Amount\n" +
                    "6. View Balance\n" +
                    "7. Update Photo\n" +
                    "8. Exit");
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
            case 3->{
                System.out.println("Enter the account number: ");
                long acc= sc.nextLong();
                bankService.deleteCustomer(acc);
            }
            case 4->{
                System.out.println("Enter the account number");
                long acc=sc.nextLong();
                System.out.println("Enter the amount to deposit");
                double  deposit = sc.nextDouble();
               double  balance = bankService.depositAmount(acc,deposit);
               if (balance>0){
                   System.out.println("deposit successful: "+balance);
               }else {
                   System.out.println("deposit failed");
               }
            }
            case 5->{
                System.out.println("Enter the account number");
                long accNum = sc.nextLong();
                System.out.println("Enter tha amount to withdraw");
                double amount = sc.nextDouble();
                bankService.withdraw(accNum,amount);

            }
            case 6->{
                System.out.println("Enter the account number");
                long accNum = sc.nextLong();
              double balance =   bankService.viewBalance(accNum);
              if (balance>=0){
                  System.out.println("current balance : "+balance);
              }
            }
            case 7->{
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
            case 8->{
                System.out.println("=============== Thank you ===============");
                System.exit(1);
            }

        }
    }
    }
}
