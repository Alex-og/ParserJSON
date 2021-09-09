package app.interpreter.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ListExpression implements Expression, Iterable<Expression> {
    private List<Expression> values;

    public ListExpression() {
        this.values = new ArrayList<>();
    }

    @Override
    public int interpret() {
        return 0;
    }

    /**
     * Returns an iterator over elements of type {@code T}.
     *
     * @return an Iterator.
     */
    @Override
    public Iterator<Expression> iterator() {
        return null;
    }

    /*public ListExpression set(int index, int value) {
        values.set(index, Json.value(value));
        return this;
    }

    public ListExpression set(int index, long value) {
        values.set(index, Json.value(value));
        return this;
    }

    public ListExpression set(int index, float value) {
        values.set(index, Json.value(value));
        return this;
    }

    public ListExpression set(int index, double value) {
        values.set(index, Json.value(value));
        return this;
    }

    public ListExpression set(int index, boolean value) {
        values.set(index, Json.value(value));
        return this;
    }

    public ListExpression set(int index, String value) {
        values.set(index, Json.value(value));
        return this;
    }

    public int size() {
        return values.size();
    }

    public boolean isEmpty() {
        return values.isEmpty();
    }

    @Override
    public Iterator<Expression> iterator() {
        return null;
    }*/
}
