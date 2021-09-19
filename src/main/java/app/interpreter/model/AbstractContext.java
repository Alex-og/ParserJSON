package app.interpreter.model;

import java.util.List;
import java.util.Map;

public interface AbstractContext {
    int getIndex();

    void setIndex(int index);

    void evaluate();

    void parse(char[] characters);

    Object checkObject(int index, char[] characters);

    Object createMap(char[] characters);

    List<Object> createList(char[] characters);

    String createString(int index, char[] chars);

    Map<String, Object> getMap();
}
