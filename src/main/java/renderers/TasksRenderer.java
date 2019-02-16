package renderers;

import data.DataStore;
import models.Status;
import models.Task;
import models.User;
import repositories.TasksRepository;
import repositories.UsersRepository;

import java.util.List;
import java.util.Scanner;

class TasksRenderer {
    private static Scanner sc;
    private UsersRepository usersRepo;
    private TasksRepository tasksRepo;

    TasksRenderer(UsersRepository usersRepo, TasksRepository tasksRepo) {
        this.usersRepo = usersRepo;
        this.tasksRepo = tasksRepo;
    }

    void renderTasksMenu() {
        System.out.println();
        System.out.println("Choose task operation");
        System.out.println("[1] List");
        System.out.println("[2] List SubTasks");
        System.out.println("[3] Add");
        System.out.println("[4] Add SubTask");
        System.out.println("[5] Update");
        System.out.println("[6] Delete");
        System.out.println("[7] Delete SubTasks");
        System.out.println("[0] Exit");

        sc = new Scanner(System.in);
        int option = this.parseConsoleInput(sc.nextLine());

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

    private void renderTaskListingMenu() {
        List<Task> tasks = tasksRepo.getAll();

        for (Task t : tasks) {
            displayTaskDetails(t);

            System.out.println("Sub-tasks: " + t.getSubTasks().stream().count());
            System.out.println();
        }
    }

    private void renderTaskAddingMenu() {
        try {
            System.out.println("Enter title data");
            System.out.println("Title:");
            String title = sc.nextLine();

            System.out.println("Description:");
            String description = sc.nextLine();

            Task task = new Task(title, description);
            if (tasksRepo.doesTaskExist(task)) {
                System.out.println("Task with the same title already exists.");
                this.renderTasksMenu();
            }

            this.changeTaskAssignee(task);

            tasksRepo.add(task);
            System.out.println("Task was successfully added.");
        } catch (Exception ex) {
            System.out.println(ex.getMessage());

            this.renderTasksMenu();
        }
    }

    private void renderTaskUpdatingMenu() {
        try {
            System.out.println("Enter title of the task you want to edit");
            String title = sc.nextLine();

            this.validateUserInput(title);

            Task task = tasksRepo.getByTitle(title);
            this.verifyTaskExists(task);

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
                this.changeTaskAssignee(task);
            }

            System.out.println("Do you want to update task status? Please enter 'yes' or 'no'");
            String changeStatusAnswer = sc.nextLine();

            if (changeStatusAnswer.trim().toLowerCase().equals("yes")) {
                this.updateTaskStatus(task);
            }

            tasksRepo.update(task);
            System.out.println("Task was successfully updated.");
        } catch (Exception ex) {
            System.out.println(ex.getMessage());

            this.renderTasksMenu();
        }
    }

    private void renderTaskDeletingMenu() {
        try {
            System.out.println("Enter title of the task you want to delete.");
            String title = sc.nextLine();

            this.validateUserInput(title);

            Task task = tasksRepo.getByTitle(title);
            this.verifyTaskExists(task);

            tasksRepo.delete(task);
            System.out.println("Task was successfully deleted.");
        } catch (Exception ex) {
            System.out.println(ex.getMessage());

            this.renderTasksMenu();
        }
    }

    private void renderSubTaskListingMenu() {
        System.out.println("Enter task title you want to list all sub-tasks");
        String taskTitle = sc.nextLine();

        this.validateUserInput(taskTitle);

        Task task = tasksRepo.getByTitle(taskTitle);
        this.verifyTaskExists(task);

        List<Task> subTasks = tasksRepo.getAllSubTasks(task.getId());
        System.out.println("Main task reference: " + task.getTitle() + " Sub-tasks: " + subTasks.stream().count());
        for (Task t : subTasks) {
            displayTaskDetails(t);
        }
    }

    private void renderSubTaskAddingMenu() {
        try {
            System.out.println("Enter task title you want to add sub-task");
            String taskTitle = sc.nextLine();

            this.validateUserInput(taskTitle);

            Task task = tasksRepo.getByTitle(taskTitle);
            this.verifyTaskExists(task);

            System.out.println("Sub-task Title:");
            String title = sc.nextLine();

            System.out.println("Sub-task Description:");
            String description = sc.nextLine();

            Task subTask = new Task(title, description);
            this.verifyTaskExists(subTask);

            this.changeTaskAssignee(subTask);

            tasksRepo.addSubTask(task.getId(), subTask);
            System.out.println("Sub-task was successfully added.");
        } catch (Exception ex) {
            System.out.println(ex.getMessage());

            this.renderTasksMenu();
        }
    }

    public void renderSubTaskDeletingMenu() {
        System.out.println("Enter task title you want to remove sub-task");
        String taskTitle = sc.nextLine();

        this.validateUserInput(taskTitle);

        Task task = tasksRepo.getByTitle(taskTitle);
        this.verifyTaskExists(task);

        System.out.println("Enter sub-task title you want to remove");
        String subTaskTitle = sc.nextLine();

        this.validateUserInput(subTaskTitle);

        tasksRepo.removeSubTask(task.getId(), subTaskTitle);
        System.out.println("Sub-task was successfully removed.");
    }

    private void verifyTaskExists(Task task) {
        if (!tasksRepo.doesTaskExist(task)) {
            System.out.println("Task with this title does not exist.");
            this.renderTasksMenu();
        }
    }

    private int parseConsoleInput(String value) {
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException ex) {
            return 0;
        }
    }

    private void validateUserInput(String value) {
        if (value == null || value.isEmpty()) {
            System.out.println("Value cannot be an empty string.");
            this.renderTasksMenu();
        }
    }

    private void displayTaskDetails(Task t) {
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

    private void changeTaskAssignee(Task subTask) {
        System.out.println("Task Assignee:");
        String username = sc.nextLine();

        this.validateUserInput(username);

        User user = usersRepo.getByName(username);
        if (user != null) {
            subTask.setAssigneeId(user.getId());
        }

        this.updateTaskStatus(subTask);
    }

    private void updateTaskStatus(Task task) {
        Status status = this.assignStatusToTask();

        if (status != null) {
            System.out.println("Add status description");
            String description = sc.nextLine();

            this.validateUserInput(description);

            status.setDescription(description);
            task.setStatusId(status.getId());
        }
    }

    private Status assignStatusToTask() {
        System.out.println("Select Status:");
        System.out.println("[1] To Do");
        System.out.println("[2] In Progress");
        System.out.println("[3] Done");

        int statusOption = this.parseConsoleInput(sc.nextLine());

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
