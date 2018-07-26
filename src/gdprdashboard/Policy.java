package gdprdashboard;

import java.util.UUID;

public class Policy extends Core {

    // Todo : check if we can store default value
    private String name;
    private String description;
    private boolean defaultValue;

    public Policy(String name, String description, boolean defaultValue) {
        this.name = name;
        this.description = description;
        this.defaultValue = defaultValue;
    }

    public Policy(String name, String description) {
        this.name = name;
        this.description = description;
        this.defaultValue = UserChoiceEnum.COMPLY.value();
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
