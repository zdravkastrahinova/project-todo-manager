package renderers;

import models.Project;
import repositories.ProjectsRepository;

import java.util.List;
import java.util.Scanner;

public class ProjectsRenderer {
    private static Scanner sc;
    private ProjectsRepository projectsRepo;

    public ProjectsRenderer(ProjectsRepository projectsRepo) {
        this.projectsRepo = projectsRepo;
    }

    public void renderProjectsMenu() {
        System.out.println();
        System.out.println("Choose operation");
        System.out.println("[1] List Projects");
        System.out.println("[2] List SubProjects");
        System.out.println("[3] Add Projects");
        System.out.println("[4] Add SubProjects");
        System.out.println("[5] Update Projects");
        System.out.println("[6] Delete Projects");
        System.out.println("[0] Exit");

        sc = new Scanner(System.in);
        int option = Integer.parseInt(sc.nextLine());

        switch (option) {
            case 1:
                this.renderProjectListingMenu();
                this.renderProjectsMenu();
                break;
            case 2:
                this.renderSubProjectListingMenu();
                this.renderProjectsMenu();
                break;
            case 3:
                this.renderProjectAddingMenu();
                this.renderProjectsMenu();
                break;
            case 4:
                this.renderSubProjectAddingMenu();
                this.renderProjectsMenu();
                break;
            case 0:
                System.exit(0);
                break;
        }
    }

    public void renderProjectListingMenu() {
        List<Project> projects = projectsRepo.getAll();

        for (Project p : projects) {
            System.out.println(p.getTitle() + " - " + p.getDescription());
            System.out.println("Sub-projects: " + p.getSubProjects().stream().count());
        }
    }

    public void renderProjectAddingMenu() {
        System.out.println("Project Title:");
        String title = sc.nextLine();

        System.out.println("Project Description:");
        String description = sc.nextLine();

        Project project = new Project(title, description);

        projectsRepo.add(project);
        System.out.println("Project was successfully added.");
    }

    public void renderSubProjectListingMenu() {
        System.out.println("Enter project title you want to list all sub-projects");
        String projectTitle = sc.nextLine();

        Project project = projectsRepo.getByTitle(projectTitle);
        if (project == null) {
            System.out.println("Project with this title does not exist");
            renderProjectsMenu();
        }

        List<Project> subProjects = projectsRepo.getAllSubProjects(project.getId());
        System.out.println("Sub-projects: " + subProjects.stream().count());
        for (Project p : subProjects) {
            System.out.println(p.getTitle() + " - " + p.getDescription());
        }

        System.out.println("Main project reference: " + project.getTitle());
    }

    public void renderSubProjectAddingMenu() {
        System.out.println("Enter project title you want to add sub-project");
        String projectTitle = sc.nextLine();

        Project project = projectsRepo.getByTitle(projectTitle);
        if (project == null) {
            System.out.println("Project with this title does not exist");
            renderProjectsMenu();
        }

        System.out.println("Sub-project Title:");
        String title = sc.nextLine();

        System.out.println("Sub-project Description:");
        String description = sc.nextLine();

        Project subProject = new Project(title, description);

        projectsRepo.addSubProjects(project.getId(), subProject);
        System.out.println("Sub-project was successfully added.");
    }
}
