package todo.models;

import java.util.UUID;

/**
 * BaseModel class generating UUID
 */
public class BaseModel {
    private UUID id;

    /**
     * Default constructor
     * Automatically generates UUID
     */
    BaseModel() {
        this.setId(UUID.randomUUID());
    }

    /**
     * @return id of the model
     */
    public UUID getId() {
        return id;
    }

    private void setId(UUID id) {
        this.id = id;
    }
}
