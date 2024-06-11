package ip_counter.service.file.info.impl;

import ip_counter.service.data.impl.UniqueIpCounter;
import ip_counter.service.file.info.FileContentInfoService;
import ip_counter.service.file.processor.FileProcessor;
import ip_counter.service.file.processor.impl.FileConcurrentProcessor;

import java.io.IOException;

public class FileUniqueIpCounterService implements FileContentInfoService<Long> {

    private FileProcessor<Long> fileProcessor;

    public FileUniqueIpCounterService() {
        this.fileProcessor = new FileConcurrentProcessor(new UniqueIpCounter());
    }

    @Override
    public Long getInfo(String filePath) throws IOException {
        return fileProcessor.start(filePath);
    }
}
