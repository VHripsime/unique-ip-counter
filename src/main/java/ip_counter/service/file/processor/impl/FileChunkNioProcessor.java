package ip_counter.service.file.processor.impl;

import ip_counter.service.data.DataProcessor;
import ip_counter.service.file.processor.FileChunkProcessor;
import ip_counter.util.SystemUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileChunkNioProcessor<T> implements FileChunkProcessor<Void> {

    private DataProcessor<T> dataProcessor;

    public FileChunkNioProcessor(DataProcessor dataProcessor) {
        this.dataProcessor = dataProcessor;
    }

    @Override
    public Void start(String path, long startPosition, long endPosition) {
        try (BufferedReader reader = Files.newBufferedReader(Path.of(path))) {
            goToStartPosition(reader, startPosition);
            long bytesRead = startPosition;
            String line = reader.readLine();

            while (line != null && bytesRead <= endPosition) {
                dataProcessor.process((T) line);
                bytesRead += line.length() + SystemUtil.LINE_SEPARATOR_LENGTH;
                line = reader.readLine();
            }
        } catch (IOException e) {
            System.out.println("Error while processing the file: " + e.getMessage());
        }
        return null;
    }

    private void goToStartPosition(BufferedReader reader, long startPosition) throws IOException {
        if (startPosition == 0) {
            return;
        }
        reader.skip(startPosition);
        reader.readLine();
    }
}
