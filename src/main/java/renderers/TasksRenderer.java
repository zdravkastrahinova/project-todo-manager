package renderers;

import data.DataStore;
import models.Status;
import models.Task;
import models.User;
import repositories.TasksRepository;
import repositories.UsersRepository;

import java.util.List;
import java.util.Scanner;

public class TasksRenderer {
    private static Scanner sc;
    private UsersRepository usersRepo;
    private TasksRepository tasksRepo;

    public TasksRenderer(UsersRepository usersRepo, TasksRepository tasksRepo) {
        this.usersRepo = usersRepo;
        this.tasksRepo = tasksRepo;
    }

    public void renderTasksMenu() {
        System.out.println();
        System.out.println("Choose operation");
        System.out.println("[1] List Tasks");
        System.out.println("[2] List SubTasks");
        System.out.println("[3] Add Task");
        System.out.println("[4] Add SubTask");
        System.out.println("[5] Update Task");
        System.out.println("[6] Delete Task");
        System.out.println("[7] Delete SubTasks");
        System.out.println("[0] Exit");

        sc = new Scanner(System.in);
        int option = Integer.parseInt(sc.nextLine());

        switch (option) {
            case 1:
                this.renderTaskListingMenu();
                this.renderTasksMenu();
                break;
            case 2:
                this.renderSubTaskListingMenu();
                this.renderTasksMenu();
                break;
            case 3:
                this.renderTaskAddingMenu();
                this.renderTasksMenu();
                break;
            case 4:
                this.renderSubTaskAddingMenu();
                this.renderTasksMenu();
                break;
            case 5:
                this.renderTaskUpdatingMenu();
                this.renderTasksMenu();
            case 6:
                this.renderTaskDeletingMenu();
                this.renderTasksMenu();
                break;
            case 7:
                this.renderSubTaskDeletingMenu();
                this.renderTasksMenu();
                break;
            case 0:
                System.exit(0);
                break;
        }
    }

    public void renderTaskListingMenu() {
        List<Task> tasks = tasksRepo.getAll();

        for (Task t : tasks) {
            System.out.println(t.getTitle() + " - " + t.getDescription());

            Status status = this.tasksRepo.getTaskStatus(t.getId());
            if (status != null) {
                System.out.println("Status: " + status.getName());
                System.out.println("Description: " + status.getDescription());
            }

            User user = this.tasksRepo.getTaskAssignee(t.getId());
            if (user != null) {
                System.out.println("Assignee: " + user.getName());
            }

            System.out.println("Sub-tasks: " + t.getSubTasks().stream().count());
            System.out.println();
        }
    }

    public void renderTaskAddingMenu() {
        System.out.println("Task Title:");
        String title = sc.nextLine();

        System.out.println("Task Description:");
        String description = sc.nextLine();

        Task task = new Task(title, description);

        System.out.println("Task Assignee:");
        String username = sc.nextLine();

        User user = usersRepo.getByName(username);
        if (user != null) {
            task.setAssigneeId(user.getId());
        }

        Status status = this.assignStatusToTask();
        if (status != null) {
            System.out.println("Add status description");
            String statusDescription = sc.nextLine();

            status.setDescription(statusDescription);
            task.setStatusId(status.getId());
        }

        tasksRepo.add(task);
        System.out.println("Task was successfully added.");
    }

    public void renderTaskUpdatingMenu() {
        System.out.println("Enter title of the task you want to edit");
        String title = sc.nextLine();

        Task task = tasksRepo.getByTitle(title);
        verifyTask(task);

        System.out.println("Enter new values for selected task");
        System.out.println("Title:");
        String titleToUpdate = sc.nextLine();

        System.out.println("Description: ");
        String descriptionToUpdate = sc.nextLine();

        task.setTitle(titleToUpdate);
        task.setDescription(descriptionToUpdate);

        System.out.println("Do you want to change task assignee? Please enter 'yes' or 'no'");
        String changeAssigneeAnswer = sc.nextLine();

        if (changeAssigneeAnswer.trim().toLowerCase().equals("yes")) {
            System.out.println("Task Assignee:");
            String usernameToUpdate = sc.nextLine();

            User userToUpdate = usersRepo.getByName(usernameToUpdate);
            if (usernameToUpdate != null) {
                task.setAssigneeId(userToUpdate.getId());
            }
        }

        System.out.println("Do you want to update task status? Please enter 'yes' or 'no'");
        String changeStatusAnswer = sc.nextLine();

        if (changeStatusAnswer.trim().toLowerCase().equals("yes")) {
            Status status = this.assignStatusToTask();

            if (status != null) {
                System.out.println("Add status description");
                String statusDescription = sc.nextLine();

                status.setDescription(statusDescription);
                task.setStatusId(status.getId());
            }
        }

        tasksRepo.update(task);
        System.out.println("Task was successfully updated.");
    }

