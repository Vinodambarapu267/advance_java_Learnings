package com.jdbc.lab_tasks.employee_management_system;

import java.sql.*;
import java.util.Scanner;
import java.util.concurrent.Callable;

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
        public static int delete(String  name){
            int deleteRows;
            try {

                Class.forName(driverName);
                Connection con= DriverManager.getConnection(url,userName,pass);
                String deleteQuery ="delete employee where name=?";
                PreparedStatement deletePstmt= con.prepareStatement(deleteQuery);
                deletePstmt.setString(1,name);
                deleteRows = deletePstmt.executeUpdate();
            } catch (ClassNotFoundException |SQLException e) {
                throw new RuntimeException(e);
            }
            return deleteRows;
        }
        public static void displayDetails(Employee emp){
            try {
                Class.forName(driverName);
                Connection con = DriverManager.getConnection(url,userName,pass);
                String  displayDetails = """
                        select id, name, salary, department from employee 
                        order by id""";
              Statement stmt = con.createStatement();

              ResultSet rs = stmt.executeQuery(displayDetails);
              ResultSetMetaData rsmd  = rs.getMetaData();
              int count=0;
              if (rs.next()){
                  String frmS = "";
                  for (int i = 1; i <= rsmd.getColumnCount(); i++) {
                      if (rsmd.getColumnTypeName(i).contains("var")) {
                          frmS = "%" + Math.max(rsmd.getColumnDisplaySize(i), rsmd.getPrecision(i)) + "s";
                      } else {
                          frmS = "%-" + Math.max(rsmd.getColumnName(i).length(), rsmd.getPrecision(i)) + "s ";
                      }
                      System.out.printf(frmS, rsmd.getColumnName(i));

                  }System.out.println();
                  System.out.println("-".repeat(rsmd.getPrecision(1)) + " " + "-".repeat(rsmd.getPrecision(2)) + " " + "-".repeat(rsmd.getPrecision(3)) + " " + "-".repeat(rsmd.getColumnName(4).length()));
                  do {
                      for (int i = 1; i <= rsmd.getColumnCount(); i++) {
                          if (rsmd.getColumnTypeName(i).contains("var")) {
                              frmS = "%" + Math.max(rsmd.getColumnDisplaySize(i), rsmd.getPrecision(i)) + "s";
                          } else {
                              frmS = "%-" + Math.max(rsmd.getColumnName(i).length(), rsmd.getPrecision(i)) + "s ";
                          }
                          System.out.printf(frmS, rs.getString(i));
                      }
                      System.out.println();
                      count++;
                  } while (rs.next());
                  System.out.println("\n " + count + " rows selected");
                  System.out.println("✅ View all stored employees\n");
              } else {
                  System.out.println(" no rows selected");
              }
            } catch (ClassNotFoundException | SQLException e) {
                throw new RuntimeException(e);
            }

        }
    }

