package gdprdashboard;

import java.util.ArrayList;
import java.util.UUID;

public interface BaseStorage
{
    public Integer getCount();
    public boolean doesExist(UUID instanceId);
}
 