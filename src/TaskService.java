import java.util.HashMap;

public class TaskService {

    private static int ID;

    private static HashMap<Integer, Task> taskMap;
    TaskStorage taskStorage;

    public TaskService(TaskStorage taskStorage) {
        this.taskStorage = taskStorage;
        taskMap = new HashMap<>(taskStorage.getPreviousRecords());
        ID = taskStorage.getLastId() + 1;
    }

    public void addTask(String title) {
        int key = ID;
        taskMap.put(key, new Task(ID++, title));
        if (taskMap.containsKey(key)) {
            taskStorage.storeCurrentData(key, title, taskMap.get(key).isCompleted());
            System.out.println("Task Added!");
        } else
            System.out.println("Failed to Add!");
    }

    public void showAllTask() {
        if (taskMap.isEmpty()) {
            System.out.println("No Records Found");
            return;
        }
        for (Task task : taskMap.values()) {
            System.out.println(task);
        }
    }

    public String getStatusFor(int id) {
        if (taskMap.isEmpty())
            return "NO RECORD FOUND!";
        if (taskMap.containsKey(id))
            return taskMap.get(id).isCompleted() ? "Done" : "Pending";
        return "ID not match!";
    }

    public void markAsCompleteFor(int id) {
        changeStausFor(id, "complete");
    }

    public void changeStausFor(int id) {
        changeStausFor(id, "change");
    }

    private void changeStausFor(int id, String completeOrChange) {
        if (taskMap.isEmpty())
            System.out.println("NO RECORD FOUND!");
        if (taskMap.containsKey(id)) {
            Task task = taskMap.get(id);
            boolean oldMark = task.isCompleted();
            if (completeOrChange.equalsIgnoreCase("change"))
                task.changeStatus();
            else
                task.markCompleted();
            if (task.isCompleted() != oldMark) {
                System.out.println("Status is Changed : ");
                System.out.println(task);
                taskStorage.updateRecordsAccodingToMap(taskMap);
            } else {
                System.out.println("Before and After both Status are same");
            }
        } else
            System.out.println("ID not match!");
    }

    public void filterTasksFor(int choice) {
        boolean isAnyMatch = false;
        if (choice < 1 || choice > 2) {
            System.out.println("Invalid Choice");
            return;
        }
        boolean isCompleted = (choice == 1) ? false : true;
        for (Task task : taskMap.values()) {
            if ((isCompleted) && (task.isCompleted())) {
                isAnyMatch = true;
                System.out.println(task);
            } else if (!isCompleted && !task.isCompleted()) {
                isAnyMatch = true;
                System.out.println(task);
            }
        }
        if (!isAnyMatch) {
            System.out.println("No Record found for that Status");
        }
    }

    public void deleteRecordFor(int id) {
        if (taskMap.isEmpty())
            System.out.println("NO RECORD FOUND!");
        if (taskMap.containsKey(id)) {
            taskMap.remove(id);
            if (!taskMap.containsKey(id)) {
                System.out.println("Deleted Sucessfully!");
                taskStorage.updateRecordsAccodingToMap(taskMap);
            } else {
                System.out.println("Failed to Delete!");
            }
        } else
            System.out.println("ID not match!");
    }

}
