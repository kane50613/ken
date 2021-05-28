package tw.kane.ken.node;

public class FloatNode extends Node {
    float _float;

    public FloatNode(float _float) {
        this._float = _float;
    }

    public float getNumber() {
        return _float;
    }

    @Override
    public Object execute() {
        return _float;
    }
}

