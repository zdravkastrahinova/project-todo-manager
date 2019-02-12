package repositories;

import data.DataStore;
import models.User;

import java.util.List;
import java.util.UUID;

public class UsersRepository {
    private List<User> users;

    public UsersRepository() {
        this.users = new DataStore().getUsersList();
    }

    public List<User> getAll() {
        return this.users;
    }

    public User getById(UUID id) {
        return this.users.stream().filter(user -> user.getId() == id).findFirst().orElse(null);
    }

    public User getByName(String name) {
        return this.users.stream().filter(us -> us.getName().equals(name)).findFirst().orElse(null);
    }

    public void add(User user) {
        this.users.add(user);
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
        this.users.remove(user);
    }

    public boolean doesUserExist(User user) {
        User u = this.users.stream().filter(us -> us.getName().equals(user.getName())).findFirst().orElse(null);

        return u != null;
    }
}
