package app.interpreter.model;

/**
 * @author Oleksandr Haleta
 */

public enum TokenType {

    BEGIN_OBJECT("{"),
    END_OBJECT("}"),
    BEGIN_ARRAY("["),
    END_ARRAY("]"),
    SEP_COLON(":"),
    SEP_COMMA(",");

    private final String tokenType;
    private int tokenCount;


    TokenType(String tokenType) {
        this.tokenType = tokenType;
    }

    public int getTokenCount() {
        return tokenCount;
    }

    public void increaseTokenCount() {
        ++tokenCount;
    }

    public void decreaseTokenCount() {
        --tokenCount;
    }

    public String getTokenType() {
        return tokenType;
    }
}
