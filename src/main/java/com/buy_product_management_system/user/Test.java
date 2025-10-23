package com.buy_product_management_system.user;

import com.buy_product_management_system.bean.User_Account;
import com.buy_product_management_system.service.ProductBuyService;
import com.buy_product_management_system.service.ProductBuyServiceImpl;
import com.connection.OracleCon;


import java.io.FileInputStream;
import java.io.FileNotFoundException;

import java.io.IOException;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.SQLException;

import java.util.Scanner;

public class Test {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ProductBuyService service = new ProductBuyServiceImpl();
        while (true) {
            System.out.println("=== BUY PRODUCT MENU ===\n" +
                    "1. Add New User\n" +
                    "2. Fetch User Details\n" +
                    "3. Delete User\n" +
                    "4. Add Money to Wallet\n" +
                    "5. Purchase Product\n" +
                    "6. View Wallet Balance\n" +
                    "7. Update Photo\n" +
                    "8. Exit\n");
            int choice = sc.nextInt();
            switch (choice) {
                case 1 -> {
                    System.out.println("Enter User ID:");
                    int id = sc.nextInt();
                    sc.nextLine();
                    System.out.println("Enter the user name:");
                    String name = sc.nextLine();
                    System.out.println("Enter the user email:");
                    String email = sc.nextLine();
                    System.out.println("Enter the initial balance:");
                    double balance = sc.nextDouble();
                    sc.nextLine();
                    System.out.println("Enter the address:");
                    String address = sc.nextLine();
                    System.out.println("Enter the photo path: ");
                    String path = sc.nextLine();
                    String filePath = path.replace("\"", "");

                    try {
                        FileInputStream photo = new FileInputStream(filePath);
                        User_Account account = new User_Account(id, name, email, balance, address, photo);
                        int rows = service.addCustomer(account);
                        System.out.println(rows > 0 ? "✅ User Added Successfully " : " no rows inserted");
                    } catch (FileNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                }
                case 2 -> {
                    System.out.println("Enter the user id");
                    int id = sc.nextInt();
                    service.fetch_details(id);
                }
                case 3 -> {
                    System.out.println("Enter the user id");
                    int id = sc.nextInt();
                    service.deleteUser(id);
                }
                case 4 -> {
                    System.out.println("Please enter the user ID to add money:");
                    int id = sc.nextInt();
                    System.out.println("Enter amount to add into wallet");
                    double amount = sc.nextDouble();
                    service.add_money(id, amount);
                }
                case 5 -> {
                    System.out.println("Enter user id: ");
                    int id = sc.nextInt();
                    System.out.println("Enter the  product Price");
                    double price = sc.nextDouble();
                    double remainingBalance = service.purchaseProduct(id, price);
                    System.out.println("Remaining balance : " + remainingBalance);
                }
                case 6 -> {
                    System.out.println("Enter user id: ");
                    int id = sc.nextInt();
                    double balance = service.viewWalletBalance(id);
                    System.out.println("Wallet Balance: " + balance);
                }
                case 7 -> {
                    System.out.println("Enter user id: ");
                    int id = sc.nextInt();
                    sc.nextLine();
                    System.out.println("Enter the photo path: ");
                    String path = sc.nextLine();
                    String filePath = path.replace("\"", "");
                    try {
                        FileInputStream fis = new FileInputStream(filePath);
                        byte[] allBytes = fis.readAllBytes();
                        Connection con = OracleCon.getConnection();
                        Blob photoBlob = con.createBlob();
                        photoBlob.setBytes(1, allBytes);
                        service.updatePhoto(id, photoBlob);
                    } catch (FileNotFoundException e) {
                        System.err.println("Error: Photo file not found at path: " + filePath);
                    } catch (IOException | SQLException e) {
                        System.err.println("Error reading file: " + e.getMessage());
                    }
                }
            }
        }
    }
}
