package tw.kane.ken.function;

import java.util.Arrays;
import java.util.stream.Collectors;

public class Print extends Function {

    public Print() {
        super("print");
    }

    @Override
    public Object execute(Object[] args) {
        System.out.print(
                Arrays
                .stream(args)
                .map(String::valueOf)
                .collect(Collectors.joining(" "))
        );
        return null;
    }
}
