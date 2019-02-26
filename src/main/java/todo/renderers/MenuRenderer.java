package todo.renderers;

import todo.repositories.ProjectsRepository;
import todo.repositories.TasksRepository;
import todo.repositories.UsersRepository;

import java.util.Scanner;

/**
 * MainMenu renderer
 */
public class MenuRenderer {
    private UsersRepository usersRepo;
    private ProjectsRepository projectsRepo;
    private TasksRepository tasksRepo;

    public MenuRenderer() {
        usersRepo = new UsersRepository();
        projectsRepo = new ProjectsRepository();
        tasksRepo = new TasksRepository();
    }

    /**
     * Allows user to select sub-menu
     */
    public void renderMainMenu() {
        System.out.println();
        System.out.println("Choose operation");
        System.out.println("[1] Users");
        System.out.println("[2] Projects");
        System.out.println("[3] Tasks");
        System.out.println("[0] Exit");

        Scanner sc = new Scanner(System.in);
        int option = parseConsoleInput(sc.nextLine());

        switch (option) {
            case 1:
                UsersRenderer usersRenderer = new UsersRenderer(usersRepo);
                usersRenderer.renderUsersMenu();
                break;
            case 2:
                ProjectsRenderer projectsRenderer = new ProjectsRenderer(projectsRepo);
                projectsRenderer.renderProjectsMenu();
                break;
            case 3:
                TasksRenderer tasksRenderer = new TasksRenderer(usersRepo, tasksRepo);
                tasksRenderer.renderTasksMenu();
                break;
            case 0:
                System.exit(0);
                break;
            default:
                renderMainMenu();
                break;
        }
    }

    private int parseConsoleInput(String value) {
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException ex) {
            return 4;
        }
    }
}
