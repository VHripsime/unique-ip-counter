package ip_counter.service.file.info;

import java.io.IOException;

public interface FileContentInfoService<R> {

    R getInfo(String filePath) throws IOException;
}
