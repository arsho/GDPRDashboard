package services;

import java.sql.Timestamp;
import java.util.Date;
import java.util.UUID;

import interfaces.*;

public class CommonService implements ServiceInterface {

    public static double getCurrentTimeStamp() {
        java.util.Date date = new java.util.Date();
        return date.getTime();
    }

    public static UUID getUUID() {
        return UUID.randomUUID();
    }

    public static UUID fromString(String value) {
        try {
            return UUID.fromString(value);
        } catch (Exception ex) {
            return null;
        }
    }
}
