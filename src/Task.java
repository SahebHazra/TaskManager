public class Task {
    private final int ID;
    private final String title;
    private boolean isCompleted;

    public Task(int id, String title) {
        this.ID = id;
        this.title = title;
        isCompleted = false;
    }

    public Task(int id, String title, boolean isCompleted) {
        this(id, title);
        this.isCompleted = isCompleted;
    }

    public int getID() {
        return ID;
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
        this.isCompleted = (isCompleted) ? false : true;
    }

    @Override
    public String toString() {
        String status = (isCompleted) ? "DONE" : "PENDING";
        return "[ " + ID + " ] " + title + " - " + status;
    }

}