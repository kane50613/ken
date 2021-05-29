package tw.kane.ken.function;

public class Println extends Function {
    public Println() {
        super("println");
    }

    @Override
    public Object execute(Object[] args) {
        for (Object arg : args)
            System.out.println(arg);
        return null;
    }
}
