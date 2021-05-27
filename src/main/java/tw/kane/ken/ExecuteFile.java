package tw.kane.ken;

import java.io.File;
import java.io.IOException;

public class ExecuteFile {
    private static ExecuteFile _instance;
    private final File file;

    public static void init(String path) throws IOException {
        if(_instance != null)
            return;
        _instance = new ExecuteFile(path);
    }

    private ExecuteFile(String path) throws IOException {
        file = new File(path);
        if(!file.exists() || !file.canRead())
            throw new IOException("input file cannot be read");
    }

    public static File getFile() {
        return _instance.file;
    }
}
