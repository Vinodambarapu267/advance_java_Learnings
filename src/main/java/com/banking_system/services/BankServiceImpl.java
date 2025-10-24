package com.banking_system.services;

import com.banking_system.model.BankCustomer;
import com.connection.OracleCon;
import oracle.jdbc.proxy.annotation.Pre;

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
    public void deleteCustomer(long accNumber) {
        Connection con= OracleCon.getConnection();
        String  deleteQuery = "delete from bank where account_no=? ";
        try {
            PreparedStatement  pstmt = con.prepareStatement(deleteQuery);
            pstmt.setLong(1,accNumber);
           int deleteRows = pstmt.executeUpdate();
           if (deleteRows>0){
               System.out.println(deleteRows+" rows deleted");
           }else {
               System.out.println("no row deleted");
           }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }

    @Override
    public double depositAmount(long accNum, double amount) {
        Connection con = OracleCon.getConnection();

        double newBalance=0.0;
        String selectQuery = "select balance from bank where account_no=?";
        String query = "update bank set balance=balance+? where account_no=?";
       try{
           PreparedStatement selectPsmt= con.prepareStatement(selectQuery);
           PreparedStatement depositPstmt = con.prepareStatement(query);
           selectPsmt.setLong(1,accNum);
           ResultSet rs = selectPsmt.executeQuery();
           if (rs.next()){
               double currentBalance = rs.getDouble("balance");
               depositPstmt.setDouble(1,amount);
               depositPstmt.setLong(2,accNum);
               depositPstmt.executeUpdate();
               newBalance = currentBalance+amount;
           }
       } catch (SQLException e) {
           throw new RuntimeException(e);
       }
        return newBalance;
    }

    @Override
    public double withdraw(long accNum, double amount) {
        Connection con = OracleCon.getConnection();

        String selectQuery = "SELECT balance FROM bank WHERE account_no = ?";

        try (PreparedStatement pstmt = con.prepareStatement(selectQuery)) {
            pstmt.setLong(1, accNum);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    double currentBalance = rs.getDouble("balance");
                    if (currentBalance < amount) {
                        System.out.println("Insufficient balance: " + currentBalance);
                        return currentBalance;
                    }
                    double newBalance = currentBalance - amount;
                    String updateQuery = "UPDATE bank SET balance = ? WHERE account_no = ?";
                    try (PreparedStatement upStmt = con.prepareStatement(updateQuery)) {
                        upStmt.setDouble(1, newBalance);
                        upStmt.setLong(2, accNum);
                        int rows = upStmt.executeUpdate();
                        if (rows > 0) {
                            System.out.println("Withdraw successful. New balance: " + newBalance);
                            return newBalance;
                        }
                    }
                    System.out.println("Withdraw failed: No rows updated for account " + accNum);
                    return currentBalance;
                }
                System.out.println("Account not found: " + accNum);
                return -1;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error during withdrawal for account: " + accNum, e);
        }
    }
    @Override
    public double viewBalance(long accNumber) {
        double output = 0;
        Connection con = OracleCon.getConnection();
        String query = "select balance from bank where account_no=?";
        try (PreparedStatement pst = con.prepareStatement(query);) {
            pst.setLong(1, accNumber);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    output = rs.getDouble("balance");

                } else {
                    System.out.println("No account found with number" + accNumber);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error retrieving balance for account: " + accNumber, e);
        }

        return output;
    }

    @Override
    public void updatePhoto(Blob photo, long accountNumber) {
        Connection con = OracleCon.getConnection();
        String updateQuery = "update bank set photo=? where account_no=?";
        try (PreparedStatement updatePstmt = con.prepareStatement(updateQuery)) {
            updatePstmt.setBlob(1, photo);
            updatePstmt.setLong(2, accountNumber);
            int rowsUpdated = updatePstmt.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Photo updated successfully for account: " + accountNumber);
            } else {
                System.out.println("no customer not found with this Account number" + accountNumber);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error updating photo for account " + accountNumber, e);
        }
    }

    @Override
    public void transferMoney(long senderAccountNum, long receiverAccount,double amount) {
        Connection con = OracleCon.getConnection();

        String  selectQuery = "select BALANCE from bank where ACCOUNT_NO=?";
        String debitQuery ="update bank set BALANCE=BALANCE-? where ACCOUNT_NO=?";
        String depositQuery = "update bank set BALANCE=BALANCE+? where ACCOUNT_NO=?";
        try {
            con.setAutoCommit(false);
            PreparedStatement ps1=con.prepareStatement(selectQuery);
            ps1.setLong(1,senderAccountNum);
          ResultSet rs  =ps1.executeQuery();
            if (rs.next()){
                double currentBalance = rs.getDouble("BALANCE");
                if (!(currentBalance >= amount)) {
                    System.out.println("insufficient Balance");
                }else {
                    PreparedStatement ps2 = con.prepareStatement(debitQuery);
                    ps2.setLong(2,senderAccountNum);
                    ps2.setDouble(1,amount);
                   int debited =  ps2.executeUpdate();
                   if (debited>0){
                       PreparedStatement ps3 = con.prepareStatement(depositQuery);
                       ps3.setDouble(1,amount);
                       ps3.setLong(2,receiverAccount);
                      int deposit =  ps3.executeUpdate();
                       if (deposit>0){
                           con.commit();
                           System.out.println("Transaction successful");
                       }else {
                           System.out.println("Transaction failed");
                           con.rollback();
                       }
                   }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
