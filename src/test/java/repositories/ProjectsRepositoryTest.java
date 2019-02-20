package repositories;

import data.DataStore;
import models.Project;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

public class ProjectsRepositoryTest {
    private ProjectsRepository projectsRepo;

    @Before
    public void setUp() {
        DataStore.generateData();
        this.projectsRepo = new ProjectsRepository();
    }

    @After
    public void tearDown() {
    }

    @Test
    public void getAll() {
        List<Project> projects = this.projectsRepo.getAll();

        Assert.assertNotNull("Returns a list of projects that is not null", projects);
        Assert.assertEquals("Returns a list of 3 projects", 3, projects.size());
    }

    @Test
    public void getByIdWithValidUuidReturnsProject() {
        UUID id = this.projectsRepo.getAll().get(0).getId();

        Assert.assertNotNull("With valid UUID returns project that is not null", this.projectsRepo.getById(id));
    }

    @Test
    public void getByIdWithInvalidUuidReturnsNull() {
        Assert.assertNull("With invalid UUID returns null", this.projectsRepo.getById(UUID.randomUUID()));
    }

    @Test
    public void getByTitleWithValidTitleReturnsProject() {
        String title = this.projectsRepo.getAll().get(0).getTitle();

        Assert.assertNotNull("With valid title returns project that is not null", this.projectsRepo.getByTitle(title));
    }

    @Test
    public void getByTitleWithInvalidTitleReturnsNull() {
        Assert.assertNull("With invalid title returns null", this.projectsRepo.getByTitle("test-title"));
    }

