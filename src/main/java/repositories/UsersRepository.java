package repositories;

import data.DataStore;
import models.User;

import java.util.List;
import java.util.UUID;

public class UsersRepository {
    public UsersRepository() {
    }

    public List<User> getAll() {
        return DataStore.getUsers();
    }

    public User getById(UUID id) {
        return DataStore.getUsers()
                .stream()
                .filter(user -> user.getId() == id)
                .findFirst()
                .orElse(null);
    }

    public User getByName(String name) {
        return DataStore.getUsers()
                .stream()
                .filter(us -> us.getName().toLowerCase().equals(name.trim().toLowerCase()))
                .findFirst()
                .orElse(null);
    }

    public void add(User user) throws Exception {
        verifyUserData(user);

        DataStore.addUsers(user);
    }

    public void update(User user) throws Exception {
        verifyUserData(user);

        User u = this.getById(user.getId());

        if (u == null) {
            return;
        }

        u.setName(user.getName());
        u.setRole(user.getRole());
    }

    public void delete(User user) throws Exception {
        verifyUserData(user);

        DataStore.getUsers().remove(user);
    }

    public boolean doesUserExist(User user) {
        User u = DataStore.getUsers()
                .stream()
                .filter(us -> us.getName().toLowerCase().equals(user.getName().trim().toLowerCase()))
                .findFirst()
                .orElse(null);

        return u != null;
    }

    public void verifyUserData(User user) throws Exception {
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
