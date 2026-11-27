package com.saheb.taskman.repository;

import com.saheb.taskman.model.Task;

import io.github.cdimascio.dotenv.Dotenv;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;

public class TaskStorageDAO {

    private static final Dotenv dotenv = Dotenv.load();

    private static final String DB_URL = dotenv.get("DB_URL");
    private static final String DB_USER = dotenv.get("DB_USER");
    private static final String DB_PASSWORD = dotenv.get("DB_PASSWORD");

    static {
        if (DB_URL == null || DB_USER == null || DB_PASSWORD == null) {
            throw new IllegalStateException(
                    "Missing database configuration in .env");
        }
    }

    public TaskStorageDAO() {
        // try {
        // DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
        // } catch (SQLException e) {
        // throw new RuntimeException(e.getMessage());
        // }
    }

    private static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
    }

    public int addData(String title) throws SQLException {
        String sql = """
                INSERT INTO tasks(title)
                VALUES(?);
                """;

        try (Connection connection = getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql,
                        Statement.RETURN_GENERATED_KEYS)) {

            preparedStatement.setString(1, title);
            int count = preparedStatement.executeUpdate();
            System.out.println("Add new : " + count + " record");
            try (ResultSet rset = preparedStatement.getGeneratedKeys()) {
                if (rset.next())
                    return rset.getInt(1);
            }
        }
        return -1;
    }

    public List<Task> getPreviousRecords() throws SQLException {

        final List<Task> taskList = new ArrayList<>();
        String sql = "SELECT * FROM tasks";

        try (Connection connection = getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                ResultSet resultSet = preparedStatement.executeQuery()) {

            for (int line = 0; resultSet.next(); line++) {
                try {
                    Task task = new Task(resultSet.getInt(1), resultSet.getString(2), resultSet.getBoolean(3));
                    taskList.add(task);
                } catch (IllegalArgumentException e) {
                    System.out.println("Invalid Record on Line: " + line + ", " + e);
                }
            }
        }
        return taskList;
    }

    public boolean updateStatusForID(int id, boolean isCompleted) throws SQLException {
        String sql = "UPDATE tasks SET STATUS=? WHERE ID=?";
        try (Connection connection = getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setBoolean(1, isCompleted);
            preparedStatement.setInt(2, id);
            int count = preparedStatement.executeUpdate();
            System.out.println("Updated Records : " + count);
            return count > 0;
        }
    }

    public boolean deleteRecordForID(int id) throws SQLException {
        String sql = "DELETE FROM tasks WHERE ID=?";
        try (Connection connection = getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            int count = preparedStatement.executeUpdate();
            System.out.println("Deleted Records : " + count);
            return count > 0;
        }
    }

}
