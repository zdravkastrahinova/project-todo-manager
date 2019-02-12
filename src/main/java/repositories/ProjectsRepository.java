package repositories;

import data.DataStore;
import models.Project;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ProjectsRepository {
    private List<Project> projects;

    public ProjectsRepository() {
        this.projects = new DataStore().getProjectsList();
    }

    public List<Project> getAll() {
        return this.projects;
    }

    public Project getById(UUID id) {
        return this.projects.stream().filter(project -> project.getId() == id).findFirst().orElse(null);
    }

    public Project getByTitle(String title) {
        return this.projects.stream().filter(p -> p.getTitle().equals(title)).findFirst().orElse(null);
    }

    public void add(Project project) {
        this.projects.add(project);
    }

    public void update(Project project) {
        Project p = this.getById(project.getId());

        if (p == null) {
            return;
        }

        p.setTitle(project.getTitle());
        p.setDescription(project.getDescription());
        p.setUsers(project.getUsers());
        p.setTasks(project.getTasks());
        p.setSubProjects(project.getSubProjects());
    }

    public void delete(Project project) {
        this.projects.remove(project);
    }

    public List<Project> getAllSubProjects(UUID projectId) {
        Project project = this.getById(projectId);

        if (project == null)
            return null;

        return project.getSubProjects();
    }

    public void addSubProjects(UUID projectId, Project subProject) {
        Project project = this.getById(projectId);

        List<Project> subProjects = project.getSubProjects();

        if (subProjects == null) {
            subProjects = new ArrayList<>();
        }

        subProjects.add(subProject);

        project.setSubProjects(subProjects);
    }

    public void removeSubProjects(UUID projectId, Project subProject) {
        Project project = this.getById(projectId);

        List<Project> subProjects = project.getSubProjects();

        if (subProjects == null) {
            return;
        }

        subProjects.remove(subProject);

        project.setSubProjects(subProjects);
    }
}
