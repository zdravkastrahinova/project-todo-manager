package repositories;

import data.DataStore;
import models.Project;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Repository class that defines methods to manipulate {@link Project} model objects
 */
public class ProjectsRepository {
    public ProjectsRepository() {
    }

    /**
     * @return collection of {@link Project}
     */
    public List<Project> getAll() {
        return DataStore.getProjects();
    }

    /**
     * @param id id of project we are looking for
     * @return {@link Project} with provided id or null if project does not exist
     */
    public Project getById(UUID id) {
        return DataStore.getProjects()
                .stream()
                .filter(project -> project.getId() == id)
                .findFirst()
                .orElse(null);
    }

    /**
     * @param title title of project we are looking for
     * @return {@link Project} with provided title or null if project does not exist
     */
    public Project getByTitle(String title) {
        return DataStore.getProjects()
                .stream()
                .filter(p -> p.getTitle().toLowerCase().equals(title.trim().toLowerCase()))
                .findFirst()
                .orElse(null);
    }

    /**
     * @param project {@link Project} that will be created
     * @throws NullPointerException when project is null
     * @throws Exception            when project title or description is null
     */
    public void add(Project project) throws Exception {
        this.verifyProjectData(project);

        DataStore.addProjects(project);
    }

    /**
     * @param project {@link Project} that will be updated
     * @throws NullPointerException when project is null
     * @throws Exception            when project title or description is null
     */
    public void update(Project project) throws Exception {
        this.verifyProjectData(project);

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

    /**
     * @param project {@link Project} that will be deleted
     * @throws NullPointerException when project is null
     * @throws Exception            when project title or description is null
     */
    public void delete(Project project) throws Exception {
        this.verifyProjectData(project);

        Project item = DataStore.getProjects()
                .stream()
                .filter(u -> u.getId() == project.getId())
                .findFirst()
                .orElse(null);

        DataStore.getProjects().remove(item);
    }

    /**
     * @param projectId id of project we are looking for
     * @return collection of {@link Project} assigned to project with provided id
     */
    public List<Project> getAllSubProjects(UUID projectId) {
        Project project = this.getById(projectId);
        if (project == null)
            return null;

        return project.getSubProjects();
    }

    /**
     * @param projectId  id of project we are looking for
     * @param subProject {@link Project} that will be created
     * @throws NullPointerException when project is null
     * @throws Exception            when project title or description is null
     */
    public void addSubProject(UUID projectId, Project subProject) throws Exception {
        this.verifyProjectData(subProject);

        Project project = this.getById(projectId);
        if (project == null) {
            return;
        }

        List<Project> subProjects = project.getSubProjects();
        if (subProjects == null) {
            subProjects = new ArrayList<>();
        }

        subProjects.add(subProject);

        project.setSubProjects(subProjects);
    }

    /**
     * @param projectId       id of project we are looking for
     * @param subProjectTitle title of sub-project that will be removed
     */
    public void removeSubProject(UUID projectId, String subProjectTitle) {
        Project project = this.getById(projectId);
        if (project == null) {
            return;
        }

        List<Project> subProjects = project.getSubProjects();
        if (subProjects == null) {
            return;
        }

        Project subProject = subProjects
                .stream()
                .filter(sp -> sp.getTitle().toLowerCase().equals(subProjectTitle.trim().toLowerCase()))
                .findFirst()
                .orElse(null);

        if (subProject == null) {
            return;
        }

        subProjects.remove(subProject);

        project.setSubProjects(subProjects);
    }

    /**
     * @param project {@link Project} that will be verified
     * @return false if project is null
     */
    public boolean doesProjectExist(Project project) {
        if (project == null) {
            return false;
        }

        Project p = DataStore.getProjects()
                .stream()
                .filter(pr -> pr.getTitle().toLowerCase().equals(project.getTitle().trim().toLowerCase()))
                .findFirst()
                .orElse(null);

        return p != null;
    }

    private void verifyProjectData(Project project) throws Exception {
        if (project == null) {
            throw new NullPointerException("Project is not defined. You should pass a valid object instance.");
        }

        if (project.getTitle().trim().isEmpty()) {
            throw new Exception("Project title cannot be an empty string.");
        }

        if (project.getDescription().trim().isEmpty()) {
            throw new Exception("Project description cannot be an empty string.");
        }
    }
}
