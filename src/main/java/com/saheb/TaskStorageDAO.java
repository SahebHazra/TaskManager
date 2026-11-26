package com.saheb;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TaskStorageDAO {

    private static final String url = "jdbc:mysql://localhost:3306/taskDB";
    private static final String user = "root";
    private static final String password = "saheb";

    private static Connection connection;

    public TaskStorageDAO() {

    }

    private static Connection getConnection() throws SQLException {
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
            connection.close();
        } catch (SQLException e) {
            throw new Exception(e.getMessage());
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
            int id;
            String title;
            boolean isCompleted;

            while (resultSet.next()) {
                Task task = new Task(resultSet.getInt(1), resultSet.getString(2), resultSet.getBoolean(3));
                taskList.add(task);
            }
            resultSet.close();
            connection.close();
        }
        return taskList;
    }

}
