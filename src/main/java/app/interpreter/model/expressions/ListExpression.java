package app.interpreter.model.expressions;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Oleksandr Haleta
 */
public class ListExpression implements Expression {

    @Override
    public int interpret(Expression context) {
        return 0;
    }

    /*public List<Object> createList(int index, String string) {
        int idx = index;
        List<Object> list = new ArrayList<>();
        char token = string.charAt(++idx);
        if (tokenType.isBeginArray(token)) {
            createList(idx, string);
        } else if (tokenType.isBeginObject(token)) {
            createObject(idx, string);
        } else if (tokenType.isEndArray(token)) {
            return list;
        } else if (tokenType.isString(token)) {
            String s = createString(idx, string);
        }
        return null;
    }*/

    private String createString(int idx, String string) {
        //while ()
        return null;
    }

    private void createObject(int idx, String string) {
    }

}
/*
{
"posts": [
  "post_id": "123456789012_123456789012",
  "actor_id": "1234567890"
]
"person": [
  "nameOfPersonWhoPosted": "Jane Doe",
  "message": "Sounds cool. Can't wait to see it!"
]
}
 */