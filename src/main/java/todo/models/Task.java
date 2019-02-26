package todo.models;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Extends BaseModel to get UUID
 * Task class describing task that can be created
 */
public class Task extends BaseModel {
    private String title;
    private String description;
    private UUID assigneeId;
    private UUID statusId;
    private UUID projectId;
    private List<Task> subTasks;

    /**
     * Default constructor
     * Initializes task related collections
     */
    public Task() {
        super();

        this.setSubTasks(new ArrayList<>());
    }

    /**
     * @param title defines title of the new task
     * @param description defines description of the new task
     * Initializes task related collections
     */
    public Task(String title, String description) {
        super();

        this.setTitle(title);
        this.setDescription(description);
        this.setSubTasks(new ArrayList<>());
    }


    /**
     * @return title of the task
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title to set title to task
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @return description of the task
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description to set description to task
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return assigned user id of the task
     */
    public UUID getAssigneeId() {
        return assigneeId;
    }

    /**
     * @param assigneeId to assign user to task
     */
    public void setAssigneeId(UUID assigneeId) {
        this.assigneeId = assigneeId;
    }

    /**
     * @return status id of the task
     */
    public UUID getStatusId() {
        return statusId;
    }

    /**
     * @param statusId to set status to task
     */
    public void setStatusId(UUID statusId) {
        this.statusId = statusId;
    }

    /**
     * @return project id to which task is assigned
     */
    public UUID getProjectId() {
        return projectId;
    }

    /**
     * @param projectId to assign project to task
     */
    public void setProjectId(UUID projectId) {
        this.projectId = projectId;
    }

    /**
     * @return list of assigned sub-tasks to task
     */
    public List<Task> getSubTasks() {
        return subTasks;
    }

    /**
     * @param subTasks to assign list of sub-tasks to task
     */
    public void setSubTasks(List<Task> subTasks) {
        this.subTasks = subTasks;
    }
}
