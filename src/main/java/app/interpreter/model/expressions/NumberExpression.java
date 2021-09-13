package app.interpreter.model.expressions;

/**
 * @author Oleksandr Haleta
 */
public class NumberExpression implements Expression, INumberExpression {
    private final String string;

    public NumberExpression(String string) {
        if (string == null) {
            throw new NullPointerException("The String is null");
        }
        this.string = string;
    }

    @Override
    public int interpret(Expression context) {
        return 0;
    }

    @Override
    public int asInt() {
        return Integer.parseInt(string, 10);
    }

    @Override
    public long asLong() {
        return Long.parseLong(string, 10);
    }

    @Override
    public float asFloat() {
        return Float.parseFloat(string);
    }

    @Override
    public double asDouble() {
        return Double.parseDouble(string);
    }
}
