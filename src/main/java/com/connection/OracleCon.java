package com.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class OracleCon {
    private  static final String  driverName= "oracle.jdbc.driver.OracleDriver";
    private static final String url = "jdbc:oracle:thin:@localhost:1521:orcl";
    private static final String userName="advanceJava49";
    private  static final String pass ="vinod267";
    private static Connection con =null;
    private OracleCon(){
    }
    public static Connection getConnection(){
        if (con==null){
            try {
                Class.forName(driverName);
                con = DriverManager.getConnection(url,userName,pass);
            } catch (ClassNotFoundException | SQLException e) {
                throw new RuntimeException(e);
            }

        }
        return con;
    }
}
