package app.interpreter;

import app.interpreter.model.Expression;
import app.interpreter.model.Pair;

import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Context {
    Stack<Pair<String, String>> stack;
    private String inString;
    private boolean flag;

    public Context(String inString) {
        stack = new Stack<>();
        this.inString = inString;
    }

    public void evaluate() {
        String str = inString.substring(1, inString.length() - 1).trim();
        parseString(str);
    }

    /*
    "pageInfo": {
         "pageName": "abc",
         "pagePic": "http://example.com/content.jpg"
    }
    "posts": [
         {
              "post_id": "123456789012_123456789012",
              "actor_id": "1234567890",
              "picOfPersonWhoPosted": "http://example.com/photo.jpg",
              "nameOfPersonWhoPosted": "Jane Doe",
              "message": "Sounds cool. Can't wait to see it!",
              "likesCount": "2",
              "comments": [],
              "timeOfPost": "1234567890"
         }
    ]
     */

    private boolean isSimple(String s) {
        Pattern p = Pattern.compile("\\{\\[");
        Matcher m = p.matcher(s);
        return m.matches();
    }

    private void parseString(String str) {
        while (!flag) {
            String left = getLeftSideBeforeColon(str.trim());
            String newString = str.substring(left.length() + 3);
            String right = getRightSideAfterColon(/*str.substring(left.length() + 3)*/ newString.trim());
            Pair<String, String> pair = new Pair<>(left, right);
            stack.push(pair);
        }
        /*System.out.println(left);
        System.out.println(right);*/
    }

    private void parsePair(String st) {

    }

    private String getLeftSideBeforeColon(String string) {
        String str = string.substring(0, string.indexOf(':'));
        String left = str.replaceAll("\"", "").trim();
        inString = string.substring(str.length() + 1);
        return left;
    }

    private String getRightSideAfterColon(String string) {
        String str;
        if (isSimple(string)) {
            str = string.substring(string.indexOf(':') + 1, string.indexOf(',')).trim();
        } else if (string.startsWith("{")){
            str = string.substring(string.indexOf('{') + 1, string.indexOf('}')).trim();
        } else {
            str = string.substring(string.indexOf('[') + 1, string.lastIndexOf(']')).trim();
        }
        if (isSimple(str)) {
            inString = string.substring(str.length() + 1).trim();
            String[] arr = str.split("\\n");
            for (String s : arr) {
                parseString(s);
            }
            return str.replaceAll("\"", "").trim();
        } else if (str.startsWith("{")) {
            parseString(str.substring(1));
        } else if (str.trim().startsWith("[")) {
            parseString(str.trim().substring(1));
        }
        return "";
    }

    public static void main(String[] args) {
        String s = "{\n" +
                "   \"pageInfo\": {\n" +
                "         \"pageName\": \"abc\",\n" +
                "         \"pagePic\": \"http://example.com/content.jpg\"\n" +
                "    }\n" +
                "    \"posts\": [\n" +
                "         {\n" +
                "              \"post_id\": \"123456789012_123456789012\",\n" +
                "              \"actor_id\": \"1234567890\",\n" +
                "              \"picOfPersonWhoPosted\": \"http://example.com/photo.jpg\",\n" +
                "              \"nameOfPersonWhoPosted\": \"Jane Doe\",\n" +
                "              \"message\": \"Sounds cool. Can't wait to see it!\",\n" +
                "              \"likesCount\": \"2\",\n" +
                "              \"comments\": [],\n" +
                "              \"timeOfPost\": \"1234567890\"\n" +
                "         }\n" +
                "    ]\n" +
                "}";
        Context context = new Context(s);
        context.evaluate();
        for (Pair pair : context.stack) {
            System.out.println(pair.getLeft() + " --- " + pair.getRight());
        }
    }
}
