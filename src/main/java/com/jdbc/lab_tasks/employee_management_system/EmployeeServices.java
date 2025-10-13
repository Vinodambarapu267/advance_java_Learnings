package com.jdbc.lab_tasks.employee_management_system;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

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
        public static int update(String currentName ){
            int updateRows = 0;
            try (Scanner sc = new Scanner(System.in);){

                Class.forName(driverName);
                Connection con = DriverManager.getConnection(url,userName,pass);
                System.out.println("1. name update\n2. salary update\n3. department update");
                int choice= sc.nextInt();
                sc.nextLine();
               switch (choice){
                   case 1-> {
                       System.out.println("Enter new name : ");
                       String name = sc.nextLine();
                       String updateNameQuery = "update employee set name=? where name=?";
                       PreparedStatement namePstmt = con.prepareStatement(updateNameQuery);
                       namePstmt.setString(1,name);
                       namePstmt.setString(2,currentName);
                       updateRows = namePstmt.executeUpdate();
                   }
                   case 2->
                   {
                       System.out.println("Enter new salary: ");
                       double cost = sc.nextDouble();
                       String updateSalaryQuery = "update employee set salary=? where name=?";
                       PreparedStatement salaryPstmt = con.prepareStatement(updateSalaryQuery);
                       salaryPstmt.setDouble(1,cost);
                       salaryPstmt.setString(2,currentName);
                       updateRows = salaryPstmt.executeUpdate();
                   }
                   case 3->{
                       System.out.println("Enter a new Department");
                       String department = sc.nextLine();
                       String updateDeptQuery = "update employee set department=? where name =? ";
                       PreparedStatement deptPstmt = con.prepareStatement(updateDeptQuery);
                       deptPstmt.setString(1,department);
                       deptPstmt.setString(2,currentName);
                       updateRows = deptPstmt.executeUpdate();
                   }

               }
            } catch (ClassNotFoundException |SQLException e) {
                throw new RuntimeException(e);
            }

            return updateRows;
        }
        public int delete(Employee emp){
            return 0;
        }
        public void displayDetails(Employee emp){

        }
    }

