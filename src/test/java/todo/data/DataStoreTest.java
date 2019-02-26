package todo.data;

import org.junit.Assert;
import org.junit.Test;

public class DataStoreTest {
    @Test
    public void generateData() {
        Assert.assertNull("Initially, users list should be null", DataStore.getUsers());
        Assert.assertNull("Initially, projects list should be null", DataStore.getProjects());
        Assert.assertNull("Initially, statuses list should be null", DataStore.getStatuses());
        Assert.assertNull("Initially, tasks list should be null", DataStore.getTasks());

        DataStore.generateData();

        Assert.assertNotNull("Finally, users list should be defined", DataStore.getUsers());
        Assert.assertNotNull("Finally, projects list should be defined", DataStore.getProjects());
        Assert.assertNotNull("Finally, statuses list should be defined", DataStore.getStatuses());
        Assert.assertNotNull("Finally, tasks list should be defined", DataStore.getTasks());

        Assert.assertEquals("Users list should contain 3 items", 3, DataStore.getUsers().size());
        Assert.assertEquals("Projects list should contain 3 items", 3, DataStore.getProjects().size());
        Assert.assertEquals("Statuses list should contain 3 items", 3, DataStore.getStatuses().size());
        Assert.assertEquals("Tasks list should contain 3 items", 3, DataStore.getTasks().size());
    }
}
