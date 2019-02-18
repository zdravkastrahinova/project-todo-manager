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
    private ProjectsRepository projectRepo;

    @Before
    public void setUp() {
        DataStore.generateData();
        this.projectRepo = new ProjectsRepository();
    }

    @After
    public void tearDown() {
    }

    @Test
    public void getAll() {
        List<Project> projects = this.projectRepo.getAll();

        Assert.assertNotNull("Returns a list of projects that is not null", projects);
        Assert.assertEquals("Returns a list of 3 projects", 3, projects.size());
    }

    @Test
    public void getByIdWithValidUuidReturnsProject() {
        UUID id = this.projectRepo.getAll().get(0).getId();

        Assert.assertNotNull("With valid UUID returns project that is not null", this.projectRepo.getById(id));
    }

    @Test
    public void getByIdWithInvalidUuidReturnsNull() {
        Assert.assertNull("With invalid UUID returns null", this.projectRepo.getById(UUID.randomUUID()));
    }

    @Test
    public void getByTitleWithValidTitleReturnsProject() {
        String title = this.projectRepo.getAll().get(0).getTitle();

        Assert.assertNotNull("With valid title returns project that is not null", this.projectRepo.getByTitle(title));
        Assert.assertNull("With incorrect UUID should return null", this.projectRepo.getByTitle("test-title"));
    }

    @Test
    public void getByTitleWithInvalidTitleReturnsProject() {
        Assert.assertNull("With invalid title returns null", this.projectRepo.getByTitle("test-title"));
    }

    @Test
    public void addWithValidDataAddsProject() {
        List<Project> projects = this.projectRepo.getAll();
        Assert.assertEquals("Initially, the projects count should be 3", 3, projects.size());

        Project projectMock = Mockito.mock(Project.class);
        when(projectMock.getTitle()).thenReturn("Mock title");
        when(projectMock.getDescription()).thenReturn("Mock description");
        when(projectMock.getUsers()).thenReturn(DataStore.getUsers());
        when(projectMock.getTasks()).thenReturn(new ArrayList<>());

        try {
            this.projectRepo.add(projectMock);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        projects = this.projectRepo.getAll();
        Assert.assertEquals("Finally, the projects count should be 4", 4, projects.size());
    }

    @Test
    public void addWhenProjectIsNullThrowsNullPointerException() {
        try {
            this.projectRepo.add(null);
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

            this.projectRepo.add(projectMock);
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

            this.projectRepo.add(projectMock);
            fail();
        } catch (Exception ex) {
            assertEquals("Project description cannot be an empty string.", ex.getMessage());
            assertTrue(true);
        }
    }

    @Test
    public void update() {

    }

    @Test
    public void delete() {
    }

    @Test
    public void getAllSubProjects() {
    }

    @Test
    public void addSubProject() {
    }

    @Test
    public void removeSubProject() {
    }
}
