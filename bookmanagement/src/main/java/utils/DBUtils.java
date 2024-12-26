/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Ato
 */
public class DBUtils {
    private final static String DB_NAME = "BookManagement";
    private final static String DB_USERNAME = "sa";
    private final static String DB_PASSWORD = "12345";

    public static Connection makeConnection() throws ClassNotFoundException, SQLException {
        Connection con = null;
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        String url = "jdbc:sqlserver://localhost:1433;databaseName=" + DB_NAME + ";encrypt=false;trustServerCertificate=true";
        con = DriverManager.getConnection(url, DB_USERNAME, DB_PASSWORD);
        return con;
    }
}
