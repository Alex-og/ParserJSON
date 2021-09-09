package app.interpreter.model;

public interface INumberExpression {
    int interpret();

    int asInt();

    long asLong();

    float asFloat();

    double asDouble();
}
