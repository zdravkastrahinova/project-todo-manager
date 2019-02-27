package todo.renderers;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import todo.models.Project;
import todo.repositories.ProjectsRepository;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;

public class ProjectsRendererTest {
    private ProjectsRenderer renderer;
    private ProjectsRepository projectsRepoMock;

    @Before
    public void setUp() {
        this.projectsRepoMock = Mockito.mock(ProjectsRepository.class);
        this.renderer = new ProjectsRenderer(this.projectsRepoMock);
    }

    @After
    public void tearDown() {
        this.projectsRepoMock = null;
    }

    @Test
    public void renderProjectListingMenuRendersProjects() {
        List<Project> projects = new ArrayList<>();
        projects.add(new Project("Awesome", "Simple console Java project"));
        projects.add(new Project("Summit", "Simple Java project with Hibernate"));
        when(this.projectsRepoMock.getAll()).thenReturn(projects);

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        this.renderer.renderProjectListingMenu();

        Assert.assertEquals("Awesome - Simple console Java project\r\nSub-projects: 0\r\n" +
                        "Summit - Simple Java project with Hibernate\r\nSub-projects: 0\r\n",
                outContent.toString());
    }
}
