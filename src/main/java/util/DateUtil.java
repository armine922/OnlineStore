package util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {
    public static final SimpleDateFormat sdf = new SimpleDateFormat("dd/mm/yyyy");

    public static Date stringToDate(String datestr) throws ParseException {
        return sdf.parse(datestr);
    }

    public static String dateToString(String date) {
        return sdf.format(date);
    }

}
