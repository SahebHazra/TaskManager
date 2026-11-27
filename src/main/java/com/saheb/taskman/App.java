package com.saheb.taskman;

import com.model.Task;
import com.repository.TaskStorageDAO;
import com.service.TaskService;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

class Menu {
    public static void taskMenu() {
        System.out.println("===== Task Manager =====");
        System.out.println("1: Add Task");
        System.out.println("2: Show All Tasks");
        System.out.println("3: Change Task Status");
        System.out.println("4: Filter Tasks by Status");
        System.out.println("5: Delete Task by ID");
        System.out.println("6: Save Record In CSV");
        System.out.println("7: Exit");
    }
}

public class App {
    private static final Scanner sc = new Scanner(System.in);
    private static boolean isEnd = false;

    public static void main(String[] args) {
        final TaskService taskService;
        try {
            taskService = new TaskService(new TaskStorageDAO());
        } catch (SQLException e) {
            System.err.println("Error on Data Initilization : " + e.getMessage());
            return;
        }

        while (!isEnd) {
            try {
                Menu.taskMenu();
                runMenu(taskService);
                System.out.println();
            } catch (IllegalArgumentException e) {
                System.err.println("Invalid input: " + e.getMessage());
            } catch (SQLException e) {
                System.err.println("Error with data handling : " + e.getMessage());
            } catch (IOException e) {
                System.err.println("Fail to save data locally: " + e.getMessage());
            } catch (Exception e) {
                System.err.println("Unknown Error : " + e);
                e.printStackTrace();
            }
        }
    }

    private static String takeInput(String message) {
        System.out.print(message);
        String input = sc.nextLine();
        if (input.isBlank()) {
            throw new IllegalArgumentException("Input cannot be empty.");
        }
        return input;
    }

    private static void printList(List<Task> records) {
        if (records.isEmpty()) {
            System.out.println("No tasks found.");
            return;
        }
        records.forEach(System.out::println);
    }

    private static void runMenu(TaskService taskService) throws IOException, SQLException {
        String option = takeInput("\nChoose: ").trim();
        switch (option) {
            case "1" -> {
                try {
                    taskService.addTask(takeInput("Title: ").trim());
                } catch (IllegalArgumentException e) {
                    System.err.println("Invalid task: " + e.getMessage());
                }
            }
            case "2" -> printList(taskService.getAllTaskRecords());
            case "3" -> {
                int id = Integer.parseInt(takeInput("Enter task ID: ").trim());
                System.out.println(taskService.changeStatusFor(id)
                        ? "Task Status has been changed."
                        : "Fail to change status for ID: " + id + ".");
            }
            case "4" -> {
                System.out.println("1: Completed Tasks");
                System.out.println("2: Pending Tasks");
                String choice = takeInput("Choose: ").trim();
                switch (choice) {
                    case "1" -> printList(taskService.getTasksByStatus(true));
                    case "2" -> printList(taskService.getTasksByStatus(false));
                    default -> System.out.println("Invalid choice. Enter 1 or 2.");
                }
            }
            case "5" -> {
                int id = Integer.parseInt(takeInput("Enter task ID: ").trim());
                System.out.println(taskService.deleteTask(id)
                        ? "Task deleted."
                        : "No task found with ID " + id + ".");
            }

            case "6" -> taskService.saveRecordsLocally();

            case "7" -> {
                isEnd = true;
                taskService.saveRecordsLocally();
                System.out.println("See you next time!");
            }

            default -> System.out.println("Invalid choice. Enter a number from 1 to 7.");
        }
    }
}