package repositories;

import data.DataStore;
import models.Status;
import models.Task;
import models.User;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class TasksRepository {
    public TasksRepository() {
    }

    public List<Task> getAll() {
        return DataStore.getTasks();
    }

    public Task getById(UUID id) {
        return DataStore.getTasks().stream().filter(task -> task.getId() == id).findFirst().orElse(null);
    }

    public Task getByTitle(String title) {
        return DataStore.getTasks().stream().filter(t -> t.getTitle().equals(title)).findFirst().orElse(null);
    }

    public void add(Task task) {
        DataStore.getTasks().add(task);
    }

    public void update(Task task) {
        Task t = this.getById(task.getId());

        if (t == null) {
            return;
        }

        t.setTitle(task.getTitle());
        t.setDescription(task.getDescription());
        t.setAssigneeId(task.getAssigneeId());
        t.setStatusId(task.getStatusId());
        t.setProjectId(task.getProjectId());
        t.setSubTasks(task.getSubTasks());
    }

    public void delete(Task task) {
        DataStore.getTasks().remove(task);
    }

    public List<Task> getAllSubTasks(UUID taskId) {
        Task task = this.getById(taskId);

        if (task == null)
            return null;

        return task.getSubTasks();
    }

    public void addSubTask(UUID taskId, Task subTask) {
        Task task = this.getById(taskId);

        List<Task> subTasks = task.getSubTasks();

        if (subTasks == null) {
            subTasks = new ArrayList<>();
        }

        subTasks.add(subTask);

        task.setSubTasks(subTasks);
    }

    public void removeSubTask(UUID taskId, String subTaskTitle) {
        Task task = this.getById(taskId);

        List<Task> subTasks = task.getSubTasks();
        if (subTasks == null) {
            return;
        }

        Task subTask = subTasks.stream().filter(sp -> sp.getTitle().equals(subTaskTitle)).findFirst().orElse(null);
        if (subTask == null) {
            return;
        }

        subTasks.remove(subTask);

        task.setSubTasks(subTasks);
    }

    public Status getTaskStatus(UUID taskId) {
        Task task = this.getById(taskId);
        if (task == null) {
            return  null;
        }

        return DataStore.getStatuses().stream().filter(s -> s.getId() == task.getStatusId()).findFirst().orElse(null);
    }

    public User getTaskAssignee(UUID taskId) {
        Task task = this.getById(taskId);
        if (task == null) {
            return  null;
        }

        return DataStore.getUsers().stream().filter(u -> u.getId() == task.getAssigneeId()).findFirst().orElse(null);
    }
}
