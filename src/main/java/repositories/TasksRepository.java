package repositories;

import data.DataStore;
import models.Status;
import models.Task;
import models.User;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class TasksRepository {
    private UsersRepository usersRepo;

    public TasksRepository() {
        this.usersRepo = new UsersRepository();
    }

    public List<Task> getAll() {
        return DataStore.getTasks();
    }

    public Task getById(UUID id) {
        return DataStore.getTasks()
                .stream()
                .filter(task -> task.getId() == id)
                .findFirst()
                .orElse(null);
    }

    public Task getByTitle(String title) {
        return DataStore.getTasks()
                .stream()
                .filter(t -> t.getTitle().toLowerCase().equals(title.trim().toLowerCase()))
                .findFirst()
                .orElse(null);
    }

    public void add(Task task) throws Exception {
        this.verifyTaskData(task);

        DataStore.addTasks(task);
    }

    public void update(Task task) throws Exception {
        this.verifyTaskData(task);

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

    public void delete(Task task) throws Exception {
        this.verifyTaskData(task);

        Task item = DataStore.getTasks()
                .stream()
                .filter(u -> u.getId() == task.getId())
                .findFirst()
                .orElse(null);

        DataStore.getTasks().remove(item);
    }

    public List<Task> getAllSubTasks(UUID taskId) {
        Task task = this.getById(taskId);
        if (task == null)
            return null;

        return task.getSubTasks();
    }

    public void addSubTask(UUID taskId, Task subTask) throws Exception {
        this.verifyTaskData(subTask);

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

        Task subTask = subTasks
                .stream()
                .filter(sp -> sp.getTitle().equals(subTaskTitle.trim().toLowerCase()))
                .findFirst()
                .orElse(null);

        if (subTask == null) {
            return;
        }

        subTasks.remove(subTask);

        task.setSubTasks(subTasks);
    }

    public Status getTaskStatus(UUID taskId) {
        Task task = this.getById(taskId);
        if (task == null) {
            return null;
        }

        return DataStore.getStatuses()
                .stream()
                .filter(s -> s.getId() == task.getStatusId())
                .findFirst()
                .orElse(null);
    }

    public User getTaskAssignee(UUID taskId) {
        Task task = this.getById(taskId);
        if (task == null) {
            return null;
        }

        return this.usersRepo.getAll()
                .stream()
                .filter(u -> u.getId() == task.getAssigneeId())
                .findFirst()
                .orElse(null);
    }

    public boolean doesTaskExist(Task task) {
        if (task == null) {
            return false;
        }

        Task t = DataStore.getTasks()
                .stream()
                .filter(ta -> ta.getTitle().toLowerCase().equals(task.getTitle().trim().toLowerCase()))
                .findFirst()
                .orElse(null);

        return t != null;
    }
    private void verifyTaskData(Task task) throws Exception {
        if (task == null) {
            throw new NullPointerException("Task is not defined. You should pass a valid object instance.");
        }

        if (task.getTitle().trim().isEmpty()) {
            throw new Exception("Task title cannot be an empty string.");
        }

        if (task.getDescription().trim().isEmpty()) {
            throw new Exception("Task description cannot be an empty string.");
        }
    }
}
