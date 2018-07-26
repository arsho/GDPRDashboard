package gdprdashboard;
// Common Services

import java.sql.Timestamp;
import java.util.Date;
import java.util.UUID;

public class CommonService implements ServiceInterface {

    public static double getCurrentTimeStamp() {
        java.util.Date date = new java.util.Date();
        return date.getTime();
    }

    public static UUID getUUID() {
        return UUID.randomUUID();
    }
}
