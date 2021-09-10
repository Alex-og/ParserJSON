package app.interpreter;

import app.interpreter.model.*;

import java.util.*;
import java.util.jar.JarOutputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import static java.lang.String.format;
import static java.lang.String.valueOf;

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

    //////////////////////////////////

    private void ddd(String s) {
        List<String> list = new ArrayList<>();
        Map<String, Object> map = new HashMap<>();
        StringTokenizer tokenizer = new StringTokenizer(s, "{[:,]}", true);
        while (tokenizer.hasMoreElements()) {
            list.add(tokenizer.nextToken().trim());
        }
        countTokens(list);

        printList(list);


        List<String> listN = createListFromJsonArray(list);
        String newListName = getNameNewList(list);
        map.put(newListName, listN);

        for (Map.Entry<String, Object> entry : map.entrySet()) {
            System.out.println(entry.getKey() + " -- " + entry.getValue());
        }


        //System.out.println(list.get(7));
        /*byte[] b = list.get(15).getBytes();
        System.out.println(Arrays.toString(b));*/



        printTokens();
    }

    private String getNameNewList(List<String> list) {
        String name = list.get(currentBeginTokenIndex - 2);
        list.remove(currentBeginTokenIndex - 1);
        list.remove(currentBeginTokenIndex - 2);
        currentBeginTokenIndex = 0;
        return name;
    }


    private JsonObject createObject() {
        return null;
    }

    private int currentBeginTokenIndex;
    private int currentEndTokenIndex;

    private List<String> createListFromJsonArray(List<String> list) {
        int counter = TokenType.BEGIN_ARRAY.getTokenCount();
        String tknBegin = TokenType.BEGIN_ARRAY.getTokenType();
        String tknEnd = TokenType.END_ARRAY.getTokenType();
        List<String> newList = null;
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).equals(tknBegin)) {
                counter--;
                if (counter == 0) {
                    currentBeginTokenIndex = i;
                    if (list.get(i + 1).equals(tknEnd)) {
                        newList = new ArrayList<>();
                        currentEndTokenIndex = i + 1;
                    } else {
                        fillList(list, i);
                    }
                }
            }
        }
        removeListItems(list);
        return newList;
    }

    private void removeListItems(List<String> list) {
        Iterator<String> iterator = list.iterator();
        for (int i = currentBeginTokenIndex; i < currentEndTokenIndex; i++) {
            while (iterator.hasNext()) {
                iterator.remove();
            }
        }
        currentBeginTokenIndex = 0;
        currentEndTokenIndex = 0;
    }

    private List<String> fillList(List<String> list, int n) {
        List<String> lst = new ArrayList<>();
        for (int i = n; i < list.size(); i++) {
            if (list.get(i).equals(TokenType.END_ARRAY.getTokenType())) {
                currentEndTokenIndex = i;
                break;
            }
            lst.add(list.get(i));
        }
        return lst;
    }

    private void countTokens(List<String> list) {
        for (TokenType token : TokenType.values()) {
            for (int i = 0; i < list.size(); i++) {
                if (token.getTokenType().equals(list.get(i))) {
                    token.increaseTokenCount();
                }
            }
        }
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
        /*Context context = new Context(s);
        context.evaluate();
        for (Pair pair : context.stack) {
            System.out.println(pair.getLeft() + " --- " + pair.getRight());
        }*/

        Context context = new Context(s);
        context.ddd(s);
    }

    private void printTokens() {
        for (TokenType token : TokenType.values()) {
            System.out.println(token.name() + " " + token.getTokenType() + " " + token.getTokenCount());
        }
    }

    private void printList(List<String> list) {
        for (int i = 0; i < list.size(); i++) {
            System.out.println("index: " + i + " -- " + list.get(i));
        }
    }
}
