package com.jdbc.practice;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Test_04_insertingRows {
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        Class.forName("oracle.jdbc.driver.OracleDriver");
        System.out.println("driver loaded");
        Connection connection= DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl","advanceJava49","vinod267");
        System.out.println("connection is established");
        Statement stmt = connection.createStatement();
        System.out.println("Stmt created");


        stmt.execute("insert into course(course_id,course_name,course_fee) values(course_seq.nextval,'Oracle',3500)");
        stmt.execute("insert into course(course_id,course_name,course_fee) values(course_seq.nextval,'html css bs',2500)");
        stmt.execute("insert into course(course_id,course_name,course_fee) values(course_seq.nextval,'SpringBoot',9000)");
        stmt.execute("insert into course(course_id,course_name,course_fee) values(course_seq.nextval,'core java',4000)");
//        System.out.println("inserted rows in course table");

        stmt.execute("insert into Student(student_id,student_name,course_id) values(student_seq.nextval,'Vinod',(SELECT course_id FROM course WHERE course_name = 'Oracle'))");
        stmt.execute("insert into Student(student_id,student_name,course_id) values(student_seq.nextval,'rajesh',(SELECT course_id FROM course WHERE course_name = 'core java'))");
        stmt.execute("insert into Student(student_id,student_name,course_id) values(student_seq.nextval,'Himagiri',(SELECT course_id FROM course WHERE course_name = 'SpringBoot'))");
        stmt.execute("insert into Student(student_id,student_name,course_id) values(student_seq.nextval,'ram',(SELECT course_id FROM course WHERE course_name = 'html css bs'))");
//        System.out.println("inserted rows in student table");

    }
}
