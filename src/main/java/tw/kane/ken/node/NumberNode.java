package tw.kane.ken.node;

public class NumberNode extends Node {
    int number;

    public NumberNode(int number) {
        this.number = number;
    }

    public int getNumber() {
        return number;
    }

    @Override
    public Object execute() {
        return number;
    }
}
