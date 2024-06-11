package ip_counter.service.file.processor.impl;

import ip_counter.service.data.DataProcessor;
import ip_counter.service.file.processor.FileProcessor;
import ip_counter.util.SystemUtil;

import java.io.File;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class FileConcurrentProcessor implements FileProcessor<Long> {

    private DataProcessor dataProcessor;
    private ExecutorService executorService;

    public FileConcurrentProcessor(DataProcessor dataProcessor) {
        this.dataProcessor = dataProcessor;
        executorService = Executors.newFixedThreadPool(SystemUtil.PROCESSORS_COUNT);
    }

    @Override
    public Long start(String path) {
        long fileSize = new File(path).length();
        long chunkSize = fileSize / SystemUtil.PROCESSORS_COUNT;

        for (int i = 0; i < SystemUtil.PROCESSORS_COUNT; i++) {
            long start = i * chunkSize;
            long end = (i == SystemUtil.PROCESSORS_COUNT - 1) ? fileSize : (i + 1) * chunkSize;
            executorService.execute(() -> new FileChunkNioProcessor<>(dataProcessor).start(path, start, end));
        }

        waitToTerminate();
        return dataProcessor.getResult();
    }

    private void waitToTerminate() {
        executorService.shutdown();
        while (!executorService.isTerminated()) {
        }
    }
}
