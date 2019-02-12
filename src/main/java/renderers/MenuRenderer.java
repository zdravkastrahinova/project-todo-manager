package renderers;

import models.Project;
import models.User;
import repositories.ProjectsRepository;
import repositories.UsersRepository;

import java.util.List;
import java.util.Scanner;

public class MenuRenderer {
    private static Scanner sc;

    private UsersRepository usersRepo;
    private ProjectsRepository projectsRepo;

    public MenuRenderer() {
        usersRepo = new UsersRepository();
        projectsRepo = new ProjectsRepository();
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
            case 0:
                System.exit(0);
                break;
        }
    }
}
