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
    static Scanner sc;
    static boolean isEnd = false;

    public static void main(String[] args) {
        TaskService taskService = new TaskService(new TaskStorage());
        sc = new Scanner(System.in);
        while (!isEnd) {
            try {
                Menu.taskMenu();
                System.out.print("\nChoose : ");
                int option = Integer.parseInt(sc.nextLine());
                runMenu(taskService, option, sc);
                System.out.println();
            } catch (Exception e) {
                System.err.println("err: " + e);
                // break;
            }
        }
    }

    public static void runMenu(TaskService taskService, int option, Scanner scanner) {
        sc = scanner;
        switch (option) {
            case 1 -> {
                System.out.print("Title : ");
                taskService.addTask(sc.nextLine());
            }
            case 2 -> taskService.showAllTask();

            case 3 -> {
                System.out.print("Enter ID: ");
                taskService.markAsCompleteFor(Integer.parseInt(sc.nextLine()));
            }
            case 4 -> {
                System.out.println("1: Pending Task");
                System.out.println("2: Completd Task");
                System.out.print("Choose :  ");
                taskService.filterTasksFor(Integer.parseInt(sc.nextLine()));
            }
            case 5 -> {
                System.out.print("Enter ID: ");
                taskService.deleteRecordFor(Integer.parseInt(sc.nextLine()));
            }
            case 6 -> {
                isEnd = true;
                System.out.println("See you next Time");
            }
            default -> System.out.println("Invalid Input, Enter between (1-5)");
        }
    }
}