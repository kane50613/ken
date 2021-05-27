package tw.kane.ken;

import java.util.HashMap;

public class Variable {
    public static HashMap<String, Object> variables = new HashMap<>();

    public void insert(String key, Object value) {
        variables.put(key, value);
    }

    public Object get(String key) {
        return variables.get(key);
    }
}
