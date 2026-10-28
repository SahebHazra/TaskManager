import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TaskService {

    private static int ID = 1;
    private static List<Task> taskList;
    private final TaskStorage taskStorage;

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
        System.out.println("Task added!");
    }

    public ArrayList<Task> getAllTaskRecords() {
        return new ArrayList<>(taskList);
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