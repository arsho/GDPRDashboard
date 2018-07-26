package gdprdashboard;

import java.util.ArrayList;
import java.util.UUID;

public interface BaseStorageInterface {

    public Integer getCount();

    public boolean doesExist(UUID instanceId);
}
