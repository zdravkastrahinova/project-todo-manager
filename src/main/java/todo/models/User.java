package todo.models;

/**
 * Extends BaseModel to get UUID
 * User class describing user that can be created
 */
public class User extends BaseModel{
    private String name;
    private String role;

    /**
     * Default constructor
     */
    public User() {
        super();
    }

    /**
     * @param name defines name of the new user
     * @param role defines role of the new user
     */
    public User(String name, String role) {
        super();

        this.setName(name);
        this.setRole(role);
    }

    /**
     * @return name of the selected user
     */
    public String getName() {
        return name;
    }

    /**
     * @param name to set name to user
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return role of the selected user
     */
    public String getRole() {
        return role;
    }

    /**
     * @param role to set role to user
     */
    public void setRole(String role) {
        this.role = role;
    }
}
