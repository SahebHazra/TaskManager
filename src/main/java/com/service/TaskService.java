package com.service;

import java.util.ArrayList;
import java.util.List;

import com.model.Task;
import com.repository.TaskStorageDAO;
import com.storage.LocalStorage;

public class TaskService {

    private final List<Task> taskList;
    private final TaskStorageDAO taskStorageDAO;

    public TaskService(TaskStorageDAO taskStorageDAO) throws Exception {
        this.taskStorageDAO = taskStorageDAO;
        taskList = new ArrayList<>(taskStorageDAO.getPreviousRecords());
    }

    public void addTask(String title) throws Exception {
        if (title == null || title.isBlank()) {
            throw new IllegalArgumentException("Task title cannot be empty.");
        }
        int id = taskStorageDAO.addData(title);
        if (id > 0) {
            Task task = new Task(id, title);
            taskList.add(task);
            System.out.println("Task added!");
        } else
            System.out.println("Failed to Add");
    }

    public ArrayList<Task> getAllTaskRecords() {
        return new ArrayList<>(taskList);
    }

    public boolean changeStatusFor(int id, boolean markAsComplete) throws Exception {
        for (Task task : taskList) {
            if (task.getID() == id) {
                boolean status = markAsComplete || !task.isCompleted();
                if (taskStorageDAO.updateStatusForID(id, status)) {
                    if (markAsComplete) // status
                        task.markCompleted();
                    else
                        task.changeStatus();
                    return true;
                }
            }
        }
        return false;

    }

    public boolean changeStatusFor(int id) throws Exception {
        return changeStatusFor(id, false);
    }

    public List<Task> getTasksByStatus(boolean completed) {
        return taskList.stream()
                .filter(task -> task.isCompleted() == completed)
                .toList();
    }

    public boolean deleteTask(int id) throws Exception {
        if (taskStorageDAO.deleteRecordForID(id)) {
            for (Task task : taskList) {
                if (task.getID() == id)
                    return taskList.remove(task);
            }
        }
        return false;
    }

    public void saveRecordsLocally() throws Exception {
        System.out.println("Saving CSV...");
        if (LocalStorage.saveRecordsLocally(taskList))
            System.out.println("Data is Saved on Local");
    }
}