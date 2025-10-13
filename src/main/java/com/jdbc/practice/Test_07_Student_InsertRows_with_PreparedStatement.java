package com.jdbc.practice;

import java.sql.*;
import java.util.Scanner;

public class Test_07_Student_InsertRows_with_PreparedStatement {
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        Class.forName("oracle.jdbc.driver.OracleDriver");
        Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl", "advanceJava49", "vinod267");
        StringBuilder courseNameBuilder = new StringBuilder();
        try (Statement stmt = con.createStatement();
             ResultSet courseRs = stmt.executeQuery("""
                     select course_name from course order by cid
                     """);
        ) {
            while (courseRs.next()) {
                courseNameBuilder.append(courseRs.getString(1));
                courseNameBuilder.append("!");
                if (courseNameBuilder.isEmpty()) {
                    System.out.println("\u0010[31m Course table is empty, add course in course table then student");
                    return;
                }
            }
        }
        String insertQuery = "insert into student(sId,sname,course_name,fee) values(student_seq.nextVal,?,?,?)";
        String selectCourseNameQuery = """
                select course_name from course 
                where COURSE_NAME=?
                """;
        String selectFeeQuery = """
                select COURSE_FEE from course 
                where COURSE_NAME=?
                """;
        PreparedStatement insertPstmt = con.prepareStatement(insertQuery);
        PreparedStatement selectCourseNamePstmt = con.prepareStatement(selectCourseNameQuery);
        PreparedStatement selectFeePstmt = con.prepareStatement(selectFeeQuery);
        Scanner sc = new Scanner(System.in);
        String choice = "N";
        do {
            System.out.println("Enter the student Name: ");
            String sname = sc.nextLine().trim();
            String course_name;
          double fee=0;
            while (true) {
                System.out.println("Enter the course_name: ");
                String courseName = sc.nextLine();
                selectCourseNamePstmt.setString(1, courseName);
                selectFeePstmt.setString(1, courseName);
                ResultSet rs = selectCourseNamePstmt.executeQuery();
                ResultSet rsf = selectFeePstmt.executeQuery();
                if (rs.next()) {
                    course_name = rs.getString(1);
                    if (rsf.next()){
                        fee=rsf.getDouble(1);
                    }else {
                        System.out.println("Fee :not found course  setting to 0;");
                    }
                    break;
                } else {
                    System.out.println("you Enter course_id not from course ");
                    System.out.println("choice one course from the below list");
                    System.out.println(courseNameBuilder);
                    System.out.println();
                }
            }
            insertPstmt.setString(1, sname);
            insertPstmt.setString(2, course_name);
            insertPstmt.setDouble(3,fee);
            int rowsInserted = insertPstmt.executeUpdate();
            System.out.println(rowsInserted + " row inserted");
            System.out.println("Do yoy want to continue");
            choice = sc.nextLine();
        } while (choice.equalsIgnoreCase("y"));
        System.out.println("================Thank you ================");
        insertPstmt.close();
        con.close();
    }
}
