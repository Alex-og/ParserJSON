package app.interpreter.model;

/**
 * @author Oleksandr Haleta
 */

import java.util.ArrayList;
import java.util.List;


public class JsonArray<T> {
    private List<T> list = new ArrayList();

    public void add(T obj) {
        list.add(obj);
    }

    public T get(int index) {
        return list.get(index);
    }

    public int size() {
        return list.size();
    }
}
