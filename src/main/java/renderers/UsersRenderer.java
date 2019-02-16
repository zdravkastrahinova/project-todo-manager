package renderers;

import models.User;
import repositories.UsersRepository;

import java.util.List;
import java.util.Scanner;

class UsersRenderer {
    private static Scanner sc;
    private UsersRepository usersRepo;

    UsersRenderer(UsersRepository usersRepo) {
        this.usersRepo = usersRepo;
    }

    void renderUsersMenu() {
        System.out.println();
        System.out.println("Choose user operation");
        System.out.println("[1] List");
        System.out.println("[2] Add");
        System.out.println("[3] Update");
        System.out.println("[4] Delete");
        System.out.println("[0] Exit");

        sc = new Scanner(System.in);
        int option = parseConsoleInput(sc.nextLine());

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
            default:
                this.renderUsersMenu();
                break;
        }
    }

    private void renderUserListingMenu() {
        List<User> users = usersRepo.getAll();

        for (User u : users) {
            System.out.println(u.getName() + " - " + u.getRole());
        }
    }

    private void renderUserAddingMenu() {
        try {
            System.out.println("Enter user data");
            System.out.println("Name:");
            String name = sc.nextLine();

            System.out.println("Role: ");
            String role = sc.nextLine();

            User user = new User(name, role);

            if (usersRepo.doesUserExist(user)) {
                System.out.println("User with the same name already exists.");
                this.renderUsersMenu();
            }

            usersRepo.add(user);

            System.out.println("User was successfully added.");
        } catch (Exception ex) {
            System.out.println(ex.getMessage());

            renderUsersMenu();
        }
    }

    private void renderUserUpdatingMenu() {
        try {
            System.out.println("Enter name of the user you want to edit:");
            String name = sc.nextLine();

            this.validateUserInput(name);

            User user = usersRepo.getByName(name);
            this.verifyUserExists(user);

            System.out.println("Enter new values for selected user");
            System.out.println("Name:");
            String nameToUpdate = sc.nextLine();

            System.out.println("Role: ");
            String roleToUpdate = sc.nextLine();

            user.setName(nameToUpdate);
            user.setRole(roleToUpdate);

            usersRepo.update(user);
            System.out.println("User was successfully updated.");
        } catch (Exception ex) {
            System.out.println(ex.getMessage());

            renderUsersMenu();
        }
    }

    private void renderUserDeletingMenu() {
        try {
            System.out.println("Enter name of the user you want to delete.");
            String name = sc.nextLine();

            this.validateUserInput(name);

            User user = usersRepo.getByName(name);
            this.verifyUserExists(user);

            usersRepo.delete(user);
            System.out.println("User was successfully deleted.");
        } catch (Exception ex) {
            System.out.println(ex.getMessage());

            renderUsersMenu();
        }
    }

    private void verifyUserExists(User user) {
        if (!usersRepo.doesUserExist(user)) {
            System.out.println("User with this name does not exist.");
            this.renderUsersMenu();
        }
    }

    private int parseConsoleInput(String value) {
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException ex) {
            return 0;
        }
    }

    private void validateUserInput(String value) {
        if (value == null || value.isEmpty()) {
            System.out.println("Value cannot be an empty string.");
            this.renderUsersMenu();
        }
    }
}
