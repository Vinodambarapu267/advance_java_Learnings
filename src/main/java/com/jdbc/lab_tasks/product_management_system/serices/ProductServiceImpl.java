package com.jdbc.lab_tasks.product_management_system.serices;

import java.sql.*;
import java.util.Scanner;


public class ProductServiceImpl {
    static Scanner sc = new Scanner(System.in);
    private static final String driverName = "oracle.jdbc.driver.OracleDriver";
    private static final String url = "jdbc:oracle:thin:@localhost:1521:orcl";
    private static final String username = "advanceJava49";
    private static final String pass = "vinod267";

    public static int insertData(Product product) {
        int result;
        try {

            Class.forName(driverName);
            Connection con = DriverManager.getConnection(url, username, pass);
            String insertQuery = "insert into product(id,productName,price,quantity) values(?,?,?,?)";
            PreparedStatement pstmt = con.prepareStatement(insertQuery);
            pstmt.setInt(1, product.getId());
            pstmt.setString(2, product.getProductName());
            pstmt.setDouble(3, product.getProductPrice());
            pstmt.setInt(4, product.getQuantity());
            result = pstmt.executeUpdate();
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }

        return result;
    }

    public static int update(String currentName) {
        int result = 0;
        try {
            Class.forName(driverName);
            Connection con = DriverManager.getConnection(url, username, pass);
            System.out.println("Select field to update:\n1. name\n2. cost\n3. quantity");
            int choice = sc.nextInt();
            switch (choice) {
                case 1 -> {
                    System.out.print("Enter new name: ");
                    String newName = sc.next();
                    String nameUpdateQuery = "update product set PRODUCTNAME=?where PRODUCTNAME =?";
                    PreparedStatement pstmt = con.prepareStatement(nameUpdateQuery);
                    pstmt.setString(1, newName);
                    pstmt.setString(2, currentName);
                    result = pstmt.executeUpdate();
                }
                case 2 -> {
                    System.out.println("Enter new price : ");
                    double cost = Double.parseDouble(sc.next());
                    String priceUpdateQuery = "update product set price=? where PRODUCTNAME =?";
                    PreparedStatement pstmt = con.prepareStatement(priceUpdateQuery);
                    pstmt.setString(2, currentName);
                    pstmt.setDouble(1, cost);
                    result = pstmt.executeUpdate();
                }
                case 3 -> {
                    System.out.println("Enter new quantity:  ");
                    int quantity = Integer.parseInt(sc.next());
                    String priceUpdateQuery = "update product set QUANTITY = ? where PRODUCTNAME =?";
                    PreparedStatement pstmt = con.prepareStatement(priceUpdateQuery);
                    pstmt.setString(2, currentName);
                    pstmt.setDouble(1, quantity);
                    result = pstmt.executeUpdate();
                }
            }

        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    public static int delete(String name) {
        int delete;
        try {
            Class.forName(driverName);
            Connection con = DriverManager.getConnection(url, username, pass);

            String deleteQuery = "delete from product where productname=?";
            PreparedStatement pstmt = con.prepareStatement(deleteQuery);
            pstmt.setString(1, name);
            delete = pstmt.executeUpdate();
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }


        return delete;
    }

    public static void displayAllProducts(Product product) {
        try {
            Class.forName(driverName);
            Connection con = DriverManager.getConnection(url, username, pass);
            Statement st = con.createStatement();
            String selectQuery = """
                    select id,productname,price,quantity from product
                    order by id""";
            ResultSet rs = st.executeQuery(selectQuery);
            ResultSetMetaData rsmd = rs.getMetaData();
            int count = 0;
            if (rs.next()) {
                String frmS = "";
                for (int i = 1; i <= rsmd.getColumnCount(); i++) {
                    if (rsmd.getColumnTypeName(i).contains("var")) {
                        frmS = "%" + Math.max(rsmd.getColumnDisplaySize(i), rsmd.getPrecision(i)) + "s";
                    } else {
                        frmS = "%-" + Math.max(rsmd.getColumnName(i).length(), rsmd.getPrecision(i)) + "s ";
                    }
                    System.out.printf(frmS, rsmd.getColumnName(i));

                }
                System.out.println();
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
            } else {
                System.out.println(" no rows selected");
            }
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
