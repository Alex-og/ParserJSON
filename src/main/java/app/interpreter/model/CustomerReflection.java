package app.interpreter.model;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class CustomerReflection<T> {
    private T t;

    public void doS() {
        Class<?> tClass = t.getClass();
    }

    public Object getTInstance() throws NoSuchMethodException, IllegalAccessException,
            InvocationTargetException, InstantiationException {
        Constructor<?> constructor = getTClass().getDeclaredConstructor(getTClass());
        return constructor.newInstance(getTClass());
    }

    public Constructor[] getTConstructors() {
        return getTClass().getConstructors();
    }

    public Field[] getFields() {
        return getTClass().getDeclaredFields();
    }

    public Method[] getTMethods() {
        return getTClass().getDeclaredMethods();
    }

    private Class<?> getTClass() {
        return t.getClass();
    }
}
