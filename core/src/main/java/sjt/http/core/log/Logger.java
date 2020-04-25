package sjt.http.core.log;

public class Logger {

    private static boolean use = true;

    public static void log(Object obj, String message) {
        if (use) {
            System.out.println(obj.getClass().getName() + " : " + message);
        }
    }

}
