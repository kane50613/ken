package tw.kane.ken;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class ExecuteFile {
    private static ExecuteFile _instance;
    private File file;

    public static void init(String path) throws IOException {
        if(_instance != null)
            return;
        _instance = new ExecuteFile(path);
    }

    private ExecuteFile(String path) throws IOException {
        file = new File(path);
    }

    public static File getFile() {
        return _instance.file;
    }
}
