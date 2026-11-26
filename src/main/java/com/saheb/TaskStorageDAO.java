package com.saheb;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class TaskStorageDAO {
    private static final String url = "jdbc:mysql://localhost:3306/taskDB";
    private static final String user = "root";
    private static final String password = "saheb";


    public TaskStorageDAO() {

    }

    private static Connection getConnection() throws SQLException{
        return DriverManager.getConnection(url, user, password);
    }

    public static void addData() throws SQLException{
        String sql = """


                """;
        PreparedStatement pStatement = getConnection().prepareStatement(sql);
    }

}
