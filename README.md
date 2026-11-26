# Task Manager CLI

A console-based task management application built with **Core Java 17** and **Maven**. It lets you create tasks, view and filter them, update their completion status, and delete them. The `master` branch stores data locally in text files, so no database is required.

## Features

- Add tasks with automatically assigned, increasing IDs
- View all saved tasks
- Mark a task as completed
- Change a task's status
- Filter tasks by status (pending or completed)
- Delete a task by ID
- Load task records when the application starts
- Persist changes locally between runs
- Validate empty task titles and handle invalid task IDs

## Requirements

- JDK 17 or newer
- Apache Maven 3.8+ (or a compatible recent Maven release)
- A terminal or command prompt

Check that Java and Maven are available:

```bash
java -version
mvn -version
```

## Run the application

Clone the repository and move into the project directory:

```bash
git clone https://github.com/SahebHazra/TaskManager.git
cd TaskManager
```

Build and run with Maven:

```bash
mvn clean compile
mvn exec:java -Dexec.mainClass="com.saheb.taskman.App"
```

You can also package the project:

```bash
mvn clean package
```

The application is a terminal-based program; run the commands from the project root.

## Menu

The application provides options to:

1. Add a task
2. Show all tasks
3. Mark a task as completed
4. Filter tasks by status
5. Delete a task by ID
6. Exit

The exact prompts are shown when the application starts.

## Data storage

On first run, the application creates a `data/` directory in the working directory. The master branch uses two local files:

| File | Purpose |
| --- | --- |
| `data/Task_Records.txt` | Stores task IDs, status, and titles |
| `data/Last_ID.txt` | Stores the last assigned ID to avoid reusing IDs after tasks are deleted |

These files are generated locally and are excluded from Git. Back up the `data/` directory if you want to keep your task records.

## Project structure

The source code follows a Maven layout:

```text
TaskManager/
├── src/
│   ├── main/
│   │   └── java/
│   │       └── com/saheb/taskman/
│   │           ├── App.java
│   │           ├── model/
│   │           │   └── Task.java
│   │           ├── service/
│   │           │   └── TaskService.java
│   │           └── storage/
│   │               └── TaskStorage.java
│   └── test/
│       └── java/
├── data/                 # Created at runtime; ignored by Git
├── pom.xml
└── README.md
```

## Concepts demonstrated

- Object-oriented programming: classes, objects, encapsulation
- Collections and the Java Stream API
- File handling and persistence
- Exception handling and input validation
- Separation of application, service, model, and storage responsibilities
- Building and running a Java project with Maven

## Branches

- **master** — the file-based version; uses local text files and does not require MySQL.
- **feature/sql** — the JDBC/MySQL development branch.

For the SQL branch, follow its branch-specific database setup and configuration before running it. The instructions above describe `master`.

## Current scope

- Command-line interface; no web interface
- Local, single-user file storage
- No cloud synchronization or multi-user access

## License

Released under the MIT License. See [LICENSE](LICENSE).
