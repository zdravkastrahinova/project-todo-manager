package repositories;

import data.DataStore;
import models.User;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.List;
import java.util.UUID;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

public class UsersRepositoryTest {
    private UsersRepository usersRepo;

    @Before
    public void setUp() {
        DataStore.generateData();
        this.usersRepo = new UsersRepository();
    }

    @After
    public void tearDown() {
    }

    @Test
    public void getAll() {
        List<User> users = this.usersRepo.getAll();

        Assert.assertNotNull("Returns a list of users that is not null", users);
        Assert.assertEquals("Returns a list of 3 users", 3, users.size());
    }

    @Test
    public void getByIdWithValidUuidReturnsUser() {
        UUID id = DataStore.getUsers().get(0).getId();

        Assert.assertNotNull("With valid UUID returns user that is not null", this.usersRepo.getById(id));
    }

    @Test
    public void getByIdWithInvalidUuidReturnsNull() {
        Assert.assertNull("With invalid UUID returns null", this.usersRepo.getById(UUID.randomUUID()));
    }

    @Test
    public void getByNameWithValidNameReturnsUser() {
        String name = DataStore.getUsers().get(0).getName();

        Assert.assertNotNull("With valid name returns user that is not null", this.usersRepo.getByName(name));
    }

    @Test
    public void getByNameWithInvalidNameReturnsUser() {
        Assert.assertNull("With invalid name returns null", this.usersRepo.getByName("test-user-name"));
    }

    @Test
    public void addWithValidDataAddsUser() {
        List<User> users = this.usersRepo.getAll();
        Assert.assertEquals("Initially, the users count should be 3", 3, users.size());

        User userMock = Mockito.mock(User.class);
        when(userMock.getName()).thenReturn("Mock user name");
        when(userMock.getRole()).thenReturn("Mock user role");

        try {
            this.usersRepo.add(userMock);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        users = this.usersRepo.getAll();
        Assert.assertEquals("Finally, the users count should be 4", 4, users.size());
    }

    @Test
    public void addWhenUserIsNullThrowsNullPointerException() {
        try {
            this.usersRepo.add(null);
            fail();
        } catch (Exception ex) {
            assertEquals("User is not defined. You should pass a valid object instance.", ex.getMessage());
            assertTrue(ex instanceof NullPointerException);
        }
    }

    @Test
    public void addWhenUserNameIsEmptyThrowsException() {
        User userMock = Mockito.mock(User.class);
        when(userMock.getName()).thenReturn("");
        when(userMock.getRole()).thenReturn("Mock user role");

        try {
            this.usersRepo.add(userMock);
            fail();
        } catch (Exception ex) {
            assertEquals("User name cannot be an empty string.", ex.getMessage());
        }
    }

    @Test
    public void addWhenUserRoleIsEmptyThrowsException() {
        User userMock = Mockito.mock(User.class);
        when(userMock.getName()).thenReturn("Mock user name");
        when(userMock.getRole()).thenReturn("");

        try {
            this.usersRepo.add(userMock);
            fail();
        } catch (Exception ex) {
            assertEquals("User role cannot be an empty string.", ex.getMessage());
        }
    }

    @Test
    public void updateWithExistingUuidValidDataUpdatesUser() {
        UUID id = DataStore.getUsers().get(0).getId();

        User userMock = Mockito.mock(User.class);
        when(userMock.getId()).thenReturn(id);
        when(userMock.getName()).thenReturn("Updated user name");
        when(userMock.getRole()).thenReturn("Updated user role");

        try {
            usersRepo.update(userMock);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        User user = this.usersRepo.getById(id);
        Assert.assertEquals(userMock.getName(), user.getName());
        Assert.assertEquals(userMock.getRole(), user.getRole());
    }

    @Test
    public void updateWithNonExistingUuidValidDataReturnsWithoutAnyChanges() {
        UUID id = UUID.randomUUID();

        User userMock = Mockito.mock(User.class);
        when(userMock.getId()).thenReturn(id);
        when(userMock.getName()).thenReturn("Updated user name");
        when(userMock.getRole()).thenReturn("Updated user role");

        try {
            usersRepo.update(userMock);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        User user = this.usersRepo.getAll()
                .stream()
                .filter(u -> u.getRole().equals(userMock.getName()))
                .findFirst()
                .orElse(null);

        Assert.assertNull(user);
    }

    @Test
    public void updateWhenUserIsNullThrowsNullPointerException() {
        try {
            this.usersRepo.update(null);
            fail();
        } catch (Exception ex) {
            assertEquals("User is not defined. You should pass a valid object instance.", ex.getMessage());
            assertTrue(ex instanceof NullPointerException);
        }
    }

    @Test
    public void updateWhenUserNameIsEmptyThrowsException() {
        User userMock = Mockito.mock(User.class);
        when(userMock.getName()).thenReturn("");
        when(userMock.getRole()).thenReturn("Updated user role");

        try {
            this.usersRepo.update(userMock);
            fail();
        } catch (Exception ex) {
            assertEquals("User name cannot be an empty string.", ex.getMessage());
        }
    }

    @Test
    public void updateWhenUserRoleIsEmptyThrowsException() {
        User userMock = Mockito.mock(User.class);
        when(userMock.getName()).thenReturn("Updated user name");
        when(userMock.getRole()).thenReturn("");

        try {
            this.usersRepo.update(userMock);
            fail();
        } catch (Exception ex) {
            assertEquals("User role cannot be an empty string.", ex.getMessage());
        }
    }

    @Test
    public void deleteWithValidDataRemovesUser() {
        Assert.assertEquals("Initially, the users count should be 3", 3, this.usersRepo.getAll().size());

        User u = DataStore.getUsers().get(0);

        User userMock = Mockito.mock(User.class);
        when(userMock.getId()).thenReturn(u.getId());
        when(userMock.getName()).thenReturn(u.getName());
        when(userMock.getRole()).thenReturn(u.getRole());

        try {
            usersRepo.delete(userMock);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        Assert.assertEquals("Finally, the users count should be 2", 2, this.usersRepo.getAll().size());
        Assert.assertNull("With valid UUID returns null after user has been removed", this.usersRepo.getById(userMock.getId()));
    }

    @Test
    public void deleteWhenUserIsNullThrowsNullPointerException() {
        try {
            this.usersRepo.delete(null);
            fail();
        } catch (Exception ex) {
            assertEquals("User is not defined. You should pass a valid object instance.", ex.getMessage());
            assertTrue(ex instanceof NullPointerException);
        }
    }

    @Test
    public void deleteWhenUserNameIsEmptyThrowsException() {
        User userMock = Mockito.mock(User.class);
        when(userMock.getName()).thenReturn("");
        when(userMock.getRole()).thenReturn("Updated user role");

        try {
            this.usersRepo.delete(userMock);
            fail();
        } catch (Exception ex) {
            assertEquals("User name cannot be an empty string.", ex.getMessage());
        }
    }

    @Test
    public void deleteWhenUserRoleIsEmptyThrowsException() {
        User userMock = Mockito.mock(User.class);
        when(userMock.getName()).thenReturn("Updated user name");
        when(userMock.getRole()).thenReturn("");

        try {
            this.usersRepo.delete(userMock);
            fail();
        } catch (Exception ex) {
            assertEquals("User role cannot be an empty string.", ex.getMessage());
        }
    }
}
