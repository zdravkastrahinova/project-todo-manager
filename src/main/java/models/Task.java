package models;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Task extends BaseModel {
    private String title;
    private String description;
    private UUID assigneeId;
    private UUID statusId;
    private UUID projectId;
    private List<Task> subTasks;

    public Task() {
        super();

        this.setSubTasks(new ArrayList<>());
    }

    public Task(String title, String description) {
        super();

        this.setTitle(title);
        this.setDescription(description);
        this.setSubTasks(new ArrayList<>());
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

    public UUID getAssigneeId() {
        return assigneeId;
    }

    public void setAssigneeId(UUID assigneeId) {
        this.assigneeId = assigneeId;
    }

    public UUID getStatusId() {
        return statusId;
    }

    public void setStatusId(UUID statusId) {
        this.statusId = statusId;
    }

    public UUID getProjectId() {
        return projectId;
    }

    public void setProjectId(UUID projectId) {
        this.projectId = projectId;
    }

    public List<Task> getSubTasks() {
        return subTasks;
    }

    public void setSubTasks(List<Task> subTasks) {
        this.subTasks = subTasks;
    }
}
