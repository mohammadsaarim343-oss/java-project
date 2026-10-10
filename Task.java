package model;

public class Task {

    private int id;
    private String title;
    private String description;
    private String assignedTo;
    private String priority;
    private String status;

    public Task(int id, String title, String description,
                String assignedTo, String priority, String status) {

        this.id = id;
        this.title = title;
        this.description = description;
        this.assignedTo = assignedTo;
        this.priority = priority;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getAssignedTo() {
        return assignedTo;
    }

    public String getPriority() {
        return priority;
    }

    public String getStatus() {
        return status;
    }
}