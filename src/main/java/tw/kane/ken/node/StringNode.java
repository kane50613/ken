package tw.kane.ken.node;

public class StringNode extends Node {

    public String string;

    public StringNode(String string) {
        this.string = string;
    }

    @Override
    public Object execute() {
        return string;
    }
}
