package tw.kane.ken.node;

public class BooleanNode extends Node {
    boolean bool;

    public BooleanNode(boolean bool) {
        this.bool = bool;
    }

    @Override
    public Object execute() {
        return bool;
    }
}
