package com.cab_customer_billing_system.services;

import com.cab_customer_billing_system.model.BankCustomer;
import com.connection.OracleCon;

import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class BankServiceImpl implements BankService{
    @Override
    public void addCustomer(BankCustomer cust) {
        Connection con = OracleCon.getConnection();
        String insertQuery = "insert into bank(account_no,name,aadhar_no,bank_name,balance,address,photo) values(?,?,?,?,?,?,?)";
        try {
            PreparedStatement pstmt =con.prepareStatement(insertQuery);
            pstmt.setLong(1,cust.getAccountNumber());
            pstmt.setString(2,cust.getAccountName());
            pstmt.setDouble(3,cust.getAadhaarNumber());
            pstmt.setString(4,cust.getBankName());
            pstmt.setDouble(5,cust.getBalance());
            pstmt.setString(6,cust.getAddress());
            pstmt.setBlob(7,cust.getPhoto());
            int insertRows= pstmt.executeUpdate();
            if (insertRows>0){
                System.out.println(insertRows+"rows inserted");
            }else {
                System.out.println("no rows inserted");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void fetchCustomerDetails(BankCustomer cust) {

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
    public double viewBalance(BankCustomer cust) {
        return 0;
    }

    @Override
    public Blob updatePhoto(BankCustomer cust) {
        return null;
    }
}
