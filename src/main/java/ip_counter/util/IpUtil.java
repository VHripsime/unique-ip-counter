package ip_counter.util;

import java.util.Arrays;
import java.util.Optional;

public final class IpUtil {

    private static int IP_MIN_NUMBER = 0;
    private static int IP_MAX_NUMBER = 255;

    private IpUtil() {}

    /**
     * Converts the given IP address into to unique number.
     *
     * Splits the IP into 4 parts, each of them represents 8-bit number.
     * Then combines that bits as a single number.
     *
     * For example, IP = "192.168.0.1":
     * 192 = "11000000",
     * 168 = "10101000",
     * 0 = "00000000",
     * 1 = "00000001".
     *
     * Combining these 8-bit values into a single number, i.e. setting them next to each other,
     * will result in 32-bit number:
     * 11000000 10101000 00000000 00000001 = 3,232,235,521.
     *
     * So, "192.168.0.1" = 3,232,235,521
     *
     * @param ip
     * @return
     */
    public static Optional<Long> toLongValue(String ip) {
        String[] parts = ip.split("\\.");
        /*
         * As all the IP addresses are valid in the provided zipped file, I'm not validating them.
         * Anyway, I have below the "isValidIp" method for validating IPs.
         */

        try {
            long value = Long.parseLong(parts[0]) << 24 |
                         Long.parseLong(parts[1]) << 16 |
                         Long.parseLong(parts[2]) << 8 |
                         Long.parseLong(parts[3]);
            return Optional.of(value);
        } catch (NumberFormatException e) {
            // We do not want to interrupt the process even if there are errors in some lines. Just skipping the line.
            return Optional.empty();
        }
    }

    private static boolean isValidIp(String[] parts) {
        return Arrays.stream(parts).filter(IpUtil::isValid).findAny().isPresent();
    }

    private static boolean isValid(String part) {
        try {
            int intValue = Integer.parseInt(part);
            return intValue >= IP_MIN_NUMBER && intValue <= IP_MAX_NUMBER;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
