package todo.data;

import todo.models.Project;
import todo.models.Status;
import todo.models.Task;
import todo.models.User;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * DataStore class generates in-memory data for application
 */
public final class DataStore {
    private static ArrayList<User> users = null;
    private static ArrayList<Project> projects = null;
    private static ArrayList<Task> tasks = null;
    private static ArrayList<Status> statuses = null;

    /**
     * @return collection of {@link User}
     */
    public static List<User> getUsers() {
        return DataStore.users;
    }

    /**
     * @param user {@link User} that will be added to collection
     */
    public static void addUsers(User user) {
        DataStore.users.add(user);
    }

    /**
     * @return collection of {@link Project}
     */
    public static List<Project> getProjects() {
        return DataStore.projects;
    }

    /**
     * @param project {@link Project} that will be added to collection
     */
    public static void addProjects(Project project) {
        DataStore.projects.add(project);
    }

    /**
     * @return collection of {@link Task}
     */
    public static List<Task> getTasks() {
        return DataStore.tasks;
    }

    /**
     * @param task {@link Task} that will be added to collection
     */
    public static void addTasks(Task task) {
        DataStore.tasks.add(task);
    }

    /**
     * @return collection of {@link Status}
     */
    public static List<Status> getStatuses() {
        return DataStore.statuses;
    }

    /**
     * Generates data and populates collections
     */
    public static void generateData() {
        users = getUsersList();
        projects = getProjectsList();
        statuses = getStatusesList();
        tasks = getTasksList();
    }

    private static ArrayList<User> getUsersList() {
        return new ArrayList<> (Arrays.asList(
                new User("William Smith", "Manager"),
                new User("Amy Adams", "Developer"),
                new User("John Allan", "Developer")));
    }

    private static ArrayList<Project> getProjectsList() {
        ArrayList<Project> projects = new ArrayList<Project>() {{
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

    private static ArrayList<Task> getTasksList() {
        ArrayList<Task> tasks = new ArrayList<>();

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
        ArrayList<Task> subTasks = new ArrayList<>();
        subTasks.add(subTask);

        Task architectureTask = new Task("Java Architecture", "Getting familiar with Java architecture");
        configureTask(architectureTask);
        architectureTask.setSubTasks(subTasks);

        tasks.add(architectureTask);

        return tasks;
    }

    private static ArrayList<Status> getStatusesList() {
        return new ArrayList<> (Arrays.asList(
                new Status("To Do", "Not started"),
                new Status("In Progress", "In development"),
                new Status("Done", "Completed")));
    }

    private static void configureTask(Task subTask) {
        subTask.setProjectId(DataStore.projects.get(1).getId());
        subTask.setAssigneeId(DataStore.users.get(1).getId());
        subTask.setStatusId(DataStore.getStatuses().get(0).getId());
    }
}
