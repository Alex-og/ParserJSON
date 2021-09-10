package app.interpreter.model;

/**
 * @author Oleksandr Haleta
 */

import java.util.ArrayList;
import java.util.List;

/**
 Форма массива JSON
 * Массив - это упорядоченный набор значений. Массив начинается с «[» (левая квадратная скобка)
 * и заканчивается «]» (правая квадратная скобка). Используйте "," (запятую) для разделения значений.
 */
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
