package tw.kane.ken;

import java.io.File;
import java.io.IOException;

public class ExecuteFile {
    private final File file;

    public ExecuteFile(String path) throws IOException {
        file = new File(path);
        if(!file.exists() || !file.canRead())
            throw new IOException("input file cannot be read");
    }

    public File getFile() {
        return file;
    }
}
