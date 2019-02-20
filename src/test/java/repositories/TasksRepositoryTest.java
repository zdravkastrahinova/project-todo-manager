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

    @Test
    public void updateWithExistingUuidValidDataUpdatesTask() {
        UUID id = DataStore.getTasks().get(0).getId();

        Task taskMock = Mockito.mock(Task.class);
        when(taskMock.getId()).thenReturn(id);
        when(taskMock.getTitle()).thenReturn("Updated task title");
        when(taskMock.getDescription()).thenReturn("Updated task description");

        try {
            this.tasksRepo.update(taskMock);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        Task task = this.tasksRepo.getById(id);
        Assert.assertEquals(taskMock.getTitle(), task.getTitle());
        Assert.assertEquals(taskMock.getDescription(), task.getDescription());
    }

    @Test
    public void updateWithNonExistingUuidAndValidDataReturnsWithoutAnyChanges() {
        UUID id = UUID.randomUUID();

        Task taskMock = Mockito.mock(Task.class);
        when(taskMock.getId()).thenReturn(id);
        when(taskMock.getTitle()).thenReturn("Updated task title");
        when(taskMock.getDescription()).thenReturn("Updated task description");

        try {
            this.tasksRepo.update(taskMock);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        Task task = this.tasksRepo.getAll()
                .stream()
                .filter(u -> u.getTitle().equals(taskMock.getTitle()))
                .findFirst()
                .orElse(null);

        Assert.assertNull(task);
    }

    @Test
    public void deleteWithValidDataRemovesTask() {
        Assert.assertEquals("Initially, the tasks count should be 3", 3, this.tasksRepo.getAll().size());

        Task t = DataStore.getTasks().get(0);

        Task taskMock = Mockito.mock(Task.class);
        when(taskMock.getId()).thenReturn(t.getId());
        when(taskMock.getTitle()).thenReturn(t.getTitle());
        when(taskMock.getDescription()).thenReturn(t.getDescription());

        try {
            this.tasksRepo.delete(taskMock);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        Assert.assertEquals("Finally, the tasks count should be 2", 2, this.tasksRepo.getAll().size());
        Assert.assertNull("With valid UUID returns null after task has been removed", this.tasksRepo.getById(taskMock.getId()));
    }

    @Test
    public void doesTaskExistWhenTaskIsNullReturnsFalse() {
        Assert.assertFalse("Task does not exist because it is null", this.tasksRepo.doesTaskExist(null));
    }

    @Test
    public void doesTaskExistWithExistingTaskNameReturnsTrue() {
        Task taskMock = Mockito.mock(Task.class);
        when(taskMock.getTitle()).thenReturn(DataStore.getTasks().get(0).getTitle());

        Assert.assertTrue("Task exists because title belongs to task", this.tasksRepo.doesTaskExist(taskMock));
    }

    @Test
    public void doesTaskExistWithNonExistingTaskNameReturnsFalse() {
        Task taskMock = Mockito.mock(Task.class);
        when(taskMock.getTitle()).thenReturn("Non-existing task title");

        Assert.assertFalse("Task does not exist because title is does not belong to any task", this.tasksRepo.doesTaskExist(taskMock));
    }

    @Test
    public void getAllSubTasksWithExistingTaskIdReturnsSubTasks() {
        List<Task> subTasks = this.tasksRepo.getAllSubTasks(DataStore.getTasks().get(2).getId());

        Assert.assertNotNull("Sub-task list with existing parent task UUID is not null", subTasks);
        Assert.assertEquals("Sub-task list count with existing parent task UUID is 1", 1, subTasks.size());
    }

    @Test
    public void getAllSubTasksWithNonExistingTaskIdReturnsNull() {
        Assert.assertNull("Sub-tasks list is null when parent task UUID belongs to non-existing task", this.tasksRepo.getAllSubTasks(UUID.randomUUID()));
    }

    @Test
    public void addSubTaskWithExistingTaskIdIdAndValidDataAddsSubTask() {
        Task task = DataStore.getTasks().get(2);
        Assert.assertEquals("Initially, task has defined 1 sub-task", 1, task.getSubTasks().size());

        Task subTaskMock = Mockito.mock(Task.class);
        when(subTaskMock.getTitle()).thenReturn("Sub-task title");
        when(subTaskMock.getDescription()).thenReturn("Sub-task description");
        when(subTaskMock.getStatusId()).thenReturn(DataStore.getStatuses().get(0).getId());
        when(subTaskMock.getAssigneeId()).thenReturn(DataStore.getUsers().get(0).getId());

        try {
            this.tasksRepo.addSubTask(task.getId(), subTaskMock);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        Assert.assertEquals("Finally, task has defined 2 sub-tasks", 2, task.getSubTasks().size());
    }

    @Test
    public void addSubTaskWithNonExistingTaskIdAndValidDataReturnsWithoutAnyChanges() {
        UUID id = UUID.randomUUID();

        Task subTaskMock = Mockito.mock(Task.class);
        when(subTaskMock.getTitle()).thenReturn("Sub-task title");
        when(subTaskMock.getDescription()).thenReturn("Sub-task description");

        try {
            this.tasksRepo.addSubTask(id, subTaskMock);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        Assert.assertNull("Task is null because UUID does not belong to existing task", this.tasksRepo.getById(id));
    }

    @Test
    public void addSubTaskWithExistingTaskIdAndSubTasksAreNullInitializesSubTasks() {
        Task task = DataStore.getTasks().get(2);
        task.setSubTasks(null);

        Assert.assertNull("Initially, sub-tasks list is null", task.getSubTasks());

        Task subTaskMock = Mockito.mock(Task.class);
        when(subTaskMock.getTitle()).thenReturn("Sub-task title");
        when(subTaskMock.getDescription()).thenReturn("Sub-task description");

        try {
            this.tasksRepo.addSubTask(task.getId(), subTaskMock);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        Assert.assertNotNull("Finally, sub-tasks list is not null", task.getSubTasks());
    }

    @Test
    public void removeSubTaskWithNonExistingTaskIdAndExistingSubTaskTitleReturnsWithoutAnyChanges() {
        UUID id = UUID.randomUUID();

        this.tasksRepo.removeSubTask(id, "Sub-task title");

        Assert.assertNull("Method returns because task is null", this.tasksRepo.getById(id));
    }

    @Test
    public void removeSubTaskWithExistingTaskIdAndSubTasksAreNullReturnsWithoutAnyChanges() {
        Task task = DataStore.getTasks().get(2);
        task.setSubTasks(null);

        Assert.assertNull("Initially, sub-tasks list is null", task.getSubTasks());

        this.tasksRepo.removeSubTask(task.getId(), "Sub-task title");

        Assert.assertNull("Finally, sub-tasks list is null", task.getSubTasks());
    }

    @Test
    public void removeSubTaskWithExistingTaskIdAndNonExistingTitleReturnsWithoutChanges() {
        Task task = DataStore.getTasks().get(2);
        Assert.assertNotNull("Initially, task has defined sub-tasks", task.getSubTasks());
        Assert.assertEquals("Initially, task has list with 1 sub-task", 1, task.getSubTasks().size());

        this.tasksRepo.removeSubTask(task.getId(), "Non-existing title");

        Assert.assertNotNull("Finally, task has defined sub-tasks", task.getSubTasks());
        Assert.assertEquals("Finally, task has list with 1 sub-task", 1, task.getSubTasks().size());
    }

    @Test
    public void removeSubTaskWithExistingTaskIdAndExistingTitleRemovesSubTask() {
        Task task = DataStore.getTasks().get(2);
        Assert.assertNotNull("Initially, task has defined sub-tasks", task.getSubTasks());
        Assert.assertEquals("Initially, task has list with 1 sub-tasks", 1, task.getSubTasks().size());

        String subTaskTitle = "Java DAL and Service layers";
        this.tasksRepo.removeSubTask(task.getId(), subTaskTitle);

        Assert.assertEquals("Finally, task has list with 0 sub-tasks", 0, task.getSubTasks().size());
        Assert.assertNull("Finally, existing title returns null after sub-task has been removed", this.tasksRepo.getByTitle(subTaskTitle));
    }

    @Test
    public void getTaskStatusWithExistingTaskIdReturnsStatus() {
        Assert.assertNotNull("Task status should be defined", this.tasksRepo.getTaskStatus(DataStore.getTasks().get(0).getId()));
    }

    @Test
    public void getTaskStatusWithNonExistingTaskIdReturnsWithNullStatus() {
        Assert.assertNull("Method returns with null status because task is null", this.tasksRepo.getTaskStatus(UUID.randomUUID()));
    }

    @Test
    public void getTaskAssigneeWithExistingTaskIdReturnsAssignee() {
        Assert.assertNotNull("Task assignee should be defined", this.tasksRepo.getTaskAssignee(DataStore.getTasks().get(0).getId()));
    }

    @Test
    public void getTaskAssigneeWithNonExistingTaskIdReturnsNullAssignee() {
        Assert.assertNull("Method returns with null assignee because task is null", this.tasksRepo.getTaskAssignee(UUID.randomUUID()));
    }
}
