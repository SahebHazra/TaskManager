import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
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
        File file = new File(fileName);
        file.createNewFile();
        return file;
    }

    public Map<Integer, Task> getPreviousRecords() {
        Map<Integer, Task> testMap = new HashMap<>();
        try {
            File file = tryToCreateFileIFNotExist(PATH_DIR + "/" + RECORD_FILE);
            try (Scanner fileScanner = new Scanner(file)) {
                int id = 0;
                String title = "";
                boolean isComplete = false;
                while (fileScanner.hasNextLine()) {
                    for (int i = 0; i < 3; i++) {
                        switch (i) {
                            case 0 -> id = Integer.parseInt(fileScanner.next());
                            case 1 -> isComplete = fileScanner.nextBoolean();
                            case 2 -> title = fileScanner.nextLine();
                        }
                    }
                    testMap.put(id, new Task(id, title, isComplete));
                }
            }
        } catch (IOException e) {
            System.out.println(e);
        }

        return testMap;
    }

    public int getLastId() {
        int id = 0;
        try {
            File file = tryToCreateFileIFNotExist(PATH_DIR + "/" + LAST_ID);
            try (Scanner fileScanner = new Scanner(file)) {
                if (fileScanner.hasNextInt())
                    id = fileScanner.nextInt();
            }
        } catch (IOException e) {
            System.out.println(e);
        }
        return id;
    }

    private void storeLastId(int id) {
        int oldId = getLastId();
        try {
            try (FileWriter fileWriter = new FileWriter(PATH_DIR + "/" + LAST_ID)) {
                id = (oldId < id) ? id : oldId;
                fileWriter.write(String.valueOf(id));
            }
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    public void storeCurrentData(int id, String title, boolean isComplete) {
        try {
            tryToCreateFileIFNotExist(PATH_DIR + "/" + RECORD_FILE);
            try (FileWriter fileWriter = new FileWriter(PATH_DIR + "/" + RECORD_FILE, true)) {
                fileWriter.write(String.valueOf(id) + " " + isComplete + " " + title + "\n");
            }
            storeLastId(id);
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    public void updateRecordsAccodingToMap(Map<Integer, Task> records) {
        int oldId = getLastId();
        int lastHeighestID = oldId;
        try {
            File file = tryToCreateFileIFNotExist(PATH_DIR + "/" + RECORD_FILE);
            try (FileWriter fileWriter = new FileWriter(file, false)) {
                for (Task task : records.values()) {
                    lastHeighestID = (task.getID() > oldId) ? task.getID() : oldId;
                    fileWriter.write(task.getID() + " " + task.isCompleted() + " " + task.getTitle() + "\n");
                }
            }
            storeLastId(lastHeighestID);
        } catch (IOException e) {
            System.out.println(e);
        }
    }
}
