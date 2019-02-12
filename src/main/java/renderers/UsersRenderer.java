package renderers;

import models.User;
import repositories.UsersRepository;

import java.util.List;
import java.util.Scanner;

public class UsersRenderer {
    private static Scanner sc;
    private UsersRepository usersRepo;

    public UsersRenderer(UsersRepository usersRepo) {
        this.usersRepo = usersRepo;
    }

    public void renderUsersMenu() {
        System.out.println();
        System.out.println("Choose operation");
        System.out.println("[1] List Users");
        System.out.println("[2] Add User");
        System.out.println("[3] Update User");
        System.out.println("[4] Delete User");
        System.out.println("[0] Exit");

        sc = new Scanner(System.in);
        int option = Integer.parseInt(sc.nextLine());

        switch (option) {
            case 1:
                this.renderUserListingMenu();
                this.renderUsersMenu();
                break;
            case 2:
                this.renderUserAddingMenu();
                this.renderUsersMenu();
                break;
            case 3:
                this.renderUserUpdatingMenu();
                this.renderUsersMenu();
                break;
            case 4:
                this.renderUserDeletingMenu();
                this.renderUsersMenu();
                break;
            case 0:
                System.exit(0);
                break;
        }
    }

    public void renderUserListingMenu() {
        List<User> users = usersRepo.getAll();

        for (User u : users) {
            System.out.println(u.getName() + " - " + u.getRole());
        }
    }

    public void renderUserAddingMenu() {
        System.out.println("User Name:");
        String name = sc.nextLine();

        System.out.println("User Role: ");
        String role = sc.nextLine();

        User user = new User(name, role);

        if (usersRepo.doesUserExist(user)) {
            System.out.println("User with the same name already exists.");
            this.renderUsersMenu();
        }

        usersRepo.add(user);
        System.out.println("User was successfully added.");
    }

    public void renderUserUpdatingMenu() {
        System.out.println("Enter name of the user you want to edit");
        String name = sc.nextLine();

        User user = usersRepo.getByName(name);

        verifyUser(user);

        System.out.println("Enter new values for selected user");
        System.out.println("User Name:");
        String nameToUpdate = sc.nextLine();

        System.out.println("User Role: ");
        String roleToUpdate = sc.nextLine();

        user.setName(nameToUpdate);
        user.setRole(roleToUpdate);

        usersRepo.update(user);
        System.out.println("User was successfully updated.");
    }

    public void renderUserDeletingMenu() {
        System.out.println("Enter name of the user you want to delete.");
        String name = sc.nextLine();

        User user = usersRepo.getByName(name);

        this.verifyUser(user);

        usersRepo.delete(user);
        System.out.println("User was successfully deleted.");
    }

    public void verifyUser(User user) {
        if (user == null) {
            System.out.println("User with this name does not exist.");
            this.renderUsersMenu();
        }
    }
}
