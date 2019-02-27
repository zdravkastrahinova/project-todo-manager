package todo.renderers;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import todo.models.Task;
import todo.repositories.TasksRepository;
import todo.repositories.UsersRepository;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;

public class TasksRendererTest {
    private TasksRenderer renderer;
    private TasksRepository tasksRepoMock;
    private UsersRepository usersRepoMock;

    @Before
    public void setUp() {
        this.tasksRepoMock = Mockito.mock(TasksRepository.class);
        this.usersRepoMock = Mockito.mock(UsersRepository.class);
        this.renderer = new TasksRenderer(this.usersRepoMock, this.tasksRepoMock);
    }

    @After
    public void tearDown() {
        this.tasksRepoMock = null;
        this.usersRepoMock = null;
    }

    @Test
    public void renderTaskListingMenuRendersTasks() {
        List<Task> tasks = new ArrayList<>();
        tasks.add(new Task("Java Path", "Getting familiar with Java"));
        tasks.add(new Task("Java Spring Path", "Getting familiar with Java and Spring"));
        when(this.tasksRepoMock.getAll()).thenReturn(tasks);

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        this.renderer.renderTaskListingMenu();

        Assert.assertEquals("Java Path - Getting familiar with Java\r\nSub-tasks: 0\r\n\r\n" +
                        "Java Spring Path - Getting familiar with Java and Spring\r\nSub-tasks: 0\r\n\r\n",
                outContent.toString());
    }
}
