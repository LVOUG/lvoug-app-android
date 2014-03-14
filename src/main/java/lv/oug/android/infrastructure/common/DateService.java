package lv.oug.android.infrastructure.common;

import javax.inject.Inject;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateService {

    private static final String DEFAULT_FORMAT = "E, dd MMM yy";

    @Inject
    public DateService() {}

    public Date currentDate() {
        return new Date();
    }

    public String format(Date date) {
        return format(date, DEFAULT_FORMAT);
    }

    public String format(Date date, String formatString) {
        SimpleDateFormat format = new SimpleDateFormat(formatString);
        return format.format(date);
    }

    public Date format(String date, String formatString) {
        try {
            SimpleDateFormat format = new SimpleDateFormat(formatString);
            return format.parse(date);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
}
