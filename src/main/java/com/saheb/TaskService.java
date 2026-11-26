package com.saheb;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TaskService {

    private static int ID = 1;
    private static List<Task> taskList;
    private final TaskStorageDAO taskStorageDAO;

    public TaskService(TaskStorageDAO taskStorageDAO) throws Exception {
        this.taskStorageDAO = taskStorageDAO;
        taskList = new ArrayList<>();
        ID = taskStorageDAO.getLastRecordID() + 1;
    }

    public void addTask(String title) throws Exception {
        if (title == null || title.isBlank()) {
            throw new IllegalArgumentException("Task title cannot be empty.");
        }
        Task task = new Task(ID++, title);
        taskList.add(task);
        if (taskStorageDAO.addData(task))
            System.out.println("Task added!");
        else {
            taskList.remove(task);
            System.out.println("Failed to Add");
        }
    }

    public ArrayList<Task> getAllTaskRecords() throws Exception {
        ArrayList<Task> allTasks = getCurrentTaskRecords();
        allTasks.addAll(getPreviousTaskRecords());
        return allTasks;
    }

    public ArrayList<Task> getCurrentTaskRecords() {
        return new ArrayList<>(taskList);
    }

    public ArrayList<Task> getPreviousTaskRecords() throws Exception {
        return new ArrayList<>(taskStorageDAO.getPreviousRecords());
    }

    
    public boolean markAsCompleteFor(int id) throws IOException {
        for (Task task : taskList) {
            if (task.getID() == id) {
                if (!task.isCompleted()) {
                    task.markCompleted();
                    taskStorage.updateAllRecords(taskList);
                }
                return true;
            }
        }
        return false;
    }

    public void changeStatusFor(int id) throws IOException {
        for (Task task : taskList) {
            if (task.getID() == id) {
                task.changeStatus();
                taskStorage.updateAllRecords(taskList);
                return;
            }
        }
    }

    public List<Task> getTasksByStatus(boolean completed) {
        return taskList.stream()
                .filter(task -> task.isCompleted() == completed)
                .toList();
    }

    public boolean deleteTask(int id) throws IOException {
        boolean removed = taskList.removeIf(task -> task.getID() == id);
        if (removed) {
            taskStorage.updateAllRecords(taskList);
        }
        return removed;
    }
}