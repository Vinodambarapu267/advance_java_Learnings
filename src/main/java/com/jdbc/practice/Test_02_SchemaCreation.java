package com.jdbc.practice;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Test_02_SchemaCreation {
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        Class.forName("oracle.jdbc.driver.OracleDriver");
        System.out.println("Driver is loaded");
        Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl","advanceJava49","vinod267");
        System.out.println("Connection established");
        Statement stmt = con.createStatement();
        System.out.println("Statement Created");
//        String courseTableQuery= """
//                create table course(
//                cid Number(4) primary key,
//                course_name varchar2(15) unique not null,
//                course_fee number(7,2)
//
//                )
//                """;
//        stmt.execute(courseTableQuery);
        System.out.println("course table is created");
        String studentTableQuery="""
                CREATE TABLE Student (
                    sid NUMBER(3) PRIMARY KEY,
                    sname VARCHAR2(20) UNIQUE NOT NULL,
                    course_name varchar2(30) REFERENCES course(course_name),
                    fee NUMBER(7,2)
                                     )""";
        stmt.execute(studentTableQuery);
        System.out.println("student table is created");
//        stmt.execute("create sequence student_seq start with 101 increment by 1");
//        System.out.println("student seq");
//        stmt.execute("create sequence course_seq start with 1 increment by 1");
//        System.out.println("course seq");
        stmt.close();
        con.close();
    }
}
