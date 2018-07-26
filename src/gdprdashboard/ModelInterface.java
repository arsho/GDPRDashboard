package gdprdashboard;

import java.util.UUID;

public interface ModelInterface {
    public UUID getId();
    public double getDateCreated();
    public void setId(UUID id);
}