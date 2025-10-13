package com.jdbc.lab_tasks.employee_management_system;

import java.util.Scanner;

public class Test {
    static Scanner sc = new Scanner(System.in);
static  Employee ref;
    public static void main(String[] args) {
        while (true){
            System.out.println("===== EMPLOYEE MANAGEMENT SYSTEM =====\n" +
                    "1. Add Employee\n" +
                    "2. Update Employee details\n" +
                    "3. Delete Employee\n" +
                    "4. View All Employees\n" +
                    "5. Exit");
            int choice = sc.nextInt();
            sc.nextLine();
            switch (choice) {
                case 1 ->Test.insert();
                case 2->Test.update();
                case 3->Test.delete();
                case 4->EmployeeServices.displayDetails(ref);
                case 5->{
                    System.out.println("=".repeat(25)+"Thank you!visit Again!!!"+"=".repeat(25));
                        System.exit(0);
                }
                default -> throw new RuntimeException("invalid options");
            }
        }
    }

    public static void insert() {
        int insertRows;
        System.out.println("Enter name");
        String name = sc.nextLine();
        System.out.println("Enter salary");
        double salary = Double.parseDouble(sc.nextLine());
        System.out.println("Enter dept");
        String dept = sc.nextLine();
        Employee emp = new Employee(name, salary, dept);
        insertRows = EmployeeServices.insert(emp);
        if (insertRows > 0) {
            System.out.println(insertRows + " rows inserted");
            System.out.println("✅ Add new employees");
        } else {
            System.out.println("no rows inserted");
        }
    }

    public  static void update(){
        int updateRows;
        System.out.println("Enter  name ");
        String  name = sc.nextLine();
        updateRows = EmployeeServices.update(name);
        if (updateRows>0){
            System.out.println(updateRows+" rows updated");
            System.out.println("✅ Update an existing employee’s");
        }
        else {
            System.out.println("no rows updated");
        }
    }

    public static  void delete(){
        int deleteRows;
        System.out.println("Enter name: ");
        String name = sc.nextLine();
        deleteRows= EmployeeServices.delete(name);
        if (deleteRows>0){
            System.out.println(deleteRows+" rows deleted");
            System.out.println("✅ Delete specific employees");
        }else {
            System.out.println("No rows deleted");
        }
    }
}

