package tw.kane.ken.function;

public class Print extends Function {

    public Print() {
        super("print");
    }

    @Override
    public void execute(String[] args) {
        System.out.println(String.join(" ", args));
    }
}
