package repositories;

import data.DataStore;
import models.User;

import java.util.List;
import java.util.UUID;

/**
 * Repository class that defines methods to manipulate {@link User} model objects
 */
public class UsersRepository {
    public UsersRepository() {
    }

    /**
     * @return collection of {@link User}
     */
    public List<User> getAll() {
        return DataStore.getUsers();
    }

    /**
     * @param id id of user we are looking for
     * @return {@link User} with provided id or null if user does not exist
     */
    public User getById(UUID id) {
        return DataStore.getUsers()
                .stream()
                .filter(user -> user.getId() == id)
                .findFirst()
                .orElse(null);
    }

    /**
     * @param name name of user we are looking for
     * @return {@link User} with provided name or null if user does not exist
     */
    public User getByName(String name) {
        return DataStore.getUsers()
                .stream()
                .filter(us -> us.getName().toLowerCase().equals(name.trim().toLowerCase()))
                .findFirst()
                .orElse(null);
    }

    /**
     * @param user {@link User} that will be created
     * @throws NullPointerException when user is null
     * @throws Exception            when user name or role is null
     */
    public void add(User user) throws Exception {
        verifyUserData(user);

        DataStore.addUsers(user);
    }

    /**
     * @param user {@link User} that will be updated
     * @throws NullPointerException when user is null
     * @throws Exception            when user name or role is null
     */
    public void update(User user) throws Exception {
        verifyUserData(user);

        User u = this.getById(user.getId());
        if (u == null) {
            return;
        }

        u.setName(user.getName());
        u.setRole(user.getRole());
    }

    /**
     * @param user {@link User} that will be deleted
     * @throws NullPointerException when user is null
     * @throws Exception            when user name or role is null
     */
    public void delete(User user) throws Exception {
        verifyUserData(user);

        User item = DataStore.getUsers()
                .stream()
                .filter(u -> u.getId() == user.getId())
                .findFirst()
                .orElse(null);

        DataStore.getUsers().remove(item);
    }

    /**
     * @param user {@link User} that will be verified
     * @return false if user is null
     */
    public boolean doesUserExist(User user) {
        if (user == null) {
            return false;
        }

        User u = DataStore.getUsers()
                .stream()
                .filter(us -> us.getName().toLowerCase().equals(user.getName().trim().toLowerCase()))
                .findFirst()
                .orElse(null);

        return u != null;
    }

    private void verifyUserData(User user) throws Exception {
        if (user == null) {
            throw new NullPointerException("User is not defined. You should pass a valid object instance.");
        }

        if (user.getName().trim().isEmpty()) {
            throw new Exception("User name cannot be an empty string.");
        }

        if (user.getRole().trim().isEmpty()) {
            throw new Exception("User role cannot be an empty string.");
        }
    }
}
