package com.saheb;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class LocalStorage {
    private static final String PATH_DIR = "data";
    private static final String RECORD_FILE = "Task_Records.csv";

    private static File createFileIfMissing(String fileName) throws IOException {
        File directory = new File(PATH_DIR);
        if (!directory.exists() && !directory.mkdirs()) {
            throw new IOException("Could not create data directory: " + directory.getAbsolutePath());
        }
        if (!directory.isDirectory()) {
            throw new IOException("Data path is not a directory: " + directory.getAbsolutePath());
        }

        File file = new File(fileName);
        if (!file.exists() && !file.createNewFile()) {
            throw new IOException("Could not create file: " + file.getPath());
        }
        return file;
    }

    public static boolean saveRecordsLocally(List<Task> records) throws Exception {
        File file = createFileIfMissing(PATH_DIR + "/" + RECORD_FILE);
        try (FileWriter writer = new FileWriter(file, false)) {
            writer.write("ID, Task Title, Status " + System.lineSeparator());
            String status;
            for (Task task : records) {
                status = (task.isCompleted()) ? "Completed" : "Pending";
                writer.write(task.getID() + ", " + task.getTitle() + ", " + status + System.lineSeparator());
            }
        } catch (IOException e) {
            throw new Exception("Failed to Save Data Locally");
        }
        return true;
    }
}