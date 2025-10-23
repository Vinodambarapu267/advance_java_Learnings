package com.buy_product_management_system.service;

import com.buy_product_management_system.bean.User_Account;
import com.connection.OracleCon;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;

public class ProductBuyServiceImpl implements ProductBuyService {
    @Override
    public int addCustomer(User_Account acc) {
        Connection con = OracleCon.getConnection();
        int insertRows;
        String insertQuery = "insert into User_Account(user_id,name,email,wallet_balance,address,photo) values(?,?,?,?,?,?)";
        try {
            PreparedStatement pstmt = con.prepareStatement(insertQuery);
            pstmt.setInt(1, acc.getUser_id());
            pstmt.setString(2, acc.getName());
            pstmt.setString(3, acc.getEmail());
            pstmt.setDouble(4, acc.getWallet_balance());
            pstmt.setString(5, acc.getAddress());
            pstmt.setBlob(6, acc.getPhoto());
            insertRows = pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return insertRows;
    }

    @Override
    public void fetch_details(int user_id) {
        String selectQuery = "select * from user_account where user_id=?";
        try (Connection con = OracleCon.getConnection()) {
            PreparedStatement pstmt = con.prepareStatement(selectQuery);
            pstmt.setInt(1, user_id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    int id = rs.getInt("user_id");
                    String username = rs.getString("name");
                    String email = rs.getString("email");
                    double balance = rs.getDouble("wallet_balance");
                    // Display other columns as needed
                    System.out.println("User ID: " + id);
                    System.out.println("Username: " + username);
                    System.out.println("Email: " + email);
                    System.out.println("Wallet balance : " + balance);
                    Blob photoBlob = rs.getBlob("photo");
                    //Creates a File object pointing to the directory where the photo will be saved.
                    File photoDir = new File("C:\\Users\\ambar\\OneDrive\\Desktop\\my folder\\photos\\downloaded\\user_account");
                    //If this directory does not exist, it creates the directory and any necessary parent directories.
                    if (!photoDir.exists()) photoDir.mkdirs();
                    File photoFile = new File(photoDir, "user_id" + user_id + ".jpg");
                    try (InputStream binaryStream = photoBlob.getBinaryStream(); FileOutputStream outputStream = new FileOutputStream(photoFile);) {

                        byte[] allBytes = binaryStream.readAllBytes();
                        outputStream.write(allBytes);
                        System.out.println("photo successfully saved: " + photoFile.getAbsolutePath());
                    }
                } else {
                    System.out.println("no user found with user_id" + user_id);
                }

            }
        } catch (SQLException | IOException e) {
            throw new RuntimeException("Error fetching customer details ", e);
        }
    }

    @Override
    public void deleteUser(int user_id) {
        Connection con = OracleCon.getConnection();
        String query = "delete from user_account where user_id=?";
        try {
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setInt(1, user_id);
            int deleteRow = pstmt.executeUpdate();
            if (deleteRow > 0) {
                System.out.println(deleteRow + " rows deleted");
            } else {
                System.out.println("No rows deleted");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void add_money(int user_id, double amount) {

        Connection con = OracleCon.getConnection();
        String query = "update user_account set wallet_balance=wallet_balance+? where user_id=?";
        try {
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setDouble(1, amount);
            pstmt.setInt(2, user_id);
            int addMoney = pstmt.executeUpdate();
            if (addMoney > 0) {
                System.out.println(amount + " is added to wallet of " + user_id);
            } else {
                System.out.println("Failed to add money");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public double purchaseProduct(int user_id, double price) {
        Connection con = OracleCon.getConnection();
        double updatedBalance = 0;
        String selectQuery = "select wallet_balance from user_account where user_id=?";
        String updateQuery = "update user_account set wallet_balance= wallet_balance-? where user_id=?";
        try {
            PreparedStatement selectPstmt = con.prepareStatement(selectQuery);
            selectPstmt.setInt(1, user_id);
            ResultSet rs = selectPstmt.executeQuery();
            if (rs.next()) {
                double currentBalance = rs.getDouble("wallet_balance");
                if (currentBalance >= price) {
                    PreparedStatement upPstmt = con.prepareStatement(updateQuery);
                    upPstmt.setDouble(1, price);
                    upPstmt.setInt(2, user_id);
                    int purchase = upPstmt.executeUpdate();
                    rs.close();
                    upPstmt.close();
                    if (purchase > 0) {
                        updatedBalance = currentBalance - price;
                        System.out.println("\uD83D\uDCB0 Product Purchased Successfully!");
                    } else {
                        System.out.println("Insufficient Balance");
                    }
                }
            } else {
                System.out.println("user not found");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return updatedBalance;
    }

    @Override
    public double viewWalletBalance(int user_id) {
        double balance = 0;
        Connection con = OracleCon.getConnection();
        String selectQuery = "select wallet_balance from user_account where user_id=?";
        try (PreparedStatement pstmt = con.prepareStatement(selectQuery)) {
            pstmt.setInt(1, user_id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                balance = rs.getDouble("wallet_balance");

            } else {
                System.out.println("user not found this id " + user_id);
                return -1;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return balance;
    }

    @Override
    public void updatePhoto(int user_id, Blob photo) {
        Connection con = OracleCon.getConnection();
        String updateQuery = "update user_account set photo =? where user_id=?";
        try (PreparedStatement pstmt = con.prepareStatement(updateQuery)) {
            pstmt.setBlob(1, photo);
            pstmt.setInt(2, user_id);
            int rowsUpdated = pstmt.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Photo updated successfully for user : " + user_id);
            } else {
                System.out.println("No user_id found with this id " + user_id);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
