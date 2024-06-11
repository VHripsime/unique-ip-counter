package ip_counter.service.file.processor;

import java.io.IOException;

public interface FileChunkProcessor<R> {

    R start(String filePath, long startPosition, long endPosition) throws IOException;
}
