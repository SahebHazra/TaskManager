# Task Manager CLI

A lightweight command-line task manager built with Core Java. Add tasks, mark them as completed, filter them by status, and save them locally between runs. No database or external libraries are required.

## Features

- Add tasks with unique, increasing IDs
- List all tasks
- Mark tasks as completed
- Filter completed and pending tasks
- Delete tasks by ID
- Automatically load and save task data
- Validate empty input and report invalid task IDs

## Requirements

- JDK 17 or newer
- A terminal or command prompt

Check your Java version:

```bash
java -version
javac -version
```

## Compile and Run

Run these commands from the project root.

Compile:

```bash
javac -d out src/*.java
```

Run:

```bash
java -cp out Main
```

On Windows, these commands also work in PowerShell when JDK 17+ is installed and available on `PATH`.

## Data Storage

The application creates a `data/` directory in the current working directory when it starts. It stores:

- `data/Task_Records.txt` — task IDs, completion status, and titles
- `data/Last_ID.txt` — the last assigned ID, so IDs are not reused after deleting tasks

The `data/` directory is ignored by Git because it contains your local task data.

## Project Structure

```text
TaskManager/
├── src/
│   ├── Main.java
│   ├── Task.java
│   ├── TaskService.java
│   └── TaskStorage.java
├── .gitignore
├── LICENSE
└── README.md
```

## Concepts Demonstrated

- Classes, objects, constructors, and encapsulation
- Java collections and the Stream API
- File handling with `java.io`
- Exception handling and input validation
- Separating application logic from storage logic

## Limitations

- Command-line interface only
- Local plain-text storage; no database or cloud sync
- Intended for one user/process at a time

## License

Released under the MIT License. See [LICENSE](LICENSE).
