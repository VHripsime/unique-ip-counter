package ip_counter;

import ip_counter.service.file.info.FileContentInfoService;
import ip_counter.service.file.info.impl.FileUniqueIpCounterService;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class FileConcurrentProcessorTest {

    private static final String FILE_PATH = "src/test/resources/ips.txt";
    private static final Long EXPECTED_COUNT = 1000l;

    private FileContentInfoService<Long> fileContentInfoService;

    public FileConcurrentProcessorTest() {
        fileContentInfoService = new FileUniqueIpCounterService();
    }

    @Test
    public void testGetInfo() throws IOException {
        Long uniqueCount = fileContentInfoService.getInfo(FILE_PATH);
        assertEquals(uniqueCount, EXPECTED_COUNT);
    }
}
