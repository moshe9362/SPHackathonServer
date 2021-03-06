package hackathon.server.utils;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class Utils {

    public static Timestamp convertStringToTimeStamp(String date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy'T'hh:mm:ss");
        java.util.Date parsedDate = null;
        try {
            parsedDate = dateFormat.parse(date);
        } catch (ParseException e) {
            return null;
        }
        return new Timestamp(parsedDate.getTime());
    }

    public static Date convertStringToDate(String date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        java.util.Date parsedDate = null;
        try {
            parsedDate = dateFormat.parse(date);
        } catch (ParseException e) {
            return null;
        }
        return new Date(parsedDate.getTime());
    }

}

