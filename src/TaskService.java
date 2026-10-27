import java.util.HashMap;

public class TaskService {

    private static int ID;

    private static HashMap<Integer, Task> taskList;
    Task task;
    TaskStorage taskStorage;

    public TaskService(TaskStorage taskStorage) {
        this.taskStorage = taskStorage;
        taskList = new HashMap<>(taskStorage.getPreviousRecords());
        ID = taskStorage.getLastId() + 1;
    }

    public void addTask(String title) {
        int key = ID;
        task = new Task(ID++, title);
        taskList.put(key, task);
        if (taskList.containsKey(key)) {
            taskStorage.storeCurrentData(key, title, taskList.get(key).isCompleted());
            System.out.println("Task Added!");
        } else
            System.out.println("Failed to Add!");
    }

    public void showAllTask() {
        if (taskList.isEmpty()) {
            System.out.println("No Records Found");
            return;
        }
        for (Task task : taskList.values()) {
            System.out.println(task);
        }
    }

    public String getStatusFor(int id) {
        if (taskList.isEmpty())
            return "NO RECORD FOUND!";
        if (taskList.containsKey(id))
            return taskList.get(id).isCompleted() ? "Done" : "Pending";
        return "ID not match!";
    }

    public void markAsCompleteFor(int id) {
        changeStausFor(id, "complete");
    }

    public void changeStausFor(int id) {
        changeStausFor(id, "change");
    }

    private void changeStausFor(int id, String completeOrChange) {
        if (taskList.isEmpty())
            System.out.println("NO RECORD FOUND!");
        if (taskList.containsKey(id)) {
            Task task = taskList.get(id);
            boolean oldMark = task.isCompleted();
            if (completeOrChange.equalsIgnoreCase("change"))
                task.changeStatus();
            else
                 task.markCompleted();
            if (task.isCompleted() != oldMark) {
                System.out.println("Status is Changed : ");
                System.out.println(task);
                taskStorage.updateRecordsAccodingToMap(taskList);
            }else{
                System.out.println("Failed to change the status!");
            }
        } else
            System.out.println("ID not match!");
    }

    public void deleteRecordFor(int id) {
        if (taskList.isEmpty())
            System.out.println("NO RECORD FOUND!");
        if (taskList.containsKey(id)) {
            taskList.remove(id);
            if (!taskList.containsKey(id)) {
                System.out.println("Deleted Sucessfully!");
                taskStorage.updateRecordsAccodingToMap(taskList);
            } else {
                System.out.println("Failed to Delete!");
            }
        } else
            System.out.println("ID not match!");
    }

}
