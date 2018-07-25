package gdprdashboard;

import java.util.UUID;

public class Policy {

    // Todo : check if we can store default value
    private UUID id;
    private String name;
    private String description;
    private boolean defaultValue;

    public Policy(String name, String description, boolean defaultValue) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.description = description;
        this.defaultValue = defaultValue;
    }

    public Policy(String name, String description) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.description = description;
        this.defaultValue = true;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(boolean defaultValue) {
        this.defaultValue = defaultValue;
    }

    
}
