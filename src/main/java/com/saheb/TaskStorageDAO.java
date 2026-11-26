package com.saheb;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class TaskStorageDAO {
    private static final String url, user, password;

    public TaskStorageDAO() {
        url = "jdbc:mysql://localhost:3306/taskDB";
        user = "root";
        password = "saheb";
    }

    private static Connection getConnection() {
        return DriverManager.getConnection(url, user, password);
    }

    public static void addData(){
        String sql = """


                """;
        PreparedStatement pStatement = getConnection().prepareStatement(sql);
    }

}
