package com.jdbc.practice;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class test_03_sequenceCreation {
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        Class.forName("oracle.jdbc.driver.OracleDriver");
        System.out.println("driver loaded");
        Connection connection= DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl","advanceJava49","vinod267");
        System.out.println("connection is established");
        Statement stmt = connection.createStatement();
        System.out.println("Stmt created");

        String courseSeqTableQuery= """
                create sequence course_seq
                start with 1
                increment by 1
                """;
        stmt.execute(courseSeqTableQuery);
        String studentSeqTableSeqQuery= """
                create sequence student_seq
                start with 101
                increment by 1
                """;
        stmt.execute(studentSeqTableSeqQuery);
        System.out.println("sequence is created");
    }
}
