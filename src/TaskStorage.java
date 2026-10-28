import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TaskStorage {
    private static final String PATH_DIR = "data";
    private static final String RECORD_FILE = "Task_Records.txt";
    private static final String LAST_ID = "Last_ID.txt";

    public TaskStorage() throws IOException {
        File directory = new File(PATH_DIR);
        if (!directory.exists() && !directory.mkdirs()) {
            throw new IOException("Could not create data directory: " + directory.getAbsolutePath());
        }
        if (!directory.isDirectory()) {
            throw new IOException("Data path is not a directory: " + directory.getAbsolutePath());
        }
    }

    private File createFileIfMissing(String fileName) throws IOException {
        File file = new File(fileName);
        if (!file.exists() && !file.createNewFile()) {
            throw new IOException("Could not create file: " + file.getPath());
        }
        return file;
    }

    public List<Task> getPreviousRecords() throws IOException {
        List<Task> tasks = new ArrayList<>();
        File file = createFileIfMissing(PATH_DIR + "/" + RECORD_FILE);
        boolean needToRewriteTheRecord = false;

        try (Scanner scanner = new Scanner(file)) {
            int lineNumber = 0;
            while (scanner.hasNextLine()) {
                lineNumber++;
                String line = scanner.nextLine();
                if (line.isBlank()) {
                    continue;
                }

                // Keep the remainder of the line as the title, including spaces between words.
                String[] parts = line.split("\\s+", 3);
                if (parts.length != 3 || !parts[1].equalsIgnoreCase("true") && !parts[1].equalsIgnoreCase("false")) {
                    // throw new IOException("Invalid task record at line " + lineNumber + ".");
                    System.out.println("\nInvalid task record at line " + lineNumber + ".");
                    if (parts.length == 3) {
                        System.out.println("set to auto fix\n");
                        parts[1] = "false";
                        needToRewriteTheRecord = true;
                    } else {
                        System.out.println("Skipping that line\n");
                        needToRewriteTheRecord = true;
                        continue;
                    }
                }

                try {
                    int id = Integer.parseInt(parts[0]);
                    boolean completed = Boolean.parseBoolean(parts[1]);
                    String title = parts[2];
                    if (title.isBlank()) {
                        // throw new IOException("Task title is empty at line " + lineNumber + ".");
                        System.out.println("Task title is empty at line " + lineNumber + ".");
                        System.out.println("Skipping that line\n");
                        needToRewriteTheRecord = true;
                        continue;
                    }
                    tasks.add(new Task(id, title, completed));
                } catch (NumberFormatException e) {
                    // throw new IOException("Invalid task ID at line " + lineNumber + ".", e);
                    System.out.println("Invalid task ID at line " + lineNumber + ". " + e.getMessage());
                    System.out.println("Skipping that line\n");
                    needToRewriteTheRecord = true;
                    continue;
                }
            }
        }
        if (needToRewriteTheRecord)
            updateAllRecords(tasks);
        return tasks;
    }

    public int getLastId() throws IOException {
        File file = createFileIfMissing(PATH_DIR + "/" + LAST_ID);
        try (Scanner scanner = new Scanner(file)) {
            if (scanner.hasNextInt()) {
                return scanner.nextInt();
            }
        }
        return 0;
    }

    private void storeLastId(int id) throws IOException {
        int lastId = Math.max(getLastId(), id);
        try (FileWriter writer = new FileWriter(PATH_DIR + "/" + LAST_ID, false)) {
            writer.write(Integer.toString(lastId));
        }
    }

    public void storeCurrentData(Task task) throws IOException {
        File file = createFileIfMissing(PATH_DIR + "/" + RECORD_FILE);
        try (FileWriter writer = new FileWriter(file, true)) {
            writer.write(task.getID() + " " + task.isCompleted() + " " + task.getTitle() + System.lineSeparator());
        }
        storeLastId(task.getID());
    }

    public void updateAllRecords(List<Task> records) throws IOException {
        File file = createFileIfMissing(PATH_DIR + "/" + RECORD_FILE);
        try (FileWriter writer = new FileWriter(file, false)) {
            int highestId = getLastId();
            for (Task task : records) {
                writer.write(task.getID() + " " + task.isCompleted() + " " + task.getTitle()
                        + System.lineSeparator());
                highestId = Math.max(highestId, task.getID());
            }
            storeLastId(highestId);
        }
    }
}