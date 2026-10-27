import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TaskStorage {
    private final String PATH_DIR;
    private final String RECORD_FILE;
    private final String LAST_ID;

    public TaskStorage() {
        PATH_DIR = "data";
        new File(PATH_DIR).mkdir();
        RECORD_FILE = "Task_Records.txt";
        LAST_ID = "Last_ID.txt";
    }

    private File tryToCreateFileIFNotExist(String fileName) throws IOException {
        var file = new File(fileName);
        file.createNewFile();
        return file;
    }

    public List<Task> getPreviousRecords() throws IOException {
        List<Task> taskList = new ArrayList<>();
        var file = tryToCreateFileIFNotExist(PATH_DIR + "/" + RECORD_FILE);
        try (Scanner fileScanner = new Scanner(file)) {
            var id = 0;
            var title = "";
            var isComplete = false;
            while (fileScanner.hasNextLine()) {
                for (int i = 0; i < 3; i++) {
                    switch (i) {
                        case 0 -> id = Integer.parseInt(fileScanner.next());
                        case 1 -> isComplete = fileScanner.nextBoolean();
                        case 2 -> title = fileScanner.nextLine();
                    }
                }
                taskList.add(new Task(id, title, isComplete));
            }
        }
        return taskList;
    }

    public int getLastId() throws IOException {
        var id = 0;
        var file = tryToCreateFileIFNotExist(PATH_DIR + "/" + LAST_ID);
        try (Scanner fileScanner = new Scanner(file)) {
            if (fileScanner.hasNextInt())
                id = fileScanner.nextInt();
        }
        return id;
    }

    private void storeLastId(int id) throws IOException {
        var oldId = getLastId();
        try (FileWriter fileWriter = new FileWriter(PATH_DIR + "/" + LAST_ID)) {
            id = (oldId < id) ? id : oldId;
            fileWriter.write(String.valueOf(id));
        }
    }

    public void storeCurrentData(Task task) throws IOException {
        tryToCreateFileIFNotExist(PATH_DIR + "/" + RECORD_FILE);
        try (FileWriter fileWriter = new FileWriter(PATH_DIR + "/" + RECORD_FILE, true)) {
            var id = task.getID();
            fileWriter.write(String.valueOf(id) + " " + task.isCompleted() + " " + task.getTitle() + "\n");
            storeLastId(id);
        }
    }

    public void updateAllRecords(List<Task> records) throws IOException {
        var lastID = getLastId();
        var file = tryToCreateFileIFNotExist(PATH_DIR + "/" + RECORD_FILE);
        try (FileWriter fileWriter = new FileWriter(file, false)) {
            for (Task task : records) {
                lastID = (task.getID() > lastID) ? task.getID() : lastID;
                fileWriter.write(task.getID() + " " + task.isCompleted() + " " + task.getTitle() + "\n");
            }
        }
        storeLastId(lastID);
    }
}
