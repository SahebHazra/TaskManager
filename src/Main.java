import java.io.IOException;
import java.util.List;
import java.util.Scanner;

class Menu {
    public static void taskMenu() {
        System.out.println("1: Add Task");
        System.out.println("2: Show All Task");
        System.out.println("3: Mark task as Completed");
        System.out.println("4: Filter Tasks by Status");
        System.out.println("5: Delete record by Task ID");
        System.out.println("6: Exit");
    }
}

public class Main {
    final static Scanner sc = new Scanner(System.in);
    static boolean isEnd = false;

    public static void main(String[] args) {
        try {
            TaskService taskService = new TaskService(new TaskStorage());
            while (!isEnd) {
                Menu.taskMenu();
                runMenu(taskService);
                System.out.println();
            }
        } catch (IOException e) {
            System.err.println("err: " + e);
            // break;
        }
    }

    private static String takeInput(String message) {
        System.out.print(message);
        return sc.nextLine();

    }

    private static void printList(List<Task> records) {
        records.forEach(record -> System.out.println(record));
    }

    public static void runMenu(TaskService taskService) throws IOException {

        var option = takeInput("\nChoose : ").trim();
        switch (option) {
            case "1" -> taskService.addTask(takeInput("Title : "));

            case "2" -> printList(taskService.getAllTaskRecords());

            case "3" -> taskService.markAsCompleteFor(Integer.parseInt(takeInput("Enter ID: ")));

            case "4" -> {
                System.out.println("1: Completd Task");
                System.out.println("2: Pending Task");

                String choise = takeInput("Choose : ");
                switch (choise) {
                    case "1" -> printList(taskService.getTasksByStatus(true));
                    case "2" -> printList(taskService.getTasksByStatus(false));
                    default -> System.out.println("Invalid Input");
                }
            }
            case "5" -> taskService.deleteTask(Integer.parseInt(takeInput("Enter ID: ")));

            case "6" -> {
                isEnd = true;
                System.out.println("See you next Time");
            }
            default -> System.out.println("Invalid Input, Enter between (1-5)");
        }
    }
}