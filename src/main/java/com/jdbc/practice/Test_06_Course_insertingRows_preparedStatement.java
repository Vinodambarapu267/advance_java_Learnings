package com.jdbc.practice;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class Test_06_Course_insertingRows_preparedStatement {
    public static void main(String[] args) {
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl", "advanceJava49", "vinod267");
            String insertQuery = "insert into course(cid,course_name,course_fee) values(course_seq.nextval,?,?)";
            PreparedStatement pstmt = con.prepareStatement(insertQuery);
            Scanner sc = new Scanner(System.in);
            String choice = "N";
            int insertedRows;
            do {
                System.out.println("Enter a course name: ");
                String courseName = sc.nextLine();
                System.out.println("Enter a course fee");
                double fee = sc.nextDouble();
                sc.nextLine();
                pstmt.setString(1, courseName);
                pstmt.setDouble(2, fee);
                insertedRows = pstmt.executeUpdate();
                System.out.println(insertedRows + " Row Inserted");
                System.out.println("Do you want to continue");
                choice = sc.nextLine();

            } while (choice.equalsIgnoreCase("Y"));

        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
