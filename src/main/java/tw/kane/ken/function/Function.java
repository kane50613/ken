package tw.kane.ken.function;

public abstract class Function {

    private String name;

    public Function(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public abstract Object execute(Object[] args);
}
