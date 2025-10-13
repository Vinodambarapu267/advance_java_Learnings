package com.jdbc.lab_tasks.product_management_system;

import com.jdbc.lab_tasks.product_management_system.exceptions.ProductNotExistException;
import com.jdbc.lab_tasks.product_management_system.serices.Product;
import com.jdbc.lab_tasks.product_management_system.serices.ProductServiceImpl;

import java.util.Scanner;

public class Test {
    static Product ref = null;
    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {

        while (true) {
            System.out.println("=".repeat(15) + " PRODUCT MANAGEMENT SYSTEM " + "=".repeat(15));
            System.out.println("1. Insert Product\n" +
                    "2. Update Product Price\n" +
                    "3. Delete Product\n" +
                    "4. View All Products\n" +
                    "5. Exit");
            int choice = sc.nextInt();
            switch (choice) {
                case 1 -> Test.insert();
                case 2 -> Test.update();
                case 3 -> Test.delete();
                case 4 -> ProductServiceImpl.displayAllProducts(ref);
                case 5 -> {
                    System.out.println("-".repeat(15) + "Thank you visiting... " + "-".repeat(15));
                    System.exit(0);
                }
                default -> System.out.println("Invalid option..");
            }
        }
    }

    public static void update() {
        System.out.println("Enter the product Name:");
        String pName = String.valueOf(sc.next().toUpperCase().charAt(0));
        int updatedRow = ProductServiceImpl.update(pName);
        if (updatedRow > 0) {
            System.out.println(updatedRow + " rows are updated");
        } else {
            System.out.println("no rows updated");
        }
    }

    public static void delete() {
        System.out.println("Enter the product Name: ");
        String name = String.valueOf(sc.next().toUpperCase().charAt(0));
        int deleteRow = ProductServiceImpl.delete(name);
        if (deleteRow > 0) {
            System.out.println(deleteRow + " rows are deleted");
        } else {
            try {
                throw new ProductNotExistException("you entered the wrong product name!!!");
            } catch (ProductNotExistException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public static void insert() {
        int insertRows = 0;
        System.out.println("Enter the product Id");
        int id = sc.nextInt();
        System.out.println("Enter the product Name:");
        String pName = String.valueOf(sc.next().toUpperCase().charAt(0));
        System.out.println("Enter the product price :");
        double pPrice = sc.nextDouble();
        System.out.println("Enter the product quantity:");
        int quantity = sc.nextInt();
        Product product = new Product(id, pName, pPrice, quantity);
        insertRows = ProductServiceImpl.insertData(product);
        if (insertRows > 0) {
            System.out.println(insertRows + " rows inserted");
        } else {
            try {
                throw new ProductNotExistException("wrong ");
            } catch (ProductNotExistException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
