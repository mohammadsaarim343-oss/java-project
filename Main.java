import model.Project;
import model.Task;
import model.TeamMember;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    static Scanner scanner = new Scanner(System.in);

    static ArrayList<Project> projects = new ArrayList<>();
    static ArrayList<Task> tasks = new ArrayList<>();
    static ArrayList<TeamMember> teamMembers = new ArrayList<>();

    static final String DATA_FILE = "teamflow_data.txt";

    public static void main(String[] args) {

        System.out.println("========================================");
        System.out.println("          TEAMFLOW LOGIN");
        System.out.println("========================================");

        System.out.print("Username: ");
        String username = scanner.nextLine().trim();

        System.out.print("Password: ");
        String password = scanner.nextLine().trim();

        if (!username.equals("admin") || !password.equals("1234")) {
            System.out.println("Login Failed!");
            return;
        }

        System.out.println("\nLogin Successful!");
        System.out.println("Welcome to TeamFlow");

        loadData();

        if (projects.isEmpty()) {
            projects.add(new Project(
                    1,
                    "TeamFlow Website",
                    "Online Project Management Tool",
                    "30-11-2026",
                    "In Progress"
            ));
        }

        if (tasks.isEmpty()) {
            tasks.add(new Task(
                    1,
                    "Create Login Page",
                    "Develop user login page",
                    "Team Member 1",
                    "High",
                    "Pending"
            ));
        }

        if (teamMembers.isEmpty()) {
            teamMembers.add(new TeamMember(
                    1,
                    "Saarim",
                    "Project Manager",
                    "saarim@gmail.com"
            ));

            teamMembers.add(new TeamMember(
                    2,
                    "Team Member 1",
                    "Developer",
                    "developer@gmail.com"
            ));
        }

        while (true) {

            System.out.println("\n========================================");
            System.out.println("              TEAMFLOW");
            System.out.println("       ONLINE PROJECT MANAGEMENT");
            System.out.println("========================================");
            System.out.println("============== DASHBOARD ==============");
            System.out.println("Projects Available : " + projects.size());
            System.out.println("Tasks Available    : " + tasks.size());
            System.out.println("Team Members       : " + teamMembers.size());
            System.out.println("----------------------------------------");

            System.out.println("1. View Projects");
            System.out.println("2. View Tasks");
            System.out.println("3. Add Project");
            System.out.println("4. Add Task");
            System.out.println("5. View Team Members");
            System.out.println("6. Add Team Member");
            System.out.println("7. Update Task Status");
            System.out.println("8. Save Data");
            System.out.println("9. Search Project");
            System.out.println("10. Update Project");
            System.out.println("11. Delete Project");
            System.out.println("12. Search Task");
            System.out.println("13. Delete Task");
            System.out.println("14. Delete Team Member");
            System.out.println("15. Exit");

            System.out.print("\nEnter your choice: ");

            int choice;

            try {
                choice = Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a number from 1 to 15.");
                continue;
            }

            switch (choice) {

                case 1:
                    viewProjects();
                    break;

                case 2:
                    viewTasks();
                    break;

                case 3:
                    addProject();
                    break;

                case 4:
                    addTask();
                    break;

                case 5:
                    viewTeamMembers();
                    break;

                case 6:
                    addTeamMember();
                    break;

                case 7:
                    updateTaskStatus();
                    break;

                case 8:
                    saveData();
                    break;

                case 9:
                    searchProject();
                    break;

                case 10:
                    updateProject();
                    break;

                case 11:
                    deleteProject();
                    break;

                case 12:
                    searchTask();
                    break;

                case 13:
                    deleteTask();
                    break;

                case 14:
                    deleteTeamMember();
                    break;

                case 15:
                    saveData();
                    System.out.println("\nThank you for using TeamFlow!");
                    scanner.close();
                    return;

                default:
                    System.out.println("Invalid choice! Select 1 to 15.");
            }
        }
    }

    // VIEW PROJECTS
    static void viewProjects() {

        System.out.println("\n========== PROJECT LIST ==========");

        if (projects.isEmpty()) {
            System.out.println("No projects available.");
            return;
        }

        for (int i = 0; i < projects.size(); i++) {

            Project p = projects.get(i);

            System.out.println("\nProject ID   : " + (i + 1));
            System.out.println("Project Name : " + p.getName());
            System.out.println("Description  : " + p.getDescription());
            System.out.println("Deadline     : " + p.getDeadline());
            System.out.println("Status       : " + p.getStatus());
            System.out.println("----------------------------------");
        }
    }

    // ADD PROJECT
    static void addProject() {

        System.out.println("\n========== ADD PROJECT ==========");

        String name = readRequired("Project Name: ");
        String description = readRequired("Description: ");
        String deadline = readRequired("Deadline (DD-MM-YYYY): ");
        String status = readRequired("Status: ");

        projects.add(new Project(
                nextProjectId(),
                name,
                description,
                deadline,
                status
        ));

        saveData();
        System.out.println("Project added successfully!");
    }

    // SEARCH PROJECT
    static void searchProject() {

        System.out.println("\n========== SEARCH PROJECT ==========");

        if (projects.isEmpty()) {
            System.out.println("No projects available.");
            return;
        }

        System.out.print("Enter project name: ");
        String searchName = scanner.nextLine().trim();

        boolean found = false;

        for (int i = 0; i < projects.size(); i++) {

            Project p = projects.get(i);

            if (p.getName().toLowerCase().contains(searchName.toLowerCase())) {

                System.out.println("\nProject ID   : " + (i + 1));
                System.out.println("Project Name : " + p.getName());
                System.out.println("Description  : " + p.getDescription());
                System.out.println("Deadline     : " + p.getDeadline());
                System.out.println("Status       : " + p.getStatus());

                found = true;
            }
        }

        if (!found) {
            System.out.println("Project not found!");
        }
    }

    // UPDATE PROJECT
    static void updateProject() {

        if (projects.isEmpty()) {
            System.out.println("No projects available.");
            return;
        }

        viewProjects();

        int id = readInt("Enter Project ID to update: ");

        if (id < 1 || id > projects.size()) {
            System.out.println("Invalid Project ID!");
            return;
        }

        System.out.println("Enter new project details:");

        String name = readRequired("New Project Name: ");
        String description = readRequired("New Description: ");
        String deadline = readRequired("New Deadline: ");
        String status = readRequired("New Status: ");

        projects.set(id - 1, new Project(
                id,
                name,
                description,
                deadline,
                status
        ));

        saveData();
        System.out.println("Project updated successfully!");
    }

    // DELETE PROJECT
    static void deleteProject() {

        if (projects.isEmpty()) {
            System.out.println("No projects available.");
            return;
        }

        viewProjects();

        int id = readInt("Enter Project ID to delete: ");

        if (id < 1 || id > projects.size()) {
            System.out.println("Invalid Project ID!");
            return;
        }

        System.out.print("Confirm deletion? Type yes/no: ");
        String confirm = scanner.nextLine().trim();

        if (confirm.equalsIgnoreCase("yes")) {

            projects.remove(id - 1);

            saveData();
            System.out.println("Project deleted successfully!");

        } else {
            System.out.println("Deletion cancelled.");
        }
    }

    // VIEW TASKS
    static void viewTasks() {

        System.out.println("\n============ TASK LIST ============");

        if (tasks.isEmpty()) {
            System.out.println("No tasks available.");
            return;
        }

        for (int i = 0; i < tasks.size(); i++) {

            Task t = tasks.get(i);

            System.out.println("\nTask ID      : " + (i + 1));
            System.out.println("Task         : " + t.getTitle());
            System.out.println("Description  : " + t.getDescription());
            System.out.println("Assigned To  : " + t.getAssignedTo());
            System.out.println("Priority     : " + t.getPriority());
            System.out.println("Status       : " + t.getStatus());
            System.out.println("----------------------------------");
        }
    }

    // ADD TASK
    static void addTask() {

        System.out.println("\n============ ADD TASK ============");

        String title = readRequired("Task Title: ");
        String description = readRequired("Description: ");
        String assignedTo = readRequired("Assigned To: ");
        String priority = readPriority();
        String status = readTaskStatus();

        tasks.add(new Task(
                nextTaskId(),
                title,
                description,
                assignedTo,
                priority,
                status
        ));

        saveData();
        System.out.println("Task added successfully!");
    }

    // SEARCH TASK
    static void searchTask() {

        if (tasks.isEmpty()) {
            System.out.println("No tasks available.");
            return;
        }

        System.out.print("Enter task title to search: ");
        String searchTitle = scanner.nextLine().trim();

        boolean found = false;

        for (int i = 0; i < tasks.size(); i++) {

            Task t = tasks.get(i);

            if (t.getTitle().toLowerCase().contains(searchTitle.toLowerCase())) {

                System.out.println("\nTask ID     : " + (i + 1));
                System.out.println("Title       : " + t.getTitle());
                System.out.println("Description : " + t.getDescription());
                System.out.println("Assigned To : " + t.getAssignedTo());
                System.out.println("Priority    : " + t.getPriority());
                System.out.println("Status      : " + t.getStatus());

                found = true;
            }
        }

        if (!found) {
            System.out.println("Task not found!");
        }
    }

    // DELETE TASK
    static void deleteTask() {

        if (tasks.isEmpty()) {
            System.out.println("No tasks available.");
            return;
        }

        viewTasks();

        int id = readInt("Enter Task ID to delete: ");

        if (id < 1 || id > tasks.size()) {
            System.out.println("Invalid Task ID!");
            return;
        }

        System.out.print("Confirm deletion? Type yes/no: ");
        String confirm = scanner.nextLine().trim();

        if (confirm.equalsIgnoreCase("yes")) {

            tasks.remove(id - 1);

            saveData();
            System.out.println("Task deleted successfully!");

        } else {
            System.out.println("Deletion cancelled.");
        }
    }

    // UPDATE TASK STATUS
    static void updateTaskStatus() {

        if (tasks.isEmpty()) {
            System.out.println("No tasks available to update.");
            return;
        }

        viewTasks();

        int id = readInt("Enter Task ID to update: ");

        if (id < 1 || id > tasks.size()) {
            System.out.println("Invalid Task ID!");
            return;
        }

        System.out.println("1. Pending");
        System.out.println("2. In Progress");
        System.out.println("3. Completed");

        int statusChoice = readInt("Select new status: ");

        String status;

        switch (statusChoice) {
            case 1:
                status = "Pending";
                break;

            case 2:
                status = "In Progress";
                break;

            case 3:
                status = "Completed";
                break;

            default:
                System.out.println("Invalid status choice!");
                return;
        }

        Task oldTask = tasks.get(id - 1);

        tasks.set(id - 1, new Task(
                id,
                oldTask.getTitle(),
                oldTask.getDescription(),
                oldTask.getAssignedTo(),
                oldTask.getPriority(),
                status
        ));

        saveData();
        System.out.println("Task status updated successfully!");
    }

    // VIEW TEAM MEMBERS
    static void viewTeamMembers() {

        System.out.println("\n========== TEAM MEMBERS ==========");

        if (teamMembers.isEmpty()) {
            System.out.println("No team members available.");
            return;
        }

        for (int i = 0; i < teamMembers.size(); i++) {

            TeamMember member = teamMembers.get(i);

            System.out.println("\nMember ID : " + (i + 1));
            System.out.println("Name      : " + member.getName());
            System.out.println("Role      : " + member.getRole());
            System.out.println("Email     : " + member.getEmail());
            System.out.println("----------------------------------");
        }
    }

    // ADD TEAM MEMBER
    static void addTeamMember() {

        System.out.println("\n========== ADD TEAM MEMBER ==========");

        String name = readRequired("Member Name: ");
        String role = readRequired("Role: ");
        String email = readRequired("Email: ");

        teamMembers.add(new TeamMember(
                nextMemberId(),
                name,
                role,
                email
        ));

        saveData();
        System.out.println("Team member added successfully!");
    }

    // DELETE TEAM MEMBER
    static void deleteTeamMember() {

        if (teamMembers.isEmpty()) {
            System.out.println("No team members available.");
            return;
        }

        viewTeamMembers();

        int id = readInt("Enter Member ID to delete: ");

        if (id < 1 || id > teamMembers.size()) {
            System.out.println("Invalid Member ID!");
            return;
        }

        System.out.print("Confirm deletion? Type yes/no: ");
        String confirm = scanner.nextLine().trim();

        if (confirm.equalsIgnoreCase("yes")) {

            teamMembers.remove(id - 1);

            saveData();
            System.out.println("Team member deleted successfully!");

        } else {
            System.out.println("Deletion cancelled.");
        }
    }

    // SAVE DATA
    static void saveData() {

        try (PrintWriter writer = new PrintWriter(
                new FileWriter(DATA_FILE))) {

            for (Project p : projects) {
                writer.println("PROJECT\t"
                        + clean(p.getName()) + "\t"
                        + clean(p.getDescription()) + "\t"
                        + clean(p.getDeadline()) + "\t"
                        + clean(p.getStatus()));
            }

            for (Task t : tasks) {
                writer.println("TASK\t"
                        + clean(t.getTitle()) + "\t"
                        + clean(t.getDescription()) + "\t"
                        + clean(t.getAssignedTo()) + "\t"
                        + clean(t.getPriority()) + "\t"
                        + clean(t.getStatus()));
            }

            for (TeamMember m : teamMembers) {
                writer.println("MEMBER\t"
                        + clean(m.getName()) + "\t"
                        + clean(m.getRole()) + "\t"
                        + clean(m.getEmail()));
            }

            System.out.println("Data saved successfully!");

        } catch (IOException e) {
            System.out.println("Error saving data: " + e.getMessage());
        }
    }

    // LOAD DATA
    static void loadData() {

        File file = new File(DATA_FILE);

        if (!file.exists()) {
            return;
        }

        try (BufferedReader reader = new BufferedReader(
                new FileReader(file))) {

            String line;

            projects.clear();
            tasks.clear();
            teamMembers.clear();

            while ((line = reader.readLine()) != null) {

                String[] data = line.split("\t", -1);

                try {
                    switch (data[0]) {

                        case "PROJECT":
                            if (data.length >= 5) {
                                projects.add(new Project(
                                        projects.size() + 1,
                                        data[1],
                                        data[2],
                                        data[3],
                                        data[4]
                                ));
                            }
                            break;

                        case "TASK":
                            if (data.length >= 6) {
                                tasks.add(new Task(
                                        tasks.size() + 1,
                                        data[1],
                                        data[2],
                                        data[3],
                                        data[4],
                                        data[5]
                                ));
                            } else if (data.length >= 5) {
                                // Compatibility with old saved task records
                                tasks.add(new Task(
                                        tasks.size() + 1,
                                        data[1],
                                        "",
                                        data[2],
                                        data[3],
                                        data[4]
                                ));
                            }
                            break;

                        case "MEMBER":
                            if (data.length >= 4) {
                                teamMembers.add(new TeamMember(
                                        teamMembers.size() + 1,
                                        data[1],
                                        data[2],
                                        data[3]
                                ));
                            }
                            break;
                    }

                } catch (RuntimeException e) {
                    System.out.println("Some saved data could not be loaded.");
                }
            }

            System.out.println("Saved data loaded successfully.");

        } catch (IOException e) {
            System.out.println("Could not load saved data: " + e.getMessage());
        }
    }

    // INPUT HELPERS
    static String readRequired(String message) {

        while (true) {
            System.out.print(message);
            String value = scanner.nextLine().trim();

            if (!value.isEmpty()) {
                return value;
            }

            System.out.println("This field cannot be empty.");
        }
    }

    static int readInt(String message) {

        while (true) {
            System.out.print(message);

            try {
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }
    }

    static String readPriority() {

        while (true) {

            System.out.print("Priority (High/Medium/Low): ");
            String priority = scanner.nextLine().trim();

            if (priority.equalsIgnoreCase("High")) {
                return "High";
            }

            if (priority.equalsIgnoreCase("Medium")) {
                return "Medium";
            }

            if (priority.equalsIgnoreCase("Low")) {
                return "Low";
            }

            System.out.println("Please enter High, Medium, or Low.");
        }
    }

    static String readTaskStatus() {

        while (true) {

            System.out.println("1. Pending");
            System.out.println("2. In Progress");
            System.out.println("3. Completed");

            int choice = readInt("Select task status: ");

            switch (choice) {
                case 1:
                    return "Pending";

                case 2:
                    return "In Progress";

                case 3:
                    return "Completed";

                default:
                    System.out.println("Please select 1, 2, or 3.");
            }
        }
    }

    // ID HELPERS
    static int nextProjectId() {
        return projects.size() + 1;
    }

    static int nextTaskId() {
        return tasks.size() + 1;
    }

    static int nextMemberId() {
        return teamMembers.size() + 1;
    }

    // CLEAN TEXT BEFORE SAVING
    static String clean(String value) {

        if (value == null) {
            return "";
        }

        return value.replace("\t", " ")
                .replace("\n", " ")
                .replace("\r", " ");
    }
}