    @Test
    public void addWithValidDataAddsProject() {
        List<Project> projects = this.projectsRepo.getAll();
        Assert.assertEquals("Initially, the projects count should be 3", 3, projects.size());

        Project projectMock = Mockito.mock(Project.class);
        when(projectMock.getTitle()).thenReturn("Mock title");
        when(projectMock.getDescription()).thenReturn("Mock description");
        when(projectMock.getUsers()).thenReturn(DataStore.getUsers());
        when(projectMock.getTasks()).thenReturn(new ArrayList<>());

        try {
            this.projectsRepo.add(projectMock);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        projects = this.projectsRepo.getAll();
        Assert.assertEquals("Finally, the projects count should be 4", 4, projects.size());
    }

    @Test
    public void addWhenProjectIsNullThrowsNullPointerException() {
        try {
            this.projectsRepo.add(null);
            fail();
        } catch (Exception ex) {
            assertEquals("Project is not defined. You should pass a valid object instance.", ex.getMessage());
            assertTrue(ex instanceof NullPointerException);
        }
    }

    @Test
    public void addWhenProjectTitleIsEmptyThrowsException() {
        try {
            Project projectMock = Mockito.mock(Project.class);
            when(projectMock.getTitle()).thenReturn("");
            when(projectMock.getDescription()).thenReturn("Mock project description");

            this.projectsRepo.add(projectMock);
            fail();
        } catch (Exception ex) {
            assertEquals("Project title cannot be an empty string.", ex.getMessage());
            assertTrue(true);
        }
    }

    @Test
    public void addWhenProjectDescriptionIsEmptyThrowsException() {
        try {
            Project projectMock = Mockito.mock(Project.class);
            when(projectMock.getTitle()).thenReturn("Mock project title");
            when(projectMock.getDescription()).thenReturn("");

            this.projectsRepo.add(projectMock);
            fail();
        } catch (Exception ex) {
            assertEquals("Project description cannot be an empty string.", ex.getMessage());
            assertTrue(true);
        }
    }

    @Test
    public void updateWithExistingUuidValidDataUpdatesProject() {
        UUID id = DataStore.getProjects().get(0).getId();

        Project projectMock = Mockito.mock(Project.class);
        when(projectMock.getId()).thenReturn(id);
        when(projectMock.getTitle()).thenReturn("Updated project title");
        when(projectMock.getDescription()).thenReturn("Updated project description");

        try {
            projectsRepo.update(projectMock);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        Project project = this.projectsRepo.getById(id);
        Assert.assertEquals(projectMock.getTitle(), project.getTitle());
        Assert.assertEquals(projectMock.getDescription(), project.getDescription());
    }

    @Test
    public void updateWithNonExistingUuidAndValidDataReturnsWithoutAnyChanges() {
        UUID id = UUID.randomUUID();

        Project projectMock = Mockito.mock(Project.class);
        when(projectMock.getId()).thenReturn(id);
        when(projectMock.getTitle()).thenReturn("Updated project title");
        when(projectMock.getDescription()).thenReturn("Updated project description");

        try {
            this.projectsRepo.update(projectMock);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        Project project = this.projectsRepo.getAll()
                .stream()
                .filter(u -> u.getTitle().equals(projectMock.getTitle()))
                .findFirst()
                .orElse(null);

        Assert.assertNull(project);
    }

    @Test
    public void deleteWithValidDataRemovesProject() {
        Assert.assertEquals("Initially, the projects count should be 3", 3, this.projectsRepo.getAll().size());

        Project p = DataStore.getProjects().get(0);

        Project projectMock = Mockito.mock(Project.class);
        when(projectMock.getId()).thenReturn(p.getId());
        when(projectMock.getTitle()).thenReturn(p.getTitle());
        when(projectMock.getDescription()).thenReturn(p.getDescription());

        try {
            this.projectsRepo.delete(projectMock);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        Assert.assertEquals("Finally, the projects count should be 2", 2, this.projectsRepo.getAll().size());
        Assert.assertNull("With valid UUID returns null after project has been removed", this.projectsRepo.getById(projectMock.getId()));
    }

    @Test
    public void doesProjectExistWhenProjectIsNullReturnsFalse() {
        Assert.assertFalse("Project does not exist because it is null", this.projectsRepo.doesProjectExist(null));
    }

    @Test
    public void doesProjectExistWithExistingProjectNameReturnsTrue() {
        Project projectMock = Mockito.mock(Project.class);
        when(projectMock.getTitle()).thenReturn(DataStore.getProjects().get(0).getTitle());

        Assert.assertTrue("Project exists because title belongs to project", this.projectsRepo.doesProjectExist(projectMock));
    }

    @Test
    public void doesProjectExistWithNonExistingProjectNameReturnsFalse() {
        Project projectMock = Mockito.mock(Project.class);
        when(projectMock.getTitle()).thenReturn("Non-existing project title");

        Assert.assertFalse("Project does not exist because title is does not belong to any project", this.projectsRepo.doesProjectExist(projectMock));
    }

    @Test
    public void getAllSubProjectsWithExistingProjectIdReturnsSubProjects() {
        List<Project> subProjects = this.projectsRepo.getAllSubProjects(DataStore.getProjects().get(0).getId());

        Assert.assertNotNull("Sub-project list with existing parent project UUID is not null", subProjects);
        Assert.assertEquals("Sub-project list count with existing parent project UUID is 3", 3, subProjects.size());
    }

    @Test
    public void getAllSubProjectsWithNonExistingProjectIdReturnsNull() {
        Assert.assertNull("Sub-projects list is null when parent project UUID belongs to non-existing project", this.projectsRepo.getAllSubProjects(UUID.randomUUID()));
    }

    @Test
    public void addSubProjectWithExistingProjectIdAndValidDataAddsSubProject() {
        Project project = DataStore.getProjects().get(0);
        Assert.assertEquals("Initially, project has defined 3 sub-projects", 3, project.getSubProjects().size());

        Project subProjectMock = Mockito.mock(Project.class);
        when(subProjectMock.getTitle()).thenReturn("Sub-project title");
        when(subProjectMock.getDescription()).thenReturn("Sub-project description");

        try {
            this.projectsRepo.addSubProject(project.getId(), subProjectMock);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        Assert.assertEquals("Finally, project has defined 4 sub-projects", 4, project.getSubProjects().size());
    }

    @Test
    public void addSubProjectWithNonExistingProjectIdAndValidDataReturnsWithoutAnyChanges() {
        UUID id = UUID.randomUUID();

        Project subProjectMock = Mockito.mock(Project.class);
        when(subProjectMock.getTitle()).thenReturn("Sub-project title");
        when(subProjectMock.getDescription()).thenReturn("Sub-project description");

        try {
            this.projectsRepo.addSubProject(id, subProjectMock);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        Assert.assertNull("Project is null because UUID does not belong to existing project", this.projectsRepo.getById(id));
    }

    @Test
    public void addSubProjectWithExistingProjectIdAndSubProjectsAreNullInitializesSubProjects() {
        Project project = DataStore.getProjects().get(2);
        project.setSubProjects(null);

        Assert.assertNull("Initially, sub-projects list is null", project.getSubProjects());

        Project subProjectMock = Mockito.mock(Project.class);
        when(subProjectMock.getTitle()).thenReturn("Sub-project title");
        when(subProjectMock.getDescription()).thenReturn("Sub-project description");

        try {
            this.projectsRepo.addSubProject(project.getId(), subProjectMock);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        Assert.assertNotNull("Finally, sub-projects list is not null", project.getSubProjects());
    }

    @Test
    public void removeSubProjectWithNonExistingProjectIdAndExistingSubProjectTitleReturnsWithoutAnyChanges() {
        UUID id = UUID.randomUUID();

        this.projectsRepo.removeSubProject(id, "Sub-project title");

        Assert.assertNull("Method returns because project is null", this.projectsRepo.getById(id));
    }

    @Test
    public void removeSubProjectWithExistingProjectIdAndSubProjectsAreNullReturnsWithoutAnyChanges() {
        Project project = DataStore.getProjects().get(2);
        project.setSubProjects(null);

        Assert.assertNull("Initially, sub-projects list is null", project.getSubProjects());

        this.projectsRepo.removeSubProject(project.getId(), "Sub-project title");

        Assert.assertNull("Finally, sub-projects list is null", project.getSubProjects());
    }

    @Test
    public void removeSubProjectWithExistingProjectIdAndNonExistingTitleReturnsWithoutChanges() {
        Project project = DataStore.getProjects().get(0);
        Assert.assertNotNull("Initially, project has defined sub-projects", project.getSubProjects());
        Assert.assertEquals("Initially, project has list with 3 sub-projects", 3, project.getSubProjects().size());

        this.projectsRepo.removeSubProject(project.getId(), "Non-existing title");

        Assert.assertNotNull("Finally, project has defined sub-projects", project.getSubProjects());
        Assert.assertEquals("Finally, project has list with 3 sub-projects", 3, project.getSubProjects().size());
    }

    @Test
    public void removeSubProjectWithExistingProjectIdAndExistingTitleRemovesSubProject() {
        Project project = DataStore.getProjects().get(0);
        Assert.assertNotNull("Initially, project has defined sub-projects", project.getSubProjects());
        Assert.assertEquals("Initially, project has list with 3 sub-projects", 3, project.getSubProjects().size());

        String subProjectTitle = "SilverMoon";
        this.projectsRepo.removeSubProject(project.getId(), subProjectTitle);

        Assert.assertEquals("Finally, project has list with 2 sub-projects", 2, project.getSubProjects().size());
        Assert.assertNull("Finally, existing title returns null after sub-project has been removed", this.projectsRepo.getByTitle(subProjectTitle));
    }
}
