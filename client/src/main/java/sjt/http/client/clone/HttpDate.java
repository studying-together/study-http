package sjt.http.client.clone;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class HttpDate {

    private static final ThreadLocal<DateFormat> STANDARD_DATE_FORMAT =
            ThreadLocal.withInitial(() -> {
                DateFormat rfc1123 = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz", Locale.US);
                rfc1123.setTimeZone(TimeZone.getTimeZone("UTC"));
                return rfc1123;
            });

    public static String format(Date date) {
        return STANDARD_DATE_FORMAT.get().format(date);
    }
}
