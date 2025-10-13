package com.jdbc.lab_tasks.employee_management_system;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class EmployeeServices {
        private static final String driverName ="oracle.jdbc.driver.OracleDriver";
        private static final String url ="jdbc:oracle:thin:@localhost:1521:orcl";
        private static final String userName = "advanceJava49";
        private static final String pass = "vinod267";
        public  static  int insert(Employee emp ){
            int result;
            try {

                Class.forName(driverName);
                Connection con = DriverManager.getConnection(url,userName,pass);
                String insertQuery= "insert into employee(id,name,salary,department) values(emp_seq.nextVal,?,?,?)";
                PreparedStatement pstmt= con.prepareStatement(insertQuery);
                pstmt.setString(1,emp.getName());
                pstmt.setDouble(2,emp.getSalary());
                pstmt.setString(3,emp.getDepartment());
                Thread.sleep(1000);
                result = pstmt.executeUpdate();

            } catch (SQLException | ClassNotFoundException | InterruptedException e) {
                throw new RuntimeException(e);
            }
            return  result;
        }
        public int update(String name ){
            return 0;
        }
        public int delete(Employee emp){
            return 0;
        }
        public void displayDetails(Employee emp){

        }
    }

