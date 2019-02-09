package models;

public class User extends BaseModel{
    private String name;
    private String role;

    public User() {
        super();
    }
    public User(String name, String role) {
        super();

        this.setName(name);
        this.setRole(role);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
