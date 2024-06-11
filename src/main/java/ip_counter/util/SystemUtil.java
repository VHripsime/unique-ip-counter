package ip_counter.util;

public final class SystemUtil {

    public static int LINE_SEPARATOR_LENGTH = System.lineSeparator().length();
    public static int PROCESSORS_COUNT = Runtime.getRuntime().availableProcessors();

    private SystemUtil() {}
}
