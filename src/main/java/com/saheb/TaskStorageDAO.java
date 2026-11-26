package com.saheb;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.ArrayList;
import java.util.List;

public class TaskStorageDAO {

    private static final String url = "jdbc:mysql://localhost:3306/taskdb";
    private static final String user = "root";
    private static final String password = "saheb";
    private static Connection connection;

    private static Connection getConnection() throws Exception {
        connection = DriverManager.getConnection(url, user, password);
        return connection;
    }

    public boolean addData(Task task) throws Exception {
        String sql = """
                INSERT INTO tasks(ID,TITLE,STATUS)
                VALUES(?,?,?);
                """;

        try (PreparedStatement preparedStatement = getConnection().prepareStatement(sql)) {
            preparedStatement.setInt(1, task.getID());
            preparedStatement.setString(2, task.getTitle());
            preparedStatement.setBoolean(3, task.isCompleted());
            int count = preparedStatement.executeUpdate();
            System.out.println("Add new : " + count + " record");
            connection.close();
        }
        return true;
    }

    public int getLastRecordID() throws Exception {
        String sql = "SELECT MAX(ID) FROM tasks";
        int id = 0;
        try (Statement statement = getConnection().createStatement()) {
            ResultSet resultSet = statement.executeQuery(sql);
            if (resultSet.next())
                id = resultSet.getInt(1);
            resultSet.close();
            connection.close();
        }
        return id;
    }

    public List<Task> getPreviousRecords() throws Exception {

        List<Task> taskList = new ArrayList<>();
        String sql = "SELECT * FROM tasks";

        try (PreparedStatement preparedStatement = getConnection().prepareStatement(sql)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Task task = new Task(resultSet.getInt(1), resultSet.getString(2), resultSet.getBoolean(3));
                taskList.add(task);
            }
            resultSet.close();
            connection.close();
        }
        return taskList;
    }

    public void updateStatusForID(int id, boolean isCompleted) throws Exception {
        String sql = "UPDATE tasks SET STATUS=? WHERE ID=?";
        try (PreparedStatement preparedStatement = getConnection().prepareStatement(sql)) {
            preparedStatement.setBoolean(1, isCompleted);
            preparedStatement.setInt(2, id);
            int count = preparedStatement.executeUpdate();
            System.out.println("Deleted Records : " + count);
            connection.close();
        }
    }

    public void deleteRecordForID(int id) throws Exception {
        String sql = "DELETE FROM tasks WHERE ID=?";
        try (PreparedStatement preparedStatement = getConnection().prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            int count = preparedStatement.executeUpdate();
            System.out.println("Deleted Records : " + count);
            connection.close();
        }
    }

}
