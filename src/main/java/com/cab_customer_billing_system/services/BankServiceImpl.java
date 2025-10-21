package com.cab_customer_billing_system.services;

import com.cab_customer_billing_system.model.BankCustomer;
import com.connection.OracleCon;

import java.io.*;
import java.sql.*;

public class BankServiceImpl implements BankService {
    @Override
    public void addCustomer(BankCustomer cust) {
        Connection con = OracleCon.getConnection();
        String insertQuery = "insert into bank(account_no,name,aadhar_no,bank_name,balance,address,photo) values(?,?,?,?,?,?,?)";
        try {
            PreparedStatement pstmt = con.prepareStatement(insertQuery);
            pstmt.setLong(1, cust.getAccountNumber());
            pstmt.setString(2, cust.getAccountName());
            pstmt.setDouble(3, cust.getAadhaarNumber());
            pstmt.setString(4, cust.getBankName());
            pstmt.setDouble(5, cust.getBalance());
            pstmt.setString(6, cust.getAddress());
            pstmt.setBlob(7, cust.getPhoto());
            int insertRows = pstmt.executeUpdate();
            if (insertRows > 0) {
                System.out.println(insertRows + "rows inserted");
            } else {
                System.out.println("no rows inserted");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void fetchCustomerDetails(long accountNumber) {

        String query = "SELECT * FROM bank WHERE account_no = ?";
        try (Connection con = OracleCon.getConnection();
             PreparedStatement pstmt = con.prepareStatement(query)) {
            pstmt.setLong(1, accountNumber);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) { // Navigate to the first row

                    Blob photoBlob = rs.getBlob("photo"); // Column 7

                    // Save photo to file
                    File photoDir = new File("C:\\Users\\ambar\\OneDrive\\Desktop\\my folder\\photos\\downloaded");
                    if (!photoDir.exists()) photoDir.mkdirs();

                    File photoFile = new File(photoDir, "account_" + accountNumber + ".jpg");
                    try (InputStream binaryStream = photoBlob.getBinaryStream();
                         FileOutputStream outputStream = new FileOutputStream(photoFile)) {
                        byte[] allBytes = binaryStream.readAllBytes();
                        outputStream.write(allBytes);
                        System.out.println("Photo successfully saved: " + photoFile.getAbsolutePath());
                    }
                } else {
                    System.out.println("No customer found with account number: " + accountNumber);
                }
            }
        } catch (SQLException | IOException e) {
            throw new RuntimeException("Error fetching customer details", e);
        }
    }
    @Override
    public void deleteCustomer(BankCustomer cust) {

    }

    @Override
    public double depositAmount(BankCustomer cust) {
        return 0;
    }

    @Override
    public double withdraw(BankCustomer cust) {
        return 0;
    }

    @Override
    public double viewBalance(long accNumber ,double balance) {
        return 0;
    }

    @Override
    public Blob updatePhoto(Blob photo,long accountNumber) {

        return null;
    }
}
