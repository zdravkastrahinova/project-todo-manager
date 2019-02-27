package todo.renderers;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import todo.models.User;
import todo.repositories.UsersRepository;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;

public class UsersRendererTest {
    private UsersRenderer renderer;
    private UsersRepository usersRepoMock;

    @Before
    public void setUp() {
        this.usersRepoMock = Mockito.mock(UsersRepository.class);
        this.renderer = new UsersRenderer(this.usersRepoMock);
    }

    @After
    public void tearDown() {
        this.usersRepoMock = null;
    }

    @Test
    public void renderUserListingMenuRendersList() {
        List<User> users = new ArrayList<>();
        users.add(new User("Amy Adams", "Developer"));
        when(usersRepoMock.getAll()).thenReturn(users);

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        this.renderer.renderUserListingMenu();

        Assert.assertEquals("Amy Adams - Developer\r\n", outContent.toString());
    }
}
