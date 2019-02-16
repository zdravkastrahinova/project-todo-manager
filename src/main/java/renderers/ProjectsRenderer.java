package renderers;

import models.Project;
import repositories.ProjectsRepository;

import java.util.List;
import java.util.Scanner;

class ProjectsRenderer {
    private static Scanner sc;
    private ProjectsRepository projectsRepo;

    ProjectsRenderer(ProjectsRepository projectsRepo) {
        this.projectsRepo = projectsRepo;
    }

    void renderProjectsMenu() {
        System.out.println();
        System.out.println("Choose project operation");
        System.out.println("[1] List");
        System.out.println("[2] List SubProjects");
        System.out.println("[3] Add");
        System.out.println("[4] Add SubProject");
        System.out.println("[5] Update");
        System.out.println("[6] Delete");
        System.out.println("[7] Delete SubProjects");
        System.out.println("[0] Exit");

        sc = new Scanner(System.in);
        int option = parseConsoleInput(sc.nextLine());

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
            case 5:
                this.renderProjectUpdatingMenu();
                this.renderProjectsMenu();
            case 6:
                this.renderProjectDeletingMenu();
                this.renderProjectsMenu();
                break;
            case 7:
                this.renderSubProjectDeletingMenu();
                this.renderProjectsMenu();
                break;
            case 0:
                System.exit(0);
                break;
        }
    }

    private void renderProjectListingMenu() {
        List<Project> projects = projectsRepo.getAll();

        for (Project p : projects) {
            System.out.println(p.getTitle() + " - " + p.getDescription());
            System.out.println("Sub-projects: " + p.getSubProjects().stream().count());
        }
    }

    private void renderProjectAddingMenu() {
        try {
            System.out.println("Enter project data");
            System.out.println("Title:");
            String title = sc.nextLine();

            System.out.println("Description:");
            String description = sc.nextLine();

            Project project = new Project(title, description);

            if (projectsRepo.doesProjectExist(project)) {
                System.out.println("Project with the same title already exists.");
                this.renderProjectsMenu();
            }

            projectsRepo.add(project);

            System.out.println("Project was successfully added.");
        } catch (Exception ex) {
            System.out.println(ex.getMessage());

            renderProjectsMenu();
        }
    }

    private void renderProjectUpdatingMenu() {
        try {
            System.out.println("Enter title of the project you want to edit:");
            String title = sc.nextLine();

            this.validateUserInput(title);

            Project project = projectsRepo.getByTitle(title);
            this.verifyProjectExists(project);

            System.out.println("Enter new values for selected project");
            System.out.println("Title:");
            String titleToUpdate = sc.nextLine();

            System.out.println("Description:");
            String descriptionToUpdate = sc.nextLine();

            project.setTitle(titleToUpdate);
            project.setDescription(descriptionToUpdate);

            projectsRepo.update(project);
            System.out.println("Project was successfully updated.");
        } catch (Exception ex) {
            System.out.println(ex.getMessage());

            renderProjectsMenu();
        }
    }

    private void renderProjectDeletingMenu() {
        try {
            System.out.println("Enter title of the project you want to delete.");
            String title = sc.nextLine();

            this.validateUserInput(title);

            Project project = projectsRepo.getByTitle(title);
            this.verifyProjectExists(project);

            projectsRepo.delete(project);
            System.out.println("Project was successfully deleted.");
        } catch (Exception ex) {
            System.out.println(ex.getMessage());

            renderProjectsMenu();
        }
    }

    private void renderSubProjectListingMenu() {
        System.out.println("Enter project title you want to list all sub-projects");
        String projectTitle = sc.nextLine();

        this.validateUserInput(projectTitle);

        Project project = projectsRepo.getByTitle(projectTitle);
        this.verifyProjectExists(project);

        List<Project> subProjects = projectsRepo.getAllSubProjects(project.getId());
        System.out.println("Main project reference: " + project.getTitle() + " Sub-projects: " + subProjects.stream().count());
        for (Project p : subProjects) {
            System.out.println(p.getTitle() + " - " + p.getDescription());
        }

    }

    private void renderSubProjectAddingMenu() {
        try {
            System.out.println("Enter project title you want to add sub-project");
            String projectTitle = sc.nextLine();

            this.validateUserInput(projectTitle);

            Project project = projectsRepo.getByTitle(projectTitle);
            this.verifyProjectExists(project);

            System.out.println("Sub-project Title:");
            String title = sc.nextLine();

            System.out.println("Sub-project Description:");
            String description = sc.nextLine();

            Project subProject = new Project(title, description);

            projectsRepo.addSubProject(project.getId(), subProject);
            System.out.println("Sub-project was successfully added.");
        } catch (Exception ex) {
            System.out.println(ex.getMessage());

            renderProjectsMenu();
        }
    }

    private void renderSubProjectDeletingMenu() {
        try {
            System.out.println("Enter project title you want to remove sub-project");
            String projectTitle = sc.nextLine();

            this.validateUserInput(projectTitle);

            Project project = projectsRepo.getByTitle(projectTitle);
            this.verifyProjectExists(project);

            System.out.println("Enter sub-project title you want to remove");
            String subProjectTitle = sc.nextLine();

            this.validateUserInput(projectTitle);

            projectsRepo.removeSubProject(project.getId(), subProjectTitle);
            System.out.println("Sub-project was successfully removed.");
        } catch (Exception ex) {
            System.out.println(ex.getMessage());

            renderProjectsMenu();
        }
    }

    private void verifyProjectExists(Project project) {
        if (!projectsRepo.doesProjectExist(project)) {
            System.out.println("Project with this title does not exist.");
            this.renderProjectsMenu();
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
            this.renderProjectsMenu();
        }
    }
}
