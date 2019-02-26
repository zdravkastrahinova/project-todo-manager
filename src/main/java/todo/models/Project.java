package todo.models;

import java.util.ArrayList;
import java.util.List;

/**
 * Extends BaseModel to get UUID
 * Project class describing project that can be created
 */
public class Project extends BaseModel {
    private String title;
    private String description;
    private List<User> users;
    private List<Task> tasks;
    private List<Project> subProjects;

    /**
     * Default constructor
     * Initializes project related collections
     */
    public Project() {
        super();

        this.setUsers(new ArrayList<>());
        this.setTasks(new ArrayList<>());
        this.setSubProjects(new ArrayList<>());
    }

    /**
     * @param title defines title of the new project
     * @param description defines description of the new project
     * Initializes project related collections
     */
    public Project(String title, String description) {
        super();

        this.setTitle(title);
        this.setDescription(description);
        this.setUsers(new ArrayList<>());
        this.setTasks(new ArrayList<>());
        this.setSubProjects(new ArrayList<>());
    }

    /**
     * @return title of the project
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title to set title to project
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @return description of the project
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description to set description to project
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return users that are assigned to project
     */
    public List<User> getUsers() {
        return users;
    }

    /**
     * @param users to assign list of users to project
     */
    public void setUsers(List<User> users) {
        this.users = users;
    }

    /**
     * @return tasks that are assigned to project
     */
    public List<Task> getTasks() {
        return tasks;
    }

    /**
     * @param tasks to assign list of tasks to project
     */
    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

    /**
     * @return list of assigned sub-projects to project
     */
    public List<Project> getSubProjects() {
        return subProjects;
    }

    /**
     * @param subProjects to assign list of sub-projects to project
     */
    public void setSubProjects(List<Project> subProjects) {
        this.subProjects = subProjects;
    }
}
