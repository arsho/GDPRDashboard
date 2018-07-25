// Common Services
import java.sql.Timestamp;
import java.util.Date;
import java.util.UUID;


public class CommonService implements ServiceInterface{
    public double getCurrentTimeStamp(){
        java.util.Date date= new java.util.Date();
        return date.getTime();
    }

    public UUID getUUID(){
        return UUID.randomUUID();
    }
}