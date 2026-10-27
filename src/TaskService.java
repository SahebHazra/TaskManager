import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TaskService {

    private static int ID = 1;
    private static List<Task> taskList;
    TaskStorage taskStorage;

    public TaskService(TaskStorage taskStorage) throws IOException {
        this.taskStorage = taskStorage;
        taskList = new ArrayList<>(taskStorage.getPreviousRecords());
        ID = taskStorage.getLastId() + 1;
    }

    public void addTask(String title) throws IOException {
        if (title == null || title.isBlank()) {
            throw new IllegalArgumentException("Task title cannot be empty.");
        }
        Task task = new Task(ID++, title);
        taskList.add(task);
        taskStorage.storeCurrentData(task);
        System.out.println("Task Added!");
    }

    public ArrayList<Task> getAllTaskRecords() {
        return new ArrayList<>(taskList);
    }

    public void markAsCompleteFor(int id) throws IOException {
        if (changeTaskStatus(id, "complete")) {
            System.out.println("Status has change");
        } else {
            System.out.println("Failed to change status");
        }
    }

    public void changeStausFor(int id) throws IOException {
        if (changeTaskStatus(id, "change")) {
            System.out.println("Status has change");
        } else {
            System.out.println("Failed to change status");
        }
    }

    private boolean changeTaskStatus(int id, String type) throws IOException {
        for (Task task : taskList) {
            if (task.getID() == id) {
                var oldMark = task.isCompleted();
                if (type.equalsIgnoreCase("complete"))
                    task.markCompleted();
                else
                    task.changeStatus();
                if (oldMark != task.isCompleted()) {
                    taskStorage.updateAllRecords(taskList);
                }
                return true;
            }
        }
        return false;
    }

    public List<Task> getTasksByStatus(boolean completed) {
        return taskList.stream()
                .filter(task -> task.isCompleted() == completed)
                .toList();
    }

    public boolean deleteTask(int id) throws IOException {
        boolean removed = taskList.removeIf(task -> task.getID() == id);
        if (removed)
            taskStorage.updateAllRecords(taskList);
        return removed;
    }
}
