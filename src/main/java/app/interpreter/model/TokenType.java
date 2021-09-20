package app.interpreter.model;

/**
 * @author Oleksandr Haleta
 */
public interface TokenType {
    static boolean isBeginArray(char c) {
        return c == '[';
    }

    static boolean isBeginObject(char c) {
        return c == '{';
    }
     static boolean isBoolean(String c) {
         return c.equalsIgnoreCase("true") || c.equalsIgnoreCase("false");
     }

     static boolean isEndArray(char c) {
         return c == ']';
     }

    static boolean isEndObject(char c) {
        return c == '}';
    }

    static boolean isColon(char c) {
        return c == ':';
    }

    static boolean isComma(char c) {
        return c == ',';
    }

    static boolean isString(char c) {
        return c == '"';
    }
}
