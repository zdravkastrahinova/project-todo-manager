package models;

import java.util.ArrayList;
import java.util.List;

public class Project extends BaseModel {
    private String title;
    private String description;
    private List<User> users;
    private List<Task> tasks;
    private List<Project> subProjects;

    public Project() {
        super();

        this.setUsers(new ArrayList<>());
        this.setTasks(new ArrayList<>());
        this.setSubProjects(new ArrayList<>());
    }

    public Project(String title, String description) {
        super();

        this.setTitle(title);
        this.setDescription(description);
        this.setUsers(new ArrayList<>());
        this.setTasks(new ArrayList<>());
        this.setSubProjects(new ArrayList<>());
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

    public List<Project> getSubProjects() {
        return subProjects;
    }

    public void setSubProjects(List<Project> subProjects) {
        this.subProjects = subProjects;
    }
}
