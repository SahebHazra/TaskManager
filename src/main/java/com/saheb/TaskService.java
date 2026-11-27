package com.saheb;

import java.util.ArrayList;
import java.util.List;

public class TaskService {

    private int nextId = 1;
    private final List<Task> taskList;
    private final TaskStorageDAO taskStorageDAO;

    public TaskService(TaskStorageDAO taskStorageDAO) throws Exception {
        this.taskStorageDAO = taskStorageDAO;
        taskList = new ArrayList<>(taskStorageDAO.getPreviousRecords());
        nextId = taskStorageDAO.getLastRecordID() + 1;
    }

    public void addTask(String title) throws Exception {
        if (title == null || title.isBlank()) {
            throw new IllegalArgumentException("Task title cannot be empty.");
        }
        Task task = new Task(nextId++, title);
        taskList.add(task);
        if (taskStorageDAO.addData(task))
            System.out.println("Task added!");
        else {
            taskList.remove(task);
            System.out.println("Failed to Add");
        }
    }

    public ArrayList<Task> getAllTaskRecords() {
        return new ArrayList<>(taskList);
    }

    public boolean markAsCompleteFor(int id) throws Exception {
        for (Task task : taskList) {
            if (task.getID() == id) {
                if (!task.isCompleted()) {
                    task.markCompleted();
                    taskStorageDAO.updateStatusForID(id, task.isCompleted());
                }
                return true;
            }
        }
        return false;
    }

    public void changeStatusFor(int id) throws Exception {
        for (Task task : taskList) {
            if (task.getID() == id) {
                task.changeStatus();
                taskStorageDAO.updateStatusForID(id, task.isCompleted());
                return;
            }
        }
    }

    public List<Task> getTasksByStatus(boolean completed) {
        return taskList.stream()
                .filter(task -> task.isCompleted() == completed)
                .toList();
    }

    public boolean deleteTask(int id) throws Exception {
        boolean removed = taskList.removeIf(task -> task.getID() == id);
        if (removed) {
            taskStorageDAO.deleteRecordForID(id);
        }
        return removed;
    }

    public void saveRecordsLocally() throws Exception {
        System.out.println("Saving CSV...");
        if (LocalStorage.saveRecordsLocally(taskList))
            System.out.println("Data is Saved on Local");
    }
}