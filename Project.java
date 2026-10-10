package model;

public class Project {

    private int id;
    private String name;
    private String description;
    private String deadline;
    private String status;

    public Project(int id, String name, String description,
                   String deadline, String status) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.deadline = deadline;
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getDeadline() {
        return deadline;
    }

    public String getStatus() {
        return status;
    }
}