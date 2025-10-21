package com.cab_customer_billing_system.services;

import com.connection.OracleCon;
import com.cab_customer_billing_system.model.CabCustomer;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CabCustomerServicesImpl implements CabCustomerServices {
    public void addCustomer(CabCustomer ref) {
        Connection con = OracleCon.getConnection();
        CabCustomer cust;
        String insertQuery = "insert into cabBilling(id,customerName,source,destination,distance,phoneNo) values(cust_seq.nextVal,?,?,?,?,?)";
        try {
            PreparedStatement insertPstmt = con.prepareStatement(insertQuery);
            insertPstmt.setString(1, ref.getCustomerName());
            insertPstmt.setString(2, ref.getSource());
            insertPstmt.setString(3, ref.getDestination());
            insertPstmt.setDouble(4, ref.getDistance());
            insertPstmt.setLong(5, ref.getPhoneNo());
            int insertRows = insertPstmt.executeUpdate();
            if (insertRows > 0) {
                System.out.println(insertRows + " rows inserted");
            } else {
                System.out.println(" no rows inserted");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public boolean isFirstCustomer(CabCustomer ref) {
        Connection con = OracleCon.getConnection();
        String query = "SELECT phoneNo FROM cabBilling WHERE phoneNo = ?";
        try (PreparedStatement pstmt = con.prepareStatement(query)) {
            pstmt.setLong(1, ref.getPhoneNo());
            try (ResultSet rs = pstmt.executeQuery()) {
                return !rs.next(); // Returns true if customer not found (first time)
            }
        } catch (SQLException e) {
            throw new RuntimeException("Database error checking customer", e);
        }
    }

    public double calculateBill(CabCustomer ref) {
        double fare = 0.0;
        if (isFirstCustomer(ref)) {
            return fare;
        } else if (ref.getDistance() <= 1 && ref.getDistance() <= 5) {
           fare = ref.getDistance() * 40;
        } else {
            fare = ref.getDistance() * 50;
        }
        return fare;
    }
    @Override
    public void printBill(CabCustomer ref) {
        if (ref == null) {
            System.out.println(" Cannot print bill. BankCustomer data is missing.");
            return;
        }

        double fare = calculateBill(ref);
        System.out.println(ref.getCustomerName() + " covered a distance of " + ref.getDistance() +
                " km from " + ref.getSource() + " to " + ref.getDestination() +
                ". Your bill is ₹" + fare);
        if (fare == 0) {
            System.out.println("[ as this is a first-time usage ]");
        }



    }

}