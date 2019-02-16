package data;

import models.Project;
import models.Status;
import models.Task;
import models.User;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class DataStore {
    private static List<User> users = null;
    private static List<Project> projects = null;
    private static List<Task> tasks = null;
    private static List<Status> statuses = null;

    public static List<User> getUsers() {
        return DataStore.users;
    }

    public static List<Project> getProjects() {
        return DataStore.projects;
    }

    public static List<Task> getTasks() {
        return DataStore.tasks;
    }

    public static List<Status> getStatuses() {
        return DataStore.statuses;
    }

    public static void generateData() {
        users = getUsersList();
        projects = getProjectsList();
        statuses = getStatusesList();
        tasks = getTasksList();
    }

    private static List<User> getUsersList() {
        return Arrays.asList(
                new User("William Smith", "Manager"),
                new User("Amy Adams", "Developer"),
                new User("John Allan", "Developer"));
    }

    private static List<Project> getProjectsList() {
        List<Project> projects = new ArrayList<Project>() {{
            add(new Project("Awesome", "Simple console Java project"));
            add(new Project("Summit", "Simple Java project with Hibernate"));
            add(new Project("Spring", "Setup Java project with Spring Boot"));
        }};

        List<Project> subProjects = new ArrayList<Project>() {{
            add(new Project("SilverMoon", "Simple console Java project"));
            add(new Project("GoldWays", "Simple Java project with Hibernate"));
            add(new Project("TheBees", "Setup Java project with Spring Boot"));
        }};

        Project project = projects.get(0);
        project.setUsers(DataStore.users);
        project.setSubProjects(subProjects);

        return projects;
    }

    private static List<Task> getTasksList() {
        List<Task> tasks = new ArrayList<>();

        Task javaTask = new Task("Java Path", "Getting familiar with Java");
        javaTask.setProjectId(DataStore.projects.get(0).getId());
        javaTask.setAssigneeId(DataStore.users.get(0).getId());
        javaTask.setStatusId(DataStore.getStatuses().get(1).getId());

        tasks.add(javaTask);

        Task javaSpringTask = new Task("Java Spring Path", "Getting familiar with Java and Spring");
        javaSpringTask.setProjectId(DataStore.projects.get(0).getId());
        javaSpringTask.setAssigneeId(DataStore.users.get(0).getId());
        javaSpringTask.setStatusId(DataStore.getStatuses().get(0).getId());

        tasks.add(javaSpringTask);

        Task subTask = new Task("Java DAL and Service layers", "Getting familiar with Java layers architecture");
        configureTask(subTask);

        Task architectureTask = new Task("Java Architecture", "Getting familiar with Java architecture");
        configureTask(architectureTask);
        architectureTask.setSubTasks(Arrays.asList(subTask));

        tasks.add(architectureTask);

        return tasks;
    }

    private static List<Status> getStatusesList() {
        return Arrays.asList(
                new Status("To Do", "Not started"),
                new Status("In Progress", "In development"),
                new Status("Done", "Completed")
        );
    }

    private static void configureTask(Task subTask) {
        subTask.setProjectId(DataStore.projects.get(1).getId());
        subTask.setAssigneeId(DataStore.users.get(1).getId());
        subTask.setStatusId(DataStore.getStatuses().get(0).getId());
    }
}
