package ip_counter.service.file.processor;

import java.io.IOException;

public interface FileProcessor<R> {

    R start(String filePath) throws IOException;
}