    public void renderTaskDeletingMenu() {
        System.out.println("Enter title of the task you want to delete.");
        String title = sc.nextLine();

        Task task = tasksRepo.getByTitle(title);
        verifyTask(task);

        tasksRepo.delete(task);
        System.out.println("Task was successfully deleted.");
    }

    public void renderSubTaskListingMenu() {
        System.out.println("Enter task title you want to list all sub-tasks");
        String taskTitle = sc.nextLine();

        Task task = tasksRepo.getByTitle(taskTitle);
        verifyTask(task);

        List<Task> subTasks = tasksRepo.getAllSubTasks(task.getId());
        System.out.println("Main task reference: " + task.getTitle() + " Sub-tasks: " + subTasks.stream().count());
        for (Task t : subTasks) {
            System.out.println(t.getTitle() + " - " + t.getDescription());

            Status status = this.tasksRepo.getTaskStatus(t.getId());
            if (status != null) {
                System.out.println("Status: " + status.getName());
                System.out.println("Description: " + status.getDescription());
            }

            User user = this.tasksRepo.getTaskAssignee(t.getId());
            if (user != null) {
                System.out.println("Assignee: " + user.getName());
            }
        }
    }

    public void renderSubTaskAddingMenu() {
        System.out.println("Enter task title you want to add sub-task");
        String taskTitle = sc.nextLine();

        Task task = tasksRepo.getByTitle(taskTitle);
        verifyTask(task);

        System.out.println("Sub-task Title:");
        String title = sc.nextLine();

        System.out.println("Sub-task Description:");
        String description = sc.nextLine();

        Task subTask = new Task(title, description);

        System.out.println("Task Assignee:");
        String username = sc.nextLine();

        User user = usersRepo.getByName(username);
        if (user != null) {
            task.setAssigneeId(user.getId());
        }

        Status status = this.assignStatusToTask();
        if (status != null) {
            System.out.println("Add status description");
            String statusDescription = sc.nextLine();

            status.setDescription(statusDescription);
            subTask.setStatusId(status.getId());
        }

        tasksRepo.addSubTask(task.getId(), subTask);
        System.out.println("Sub-task was successfully added.");
    }

    public void renderSubTaskDeletingMenu() {
        System.out.println("Enter task title you want to remove sub-task");
        String taskTitle = sc.nextLine();

        Task task = tasksRepo.getByTitle(taskTitle);
        verifyTask(task);

        System.out.println("Enter sub-task title you want to remove");
        String subProjectTitle = sc.nextLine();

        tasksRepo.removeSubTask(task.getId(), subProjectTitle);
        System.out.println("Sub-task was successfully removed.");
    }

    private void verifyTask(Task task) {
        if (task == null) {
            System.out.println("Task with this title does not exist");
            renderTasksMenu();
        }
    }

    private Status assignStatusToTask() {
        System.out.println("Select Status:");
        System.out.println("[1] To Do");
        System.out.println("[2] In Progress");
        System.out.println("[3] Done");
        int statusOption = Integer.parseInt(sc.nextLine());

        Status status;
        switch (statusOption) {
            case 2:
                status = DataStore.getStatuses().stream().filter(s -> s.getName().equals("In Progress")).findFirst().orElse(null);
                break;
            case 3:
                status = DataStore.getStatuses().stream().filter(s -> s.getName().equals("Done")).findFirst().orElse(null);
                break;
            case 1:
            default:
                status = DataStore.getStatuses().stream().filter(s -> s.getName().equals("To Do")).findFirst().orElse(null);
                break;
        }

        return status;
    }
}
