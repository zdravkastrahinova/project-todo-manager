package renderers;

import repositories.ProjectsRepository;
import repositories.TasksRepository;
import repositories.UsersRepository;

import java.util.Scanner;

public class MenuRenderer {
    private static Scanner sc;

    private UsersRepository usersRepo;
    private ProjectsRepository projectsRepo;
    private TasksRepository tasksRepo;

    public MenuRenderer() {
        usersRepo = new UsersRepository();
        projectsRepo = new ProjectsRepository();
        tasksRepo = new TasksRepository();
    }

    public void renderMainMenu() {
        System.out.println();
        System.out.println("Choose operation");
        System.out.println("[1] Users");
        System.out.println("[2] Projects");
        System.out.println("[3] Tasks");
        System.out.println("[0] Exit");

        sc = new Scanner(System.in);
        int option = Integer.parseInt(sc.nextLine());

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
        }
    }
}
