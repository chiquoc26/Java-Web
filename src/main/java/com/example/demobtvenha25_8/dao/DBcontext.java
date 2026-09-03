package com.example.demobtvenha25_8.dao;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBcontext {

    // Dùng named instance SQLEXPRESS (không cần cấu hình TCP/IP)
    private static final String DB_NAME = "demo_db";
    private static final String USERNAME = "sa";
    private static final String PASSWORD = "123456";

    private static final String URL = "jdbc:sqlserver://localhost:1433"
            + ";databaseName=" + DB_NAME
            + ";encrypt=false;trustServerCertificate=true";

    public static Connection getConnection() {
        Connection conn = null;
        try {
            // Nạp Driver SQL Server
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");

            // Mở kết nối
            conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (Exception e) {
            System.err.println("Lỗi kết nối CSDL Local: " + e.getMessage());
            e.printStackTrace();
        }
        return conn;
    }

    // Hàm main để test thử kết nối trực tiếp trong IntelliJ
    public static void main(String[] args) {
        Connection testConn = getConnection();
        if (testConn != null) {
            System.out.println("-> Kết nối SQL Server Local THÀNH CÔNG!");
        } else {
            System.out.println("-> Kết nối THẤT BẠI! Hãy kiểm tra lại username/password hoặc SQL Server Service.");
        }
    }
}