# Task Manager CLI

A lightweight command-line task management application built with Core Java. Create tasks, track completion, filter by status, and persist data locally without a database or external dependencies.

## Features

- Add tasks with unique IDs
- View all tasks
- Mark tasks as completed
- Delete tasks
- Filter pending and completed tasks
- Save and reload tasks automatically
- Validate empty titles and invalid IDs

## Requirements

- Java Development Kit (JDK) 17 or newer
- Terminal or command prompt

## Project Structure

```text
TaskManager/
├── src/
│   ├── Main.java
│   ├── Task.java
│   ├── TaskService.java
│   └── TaskStorage.java
├── data/                 # Created automatically
├── .gitignore
└── README.md
```

## Run the Application

Compile the source files:

```bash
javac -d out src/*.java
```

Start the application:

```bash
java -cp out Main
```

## Data Storage

Tasks are saved locally in `data/tasks.txt`. The file is created automatically when a task is saved.

## Concepts Demonstrated

- Object-oriented programming
- Encapsulation and constructors
- Collections and Stream API
- File handling with Java NIO
- Exception handling and input validation
- Basic data persistence

## Limitations

- Console interface only
- Local file storage; no database or cloud synchronization
- No concurrent access support

## License

This project is available under the MIT License. See [LICENSE](LICENSE) for details.
