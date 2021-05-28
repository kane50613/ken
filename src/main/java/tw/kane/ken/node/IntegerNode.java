package tw.kane.ken.node;

public class IntegerNode extends Node {
    public int integer;

    public IntegerNode(int integer) {
        this.integer = integer;
    }

    public int getNumber() {
        return integer;
    }

    @Override
    public Object execute() {
        return integer;
    }
}
