package tw.kane.ken;

import java.io.IOException;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        if(args.length > 0) {
            try {
                ExecuteFile.init(args[0]);
                System.out.println(String.join(",", new Lexer(ExecuteFile.getFile()).parse().stream().map(x -> x.type + ":" + x.value).collect(Collectors.toList())));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
