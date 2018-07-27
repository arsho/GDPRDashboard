package services;

import java.util.UUID;

public class CommonService {

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
