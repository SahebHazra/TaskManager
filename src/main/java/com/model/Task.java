package com.model;

public class Task {
    private int id;
    private String title;
    private boolean isCompleted;

    public Task(int id, String title) {
        setId(id);
        setTitle(title);
        isCompleted = false;
    }

    public Task(int id, String title, boolean isCompleted) {
        this(id, title);
        this.isCompleted = isCompleted;
    }

    private void setId(int id) {
        if (id < 1)
            throw new IllegalArgumentException("Task id can't negative");
        this.id = id;
    }

    private void setTitle(String title) {
        if (title == null || title.isBlank())
            throw new IllegalArgumentException("Task title cannot be empty.");
        this.title = title;
    }


    public int getID() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public void markCompleted() {
        this.isCompleted = true;
    }

    public void changeStatus() {
        this.isCompleted = (this.isCompleted) ? false : true;
    }

    @Override
    public String toString() {
        String status = (isCompleted) ? "DONE" : "PENDING";
        return "[ " + id + " ] " + title + " - " + status;
    }

}