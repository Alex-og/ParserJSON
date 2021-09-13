package app.interpreter.model.expressions;

/**
 * @author Oleksandr Haleta
 */
public interface Expression {
    int interpret(Expression context);
}
