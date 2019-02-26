package todo.models;

/**
 * Extends BaseModel to get UUID
 * Status class describing status that can be created and assigned to task
 */
public class Status extends BaseModel {
    private String name;
    private String description;

    /**
     * Default constructor
     */
    public Status() {
        super();
    }

    /**
     * @param name defines name of the new status
     * @param description defines description of the new status
     */
    public Status(String name, String description) {
        super();

        this.setName(name);
        this.setDescription(description);
    }

    /**
     * @return name of the status
     */
    public String getName() {
        return name;
    }

    /**
     * @param name to set name to status
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return description of the status
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description to set description to status
     */
    public void setDescription(String description) {
        this.description = description;
    }
}
