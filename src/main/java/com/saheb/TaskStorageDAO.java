package com.saheb;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.ArrayList;
import java.util.List;

public class TaskStorageDAO {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/taskdb";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "saheb";

    public TaskStorageDAO() {
        try {
            Class.forName("com.mysql.cj.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    private static Connection getConnection() throws Exception {
        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
    }

    public boolean addData(Task task) throws Exception {
        String sql = """
                INSERT INTO tasks(ID,TITLE,STATUS)
                VALUES(?,?,?);
                """;

        try (Connection connection = getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, task.getID());
            preparedStatement.setString(2, task.getTitle());
            preparedStatement.setBoolean(3, task.isCompleted());
            int count = preparedStatement.executeUpdate();
            System.out.println("Add new : " + count + " record");
        }
        return true;
    }

    public int getLastRecordID() throws Exception {
        String sql = "SELECT MAX(ID) FROM tasks";
        int id = 0;
        try (Connection connection = getConnection();
                Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(sql);
            if (resultSet.next())
                id = resultSet.getInt(1);
            resultSet.close();
        }
        return id;
    }

    public List<Task> getPreviousRecords() throws Exception {

        final List<Task> taskList = new ArrayList<>();
        String sql = "SELECT * FROM tasks";

        try (Connection connection = getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                Task task = new Task(resultSet.getInt(1), resultSet.getString(2), resultSet.getBoolean(3));
                taskList.add(task);
            }
        }
        return taskList;
    }

    public void updateStatusForID(int id, boolean isCompleted) throws Exception {
        String sql = "UPDATE tasks SET STATUS=? WHERE ID=?";
        try (Connection connection = getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setBoolean(1, isCompleted);
            preparedStatement.setInt(2, id);
            int count = preparedStatement.executeUpdate();
            System.out.println("Deleted Records : " + count);
        }
    }

    public void deleteRecordForID(int id) throws Exception {
        String sql = "DELETE FROM tasks WHERE ID=?";
        try (Connection connection = getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            int count = preparedStatement.executeUpdate();
            System.out.println("Deleted Records : " + count);
        }
    }

}
