package app.interpreter;

import app.interpreter.model.AbstractContext;
import app.interpreter.model.Container;
import app.interpreter.model.Context;
import app.interpreter.model.Parser;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

public class Provider<T> {
    private final Parser parser;
    AbstractContext context;
    private T customer;
    private String jsonString;

    public Provider() {
        parser = new Parser();
        parser.setSource(customer);
    }

    public T getCustomer() {
        return customer;
    }

    public void setCustomer(T customer) {
        this.customer = customer;
    }

    public T getContext(String string) {
        this.jsonString = string;
        context = new Context(string);
        customer = extractData(context);
        return customer;
    }

    public void bind() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, NoSuchFieldException {
        Method[] customerSetters = parser.getSetters();
        if (customerSetters.length > 0) {
            delegateDataToCustomerSetters(customerSetters);
        } else {
            delegateDataToTargetFields();
        }
    }

    private void delegateDataToTargetFields() throws IllegalAccessException, NoSuchFieldException {
        Field[] customerFields = parser.getFields();
        if (customerFields.length > 0) {
            for (Field field : customerFields) {
                Field sourceField = customer.getClass().getDeclaredField(field.getName());
                sourceField.setAccessible(true);
                Object data = getDataFromJson(field.getName());
                sourceField.set(customer, data);
            }
        }
    }

    private void delegateDataToCustomerSetters(Method[] customerSetters)
            throws InvocationTargetException, IllegalAccessException {

        for (int i = 0; i < customerSetters.length; i++) {
            String nameSetter = customerSetters[i].getName().substring(3);
            Object data = getDataFromJson(nameSetter);
            customerSetters[i].setAccessible(true);
            customerSetters[i].invoke(customer, data);
        }
    }

    private T extractData(AbstractContext context) {
        Map<String, Object> map = context.getMap();
        return null;
    }

    private Object getDataFromJson(String name) {
        Map<String, Object> map = context.getMap();
        Object obj = null;
        for (Map.Entry<String, Object> mp : map.entrySet()) {
            if (mp.getKey().equals(name)) {
                obj = mp.getValue();
                return obj;
            }
            if (mp.getValue() instanceof List) {
                iterateList(mp.getValue(), name);
            }
        }
        if (obj == null) {

        }
        return null;
    }

    private<T> Object iterateList(List<T> value, String name) {
        for (Object ob : value) {
            if (name.equals(ob)) {
                return ob;
            } else if (ob instanceof Map) {
                Container container = (Container) ob;
                getDataFromJson(container.getName());
            }
        }
    }
}
