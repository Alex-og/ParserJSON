package app.interpreter.model;

/**
 * @author Oleksandr Haleta
 */

import java.util.HashMap;
import java.util.Map;

/**
 * Форма объекта JSON
 * Объект представляет собой неупорядоченный набор пар «имя / значение». Объект начинается
 * с "{" (левая скобка) и заканчивается "}" (правая скобка). За каждым «именем» следует «:» (двоеточие);
 * пары «имя / значение» разделяются «,» (запятая).
 */
public class JsonObject {
    private Map<String, Object> map = new HashMap<>();

    public void put(String key, Object value) {
        map.put(key, value);
    }

    public Object get(String key) {
        return map.get(key);
    }
}