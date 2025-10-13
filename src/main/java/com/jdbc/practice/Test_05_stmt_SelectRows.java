package com.jdbc.practice;



import java.sql.*;

public class Test_05_stmt_SelectRows {
    public static void main(String[] args) throws ClassNotFoundException,SQLException {

           Class.forName("oracle.jdbc.driver.OracleDriver");
           Connection connection= DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl",
                   "advanceJava49","vinod267");
           System.out.println("connection is created");
           Statement stmt= connection.createStatement();
           System.out.println("Statement is created");
           String selectQuery = """
            Select course_id,course_name,course_fee from course
            order by course_id
           """;
           ResultSet rs= stmt.executeQuery(selectQuery);
           ResultSetMetaData rsmd = rs.getMetaData();
           int count=0;
           if(rs.next()) {
               for (int i = 1; i <= rsmd.getColumnCount(); i++){
                   System.out.printf("%-20s", rsmd.getColumnName(i));
           }
               System.out.println();
               System.out.println("-".repeat(50));
               do{
                  for (int i=1;i<=rsmd.getColumnCount();i++){
                      System.out.printf("%-20s",rs.getString(i));
                  }
                   System.out.println();
                   count++;
               } while (rs.next());
               System.out.println(count+" rows selected");
           }else
               System.out.println("no rows selected");
           rs.close();
           stmt.close();
           connection.close();
    }
}
