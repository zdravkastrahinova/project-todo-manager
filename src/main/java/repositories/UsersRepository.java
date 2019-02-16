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
        return DataStore.getUsers().stream().filter(user -> user.getId() == id).findFirst().orElse(null);
    }

    public User getByName(String name) {
        return DataStore.getUsers().stream().filter(us -> us.getName().equals(name)).findFirst().orElse(null);
    }

    public void add(User user) {
        DataStore.getUsers().add(user);
    }

    public void update(User user) {
        User u = this.getById(user.getId());

        if (u == null) {
            return;
        }

        u.setName(user.getName());
        u.setRole(user.getRole());
    }

    public void delete(User user) {
        DataStore.getUsers().remove(user);
    }

    public boolean doesUserExist(User user) {
        User u = DataStore.getUsers().stream().filter(us -> us.getName().equals(user.getName())).findFirst().orElse(null);

        return u != null;
    }
}
