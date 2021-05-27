package tw.kane.ken;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        if(args.length > 0) {
            try {
                ExecuteFile.init(args[0]);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
