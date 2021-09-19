package app.interpreter.model;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * @author Oleksandr Haleta
 */
public interface AbstractParser {
    <T> void setSource(T source);

    <T> Field[] getFields();

    <T> Method[] getSetters();

    <T> Method[] getGetters();
}
