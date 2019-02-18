package repositories;

import data.DataStore;
import models.Task;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.List;
import java.util.UUID;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

public class TasksRepositoryTest {
    private TasksRepository tasksRepo;

    @Before
    public void setUp() {
        DataStore.generateData();
        this.tasksRepo = new TasksRepository();
    }

    @After
    public void tearDown() {
    }

    @Test
    public void getAll() {
        List<Task> tasks = this.tasksRepo.getAll();

        Assert.assertNotNull("Returns a list of tasks that is not null", tasks);
        Assert.assertEquals("Returns a list of 3 tasks", 3, tasks.size());
    }

    @Test
    public void getByIdWithValidUuidReturnsTask() {
        UUID id = this.tasksRepo.getAll().get(0).getId();

        Assert.assertNotNull("With valid UUID returns task that is not null", this.tasksRepo.getById(id));
    }

    @Test
    public void getByIdWithInvalidUuidReturnsNull() {
        Assert.assertNull("With invalid UUID returns null", this.tasksRepo.getById(UUID.randomUUID()));
    }

    @Test
    public void getByTitleWithValidTitleReturnsTask() {
        String title = this.tasksRepo.getAll().get(0).getTitle();

        Assert.assertNotNull("With valid title returns task that is not null", this.tasksRepo.getByTitle(title));
    }

    @Test
    public void getByTitleWithInvalidTitleReturnsNull() {
        Assert.assertNull("With invalid title returns null", this.tasksRepo.getByTitle("test-title"));
    }

    @Test
    public void addWithValidDataAddsTask() {
        List<Task> tasks = this.tasksRepo.getAll();
        Assert.assertEquals("Initially, the tasks count should be 3", 3, tasks.size());

        Task taskMock = Mockito.mock(Task.class);
        when(taskMock.getTitle()).thenReturn("Mock title");
        when(taskMock.getDescription()).thenReturn("Mock description");
        when(taskMock.getAssigneeId()).thenReturn(DataStore.getUsers().get(0).getId());
        when(taskMock.getStatusId()).thenReturn(DataStore.getStatuses().get(0).getId());
        when(taskMock.getProjectId()).thenReturn(DataStore.getProjects().get(0).getId());

        try {
            this.tasksRepo.add(taskMock);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        tasks = this.tasksRepo.getAll();
        Assert.assertEquals("Finally, the tasks count should be 4", 4, tasks.size());
    }

    @Test
    public void addWhenTaskIsNullThrowsNullPointerException() {
        try {
            this.tasksRepo.add(null);
            fail();
        } catch (Exception ex) {
            assertEquals("Task is not defined. You should pass a valid object instance.", ex.getMessage());
            assertTrue(ex instanceof NullPointerException);
        }
    }

    @Test
    public void addWhenTaskTitleIsEmptyThrowsException() {
        try {
            Task taskMock = Mockito.mock(Task.class);
            when(taskMock.getTitle()).thenReturn("");
            when(taskMock.getDescription()).thenReturn("Mock task description");

            this.tasksRepo.add(taskMock);
            fail();
        } catch (Exception ex) {
            assertEquals("Task title cannot be an empty string.", ex.getMessage());
            assertTrue(true);
        }
    }

    @Test
    public void addWhenTaskDescriptionIsEmptyThrowsException() {
        try {
            Task taskMock = Mockito.mock(Task.class);
            when(taskMock.getTitle()).thenReturn("Mock task title");
            when(taskMock.getDescription()).thenReturn("");

            this.tasksRepo.add(taskMock);
            fail();
        } catch (Exception ex) {
            assertEquals("Task description cannot be an empty string.", ex.getMessage());
            assertTrue(true);
        }
    }
}
