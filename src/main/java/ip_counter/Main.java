package ip_counter;

import ip_counter.service.file.info.FileContentInfoService;
import ip_counter.service.file.info.impl.FileUniqueIpCounterService;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;

/**
 * PLEASE refer to the "src/main/resources/notes.txt" file to see the program
 * performance description while handling the huge file posted in:
 * https://ecwid-vgv-storage.s3.eu-central-1.amazonaws.com/ip_addresses.zip
 */
public class Main {

    public static void main(String[] args) {
        String filePath = "src/test/resources/ips.txt";
        process(filePath);
    }

    private static void process(String filePath) {
        FileContentInfoService<Long> fileContentInfoService = new FileUniqueIpCounterService();

        System.out.println("\nProcessing...");
        LocalDateTime start = LocalDateTime.now();

        try {
            Long count = fileContentInfoService.getInfo(filePath);

            LocalDateTime end = LocalDateTime.now();
            printExecutionDetails(start, end, count);
        } catch (IOException e) {
            System.out.println("Something went wrong while trying to read and process the file data:" + e.getMessage());
        }
    }

    private static void printExecutionDetails(LocalDateTime start, LocalDateTime end, Long count) {
        long seconds = Duration.between(start, end).toSeconds();
        int minutes = (int) (seconds / 60);

        System.out.println();
        System.out.println("Unique IPs Count: " + String.format("%,d", count));
        System.out.println();
        System.out.println("Duration : " + minutes + " minutes " + (seconds - 60 * minutes) + " seconds.");
    }
}
